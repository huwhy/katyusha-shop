package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.mp.MpConfigUtil;
import cn.huwhy.katyusha.shop.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/mp")
public class MpController extends BaseController {

    @Autowired
    private MpConfigUtil mpConfigUtil;

    @RequestMapping("js-sign")
    public Json jsSign(HttpServletRequest request, String url) {
        logger.debug("js-sign: url-{}", url);
        return Json.SUCCESS().setData(mpConfigUtil.createJsapiSign(url));
    }

}
