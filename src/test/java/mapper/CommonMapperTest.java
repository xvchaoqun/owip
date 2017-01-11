package mapper;

import domain.cadre.Cadre;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.common.CommonMapper;
import sys.constants.SystemConstants;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CommonMapperTest {

	@Autowired
	CommonMapper commonMapper;

	@Test
	public void max(){

	}
	@Test
	public void list() {

		List<Cadre> cadres = commonMapper.selectCadreList("%sd%", null, SystemConstants.CADRE_STATUS_SET, new RowBounds(0, 2));
		System.out.println("================" + cadres.size());

		int sd = commonMapper.countCadre("sd", null, SystemConstants.CADRE_STATUS_SET);
		System.out.println(sd);
	}
}
