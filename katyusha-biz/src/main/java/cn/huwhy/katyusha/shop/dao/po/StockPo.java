package cn.huwhy.katyusha.shop.dao.po;

import java.io.Serializable;

public class StockPo implements Serializable {
    private long id;
    private int num;
    private int version;

    public StockPo() {
    }

    public StockPo(long id, int num) {
        this.id = id;
        this.num = num;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
