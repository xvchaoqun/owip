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
    protected ICetMapper iCetMapper;
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
    protected CetPartyViewMapper cetPartyViewMapper;
    @Autowired(required = false)
    protected CetPartySchoolViewMapper cetPartySchoolViewMapper;
    @Autowired(required = false)
    protected CetProjectMapper cetProjectMapper;
    @Autowired(required = false)
    protected CetProjectViewMapper cetProjectViewMapper;
    @Autowired(required = false)
    protected CetProjectObjMapper cetProjectObjMapper;
    @Autowired(required = false)
    protected CetProjectObjViewMapper cetProjectObjViewMapper;
    @Autowired(required = false)
    protected CetProjectObjCadreViewMapper cetProjectObjCadreViewMapper;
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
    protected CetTrainCourseViewMapper cetTrainCourseViewMapper;
    @Autowired(required = false)
    protected CetTrainCourseStatViewMapper cetTrainCourseStatViewMapper;
    @Autowired(required = false)
    protected CetTraineeMapper cetTraineeMapper;
    @Autowired(required = false)
    protected CetTraineeViewMapper cetTraineeViewMapper;
    @Autowired(required = false)
    protected CetTraineeCadreViewMapper cetTraineeCadreViewMapper;
    @Autowired(required = false)
    protected CetTraineeCourseViewMapper cetTraineeCourseViewMapper;
    @Autowired(required = false)
    protected CetTraineeCourseCadreViewMapper cetTraineeCourseCadreViewMapper;
    @Autowired(required = false)
    protected CetTraineeCourseMapper cetTraineeCourseMapper;
    @Autowired(required = false)
    protected CetTraineeTypeMapper cetTraineeTypeMapper;
    @Autowired(required = false)
    protected CetProjectTraineeTypeMapper cetProjectTraineeTypeMapper;
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
