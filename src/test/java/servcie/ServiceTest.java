package servcie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.abroad.ApplySelfService;
import service.member.MemberService;

/**
 * Created by fafa on 2015/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ApplySelfService applySelfService;

    @Test
    public void changeParty(){
        memberService.changeParty(new Integer[]{92148}, 17, 704);
    }

    @Test
    public void sendApprovalMsg(){
        applySelfService.sendApprovalMsg();
    }
}
