package mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.common.IExtMapper;

import java.math.BigDecimal;

/**
 * Created by lm on 2017/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class IExtMapperTest {

    @Autowired
    IExtMapper iExtMapper;

    @Test
    public void t(){

        BigDecimal ltxf = iExtMapper.getLtxf("2222");
        System.out.println(ltxf);
    }
}
