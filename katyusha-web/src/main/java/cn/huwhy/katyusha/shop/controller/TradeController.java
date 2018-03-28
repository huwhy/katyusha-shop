package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.common.json.JsonUtil;
import cn.huwhy.common.util.StringUtil;
import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.biz.ItemBiz;
import cn.huwhy.katyusha.shop.biz.TradeBiz;
import cn.huwhy.katyusha.shop.model.Order;
import cn.huwhy.katyusha.shop.model.Sku;
import cn.huwhy.katyusha.shop.model.Trade;
import cn.huwhy.katyusha.shop.model.TradeStatus;
import cn.huwhy.katyusha.shop.mp.MpConfigUtil;
import cn.huwhy.katyusha.shop.util.RequestUtil;
import cn.huwhy.wx.sdk.aes.MpConfig;
import cn.huwhy.wx.sdk.api.MpOrderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

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
    @Autowired
    private MpConfigUtil mpConfigUtil;

    @RequestMapping(method = POST)
    public Json add(@RequestBody Trade trade, HttpServletRequest request) throws Exception {
        trade.setMemberId(getMemberId(request));
        tradeBiz.add(trade);
        Map<String, String> jsPay = generalPrepay(trade, request);
        Trade dbTrade = tradeBiz.get(trade.getId());
        dbTrade.setPayParams(jsPay);
        return Json.SUCCESS().setData(dbTrade);
    }

    @RequestMapping(value = "{id:\\d++}", method = GET)
    public Json get(@PathVariable("id") long id, HttpServletRequest request) throws Exception {
        Trade trade = tradeBiz.get(id);
        if (trade == null || trade.getMemberId() != getMemberId(request)) {
            return Json.ERROR().setMessage("订单不存在!");
        }
        for (Order order : trade.getOrders()) {
            Sku sku = itemBiz.getSku(order.getSkuId());
            order.setImg(sku.getImg());
        }
        if (trade.getStatus().equals(TradeStatus.CREATED)) {
            Map<String, String> jsPay = generalPrepay(trade, request);
            trade.setPayParams(jsPay);
        } else if (trade.getStatus().equals(TradeStatus.WAIT_PAY)) {
            Map<String, String> jsPay = mpConfigUtil.generalJsPay(trade.getPrepayId());
            trade.setPayParams(jsPay);
        }
        return Json.SUCCESS().setData(trade);
    }

    private Map<String, String> generalPrepay(Trade trade, HttpServletRequest request) throws Exception {
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
        Map<String, String> map;
        if (StringUtil.isNotEmpty(orderResult.getPrepayId())) {
            tradeBiz.prepay(trade.getId(), orderResult.getPrepayId());
            map = mpConfigUtil.generalJsPay(orderResult.getPrepayId());
            return map;
        }
        return null;
    }
}
