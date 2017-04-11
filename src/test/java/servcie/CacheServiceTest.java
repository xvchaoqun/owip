package servcie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.global.CacheService;

/**
 * Created by fafa on 2017/4/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;
    @Test
    public void flushLocation(){

        //cacheService.flushLocation();
    }
}
