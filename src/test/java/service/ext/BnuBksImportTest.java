package service.ext;

import ext.service.ExtBksImport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BnuBksImportTest{
    @Autowired
    public ExtBksImport extBksImport;
    @Test
    public void excute() throws Exception {
        extBksImport.excute(null);
    }
}
