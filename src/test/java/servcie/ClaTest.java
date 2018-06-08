package servcie;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.cla.ClaShortMsgService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ClaTest {

    @Autowired
    ClaShortMsgService claShortMsgService;

    @Test
    public void applyBackMsg() throws JsonProcessingException {

        claShortMsgService.applyBackMsg();
    }
}
