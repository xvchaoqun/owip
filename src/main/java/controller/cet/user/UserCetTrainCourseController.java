package controller.cet.user;

import controller.cet.CetBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/cet")
public class UserCetTrainCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


}
