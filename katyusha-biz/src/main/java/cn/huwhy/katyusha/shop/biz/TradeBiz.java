package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.common.util.CollectionUtil;
import cn.huwhy.katyusha.shop.biz.mgr.ItemManager;
import cn.huwhy.katyusha.shop.biz.mgr.MpPayManager;
import cn.huwhy.katyusha.shop.biz.mgr.OrderManager;
import cn.huwhy.katyusha.shop.biz.mgr.SkuManager;
import cn.huwhy.katyusha.shop.biz.mgr.TradeManager;
import cn.huwhy.katyusha.shop.dao.po.MpPayPo;
import cn.huwhy.katyusha.shop.model.Item;
import cn.huwhy.katyusha.shop.model.Order;
import cn.huwhy.katyusha.shop.model.RefundStatus;
import cn.huwhy.katyusha.shop.model.ShoppingCart;
import cn.huwhy.katyusha.shop.model.Sku;
import cn.huwhy.katyusha.shop.model.Trade;
import cn.huwhy.katyusha.shop.model.TradeStatus;
import com.google.common.collect.Collections2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;

@Service
public class TradeBiz {

    @Autowired
    private ShoppingCartBiz shoppingCartBiz;
    @Autowired
    private TradeManager tradeManager;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private MpPayManager mpPayManager;
    @Autowired
    private ItemManager itemManager;
    @Autowired
    private SkuManager skuManager;

    @Transactional
    public void add(Trade trade) {
        trade.setId(tradeManager.nextId());
        int totalAmount = 0;
        int totalPayment = 0;
        if (CollectionUtil.isNotEmpty(trade.getCartIds())) {
            List<ShoppingCart> carts = shoppingCartBiz.getShoppingCarts(trade.getMemberId(), trade.getCartIds());
            List<Order> orders = new ArrayList<>(carts.size());
            for (ShoppingCart cart : carts) {
                Order order = new Order();
                order.setId(orderManager.nextId());
                order.setTid(trade.getId());
                order.setItemId(cart.getItemId());
                order.setSkuId(cart.getSkuId());
                order.setTitle(cart.getItem().getTitle());
                order.setSpec(cart.getSku().getSpec());
                order.setBarcode(cart.getSku().getBarcode());
                order.setNum(cart.getNum());
                order.setPrice(cart.getSku().getPrice());
                order.setTotalAmount(order.getNum() * order.getPrice());
                order.setPayment(order.getNum() * order.getPrice());
                order.setStatus(TradeStatus.CREATED);
                order.setRefundStatus(RefundStatus.NO_VALUE);
                order.setEvaluate(false);
                order.setCommissionFee(cart.getItem().getCommissionRate() * order.getPayment() / 100);
                order.setModified(new Date());
                order.setCreated(new Date());
                orders.add(order);
                totalAmount += order.getTotalAmount();
                totalPayment += order.getPayment();
            }
            trade.setOrders(orders);
            trade.setOrderIds(Collections2.transform(orders, Order::getId));
            shoppingCartBiz.deleteByIds(trade.getCartIds());
        } else if (trade.getCart() != null) {
            Sku sku = skuManager.get(trade.getCart().getSkuId());
            Item item = itemManager.get(sku.getItemId());
            Order order = new Order();
            order.setId(orderManager.nextId());
            order.setTid(trade.getId());
            order.setItemId(item.getId());
            order.setSkuId(sku.getId());
            order.setTitle(item.getTitle());
            order.setSpec(sku.getSpec());
            order.setBarcode(sku.getBarcode());
            order.setNum(trade.getCart().getNum());
            order.setPrice(sku.getPrice());
            order.setTotalAmount(order.getNum() * order.getPrice());
            order.setPayment(order.getNum() * order.getPrice());
            order.setStatus(TradeStatus.CREATED);
            order.setRefundStatus(RefundStatus.NO_VALUE);
            order.setEvaluate(false);
            order.setCommissionFee(item.getCommissionRate() * order.getPayment() / 100);
            order.setModified(new Date());
            order.setCreated(new Date());
            trade.setOrders(singletonList(order));
            trade.setOrderIds(singletonList(order.getId()));
            totalAmount += order.getTotalAmount();
            totalPayment += order.getPayment();
        }
        trade.setTotalAmount(totalAmount);
        trade.setTotalPayment(totalPayment);
        trade.setStatus(TradeStatus.CREATED);
        orderManager.save(trade.getOrders());
        tradeManager.save(trade);
    }

    public Trade get(long id) {
        Trade trade = tradeManager.get(id);
        trade.setOrders(orderManager.getByIds(trade.getOrderIds()));
        if (TradeStatus.WAIT_PAY.equals(trade.getStatus())) {
            trade.setPrepayId(mpPayManager.getPrepayId(id));
        }
        return trade;
    }

    @Transactional
    public void prepay(long id, String prepayId) {
        Trade trade = get(id);
        if (TradeStatus.CREATED.equals(trade.getStatus())) {
            tradeManager.prepay(id);
            orderManager.prepay(trade.getOrderIds());
            mpPayManager.prepay(id, prepayId);
        }
    }

    @Transactional
    public void paid(long id, String transactionId) {
        Trade trade = get(id);
        if (TradeStatus.WAIT_PAY.equals(trade.getStatus())) {
            tradeManager.paid(id);
            orderManager.paid(trade.getOrderIds());
            mpPayManager.paid(id, transactionId);
        }
    }
}
