package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.biz.CategoryBiz;
import cn.huwhy.katyusha.shop.model.Category;
import cn.huwhy.katyusha.shop.model.CategoryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryBiz categoryBiz;

    @RequestMapping(method = RequestMethod.GET)
    public Json list() {
        List<Category> list = categoryBiz.listCategories(new CategoryTerm());
        return Json.SUCCESS().setData(list);
    }
}
