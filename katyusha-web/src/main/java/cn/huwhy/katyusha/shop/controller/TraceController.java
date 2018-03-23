package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TraceController extends BaseController{

    @RequestMapping("/mid")
    public Json mid(long mid) {
        return Json.SUCCESS().setData(mid);
    }
}
