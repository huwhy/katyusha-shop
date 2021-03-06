package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.common.json.JsonUtil;
import cn.huwhy.katyusha.shop.biz.MemberBiz;
import cn.huwhy.katyusha.shop.model.Member;
import cn.huwhy.katyusha.shop.model.MpUser;
import cn.huwhy.katyusha.shop.mp.MpConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/api")
public class OauthController extends BaseController {
    @Autowired
    private MpConfigUtil mpConfigUtil;
    @Autowired
    private MemberBiz memberBiz;
    @Value("${common.base-url:}")
    private String baseUrl;

    @RequestMapping(value = "/oauth.html", method = {RequestMethod.GET})
    public String oauth(HttpServletRequest request,
                        @RequestParam(name = "code") String code,
                        @RequestParam(name = "state") String state) {
        logger.debug("oauth: code-{}, state-{}", code, state);
        MpUser mpUser = mpConfigUtil.getOAuth2UserInfo(code);
        Member member = memberBiz.saveForMp(mpUser);
        logger.debug("oauth-user: mpUser-{}", JsonUtil.toJson(mpUser));
        setMember(request, member, mpUser);
        return "redirect:" + state;
    }

    @RequestMapping(value = "/oauth_base.html", method = {RequestMethod.GET})
    public String oauthBaseUser(HttpServletRequest request,
                                @RequestParam(name = "code") String code,
                                @RequestParam(name = "state") String state) throws UnsupportedEncodingException {
        logger.info("oauth: code-{}, state-{}", code, state);
        String openId = mpConfigUtil.getOAuth2OpenId(code);
        MpUser mpUser = memberBiz.getMpUser(openId);
        logger.info("oauth-user: mpUser-{}", JsonUtil.toJson(mpUser));
        if (mpUser == null) {
            return "redirect:" + mpConfigUtil.getOAuth2Url(baseUrl + "/api/oauth.html", false, state);
        } else {
            Member member = memberBiz.get(mpUser.getMemberId());
            setMember(request, member, mpUser);
            return "redirect:" + state;
        }
    }
}
