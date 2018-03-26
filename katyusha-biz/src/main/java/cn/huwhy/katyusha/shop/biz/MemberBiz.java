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

import java.util.Date;

@Service
public class MemberBiz {
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private MpUserManager mpUserManager;
    @Autowired
    private AccountManager accountManager;

    @Transactional
    public Member saveForMp(MpUser mpUser) {
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
        member.setLastLoginTime(new Date());
        memberManager.save(member);
        mpUser.setMemberId(member.getId());
        mpUserManager.save(mpUser);
        if (mp == null) {
            Account account = new Account();
            account.setId(member.getId());
            accountManager.save(account);
        }
        return member;
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

    public MpUser getMpUser(String openId) {
        return mpUserManager.get(openId);
    }

    public MpUser getMpUser(long memberId) {
        return mpUserManager.getByMemberId(memberId);
    }

    public Account getAccount(long id) {
        return accountManager.get(id);
    }


}
