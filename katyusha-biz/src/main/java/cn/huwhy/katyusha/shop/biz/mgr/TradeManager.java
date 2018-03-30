package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.common.util.BeanCopyUtils;
import cn.huwhy.interfaces.Paging;
import cn.huwhy.interfaces.Term;
import cn.huwhy.katyusha.shop.dao.TradeDao;
import cn.huwhy.katyusha.shop.dao.po.TradePo;
import cn.huwhy.katyusha.shop.model.Trade;
import cn.huwhy.katyusha.shop.model.TradeStatus;
import cn.huwhy.katyusha.shop.model.TradeTerm;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.huwhy.common.util.BeanCopyUtils.copyProperties;
import static cn.huwhy.common.util.BeanCopyUtils.of;

@Service
public class TradeManager {
    @Autowired
    private TradeDao tradeDao;
    private Map<String, BeanCopyUtils.CopyFunction> toFuncMap = of(new BeanCopyUtils.CopyFunction<String, List<Long>>("orderIds") {
        @Override
        public List<Long> copy(String value) {
            List<Long> orderIds = new ArrayList<>();
            for (String v : Splitter.on(',').split(value)) {
                orderIds.add(Long.valueOf(v));
            }
            return orderIds;
        }
    });

    public void save(Trade trade) {
        TradePo po = copyProperties(trade, TradePo.class, "orderIds");
        po.setOrderIds(Joiner.on(',').join(trade.getOrderIds()));
        po.setModified(new Date());
        po.setCreated(new Date());
        tradeDao.save(po);
    }

    public Trade get(long id) {
        TradePo po = tradeDao.get(id);
        Trade trade = copyProperties(po, Trade.class, toFuncMap);
        List<Long> orderIds = new ArrayList<>();
        for (String v : Splitter.on(',').split(po.getOrderIds())) {
            orderIds.add(Long.valueOf(v));
        }
        trade.setOrderIds(orderIds);
        return trade;
    }

    public long nextId() {
        return tradeDao.nextId();
    }

    public void prepay(long id) {
        tradeDao.setStatus(id, TradeStatus.WAIT_PAY);
    }

    public void paid(long id) {
        tradeDao.setStatus(id, TradeStatus.WAIT_DELIVER_GOODS);
    }

    public Paging<Trade> findPaging(TradeTerm term) {
        term.addSort("created", Term.Sort.DESC);
        List<TradePo> pos = tradeDao.findPaging(term);
        return new Paging<>(term, copyProperties(pos, Trade.class, toFuncMap));
    }
}
