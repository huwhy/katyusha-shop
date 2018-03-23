package cn.huwhy.katyusha.shop.biz;

import cn.huwhy.katyusha.shop.biz.mgr.AccountManager;
import cn.huwhy.katyusha.shop.biz.mgr.MemberManager;
import cn.huwhy.katyusha.shop.model.Account;
import cn.huwhy.katyusha.shop.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberBiz {
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private AccountManager accountManager;

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
