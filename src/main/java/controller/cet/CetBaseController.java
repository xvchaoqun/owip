package controller.cet;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.cet.CetColumnCourseService;
import service.cet.CetColumnService;
import service.cet.CetCourseService;
import service.cet.CetCourseTypeService;
import service.cet.CetExpertService;
import service.cet.CetTrainService;
import service.cet.CetTrainTraineeTypeService;
import service.cet.CetTraineeTypeService;

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
    @Autowired
    protected CetColumnService cetColumnService;
    @Autowired
    protected CetColumnCourseService cetColumnCourseService;
    @Autowired
    protected CetTrainService cetTrainService;
    @Autowired
    protected CetTraineeTypeService cetTraineeTypeService;
    @Autowired
    protected CetTrainTraineeTypeService cetTrainTraineeTypeService;
}
