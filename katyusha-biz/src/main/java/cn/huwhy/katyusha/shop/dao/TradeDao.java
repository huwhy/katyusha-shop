package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.ibatis.BaseDao;
import cn.huwhy.katyusha.shop.dao.po.TradePo;
import cn.huwhy.katyusha.shop.model.TradeStatus;
import org.apache.ibatis.annotations.Param;

public interface TradeDao extends BaseDao<TradePo, Long> {

    int setStatus(@Param("id") long id, @Param("status")TradeStatus status);
}
