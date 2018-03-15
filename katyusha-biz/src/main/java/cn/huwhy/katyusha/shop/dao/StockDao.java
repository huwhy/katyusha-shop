package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.ibatis.BaseDao;
import cn.huwhy.katyusha.shop.dao.po.StockPo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface StockDao extends BaseDao<StockPo, StockPo> {
    List<StockPo> getByIds(@Param("ids") Collection<Long> ids);
}
