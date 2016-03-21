package service.source;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BnuYjsImportTest  {

    @Autowired
    public BnuYjsImport bnuYjsImport;
    @Test
    public void excute() throws Exception {

        bnuYjsImport.excute();
    }
}
