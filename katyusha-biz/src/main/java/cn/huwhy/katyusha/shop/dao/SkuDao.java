package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.ibatis.BaseDao;
import cn.huwhy.katyusha.shop.dao.po.SkuPo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface SkuDao extends BaseDao<SkuPo, Long> {

    List<SkuPo> getByIds(@Param("ids")Collection<Long> ids);
}
