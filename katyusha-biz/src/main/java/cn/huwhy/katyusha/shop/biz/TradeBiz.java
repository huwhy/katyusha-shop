package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.katyusha.shop.biz.mgr.OrderManager;
import cn.huwhy.katyusha.shop.biz.mgr.TradeManager;
import cn.huwhy.katyusha.shop.model.Order;
import cn.huwhy.katyusha.shop.model.RefundStatus;
import cn.huwhy.katyusha.shop.model.ShoppingCart;
import cn.huwhy.katyusha.shop.model.Trade;
import cn.huwhy.katyusha.shop.model.TradeStatus;
import com.google.common.collect.Collections2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TradeBiz {

    @Autowired
    private ShoppingCartBiz shoppingCartBiz;
    @Autowired
    private TradeManager tradeManager;
    @Autowired
    private OrderManager orderManager;

    @Transactional
    public void add(Trade trade) {
        List<ShoppingCart> carts = shoppingCartBiz.getShoppingCarts(trade.getMemberId(), trade.getCartIds());
        List<Order> orders = new ArrayList<>(carts.size());
        trade.setId(tradeManager.nextId());
        int totalAmount = 0;
        int totalPayment = 0;
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
        trade.setTotalAmount(totalAmount);
        trade.setTotalPayment(totalPayment);
        trade.setStatus(TradeStatus.CREATED);
        orderManager.save(orders);
        tradeManager.save(trade);
    }

    public Trade get(long id) {
        Trade trade = tradeManager.get(id);
        trade.setOrders(orderManager.getByIds(trade.getOrderIds()));
        return trade;
    }

}
