package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.common.util.CollectionUtil;
import cn.huwhy.katyusha.shop.biz.mgr.ItemManager;
import cn.huwhy.katyusha.shop.biz.mgr.ShoppingCartManager;
import cn.huwhy.katyusha.shop.biz.mgr.SkuManager;
import cn.huwhy.katyusha.shop.model.Item;
import cn.huwhy.katyusha.shop.model.ShoppingCart;
import cn.huwhy.katyusha.shop.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartBiz {

    @Autowired
    private ShoppingCartManager shoppingCartManager;
    @Autowired
    private ItemManager itemManager;
    @Autowired
    private SkuManager skuManager;

    public List<ShoppingCart> getShoppingCarts(long memberId, Collection<Long> ids) {
        List<ShoppingCart> list = shoppingCartManager.getShoppingCarts(memberId, ids);
        List<Long> itemIds = new ArrayList<>(list.size());
        List<Long> skuIds = new ArrayList<>(list.size());
        for (ShoppingCart cart : list) {
            itemIds.add(cart.getItemId());
            skuIds.add(cart.getSkuId());
        }
        List<Item> items = itemManager.listByIds(itemIds);
        Map<Long, Item> itemMap = CollectionUtil.index(items, Item::getId);
        List<Sku> skuList = skuManager.getSkuList(skuIds);
        Map<Long, Sku> skuMap = CollectionUtil.index(skuList, Sku::getId);
        for (ShoppingCart cart : list) {
            Item item = itemMap.get(cart.getItemId());
            if (item != null) {
                cart.setItem(item);
            }
            Sku sku = skuMap.get(cart.getSkuId());
            if (sku != null) {
                cart.setSku(sku);
            }
        }
        return list;
    }

    @Transactional
    public ShoppingCart add(ShoppingCart cart) {
        shoppingCartManager.add(cart);
        cart.setItem(itemManager.get(cart.getItemId()));
        cart.setSku(skuManager.get(cart.getSkuId()));
        return cart;
    }

    public void deleteByIds(Collection<Long> ids) {
        shoppingCartManager.deleteByIds(ids);
    }

    public void updateNum(long id, int num, long memberId) {
        shoppingCartManager.updateNum(id, num, memberId);
    }
}
