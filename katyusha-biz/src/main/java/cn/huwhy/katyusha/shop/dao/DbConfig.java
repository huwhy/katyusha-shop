package cn.huwhy.katyusha.shop.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author huwhy
 * @date 2018/2/6
 * @Desc
 */
@Component
public class DbConfig {

    @Value("${driverClassName}")
    private String  driverClassName;
    @Value("${url}")
    private String  url;
    @Value("${username}")
    private String  username;
    @Value("${password}")
    private String  password;
    @Value("${defaultAutoCommit}")
    private Boolean defaultAutoCommit;
    @Value("${maxActive}")
    private Integer maxActive;
    @Value("${maxIdle}")
    private Integer maxIdle;
    @Value("${maxWait}")
    private Long    maxWait;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
    }
}
