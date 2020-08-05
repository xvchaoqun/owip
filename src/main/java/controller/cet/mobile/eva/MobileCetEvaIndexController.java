package controller.cet.mobile.eva;

import controller.cet.CetBaseController;
import domain.cet.*;
import domain.sys.SysUserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.cet.common.ICetTrainCourse;
import sys.helper.CetHelper;
import sys.tags.CmTag;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m/cet_eva")
public class MobileCetEvaIndexController extends CetBaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/index")
	public String index() {

		return "cet/mobile/eva/index";
	}

	@RequestMapping("/")
	public String _index() {

		return "redirect:/m/cet_eva/index";
	}

	@RequestMapping("/index_page")
	public String index_page(HttpServletRequest request, ModelMap modelMap) {

		CetTrainInspector trainInspector = CetHelper.getTrainInspector(request);
		Integer trainId = trainInspector.getTrainId();
		CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

		Map<Integer, CetTrainCourse> trainCourseMap = new LinkedHashMap<>();
		if(cetTrain.getIsOnCampus()){

			// 校内培训，读取已选课程
			String code = trainInspector.getMobile();
			SysUserView uv = CmTag.getUserByCode(code);
			int userId = uv.getId();
			List<ICetTrainCourse> cetTrainCourses = iCetMapper.selectedCetTrainCourses(trainId, null, userId);
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

		return "cet/mobile/eva/index_page";
	}

}
