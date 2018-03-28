package cn.huwhy.katyusha.shop.mp;

import cn.huwhy.common.util.StringUtil;
import cn.huwhy.katyusha.shop.biz.TradeBiz;
import cn.huwhy.wx.sdk.aes.MpConfig;
import cn.huwhy.wx.sdk.aes.WxCryptUtil;
import cn.huwhy.wx.sdk.model.WxPayResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/wechat/mp")
public class MpPayController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String SUCCESS_RESULT = "<xml>\n" +
            "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
            "</xml>";
    private static final String FAIL_RESULT = "<xml>\n" +
            "<return_code><![CDATA[FAIL]]></return_code>\n" +
            "<return_msg>![CDATA[FAIL_REPLACE_KEY]]</return_msg>\n" +
            "</xml>";
    private static final String FAIL_REPLACE_KEY = "FAIL_REPLACE_KEY";

    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private TradeBiz tradeBiz;

    @RequestMapping(value = "pay.client", method = {GET, POST})
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取微信回调xml
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null)
                sb.append(line);
            logger.debug("pay notice: {}", sb);
            WxPayResult result = WxCryptUtil.transform(sb.toString());

            // 验证签名信息
            if (!checkSign(result))
                return;

            logger.info("WxMpPayController - payClient, receive = {}", result);


            if (result.ok()) {
                Long tid = Long.valueOf(result.getOut_trade_no());
                String transactionId = result.getTransaction_id();
                tradeBiz.paid(tid, transactionId);
                printResponse(response, SUCCESS_RESULT);
            } else
                printResponse(response, FAIL_RESULT.replace(FAIL_REPLACE_KEY, ""));
        } catch (Exception e) {
            logger.error("WxMpPayController - payClient - exception", e);
        }
    }

    private void printResponse(HttpServletResponse response, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            if (StringUtil.isNotEmpty(message))
                writer.write(message);
        } catch (Exception e) {
            logger.error("MpPayController - printResponse - exception", e);
        } finally {
            try {
                if (null != writer)
                    writer.close();
            } catch (Exception ignore) {
            }
        }
    }

    private Boolean checkSign(WxPayResult payResult) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        if (StringUtil.isNotEmpty(payResult.getReturn_code()))
            parameters.put("return_code", payResult.getReturn_code());
        if (StringUtil.isNotEmpty(payResult.getReturn_msg()))
            parameters.put("return_msg", payResult.getReturn_msg());
        if (StringUtil.isNotEmpty(payResult.getAppid()))
            parameters.put("appid", payResult.getAppid());
        if (StringUtil.isNotEmpty(payResult.getMch_id()))
            parameters.put("mch_id", payResult.getMch_id());
        if (StringUtil.isNotEmpty(payResult.getDevice_info()))
            parameters.put("device_info", payResult.getDevice_info());
        if (StringUtil.isNotEmpty(payResult.getNonce_str()))
            parameters.put("nonce_str", payResult.getNonce_str());
        if (StringUtil.isNotEmpty(payResult.getResult_code()))
            parameters.put("result_code", payResult.getResult_code());
        if (StringUtil.isNotEmpty(payResult.getErr_code()))
            parameters.put("err_code", payResult.getErr_code());
        if (StringUtil.isNotEmpty(payResult.getErr_code_des()))
            parameters.put("err_code_des", payResult.getErr_code_des());
        if (StringUtil.isNotEmpty(payResult.getOpenid()))
            parameters.put("openid", payResult.getOpenid());
        if (StringUtil.isNotEmpty(payResult.getIs_subscribe()))
            parameters.put("is_subscribe", payResult.getIs_subscribe());
        if (StringUtil.isNotEmpty(payResult.getTrade_type()))
            parameters.put("trade_type", payResult.getTrade_type());
        if (StringUtil.isNotEmpty(payResult.getBank_type()))
            parameters.put("bank_type", payResult.getBank_type());
        if (StringUtil.isNotEmpty(payResult.getTotal_fee()))
            parameters.put("total_fee", payResult.getTotal_fee());
        if (StringUtil.isNotEmpty(payResult.getFee_type()))
            parameters.put("fee_type", payResult.getFee_type());
        if (StringUtil.isNotEmpty(payResult.getCash_fee()))
            parameters.put("cash_fee", payResult.getCash_fee());
        if (StringUtil.isNotEmpty(payResult.getCash_fee_type()))
            parameters.put("cash_fee_type", payResult.getCash_fee_type());
        if (StringUtil.isNotEmpty(payResult.getCoupon_fee()))
            parameters.put("coupon_fee", payResult.getCoupon_fee());
        if (StringUtil.isNotEmpty(payResult.getCoupon_count()))
            parameters.put("coupon_count", payResult.getCoupon_count());
        if (StringUtil.isNotEmpty(payResult.getTransaction_id()))
            parameters.put("transaction_id", payResult.getTransaction_id());
        if (StringUtil.isNotEmpty(payResult.getOut_trade_no()))
            parameters.put("out_trade_no", payResult.getOut_trade_no());
        if (StringUtil.isNotEmpty(payResult.getAttach()))
            parameters.put("attach", payResult.getAttach());
        if (StringUtil.isNotEmpty(payResult.getTime_end()))
            parameters.put("time_end", payResult.getTime_end());

        return WxCryptUtil.createSign(parameters, mpConfig.getPartnerKey()).equals(payResult.getSign());
    }
}
