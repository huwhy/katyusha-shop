package cn.huwhy.katyusha.shop;

import cn.huwhy.katyusha.shop.biz.CategoryBiz;
import cn.huwhy.katyusha.shop.biz.mgr.CategoryManager;
import cn.huwhy.katyusha.shop.dao.CategoryDao;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
@ComponentScan(basePackageClasses = {CategoryDao.class, CategoryManager.class, CategoryBiz.class})
public class AppTestConfig {

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        FileSystemResource[] locations = getFileSystemResources();
        configurer.setLocations(locations);
        return configurer;
    }

    private FileSystemResource[] getFileSystemResources() {
        String confPath = "/data/katyusha/conf";
        File file = new File(confPath);
        String[] props = file.list((dir, name) -> name.endsWith(".properties"));
        FileSystemResource[] locations = new FileSystemResource[props.length];
        for (int i = 0; i < props.length; i++) {
            locations[i] = new FileSystemResource(confPath + File.separator + props[i]);
        }
        return locations;
    }

}