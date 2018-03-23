package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.AccountDao;
import cn.huwhy.katyusha.shop.dao.po.AccountPo;
import cn.huwhy.katyusha.shop.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;

@Service
public class AccountManager {
    @Autowired
    private AccountDao accountDao;

    public void save(Account account) {
        AccountPo po = copyProperties(account, AccountPo.class);
        accountDao.save(po);
    }

    public Account get(long id) {
        AccountPo po = accountDao.get(id);
        return copyProperties(po, Account.class);
    }

}
