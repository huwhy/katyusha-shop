package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.katyusha.shop.biz.mgr.CategoryManager;
import cn.huwhy.katyusha.shop.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryBiz {

    @Autowired
    private CategoryManager categoryManager;

    public void save(Category category) {
        categoryManager.save(category);
    }

    public Category get(int id) {
        return categoryManager.get(id);
    }
}
