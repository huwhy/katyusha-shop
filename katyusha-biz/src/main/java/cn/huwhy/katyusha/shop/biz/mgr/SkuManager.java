package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.SkuDao;
import cn.huwhy.katyusha.shop.dao.po.SkuPo;
import cn.huwhy.katyusha.shop.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;

@Service
public class SkuManager {

    @Autowired
    private SkuDao skuDao;

    public void save(List<Sku> skuList) {
        List<SkuPo> skuPoList = new ArrayList<>(skuList.size());
        for (Sku sku : skuList) {
            SkuPo skuPo = copyProperties(sku, SkuPo.class);
            if (skuPo.getId() == 0) {
                skuPo.setId(skuDao.nextId());
                sku.setId(skuPo.getId());
            }
            skuPoList.add(skuPo);
        }
        skuDao.saves(skuPoList);
    }

    public List<Sku> getSkuList(List<Long> ids) {
        if (ids.isEmpty()) return Collections.emptyList();
        List<SkuPo> list = skuDao.getByIds(ids);
        return copyProperties(list, Sku.class);
    }

    public Sku get(long id) {
         SkuPo po = skuDao.get(id);
         return copyProperties(po, Sku.class);
    }

    public long nextId() {
        return skuDao.nextId();
    }
}
