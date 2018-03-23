package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.biz.ItemBiz;
import cn.huwhy.katyusha.shop.biz.ShoppingCartBiz;
import cn.huwhy.katyusha.shop.biz.TradeBiz;
import cn.huwhy.katyusha.shop.model.Order;
import cn.huwhy.katyusha.shop.model.Sku;
import cn.huwhy.katyusha.shop.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/trade")
public class TradeController extends BaseController {

    @Autowired
    private ItemBiz itemBiz;
    @Autowired
    private ShoppingCartBiz shoppingCartBiz;
    @Autowired
    private TradeBiz tradeBiz;

    @RequestMapping(method = POST)
    public Json add(@RequestBody Trade trade, HttpServletRequest request) {
        trade.setMemberId(getMemberId(request));
        tradeBiz.add(trade);
        return Json.SUCCESS().setData(trade);
    }

    @RequestMapping(value = "{id:\\d++}", method = GET)
    public Json get(@PathVariable("id") long id, HttpServletRequest request) {
        Trade trade = tradeBiz.get(id);
        if (trade == null || trade.getMemberId() != getMemberId(request)) {
            return Json.ERROR().setMessage("订单不存在!");
        }
        for (Order order : trade.getOrders()) {
            Sku sku = itemBiz.getSku(order.getSkuId());
            order.setImg(sku.getImg());
        }
        return Json.SUCCESS().setData(trade);
    }
}
