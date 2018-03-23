package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.mp.MpConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mp")
public class MpController extends BaseController {

    @Autowired
    private MpConfigUtil mpConfigUtil;

    @RequestMapping("js-sign")
    public Json jsSign(String url) {
        return Json.SUCCESS().setData(mpConfigUtil.createJsapiSign(url));
    }

}
