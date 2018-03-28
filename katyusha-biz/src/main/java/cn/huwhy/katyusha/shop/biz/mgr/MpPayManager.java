package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.MpPayDao;
import cn.huwhy.katyusha.shop.dao.po.MpPayPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MpPayManager {

    @Autowired
    private MpPayDao mpPayDao;

    public void prepay(long tid, String prepayId) {
        mpPayDao.save(new MpPayPo(tid, prepayId, ""));
    }

    public void paid(long tid, String transactionId) {
        MpPayPo po = mpPayDao.get(tid);
        mpPayDao.save(new MpPayPo(tid, po.getPrepayId(), transactionId));
    }

    public String getPrepayId(long tid) {
        MpPayPo po = mpPayDao.get(tid);
        return po.getPrepayId();
    }
}
