package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.biz.ShoppingCartBiz;
import cn.huwhy.katyusha.shop.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartBiz shoppingCartBiz;

    @RequestMapping(method = GET)
    public Json list() {
        List<ShoppingCart> list = shoppingCartBiz.getShoppingCarts(0);
        return Json.SUCCESS().setData(list);
    }

    @RequestMapping(method = POST)
    public Json save(@RequestParam(value = "itemId", required = false, defaultValue = "0") long itemId,
                     @RequestParam(value = "skuId", required = false, defaultValue = "0") long skuId) {
        ShoppingCart cart = shoppingCartBiz.add(new ShoppingCart(0, itemId, skuId, 1));
        return Json.SUCCESS().setData(cart);
    }

    @RequestMapping(value = "chgNum", method = POST)
    public Json update(@RequestParam(value = "id", required = false, defaultValue = "0") long id,
                       @RequestParam(value = "num", required = false, defaultValue = "0") int num) {
        shoppingCartBiz.updateNum(id, num);
        return Json.SUCCESS();
    }

}
