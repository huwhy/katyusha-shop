package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.common.util.StringUtil;
import cn.huwhy.interfaces.Paging;
import cn.huwhy.katyusha.shop.biz.mgr.CategoryManager;
import cn.huwhy.katyusha.shop.model.Category;
import cn.huwhy.katyusha.shop.model.CategoryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Paging<Category> findCategories(CategoryTerm term) {
        if (StringUtil.isNotEmpty(term.getName())) {
            term.setName("%" + term.getName() + "%");
        }
        return categoryManager.findCategories(term);
    }

    public List<Category> listCategories(CategoryTerm term) {
        if (StringUtil.isNotEmpty(term.getName())) {
            term.setName("%" + term.getName() + "%");
        }
        return categoryManager.listCategories(term);
    }

}
