package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.katyusha.shop.BaseTest;
import cn.huwhy.katyusha.shop.dao.po.SkuPo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SkuDaoTest extends BaseTest {

    @Autowired
    private SkuDao skuDao;

    @Test
    public void getByIds() throws Exception {
        List<SkuPo> list = skuDao.getByIds(Arrays.asList(1L, 2L));
        System.out.println(list.size() == 0);
    }

}