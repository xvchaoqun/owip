package mapper;

import domain.cadre.Cadre;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.abroad.common.IAbroadMapper;
import persistence.cadre.common.ICadreMapper;
import sys.constants.CadreConstants;

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

		List<Cadre> cadres = iCadreMapper.selectCadreList("%sd%", CadreConstants.CADRE_STATUS_SET, null, new RowBounds(0, 2));
		System.out.println("================" + cadres.size());

		int sd = iCadreMapper.countCadreList("sd", CadreConstants.CADRE_STATUS_SET, null);
		System.out.println(sd);
	}
}
