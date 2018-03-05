package controller.cet;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.cet.CetCourseService;
import service.cet.CetCourseTypeService;
import service.cet.CetExpertService;

/**
 * Created by lm on 2017/9/20.
 */
public class CetBaseController extends BaseController {

    @Autowired
    protected CetCourseService cetCourseService;
    @Autowired
    protected CetCourseTypeService cetCourseTypeService;
    @Autowired
    protected CetExpertService cetExpertService;
}
