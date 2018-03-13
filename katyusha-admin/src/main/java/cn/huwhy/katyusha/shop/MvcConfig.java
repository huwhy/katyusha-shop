package cn.huwhy.katyusha.shop;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {
    final static Charset UTF8 = Charset.forName("UTF-8");


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
        super.configureDefaultServletHandling(configurer);
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json", UTF8),
                new MediaType("text", "xml", UTF8)));
        converters.add(converter);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
    }

}
