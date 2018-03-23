package cn.huwhy.katyusha.shop;

import cn.huwhy.katyusha.shop.mp.TextMsgListener;
import cn.huwhy.wx.sdk.aes.AesException;
import cn.huwhy.wx.sdk.aes.WXBizMsgCrypt;
import cn.huwhy.wx.sdk.listener.EventHandler;
import cn.huwhy.wx.sdk.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
public class FileConfig {
    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        FileSystemResource[] locations = getFileSystemResources();
        configurer.setLocations(locations);
        return configurer;
    }

    private FileSystemResource[] getFileSystemResources() {
        String confPath = System.getProperties().getProperty("conf.path");
        if (confPath == null) {
            confPath = "/data/katyusha/conf";
        }
        File file = new File(confPath);
        String[] props = file.list((dir, name) -> name.endsWith(".properties"));
        FileSystemResource[] locations = new FileSystemResource[props.length];
        for (int i = 0; i < props.length; i++) {
            locations[i] = new FileSystemResource(confPath + File.separator + props[i]);
        }
        return locations;
    }
}
