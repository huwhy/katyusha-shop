package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.biz.MemberBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/member")
public class MemberController extends BaseController {
    @Autowired
    private MemberBiz memberBiz;

    @RequestMapping(method = GET)
    public Json member(HttpServletRequest request) {
        return Json.SUCCESS().setData(getMember(request));
    }

    @RequestMapping(value = "login", method = GET)
    public Json login(HttpServletRequest request) {
        setMember(request, memberBiz.get(1000));
        return Json.SUCCESS();
    }
}
