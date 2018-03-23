package cn.huwhy.katyusha.shop.controller;

import cn.huwhy.common.util.StringUtil;
import cn.huwhy.interfaces.Json;
import cn.huwhy.katyusha.shop.biz.ShoppingCartBiz;
import cn.huwhy.katyusha.shop.model.ShoppingCart;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController extends BaseController {

    @Autowired
    private ShoppingCartBiz shoppingCartBiz;

    @RequestMapping(method = GET)
    public Json list(@RequestParam(value = "ids", required = false) String value,
                     HttpServletRequest request) {
        List<Long> ids = new ArrayList<>();
        if (StringUtil.isNotEmpty(value)) {
            Splitter.on(',').splitToList(value).forEach(s -> ids.add(Long.parseLong(s)));
        }
        List<ShoppingCart> list = shoppingCartBiz.getShoppingCarts(getMemberId(request), ids);
        return Json.SUCCESS().setData(list);
    }

    @RequestMapping(method = POST)
    public Json save(@RequestParam(value = "itemId", required = false, defaultValue = "0") long itemId,
                     @RequestParam(value = "skuId", required = false, defaultValue = "0") long skuId,
                     HttpServletRequest request) {
        ShoppingCart cart = shoppingCartBiz.add(new ShoppingCart(getMemberId(request), itemId, skuId, 1));
        return Json.SUCCESS().setData(cart);
    }

    @RequestMapping(value = "chgNum", method = POST)
    public Json update(@RequestParam(value = "id", required = false, defaultValue = "0") long id,
                       @RequestParam(value = "num", required = false, defaultValue = "0") int num,
                       HttpServletRequest request) {
        shoppingCartBiz.updateNum(id, num, getMemberId(request));
        return Json.SUCCESS();
    }

}
