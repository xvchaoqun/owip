package controller.front.train;

import controller.BaseController;
import domain.train.TrainInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/train")
public class FrontTrainLoginController extends BaseController {


	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/login")
	public String login(HttpServletRequest request) {

		TrainInspector trainInspector = SessionUtils.getTrainInspector(request);
		if(trainInspector!=null){
			return "redirect:/train/index";
		}
		return "front/train/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> do_login(String username, String password,
										HttpServletRequest request) throws IOException {

		TrainInspector trainInspector = trainInspectorService.tryLogin(username, password);
		if(trainInspector==null){
			logger.info(sysLoginLogService.trainInspectorLoginlog(null, username,  false, "登录失败，账号或密码错误"));
			return failed("账号或密码错误");
		}

		logger.info(sysLoginLogService.trainInspectorLoginlog(trainInspector.getId(), username,  true, "登录成功"));

		SessionUtils.setTrainInspector(request, trainInspector);

		return success("登入成功");
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {

		TrainInspector trainInspector = SessionUtils.trainInspector_logout(request);

		logger.debug("logout success. {}", (trainInspector != null) ? trainInspector.getUsername() : "");

		return "redirect:/train/index";
	}

}
