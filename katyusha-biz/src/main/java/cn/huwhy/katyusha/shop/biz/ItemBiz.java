package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.interfaces.Paging;
import cn.huwhy.katyusha.shop.biz.mgr.ItemManager;
import cn.huwhy.katyusha.shop.biz.mgr.SkuManager;
import cn.huwhy.katyusha.shop.biz.mgr.StockManager;
import cn.huwhy.katyusha.shop.dao.po.StockPo;
import cn.huwhy.katyusha.shop.model.Item;
import cn.huwhy.katyusha.shop.model.ItemStatus;
import cn.huwhy.katyusha.shop.model.ItemTerm;
import cn.huwhy.katyusha.shop.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ItemBiz {
    @Autowired
    private ItemManager itemManager;
    @Autowired
    private SkuManager skuManager;
    @Autowired
    private StockManager stockManager;

    public void save(Item item) {
        if (item.getId() == 0) {
            item.setId(itemManager.nextId());
        }
        List<Long> skuIds = new ArrayList<>(item.getSkuList().size());
        List<StockPo> stockList = new ArrayList<>(item.getSkuList().size());
        int lowPrice = Integer.MAX_VALUE,
                highPrice = Integer.MIN_VALUE,
                totalStock = 0;
        for (Sku sku : item.getSkuList()) {
            if (sku.getId() == 0) {
                sku.setId(skuManager.nextId());
            }
            sku.setItemId(item.getId());
            skuIds.add(sku.getId());
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

    public Item get(long id) {
        Item item = itemManager.get(id);
        if (item != null) {
            List<Sku> skuList = skuManager.getSkuList(item.getSkuIds());
            Map<Long, Integer> map = stockManager.getStockMap(item.getSkuIds());
            for (Sku sku : skuList) {
                Integer num = map.get(sku.getId());
                sku.setStock(num == null ? 0 : num);
            }
            item.setSkuList(skuList);
        }
        return item;
    }

    public Paging<Item> findItems(ItemTerm term) {
        return itemManager.findItems(term);
    }

    public int shelf(long id, ItemStatus status) {
        return itemManager.shelf(id, status);
    }
}
