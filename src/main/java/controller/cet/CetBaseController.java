package controller.cet;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.cet.CetColumnCourseService;
import service.cet.CetColumnService;
import service.cet.CetCourseFileService;
import service.cet.CetCourseItemService;
import service.cet.CetCourseService;
import service.cet.CetCourseTypeService;
import service.cet.CetDiscussGroupObjService;
import service.cet.CetDiscussGroupService;
import service.cet.CetDiscussService;
import service.cet.CetExpertService;
import service.cet.CetExportService;
import service.cet.CetPartySchoolService;
import service.cet.CetPartyService;
import service.cet.CetPlanCourseObjResultService;
import service.cet.CetPlanCourseObjService;
import service.cet.CetPlanCourseService;
import service.cet.CetProjectObjService;
import service.cet.CetProjectPlanService;
import service.cet.CetProjectService;
import service.cet.CetProjectTraineeTypeService;
import service.cet.CetShortMsgService;
import service.cet.CetTrainCourseService;
import service.cet.CetTrainEvaNormService;
import service.cet.CetTrainEvaRankService;
import service.cet.CetTrainEvaTableService;
import service.cet.CetTrainInspectorCourseService;
import service.cet.CetTrainInspectorService;
import service.cet.CetTrainService;
import service.cet.CetTrainStatService;
import service.cet.CetTraineeCourseService;
import service.cet.CetTraineeService;
import service.cet.CetTraineeTypeService;
import service.cet.CetUnitService;

public class CetBaseController extends BaseController {

    @Autowired
    protected CetUnitService cetUnitService;
    @Autowired
    protected CetPartyService cetPartyService;
    @Autowired
    protected CetPartySchoolService cetPartySchoolService;
    @Autowired
    protected CetProjectService cetProjectService;
    @Autowired
    protected CetProjectObjService cetProjectObjService;
    @Autowired
    protected CetDiscussService cetDiscussService;
    @Autowired
    protected CetDiscussGroupService cetDiscussGroupService;
    @Autowired
    protected CetDiscussGroupObjService cetDiscussGroupObjService;
    @Autowired
    protected CetProjectPlanService cetProjectPlanService;
    @Autowired
    protected CetPlanCourseService cetPlanCourseService;
    @Autowired
    protected CetPlanCourseObjService cetPlanCourseObjService;
    @Autowired
    protected CetPlanCourseObjResultService cetPlanCourseObjResultService;
    @Autowired
    protected CetCourseService cetCourseService;
    @Autowired
    protected CetCourseFileService cetCourseFileService;
    @Autowired
    protected CetCourseItemService cetCourseItemService;
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
    protected CetProjectTraineeTypeService cetProjectTraineeTypeService;
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
