package controller.mobile.train;

import controller.train.TrainBaseController;
import domain.train.Train;
import domain.train.TrainCourse;
import domain.train.TrainInspector;
import domain.train.TrainInspectorCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/m_train")
public class MobileTrainIndexController extends TrainBaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/index")
	public String index() {

		return "mobile/train/index";
	}

	@RequestMapping("/")
	public String _index() {

		return "redirect:/m_train/index";
	}

	@RequestMapping("/index_page")
	public String index_page(HttpServletRequest request, ModelMap modelMap) {

		TrainInspector trainInspector = SessionUtils.getTrainInspector(request);
		Integer trainId = trainInspector.getTrainId();
		Train train = trainMapper.selectByPrimaryKey(trainId);
		Map<Integer, TrainCourse> trainCourseMap = trainCourseService.findAll(trainId);
		Map<Integer, TrainInspectorCourse> ticMap = trainInspectorCourseService.get(trainInspector.getId());
		modelMap.put("train", train);
		modelMap.put("trainCourseMap", trainCourseMap);
		modelMap.put("ticMap", ticMap);

		return "mobile/train/index_page";
	}

}
