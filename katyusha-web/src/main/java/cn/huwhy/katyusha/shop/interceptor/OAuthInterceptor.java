package cn.huwhy.katyusha.shop.interceptor;

import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.mp.MpConfigUtil;
import cn.huwhy.katyusha.shop.util.RequestUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;

@Component
public class OAuthInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MpConfigUtil mpConfigUtil;
    @Value("${common.base-url:}")
    private String baseUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (request.getSession().getAttribute("S_MEMBER") == null) {
            if (RequestUtil.isAjax(request)) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                try {
                    String oauth2Url = getOauth2Url(URLEncoder.encode(RequestUtil.getReferer(request), "UTF-8"));
                    PrintWriter out = response.getWriter();
                    out.write(JSON.toJSONString(Json.REDIRECT().setMessage("302").setUrl(oauth2Url)));
                } catch (Exception e) {
                    logger.error("", e);
                }
            } else {
                String oauth2Url = getOauth2Url(URLEncoder.encode(getRequestURI(request), "UTF-8"));
                logger.debug("oauth2Url: {}", oauth2Url);
                response.sendRedirect(oauth2Url);
            }
            return false;
        }
        return true;
    }

    private String getRequestURI(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(baseUrl);
        sb.append(request.getRequestURI());
        if (!Strings.isNullOrEmpty(request.getQueryString())) {
            sb.append("?").append(request.getQueryString().replace("isappinstalled=0", "").replace("isappinstalled=1", ""));
        }
        return sb.toString();
    }


    protected String getOauth2Url(String state) {
        return mpConfigUtil.getOAuth2Url(baseUrl + "/api/oauth_base.html", true, state);
    }
}
