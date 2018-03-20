package controller.cet.mobile;

import controller.cet.CetBaseController;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainInspector;
import domain.cet.CetTrainInspectorCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.SessionUtils;

import javax.servlet.http.HttpServletRequest;
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
		CetTrain train = cetTrainMapper.selectByPrimaryKey(trainId);
		Map<Integer, CetTrainCourse> trainCourseMap = cetTrainCourseService.findAll(trainId);
		Map<Integer, CetTrainInspectorCourse> ticMap = cetTrainInspectorCourseService.get(trainInspector.getId());
		modelMap.put("train", train);
		modelMap.put("trainCourseMap", trainCourseMap);
		modelMap.put("ticMap", ticMap);

		return "cet/mobile/index_page";
	}

}
