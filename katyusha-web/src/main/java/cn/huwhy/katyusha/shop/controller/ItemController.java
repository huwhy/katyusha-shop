package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.interfaces.Paging;
import cn.huwhy.katyusha.shop.biz.ItemBiz;
import cn.huwhy.katyusha.shop.model.Item;
import cn.huwhy.katyusha.shop.model.ItemStatus;
import cn.huwhy.katyusha.shop.model.ItemTerm;
import cn.huwhy.katyusha.shop.model.ShoppingCart;
import cn.huwhy.katyusha.shop.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/item")
public class ItemController extends BaseController {

    @Autowired
    private ItemBiz itemBiz;

    @RequestMapping(method = GET)
    public Json list(@RequestParam(value = "title", required = false, defaultValue = "") String title,
                     @RequestParam(value = "categoryId", required = false, defaultValue = "0") int categoryId,
                     @RequestParam(value = "size", required = false, defaultValue = "10") Long size,
                     @RequestParam(value = "page", required = false, defaultValue = "1") Long page) {
        ItemTerm term = new ItemTerm();
        term.setCategoryId(categoryId);
        term.setStatus(ItemStatus.IN_SALE);
        term.setTitle(title);
        term.setPage(page);
        term.setSize(size);
        Paging<Item> item = itemBiz.findItems(term);
        return Json.SUCCESS().setData(item.getData());
    }

    @RequestMapping(value = "sku/{id:\\d+}", method = GET)
    public Json getSku(@PathVariable("id") long skuId) {
        Sku sku = itemBiz.getSku(skuId);
        Item item = itemBiz.get(sku.getItemId());
        ShoppingCart cart = new ShoppingCart();
        cart.setItem(item);
        cart.setSku(sku);
        cart.setNum(1);
        return Json.SUCCESS().setData(cart);
    }

    @RequestMapping(value = "{id:\\d+}", method = GET)
    public Json get(@PathVariable("id") Long id) {
        Item item = itemBiz.get(id);
        return Json.SUCCESS().setData(item);
    }
}
