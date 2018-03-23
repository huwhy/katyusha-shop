package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.katyusha.shop.model.Member;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {

    protected void setMember(HttpServletRequest request, Member member) {
        request.getSession().setAttribute("S_MEMBER", member);
    }

    protected Member getMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute("S_MEMBER");
    }

    protected long getMemberId(HttpServletRequest request) {
        return getMember(request).getId();
    }

}
