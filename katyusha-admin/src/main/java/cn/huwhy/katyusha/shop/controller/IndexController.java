package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.biz.CategoryBiz;
import cn.huwhy.katyusha.shop.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private CategoryBiz categoryBiz;

    @RequestMapping({"/", ""})
    public Json index() {
        Category category = categoryBiz.get(1);
        return Json.SUCCESS().setData(category);
    }
}
