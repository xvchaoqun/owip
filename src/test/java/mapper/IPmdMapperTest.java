package mapper;

import domain.cadre.Cadre;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.common.IAbroadMapper;
import persistence.common.ICadreMapper;
import persistence.common.IPmdMapper;
import sys.constants.SystemConstants;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class IPmdMapperTest {

	@Autowired
	IPmdMapper iPmdMapper;

	@Test
	public void list(){

		System.out.println(iPmdMapper.extSalaryMonthList());

	}

}
