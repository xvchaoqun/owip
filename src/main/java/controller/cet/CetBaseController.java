package controller.cet;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.cet.CetColumnCourseService;
import service.cet.CetColumnService;
import service.cet.CetCourseService;
import service.cet.CetCourseTypeService;
import service.cet.CetExpertService;
import service.cet.CetExportService;
import service.cet.CetShortMsgService;
import service.cet.CetTrainCourseService;
import service.cet.CetTrainEvaNormService;
import service.cet.CetTrainEvaRankService;
import service.cet.CetTrainEvaTableService;
import service.cet.CetTrainInspectorCourseService;
import service.cet.CetTrainInspectorService;
import service.cet.CetTrainService;
import service.cet.CetTrainTraineeTypeService;
import service.cet.CetTraineeCourseService;
import service.cet.CetTraineeService;
import service.cet.CetTraineeTypeService;
import service.cet.CetTrainStatService;

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
    protected CetTrainCourseService cetTrainCourseService;
    @Autowired
    protected CetTraineeService cetTraineeService;
    @Autowired
    protected CetTraineeCourseService cetTraineeCourseService;
    @Autowired
    protected CetTraineeTypeService cetTraineeTypeService;
    @Autowired
    protected CetTrainTraineeTypeService cetTrainTraineeTypeService;
    @Autowired
    protected CetShortMsgService cetShortMsgService;
    @Autowired
    protected CetExportService cetExportService;

    @Autowired
    protected CetTrainStatService cetTrainStatService;

    @Autowired
    protected CetTrainEvaNormService cetTrainEvaNormService;
    @Autowired
    protected CetTrainEvaRankService cetTrainEvaRankService;
    @Autowired
    protected CetTrainEvaTableService cetTrainEvaTableService;
    @Autowired
    protected CetTrainInspectorService cetTrainInspectorService;
    @Autowired
    protected CetTrainInspectorCourseService cetTrainInspectorCourseService;
}
