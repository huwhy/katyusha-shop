package cn.huwhy.katyusha.shop.mp;

import cn.huwhy.wx.sdk.aes.AesException;
import cn.huwhy.wx.sdk.aes.WXBizMsgCrypt;
import cn.huwhy.wx.sdk.listener.EventHandler;
import cn.huwhy.wx.sdk.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MpConfig {

    @Bean
    public WXBizMsgCrypt wxBizMsgCrypt(@Value("${mp.appId}") String appId,
                                       @Value("${mp.aesKey}") String aesKey,
                                       @Value("${mp.token}") String token) throws AesException {
        return new WXBizMsgCrypt(token, aesKey, appId);
    }

    @Bean
    @Autowired
    public EventHandler eventHandler(TextMsgListener textListener) {
        EventHandler handler = new EventHandler();
        handler.register(Message.EVENT_KEYS.TEXT_MSG, textListener);
        return handler;
    }
}
