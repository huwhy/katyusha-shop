package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.interfaces.Paging;
import cn.huwhy.katyusha.shop.dao.CategoryDao;
import cn.huwhy.katyusha.shop.dao.po.CategoryPo;
import cn.huwhy.katyusha.shop.model.Category;
import cn.huwhy.katyusha.shop.model.CategoryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;

@Service
public class CategoryManager {

    @Autowired
    private CategoryDao categoryDao;

    public void save(Category category) {
        CategoryPo po = copyProperties(category, CategoryPo.class);
        categoryDao.save(po);
        category.setId(po.getId());
    }

    public Category get(int id) {
        CategoryPo po = categoryDao.get(id);
        return copyProperties(po, Category.class);
    }

    public Paging<Category> findCategories(CategoryTerm term) {
        List<CategoryPo> list = categoryDao.findPaging(term);
        return new Paging<>(term, copyProperties(list, Category.class));
    }

    public List<Category> listCategories(CategoryTerm term) {
        List<CategoryPo> list = categoryDao.findByTerm(term);
        return copyProperties(list, Category.class);
    }
}
