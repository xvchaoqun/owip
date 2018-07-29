package controller.sc.scMotion;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scMotion.ScMotionPostService;
import service.sc.scMotion.ScMotionService;

public class ScMotionBaseController extends BaseController {

    @Autowired
    protected ScMotionService scMotionService;
    @Autowired
    protected ScMotionPostService scMotionPostService;

}
