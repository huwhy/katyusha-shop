package cn.huwhy.katyusha.shop.mp;

import cn.huwhy.common.util.RandomUtil;
import cn.huwhy.wx.sdk.aes.MpConfig;
import cn.huwhy.wx.sdk.aes.SHA1;
import cn.huwhy.wx.sdk.api.AccessTokenApi;
import cn.huwhy.wx.sdk.api.JsApiTicketApi;
import cn.huwhy.wx.sdk.model.AccessToken;
import cn.huwhy.wx.sdk.model.JsTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class MpConfigUtil {

    protected final Object accessTokenLock = new Object();
    protected final Object jsapiTicketLock = new Object();

    @Autowired
    private MpConfig mpConfig;

    public String getAccessToken() {
        if (mpConfig.isAccessTokenExpired()) {
            synchronized (accessTokenLock) {
                if (mpConfig.isAccessTokenExpired()) {
                    AccessToken ak = AccessTokenApi.getAppAccessToken(mpConfig.getAppId(), mpConfig.getSecret());
                    mpConfig.updateAccessToken(ak);
                }
            }
        }
        return mpConfig.getAccessToken();
    }

    public String getJsapiTicket() {
        if (mpConfig.isJsapiTicketExpired()) {
            synchronized (jsapiTicketLock) {
                if (mpConfig.isJsapiTicketExpired()) {
                    JsTicket jsTicket = JsApiTicketApi.getTicked(getAccessToken());
                    mpConfig.updateJsapiTicket(jsTicket.getTicket(), jsTicket.getExpiresIn());
                }
            }
        }
        return mpConfig.getJsapiTicket();
    }

    public WxJsapiSign createJsapiSign(String url) {
        long timestamp = System.currentTimeMillis() / 1000;
        String nonce = RandomUtil.getRandomString(8);
        String jsTicket = getJsapiTicket();
        try {
            String signature = SHA1.genWithAmple(
                    "jsapi_ticket=" + jsTicket,
                    "noncestr=" + nonce,
                    "timestamp=" + timestamp,
                    "url=" + url
            );
            WxJsapiSign sign = new WxJsapiSign();
            sign.setAppId(mpConfig.getAppId());
            sign.setTimestamp(timestamp);
            sign.setNonce(nonce);
            sign.setSignature(signature);
            sign.setUrl(url);
            return sign;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
