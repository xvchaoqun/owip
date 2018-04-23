package mapper;

import bean.StatByteBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.member.common.StatMemberMapper;

import java.util.List;

/**
 * Created by fafa on 2016/8/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class StatMapperTest {
    @Autowired
    StatMemberMapper statMemberMapper;

    @Test
    public void t(){
        List<StatByteBean> statByteBeans = statMemberMapper.member_groupByPoliticalStatus(null, null);
        System.out.println(statByteBeans);
    }
}
