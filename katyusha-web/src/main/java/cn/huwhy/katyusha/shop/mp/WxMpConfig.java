package cn.huwhy.katyusha.shop.mp;

import cn.huwhy.wx.sdk.aes.MpConfig;
import cn.huwhy.wx.sdk.api.HttpClientUtil;
import cn.huwhy.wx.sdk.api.WXPayApi;
import cn.huwhy.wx.sdk.listener.EventHandler;
import cn.huwhy.wx.sdk.message.Message;
import com.google.common.base.Strings;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

@Configuration
public class WxMpConfig {

    @Bean
    public MpConfig mpConfig(@Value("${mp.appId}") String appId,
                             @Value("${mp.secret}") String secret,
                             @Value("${mp.token}") String token,
                             @Value("${mp.aesKey}") String aesKey,
                             @Value("${mp.partnerId:}") String partnerId,
                             @Value("${mp.partnerKey:}") String partnerKey,
                             @Value("${mp.certPath:}") String certPath,
                             @Value("${mp.certPassword:}") String certPassword,
                             @Value("${mp.notifyUrl:}") String notifyUrl) {
        MpConfig config = new MpConfig();
        config.setAppId(appId);
        config.setSecret(secret);
        config.setToken(token);
        config.setAesKey2(aesKey);
        config.setPartnerId(partnerId);
        config.setPartnerKey(partnerKey);
        config.setNotifyUrl(notifyUrl);
        if (!Strings.isNullOrEmpty(certPath) && !Strings.isNullOrEmpty(certPassword)) {
            FileInputStream fileInputStream = null;
            try {
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                fileInputStream = new FileInputStream(new File(certPath));
                keyStore.load(fileInputStream, certPassword.toCharArray());

                SSLContext sslcontext = SSLContexts.custom()
                        .loadKeyMaterial(keyStore, certPassword.toCharArray())
                        .build();
                config.setSSLContext(sslcontext);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != fileInputStream)
                    try {
                        fileInputStream.close();
                    } catch (IOException ignore) {
                    }
            }
            final HttpClientBuilder builder = HttpClients.custom();
            if (config.getSSLContext() != null){
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                        config.getSSLContext(),
                        new String[] { "TLSv1" },
                        null,
                        SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
                builder.setSSLSocketFactory(sslsf);
            }
            CloseableHttpClient httpClient = builder.build();
            HttpClientUtil.setHttpClient(httpClient);
            WXPayApi.setHttpClient(httpClient);
        }
        return config;
    }

    @Autowired
    private SubscribeListener subscribeListener;

    @Bean
    @Autowired
    public EventHandler eventHandler(TextMsgListener textListener) {
        EventHandler handler = new EventHandler();
        handler.register(Message.EVENT_KEYS.TEXT_MSG, textListener);
        handler.register(Message.EVENT_KEYS.SUBSCRIBE_EVENT, subscribeListener);
        return handler;
    }
}
