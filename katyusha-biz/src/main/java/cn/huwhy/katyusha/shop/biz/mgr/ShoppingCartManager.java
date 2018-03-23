package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.ShoppingCartDao;
import cn.huwhy.katyusha.shop.dao.po.ShoppingCartPo;
import cn.huwhy.katyusha.shop.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;

@Service
public class ShoppingCartManager {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    public List<ShoppingCart> getShoppingCarts(long memberId, Collection<Long> ids) {
        List<ShoppingCartPo> list = shoppingCartDao.getByMemberId(memberId, ids);
        return copyProperties(list, ShoppingCart.class);
    }

    public void add(ShoppingCart cart) {
        ShoppingCartPo po = copyProperties(cart, ShoppingCartPo.class);
        po.setModified(new Date());
        po.setCreated(new Date());
        shoppingCartDao.save(po);
        cart.setId(po.getId());
    }

    public void updateNum(long id, int num, long memberId) {
        shoppingCartDao.updateNum(id, num, memberId);
    }

    public void deleteByIds(Collection<Long> ids) {
        shoppingCartDao.delByIds(ids);
    }
}
