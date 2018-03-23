package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.biz.MemberBiz;
import cn.huwhy.katyusha.shop.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/account")
public class AccountController extends BaseController {
    @Autowired
    private MemberBiz memberBiz;

    @RequestMapping(method = GET)
    public Json account(HttpServletRequest request) {
        Account account = memberBiz.getAccount(getMemberId(request));
        return Json.SUCCESS().setData(account);
    }
}
