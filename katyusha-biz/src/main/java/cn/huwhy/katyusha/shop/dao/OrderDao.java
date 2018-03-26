package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.ibatis.BaseDao;
import cn.huwhy.katyusha.shop.dao.po.OrderPo;
import cn.huwhy.katyusha.shop.model.TradeStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface OrderDao extends BaseDao<OrderPo, Long> {
    List<OrderPo> getByIds(@Param("ids") Collection<Long> ids);

    int setStatus(@Param("ids") Collection<Long> ids, @Param("status")TradeStatus status);
}
