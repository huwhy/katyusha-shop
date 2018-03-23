package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.katyusha.shop.biz.mgr.AccountManager;
import cn.huwhy.katyusha.shop.biz.mgr.MemberManager;
import cn.huwhy.katyusha.shop.biz.mgr.MpUserManager;
import cn.huwhy.katyusha.shop.model.Account;
import cn.huwhy.katyusha.shop.model.Member;
import cn.huwhy.katyusha.shop.model.MpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberBiz {
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private MpUserManager mpUserManager;
    @Autowired
    private AccountManager accountManager;

    @Transactional
    public void addByMp(MpUser mpUser) {
        MpUser mp = mpUserManager.get(mpUser.getOpenId());
        Member member = null;
        if (mp != null) {
            member = memberManager.get(mp.getMemberId());
        }
        if (member == null) {
            member = new Member();
        }
        member.setNick(mpUser.getNickname());
        member.setHeadImg(mpUser.getHeadImgUrl());
        memberManager.save(member);
        Account account = new Account();
        account.setId(member.getId());
        accountManager.save(account);
    }

    @Transactional
    public void save(Member member) {
        memberManager.save(member);
        Account account = new Account();
        account.setId(member.getId());
        accountManager.save(account);
    }

    public Member get(long id) {
        return memberManager.get(id);
    }

    public Account getAccount(long id) {
        return accountManager.get(id);
    }


}
