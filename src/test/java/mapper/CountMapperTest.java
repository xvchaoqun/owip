package mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.common.CountMapper;
import sys.utils.JSONUtils;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CountMapperTest {

	@Autowired
	CountMapper countMapper;

	@Test
	public void max(){

	}
	@Test
	public void count() {

		List<Map> map = countMapper.abroadPassportDraw();
		for (Map map1 : map) {
			int num = ((Long) map1.get("num")).intValue();
			byte type = ((Integer) map1.get("type")).byteValue();
			System.out.println(num + " " + type);
		}
		System.out.println(map);
		System.out.println(JSONUtils.toString(map));

		System.out.println(countMapper.modifyBaseApply());
	}
}
