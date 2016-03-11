package mapper;

import domain.ApplySelf;
import domain.Cadre;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.common.CommonMapper;
import persistence.common.SelectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SelectMapperTest {

	@Autowired
	SelectMapper selectMapper;

	@Test
	public void max(){

	}
	@Test
	public void list() {
		Map<Integer, List<Integer>> approverTypeUnitIdListMap = new HashMap<>();
		Map<Integer, List<Integer>> approverTypePostIdListMap = new HashMap<>();

		List<Integer> userIds = new ArrayList<>();
		userIds.add(5);
		userIds.add(6);

		approverTypeUnitIdListMap.put(100, userIds);
		approverTypeUnitIdListMap.put(201, userIds);

		List<Integer> postIds = new ArrayList<>();
		postIds.add(5);
		postIds.add(6);

		approverTypePostIdListMap.put(300, postIds);

		List<ApplySelf> applySelfs = selectMapper.selectNotApprovalList(approverTypeUnitIdListMap, null, new RowBounds());
		//List<ApplySelf> applySelfs = selectMapper.selectHasApprovalList(approverTypeUnitIdListMap, approverTypePostIdListMap,10, new RowBounds());
		System.out.println(applySelfs.size() + "=========");
	}
}
