package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.ShoppingCartDao;
import cn.huwhy.katyusha.shop.dao.po.ShoppingCartPo;
import cn.huwhy.katyusha.shop.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;

@Service
public class ShoppingCartManager {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    public List<ShoppingCart> getShoppingCarts(long memberId) {
        List<ShoppingCartPo> list = shoppingCartDao.getByMemberId(memberId);
        return copyProperties(list, ShoppingCart.class);
    }

    public void add(ShoppingCart cart) {
        ShoppingCartPo po = copyProperties(cart, ShoppingCartPo.class);
        po.setModified(new Date());
        po.setCreated(new Date());
        shoppingCartDao.save(po);
        cart.setId(po.getId());
    }

    public void updateNum(long id, int num) {
        shoppingCartDao.updateNum(id, num);
    }
}
