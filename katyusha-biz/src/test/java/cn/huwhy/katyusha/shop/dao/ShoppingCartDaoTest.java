package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.katyusha.shop.BaseTest;
import cn.huwhy.katyusha.shop.dao.po.ShoppingCartPo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ShoppingCartDaoTest extends BaseTest {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Test
    public void getByMemberId() throws Exception {
        Collection<Long> ids = Arrays.asList(1L);
        List<ShoppingCartPo> list = shoppingCartDao.getByMemberId(0, ids);
        System.out.println(list.size());
    }

}