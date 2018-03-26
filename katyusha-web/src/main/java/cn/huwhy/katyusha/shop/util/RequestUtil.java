package cn.huwhy.katyusha.shop.util;

import cn.huwhy.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    /**
     * AJAX请求
     */
    private static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /**
     * Json请求
     */
    private static boolean isJsonRequest(HttpServletRequest request) {
        String type = request.getHeader("Accept");
        if (StringUtil.isEmpty(type))
            return false;
        return type.toLowerCase().contains("application/json");
    }

    public static boolean isAjax(HttpServletRequest request) {
        return isAjaxRequest(request) || isJsonRequest(request);
    }

    public static String getReferer(HttpServletRequest request) {
        return request.getHeader("referer");
    }

    public static String getRemoteIp(HttpServletRequest request) {
        return request.getHeader("remote-ip");
    }

}
