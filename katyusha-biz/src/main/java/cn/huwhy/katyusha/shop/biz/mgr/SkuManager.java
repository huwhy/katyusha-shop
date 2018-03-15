package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.SkuDao;
import cn.huwhy.katyusha.shop.dao.po.SkuPo;
import cn.huwhy.katyusha.shop.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<SkuPo> list = skuDao.getByIds(ids);
        return copyProperties(list, Sku.class);
    }

    public long nextId() {
        return skuDao.nextId();
    }
}
