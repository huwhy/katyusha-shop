package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.ibatis.BaseDao;
import cn.huwhy.katyusha.shop.dao.po.ShoppingCartPo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ShoppingCartDao extends BaseDao<ShoppingCartPo, Long> {

    List<ShoppingCartPo> getByMemberId(@Param("memberId") long memberId, @Param("ids") Collection<Long> ids);

    void updateNum(@Param("id") long id, @Param("num") int num, @Param("memberId") long memberId);

    void delByIds(@Param("ids") Collection<Long> ids);
}
