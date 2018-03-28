package cn.huwhy.katyusha.shop.mp;

import cn.huwhy.common.util.CollectionUtil;
import cn.huwhy.common.util.RandomUtil;
import cn.huwhy.katyusha.shop.biz.MemberBiz;
import cn.huwhy.katyusha.shop.model.MpUser;
import cn.huwhy.wx.sdk.aes.MpConfig;
import cn.huwhy.wx.sdk.aes.SHA1;
import cn.huwhy.wx.sdk.aes.WxCryptUtil;
import cn.huwhy.wx.sdk.api.AccessTokenApi;
import cn.huwhy.wx.sdk.api.JsApiTicketApi;
import cn.huwhy.wx.sdk.api.UserApi;
import cn.huwhy.wx.sdk.model.AccessToken;
import cn.huwhy.wx.sdk.model.JsTicket;
import cn.huwhy.wx.sdk.model.UserInfo;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class MpConfigUtil {

    protected final Object accessTokenLock = new Object();
    protected final Object jsapiTicketLock = new Object();
    /** 不弹出授权页面，直接跳转，只能获取用户openid */
    public static final String OAUTH2_SCOPE_BASE = "snsapi_base";
    /** 弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息 */
    public static final String OAUTH2_SCOPE_USER_INFO = "snsapi_userinfo";

    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private MemberBiz memberBiz;

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

    public MpUser getOAuth2UserInfo(String code) {
        AccessToken token = AccessTokenApi.getUserAccessToken(mpConfig.getAppId(), mpConfig.getSecret(), code);
        UserInfo userInfo = UserApi.getSnsUserInfo(token.getAccessToken(), token.getOpenId());
        return toMpUser(userInfo);
    }

    public String getOAuth2OpenId(String code) {
        AccessToken token = AccessTokenApi.getUserAccessToken(mpConfig.getAppId(), mpConfig.getSecret(), code);
        return token.getOpenId();
    }

    public MpUser toMpUser(UserInfo userInfo) {
        MpUser mpUser = new MpUser();
        mpUser.setOpenId(userInfo.getOpenId());
        mpUser.setUnionId(userInfo.getUnionId());
        mpUser.setSubscribe(userInfo.getSubscribe() != 0);
        mpUser.setNickname(userInfo.getNick());
        mpUser.setSex(userInfo.getSex());
        mpUser.setLanguage(userInfo.getLang());
        mpUser.setCity(userInfo.getCity());
        mpUser.setProvince(userInfo.getProvince());
        mpUser.setCountry(userInfo.getCountry());
        mpUser.setHeadImgUrl(userInfo.getHeadImgUrl());
        mpUser.setSubscribeTime(userInfo.getSubscribeTime());
        mpUser.setRemark(userInfo.getRemark());
        mpUser.setGroupId(userInfo.getGroupid());
        if (CollectionUtil.isNotEmpty(userInfo.getTagIds())) {
            mpUser.setTagIds(Joiner.on(',').join(userInfo.getTagIds()));
        }
        mpUser.setSubscribeScene(userInfo.getSubscribeScene());
        mpUser.setQrScene(userInfo.getQrScene());
        mpUser.setQrSceneStr(userInfo.getQrSceneStr());
        mpUser.setUpdated(new Date());
        return mpUser;
    }

    public String getOAuth2Url(String redirectUrl, boolean baseScope, String state) {
        String scope = baseScope ? OAUTH2_SCOPE_BASE : OAUTH2_SCOPE_USER_INFO;
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
        url += "appid=" + mpConfig.getAppId();
        try {
            url += "&redirect_uri=" + URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url += "&response_type=code";
        url += "&scope=" + scope;
        if (state != null) {
            url += "&state=" + state;
        }
        url += "#wechat_redirect";
        return url;
    }

    public Map<String, String> generalJsPay(String prepayId) throws Exception {
        Map<String, String> payInfo = new HashMap<>();
        payInfo.put("appId", mpConfig.getAppId());
        payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        payInfo.put("nonceStr", System.currentTimeMillis() + "");
        payInfo.put("package", "prepay_id=" + prepayId);
        payInfo.put("signType", "MD5");
        payInfo.put("paySign", WxCryptUtil.createSign(payInfo, mpConfig.getPartnerKey()));
        return payInfo;
    }

}
