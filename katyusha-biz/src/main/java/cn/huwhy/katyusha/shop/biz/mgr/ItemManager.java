package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.common.util.StringUtil;
import cn.huwhy.interfaces.Paging;
import cn.huwhy.katyusha.shop.dao.ItemDao;
import cn.huwhy.katyusha.shop.dao.po.ItemPo;
import cn.huwhy.katyusha.shop.model.Item;
import cn.huwhy.katyusha.shop.model.ItemStatus;
import cn.huwhy.katyusha.shop.model.ItemTerm;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;

@Service
public class ItemManager {

    @Autowired
    private ItemDao itemDao;

    public void save(Item item) {
        ItemPo itemPo = copyProperties(item, ItemPo.class, "skuIds","images");
        itemPo.setSkuIds(Joiner.on(',').join(item.getSkuIds()));
        itemPo.setImages(Joiner.on(',').join(item.getImages()));
        itemDao.save(itemPo);
        itemDao.saveContent(itemPo);
    }

    public Item get(long id) {
        ItemPo po = itemDao.get(id);
        String content = itemDao.getContent(id);
        po.setContent(content);
        return toItem(po);
    }

    public Long nextId() {
        return itemDao.nextId();
    }

    public Paging<Item> findItems(ItemTerm term) {
        if (StringUtil.isNotEmpty(term.getTitle())) {
            term.setTitle("%" + term.getTitle() + "%");
        }
        List<ItemPo> list = itemDao.findPaging(term);
        List<Item> items = new ArrayList<>(list.size());
        for (ItemPo po : list) {
            items.add(toItem(po));
        }
        return new Paging<>(term, items);
    }

    public List<Item> listByIds(Collection<Long> ids) {
        if (ids.isEmpty()) return Collections.emptyList();
        List<ItemPo> pos = itemDao.listByIds(ids);
        List<Item> items = new ArrayList<>(pos.size());
        for (ItemPo po : pos) {
            items.add(toItem(po));
        }
        return items;
    }

    private Item toItem(ItemPo po) {
        Item item = copyProperties(po, Item.class, "skuIds", "images");
        List<Long> skuIds = new ArrayList<>(3);
        for (String v : Splitter.on(',').split(po.getSkuIds())) {
            skuIds.add(Long.parseLong(v));
        }
        item.setSkuIds(skuIds);
        item.setImages(Splitter.on(',').splitToList(po.getImages()));
        return item;
    }

    public int shelf(long id, ItemStatus status) {
        return itemDao.shelf(id, status);
    }
}
