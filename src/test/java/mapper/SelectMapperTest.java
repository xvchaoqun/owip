package mapper;

import persistence.abroad.common.ApplySelfSearchBean;
import domain.abroad.ApplySelf;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.abroad.common.IAbroadMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SelectMapperTest {

	@Autowired
	IAbroadMapper iAbroadMapper;

	@Test
	public void max(){

	}
	@Test
	public void list() {
	}
}
