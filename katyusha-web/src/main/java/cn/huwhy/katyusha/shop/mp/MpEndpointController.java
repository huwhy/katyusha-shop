package cn.huwhy.katyusha.shop.mp;

import cn.huwhy.common.util.StringUtil;
import cn.huwhy.wx.sdk.aes.AesException;
import cn.huwhy.wx.sdk.aes.MpConfig;
import cn.huwhy.wx.sdk.aes.WXBizMsgCrypt;
import cn.huwhy.wx.sdk.aes.WxCryptUtil;
import cn.huwhy.wx.sdk.listener.EventHandler;
import cn.huwhy.wx.sdk.model.Command;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/mp")
public class MpEndpointController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final static String ERROR_MSG = "非法请求";

    @Autowired
    private MpConfig mpConfig;

    @Autowired
    private EventHandler eventHandler;

    @RequestMapping("endpoint")
    public void endpoint(HttpServletRequest request, HttpServletResponse response) throws AesException, NoSuchAlgorithmException, IOException {
        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");
        String echostr = request.getParameter("echostr");
        if (!Strings.isNullOrEmpty(echostr)) {
            printResponse(response, echostr);
            return;
        }
        logger.debug("endpoint-params: {}, {}, {}, {}", signature, nonce, timestamp, echostr);
        if (!WXBizMsgCrypt.check(mpConfig.getToken(), signature, timestamp, nonce)) {
            printResponse(response, ERROR_MSG);
            return;
        }
        // 处理接收消息
        ServletInputStream in = request.getInputStream();
        StringBuilder xmlMsg = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            xmlMsg.append(new String(b, 0, n, "iso8859-1"));
        }
        logger.debug("receive-xml: {}", xmlMsg);
        Command command = WxCryptUtil.transform(mpConfig, signature, timestamp, nonce, xmlMsg.toString());
        String replyMsg = eventHandler.handler(command);
        logger.debug("reply-xml: {}", replyMsg);
//        String encryptMsg = WxCryptUtil.encryptMsg(wxBizMsgCrypt, replyMsg, timestamp, nonce);
        printResponse(response, Strings.isNullOrEmpty(replyMsg) ? "success" : replyMsg);
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
            logger.error("WxMpEndpointController - printResponse - exception", e);
        } finally {
            try {
                if (null != writer)
                    writer.close();
            } catch (Exception ignore) {
            }
        }
    }

}
