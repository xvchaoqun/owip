package servcie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.member.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.party.MemberService;
import sys.utils.JSONUtils;

import java.text.SimpleDateFormat;

/**
 * Created by fafa on 2017/1/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MemberTest {

    @Autowired
    MemberService memberService;

    @Test
    public void test() throws JsonProcessingException {

        Member member = memberService.get(13429);
        System.out.println(member.getGrowTime());
        System.out.println(JSONUtils.toString(member.getGrowTime()));

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(sdf);
        System.out.println(objectMapper.writeValueAsString(member.getGrowTime()));

        /*objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        System.out.println(objectMapper.writeValueAsString(member.getGrowTime()));

        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(sdf2);
        System.out.println(objectMapper.writeValueAsString(member.getGrowTime()));*/
    }
}
