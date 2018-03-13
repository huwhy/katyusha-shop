package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.katyusha.shop.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("category", categoryDao.get(1));
        return "index";
    }
}
