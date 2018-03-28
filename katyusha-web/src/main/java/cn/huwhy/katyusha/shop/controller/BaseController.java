package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.katyusha.shop.model.Member;
import cn.huwhy.katyusha.shop.model.MpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected void setMember(HttpServletRequest request, Member member, MpUser mpUser) {
        request.getSession().setAttribute("S_MEMBER", member);
        request.getSession().setAttribute("S_MP_USER", mpUser);
    }

    protected Member getMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute("S_MEMBER");
    }

    protected MpUser getMpUser(HttpServletRequest request) {
        return (MpUser) request.getSession().getAttribute("S_MP_USER");
    }

    protected long getMemberId(HttpServletRequest request) {
        return getMember(request).getId();
    }

    protected String getOpenId(HttpServletRequest request) {
        return getMpUser(request).getOpenId();
    }

}
