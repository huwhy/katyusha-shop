package cn.huwhy.katyusha.shop.dao;

import cn.huwhy.ibatis.BaseDao;
import cn.huwhy.katyusha.shop.dao.po.MpUserPo;
import org.apache.ibatis.annotations.Param;

public interface MpUserDao extends BaseDao<MpUserPo, String> {
    MpUserPo getByMemberId(@Param("memberId") long memberId);
}
