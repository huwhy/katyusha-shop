package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.common.json.JsonUtil;
import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.biz.ItemBiz;
import cn.huwhy.katyusha.shop.biz.TradeBiz;
import cn.huwhy.katyusha.shop.model.Order;
import cn.huwhy.katyusha.shop.model.Sku;
import cn.huwhy.katyusha.shop.model.Trade;
import cn.huwhy.katyusha.shop.util.RequestUtil;
import cn.huwhy.wx.sdk.aes.MpConfig;
import cn.huwhy.wx.sdk.api.MpOrderApi;
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
    private TradeBiz tradeBiz;
    @Autowired
    private MpConfig mpConfig;

    @RequestMapping(method = POST)
    public Json add(@RequestBody Trade trade, HttpServletRequest request) {
        trade.setMemberId(getMemberId(request));
        tradeBiz.add(trade);
        MpOrderApi.MpOrderParam param = new MpOrderApi.MpOrderParam();
        param.setAppId(mpConfig.getAppId());
        param.setMchId(mpConfig.getPartnerId());
        param.setMchKey(mpConfig.getPartnerKey());
        param.setOutTradeNo(Long.toString(trade.getId()));
        param.setBody(trade.getOrders().get(0).getTitle());
        param.setTotalFee(trade.getTotalPayment());
        param.setSpbillCreateIp(RequestUtil.getRemoteIp(request));
        param.setOpenId(getOpenId(request));
        param.setNotifyUrl(mpConfig.getNotifyUrl());
        MpOrderApi.MpOrderResult orderResult = MpOrderApi.orderByMp(param);
        logger.info("trade prepay: {}", JsonUtil.toJson(orderResult));
        if (orderResult.isOk()) {
            tradeBiz.prepay(trade.getId(), orderResult.getPrepayId());
        }
        return Json.SUCCESS().setData(orderResult).setMessage(Long.toString(trade.getId()));
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
