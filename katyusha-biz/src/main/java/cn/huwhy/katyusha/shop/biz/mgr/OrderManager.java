package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.OrderDao;
import cn.huwhy.katyusha.shop.dao.po.OrderPo;
import cn.huwhy.katyusha.shop.model.Order;
import cn.huwhy.katyusha.shop.model.TradeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;

@Service
public class OrderManager {
    @Autowired
    private OrderDao orderDao;

    public void save(List<Order> orders) {
        List<OrderPo> pos = copyProperties(orders, OrderPo.class);
        orderDao.saves(pos);
    }

    public List<Order> getByIds(Collection<Long> ids) {
        List<OrderPo> pos = orderDao.getByIds(ids);
        return copyProperties(pos, Order.class);
    }

    public long nextId() {
        return orderDao.nextId();
    }

    public void prepay(Collection<Long> ids) {
        orderDao.setStatus(ids, TradeStatus.WAIT_PAY);
    }

    public void paid(Collection<Long> ids) {
        orderDao.setStatus(ids, TradeStatus.WAIT_DELIVER_GOODS);
    }
}
