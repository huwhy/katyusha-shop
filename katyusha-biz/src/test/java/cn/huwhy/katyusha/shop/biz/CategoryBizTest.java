package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.common.util.RandomUtil;
import cn.huwhy.katyusha.shop.BaseTest;
import cn.huwhy.katyusha.shop.biz.mgr.ItemManager;
import cn.huwhy.katyusha.shop.biz.mgr.SkuManager;
import cn.huwhy.katyusha.shop.biz.mgr.StockManager;
import cn.huwhy.katyusha.shop.dao.po.StockPo;
import cn.huwhy.katyusha.shop.model.Category;
import cn.huwhy.katyusha.shop.model.Item;
import cn.huwhy.katyusha.shop.model.Sku;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CategoryBizTest extends BaseTest {

    @Autowired
    private CategoryBiz categoryBiz;
    @Autowired
    private ItemBiz itemBiz;
    @Autowired
    private ItemManager itemManager;
    @Autowired
    private SkuManager skuManager;
    @Autowired
    private StockManager stockManager;

    @Test
    public void save() {
        for(int i = 1; i < 52; i++) {
            Item item = itemBiz.get(i);
            List<Long> skuIds = new ArrayList<>(item.getSkuList().size());
            List<StockPo> stockList = new ArrayList<>(item.getSkuList().size());
            int lowPrice = Integer.MAX_VALUE,
                    highPrice = Integer.MIN_VALUE,
                    totalStock = 0;
            for (Sku sku : item.getSkuList()) {
                sku.setItemId(item.getId());
                skuIds.add(sku.getId());
                sku.setPrice(RandomUtil.randomInt(10000));
                sku.setStock(i);
                stockList.add(new StockPo(sku.getId(), sku.getStock()));
                if (lowPrice > sku.getPrice()) {
                    lowPrice = sku.getPrice();
                }
                if (highPrice < sku.getPrice()) {
                    highPrice = sku.getPrice();
                }
                totalStock += sku.getStock();
            }
            item.setSkuIds(skuIds);
            item.setLowPrice(lowPrice);
            item.setHighPrice(highPrice);
            item.setTotalStock(totalStock);
            itemManager.save(item);
            skuManager.save(item.getSkuList());
            stockManager.saves(stockList);
        }
    }

    @Test
    public void testGet() {
        Category category = categoryBiz.get(1);
        System.out.println(category.getId() == 1);
    }
}