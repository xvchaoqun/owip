package controller.cet;

import org.springframework.beans.factory.annotation.Autowired;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.cadreReserve.CadreReserveService;
import service.cet.*;
import service.member.MemberApplyService;
import service.party.BranchMemberService;
import service.party.PartyMemberService;
import service.sys.SysApprovalLogService;
import service.sys.SysLoginLogService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
import sys.HttpResponseMethod;

public class CetBaseController extends CetBaseMapper implements HttpResponseMethod {

    @Autowired
    protected SysApprovalLogService sysApprovalLogService;
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected CadreReserveService cadreReserveService;
    @Autowired(required = false)
    protected MemberApplyService memberApplyService;
    @Autowired
    protected PartyMemberService partyMemberService;
    @Autowired
    protected BranchMemberService branchMemberService;
    @Autowired
    protected CadreCommonService cadreCommonService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected SysLoginLogService sysLoginLogService;
    
    @Autowired
    protected CetAnnualService cetAnnualService;
    @Autowired
    protected CetAnnualObjService cetAnnualObjService;
    @Autowired
    protected CetUnitProjectService cetUnitProjectService;
    @Autowired
    protected CetUnitTrainService cetUnitTrainService;
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
    protected CetProjectTypeService cetProjectTypeService;
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

    @Autowired
    protected CetUpperTrainAdminService cetUpperTrainAdminService;
    @Autowired
    protected CetUpperTrainService cetUpperTrainService;
}
