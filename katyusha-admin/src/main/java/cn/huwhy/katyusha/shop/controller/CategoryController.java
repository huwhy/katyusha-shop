package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.interfaces.Paging;
import cn.huwhy.katyusha.shop.biz.CategoryBiz;
import cn.huwhy.katyusha.shop.model.Category;
import cn.huwhy.katyusha.shop.model.CategoryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryBiz categoryBiz;

    @RequestMapping(method = GET)
    public Json list(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                     @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                     @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                     @RequestParam(value = "size", required = false, defaultValue = "10") Long size) {
        CategoryTerm term = new CategoryTerm();
        term.setName(name);
        term.setId(id);
        term.setPage(page);
        term.setSize(size);
        Paging<Category> paging = categoryBiz.findCategories(term);
        return Json.SUCCESS().setData(paging);
    }

    @RequestMapping(method = POST)
    public Json save(@RequestBody Category category) {
        categoryBiz.save(category);
        return Json.SUCCESS().setData(category.getId());
    }

    @RequestMapping(value = "{id:\\d+}", method = GET)
    public Json get(@PathVariable("id") int id) {
        Category category = categoryBiz.get(id);
        return Json.SUCCESS().setData(category);
    }

}
