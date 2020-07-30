package service.cet;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.cet.*;
import persistence.cet.common.ICetMapper;
import service.CoreBaseMapper;

public class CetBaseMapper extends CoreBaseMapper {

    /**
     * 干部教育培训
     */
    @Autowired(required = false)
    protected CetRecordMapper cetRecordMapper;
    @Autowired(required = false)
    protected CetPartyAdminMapper cetPartyAdminMapper;
    @Autowired(required = false)
    protected ICetMapper iCetMapper;
    @Autowired(required = false)
    protected CetAnnualMapper cetAnnualMapper;
    @Autowired(required = false)
    protected CetAnnualObjMapper cetAnnualObjMapper;
    @Autowired(required = false)
    protected CetUnitProjectMapper cetUnitProjectMapper;
    @Autowired(required = false)
    protected CetUnitTrainMapper cetUnitTrainMapper;
    @Autowired(required = false)
    protected CetUnitMapper cetUnitMapper;
    @Autowired(required = false)
    protected CetPartyMapper cetPartyMapper;
    @Autowired(required = false)
    protected CetPartySchoolMapper cetPartySchoolMapper;
    @Autowired(required = false)
    protected CetUnitViewMapper cetUnitViewMapper;
    @Autowired(required = false)
    protected CetPartySchoolViewMapper cetPartySchoolViewMapper;
    @Autowired(required = false)
    protected CetProjectMapper cetProjectMapper;
    @Autowired(required = false)
    protected CetProjectFileMapper cetProjectFileMapper;
    @Autowired(required = false)
    protected CetProjectObjMapper cetProjectObjMapper;
    @Autowired(required = false)
    protected CetProjectObjPlanMapper cetProjectObjPlanMapper;
    @Autowired(required = false)
    protected CetDiscussMapper cetDiscussMapper;
    @Autowired(required = false)
    protected CetDiscussGroupMapper cetDiscussGroupMapper;
    @Autowired(required = false)
    protected CetDiscussGroupObjMapper cetDiscussGroupObjMapper;
    @Autowired(required = false)
    protected CetProjectPlanMapper cetProjectPlanMapper;
    @Autowired(required = false)
    protected CetPlanCourseMapper cetPlanCourseMapper;
    @Autowired(required = false)
    protected CetPlanCourseObjMapper cetPlanCourseObjMapper;
    @Autowired(required = false)
    protected CetPlanCourseObjResultMapper cetPlanCourseObjResultMapper;
    @Autowired(required = false)
    protected CetCourseMapper cetCourseMapper;
    @Autowired(required = false)
    protected CetCourseFileMapper cetCourseFileMapper;
    @Autowired(required = false)
    protected CetCourseItemMapper cetCourseItemMapper;
    @Autowired(required = false)
    protected CetProjectTypeMapper cetProjectTypeMapper;
    @Autowired(required = false)
    protected CetExpertMapper cetExpertMapper;
    @Autowired(required = false)
    protected CetExpertViewMapper cetExpertViewMapper;
    @Autowired(required = false)
    protected CetColumnMapper cetColumnMapper;
    @Autowired(required = false)
    protected CetColumnViewMapper cetColumnViewMapper;
    @Autowired(required = false)
    protected CetColumnCourseMapper cetColumnCourseMapper;
    @Autowired(required = false)
    protected CetColumnCourseViewMapper cetColumnCourseViewMapper;
    @Autowired(required = false)
    protected CetTrainMapper cetTrainMapper;
    @Autowired(required = false)
    protected CetTrainViewMapper cetTrainViewMapper;
    @Autowired(required = false)
    protected CetTrainCourseMapper cetTrainCourseMapper;
    @Autowired(required = false)
    protected CetTrainCourseFileMapper cetTrainCourseFileMapper;
    @Autowired(required = false)
    protected CetTrainCourseStatViewMapper cetTrainCourseStatViewMapper;
    @Autowired(required = false)
    protected CetTrainObjViewMapper cetTrainObjViewMapper;
    @Autowired(required = false)
    protected CetTrainObjMapper cetTrainObjMapper;
    @Autowired(required = false)
    protected CetTraineeViewMapper cetTraineeViewMapper;
    @Autowired(required = false)
    protected CetTraineeTypeMapper cetTraineeTypeMapper;
    @Autowired(required = false)
    protected CetShortMsgMapper cetShortMsgMapper;
    @Autowired(required = false)
    protected CetTrainEvaNormMapper cetTrainEvaNormMapper;
    @Autowired(required = false)
    protected CetTrainEvaRankMapper cetTrainEvaRankMapper;
    @Autowired(required = false)
    protected CetTrainEvaTableMapper cetTrainEvaTableMapper;
    @Autowired(required = false)
    protected CetTrainEvaResultMapper cetTrainEvaResultMapper;
    @Autowired(required = false)
    protected CetTrainInspectorMapper cetTrainInspectorMapper;
    @Autowired(required = false)
    protected CetTrainInspectorViewMapper cetTrainInspectorViewMapper;
    @Autowired(required = false)
    protected CetTrainInspectorCourseMapper cetTrainInspectorCourseMapper;
    @Autowired(required = false)
    protected CetUpperTrainMapper cetUpperTrainMapper;
    @Autowired(required = false)
    protected CetUpperTrainAdminMapper cetUpperTrainAdminMapper;
}
