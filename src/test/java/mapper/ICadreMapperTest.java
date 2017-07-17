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
import sys.constants.SystemConstants;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ICadreMapperTest {

	@Autowired
	ICadreMapper iCadreMapper;
	@Autowired
	IAbroadMapper iAbroadMapper;

	@Test
	public void max(){

		System.out.println(iAbroadMapper.passportCount());

	}
	@Test
	public void list() {

		List<Cadre> cadres = iCadreMapper.selectCadreList("%sd%", SystemConstants.CADRE_STATUS_SET, new RowBounds(0, 2));
		System.out.println("================" + cadres.size());

		int sd = iCadreMapper.countCadre("sd", SystemConstants.CADRE_STATUS_SET);
		System.out.println(sd);
	}
}
