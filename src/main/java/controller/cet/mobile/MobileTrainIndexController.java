package controller.cet.mobile;

import controller.cet.CetBaseController;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainInspector;
import domain.cet.CetTrainInspectorCourse;
import domain.cet.CetTraineeView;
import domain.sys.SysUserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.cet.common.ICetTrainCourse;
import sys.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m/cet")
public class MobileTrainIndexController extends CetBaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/index")
	public String index() {

		return "cet/mobile/index";
	}

	@RequestMapping("/")
	public String _index() {

		return "redirect:/m/cet/index";
	}

	@RequestMapping("/index_page")
	public String index_page(HttpServletRequest request, ModelMap modelMap) {

		CetTrainInspector trainInspector = SessionUtils.getTrainInspector(request);
		Integer trainId = trainInspector.getTrainId();
		CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

		Map<Integer, CetTrainCourse> trainCourseMap = new HashMap<>();
		if(cetTrain.getIsOnCampus()){

			// 校内培训，读取已选课程
			String code = trainInspector.getMobile();
			SysUserView uv = sysUserService.findByCode(code);
			int userId = uv.getUserId();
			CetTraineeView cetTrainee = cetTraineeService.get(userId, trainId);
			int traineeId = cetTrainee.getId();
			List<ICetTrainCourse> cetTrainCourses = iCetMapper.selectedCetTrainCourses(traineeId);
			for (CetTrainCourse cetTrainCourse : cetTrainCourses) {
				trainCourseMap.put(cetTrainCourse.getId(), cetTrainCourse);
			}

		}else {
			// 校外培训
			trainCourseMap = cetTrainCourseService.findAll(trainId);
		}

		Map<Integer, CetTrainInspectorCourse> ticMap = cetTrainInspectorCourseService.get(trainInspector.getId());
		modelMap.put("cetTrain", cetTrain);
		modelMap.put("trainCourseMap", trainCourseMap);
		modelMap.put("ticMap", ticMap);

		return "cet/mobile/index_page";
	}

}
