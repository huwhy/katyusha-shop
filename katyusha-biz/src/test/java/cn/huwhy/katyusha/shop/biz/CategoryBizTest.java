package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.katyusha.shop.BaseTest;
import cn.huwhy.katyusha.shop.model.Category;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryBizTest extends BaseTest {

    @Autowired
    private CategoryBiz categoryBiz;

    @Test
    public void testGet() {
        Category category = categoryBiz.get(1);
        System.out.println(category.getId() == 1);
    }
}