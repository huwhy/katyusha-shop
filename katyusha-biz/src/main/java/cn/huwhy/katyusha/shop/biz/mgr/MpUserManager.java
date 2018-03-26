package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.MpUserDao;
import cn.huwhy.katyusha.shop.dao.po.MpUserPo;
import cn.huwhy.katyusha.shop.model.MpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;

@Service
public class MpUserManager {
    @Autowired
    private MpUserDao mpUserDao;

    public void save(MpUser mpUser) {
        MpUserPo po = copyProperties(mpUser, MpUserPo.class);
        mpUserDao.save(po);
    }

    public MpUser get(String openId) {
        MpUserPo po = mpUserDao.get(openId);
        return copyProperties(po, MpUser.class);
    }

    public MpUser getByMemberId(long memberId) {
        MpUserPo po = mpUserDao.getByMemberId(memberId);
        return copyProperties(po, MpUser.class);
    }
}
