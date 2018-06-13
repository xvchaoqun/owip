package service.ext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BnuRetireSalaryImportTest {

    @Autowired
    public ExtRetireSalaryImport extRetireSalaryImport;
    @Test
    public void excute() throws Exception {

        extRetireSalaryImport.excute(null);
    }
}
