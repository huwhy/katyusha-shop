package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.common.util.CollectionUtil;
import cn.huwhy.common.util.EnumUtil;
import cn.huwhy.common.util.StringUtil;
import cn.huwhy.interfaces.Json;
import cn.huwhy.interfaces.Paging;
import cn.huwhy.katyusha.shop.biz.ItemBiz;
import cn.huwhy.katyusha.shop.model.Item;
import cn.huwhy.katyusha.shop.model.ItemPhyType;
import cn.huwhy.katyusha.shop.model.ItemStatus;
import cn.huwhy.katyusha.shop.model.ItemTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemBiz itemBiz;

    @RequestMapping(method = GET)
    public Json list(@RequestParam(value = "categoryId", required = false, defaultValue = "0") int categoryId,
                     @RequestParam(value = "title", required = false, defaultValue = "") String title,
                     @RequestParam(value = "phyType", required = false, defaultValue = "") String phyTypeName,
                     @RequestParam(value = "status", required = false, defaultValue = "") String statusName,
                     @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                     @RequestParam(value = "size", required = false, defaultValue = "10") Long size) {
        ItemTerm term = new ItemTerm();
        term.setCategoryId(categoryId);
        term.setTitle(title);
        term.setPhyType(EnumUtil.nameOf(ItemPhyType.class, phyTypeName));
        term.setStatus(EnumUtil.nameOf(ItemStatus.class, statusName));
        term.setPage(page);
        term.setSize(size);
        Paging<Item> paging = itemBiz.findItems(term);
        return Json.SUCCESS().setData(paging);
    }

    @RequestMapping(method = POST)
    public Json save(@RequestBody Item item) {
        if (item.getStatus() == null) {
            item.setStatus(ItemStatus.OFF_SHELF);
        }
        if (StringUtil.isEmpty(item.getMainImg()) && CollectionUtil.isNotEmpty(item.getImages())) {
            item.setMainImg(item.getImages().get(0));
        }
        itemBiz.save(item);
        return Json.SUCCESS().setData(item.getId());
    }

    @RequestMapping(value = "shelf", method = POST)
    public Json onSale(@RequestParam(value = "id") long id, @RequestParam("status") String statusName) {
        ItemStatus status = EnumUtil.nameOf(ItemStatus.class, statusName);
        if (status == null) {
            return Json.ERROR().setMessage("数据错误");
        }
        int cnt = itemBiz.shelf(id, status);
        return Json.SUCCESS().setData(cnt);
    }

    @RequestMapping(value = "{id:\\d+}", method = GET)
    public Json get(@PathVariable("id") long id) {
        Item item = itemBiz.get(id);
        return Json.SUCCESS().setData(item);
    }
}
