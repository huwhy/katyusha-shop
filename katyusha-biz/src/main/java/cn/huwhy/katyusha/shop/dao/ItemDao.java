package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.ibatis.BaseDao;
import cn.huwhy.katyusha.shop.dao.po.ItemPo;
import cn.huwhy.katyusha.shop.model.ItemStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ItemDao extends BaseDao<ItemPo, Long> {

    void saveContent(ItemPo po);

    String getContent(long id);

    List<ItemPo> listByIds(@Param("ids") Collection<Long> ids);

    int shelf(@Param("id") long id, @Param("status")ItemStatus status);
}
