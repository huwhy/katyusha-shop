package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.katyusha.shop.BaseTest;
import cn.huwhy.katyusha.shop.dao.po.CategoryPo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryDaoTest extends BaseTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void testGet() {
        CategoryPo po = categoryDao.get(1);
        System.out.println(po.getId() == 1);
    }

}