package cn.huwhy.katyusha.shop;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppTestConfig.class})
public abstract class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

}
