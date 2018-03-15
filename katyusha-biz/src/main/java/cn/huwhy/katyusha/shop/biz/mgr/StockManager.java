package cn.huwhy.katyusha.shop.biz.mgr;

import cn.huwhy.katyusha.shop.dao.StockDao;
import cn.huwhy.katyusha.shop.dao.po.StockPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockManager {
    @Autowired
    private StockDao stockDao;

    public void saves(List<StockPo> stockList) {
        stockDao.saves(stockList);
    }

    public Map<Long, Integer> getStockMap(List<Long> ids) {
        List<StockPo> list = stockDao.getByIds(ids);
        Map<Long, Integer> map = new HashMap<>(list.size());
        for (StockPo po : list) {
            map.put(po.getId(), po.getNum());
        }
        return map;
    }
}
