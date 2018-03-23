package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.MemberDao;
import cn.huwhy.katyusha.shop.dao.po.MemberPo;
import cn.huwhy.katyusha.shop.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;

@Service
public class MemberManager {
    @Autowired
    private MemberDao memberDao;

    public void save(Member member) {
        MemberPo po = copyProperties(member, MemberPo.class);
        po.setLastLoginTime(new Date());
        po.setCreated(new Date());
        if (po.getId() == 0) {
            po.setId(memberDao.nextId());
            member.setId(po.getId());
        }
    }

    public Member get(long id) {
        MemberPo po = memberDao.get(id);
        return copyProperties(po, Member.class);
    }
}
