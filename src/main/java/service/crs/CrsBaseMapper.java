package service.crs;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.crs.*;
import persistence.crs.common.ICrsMapper;
import service.CoreBaseMapper;

public class CrsBaseMapper extends CoreBaseMapper {

    /**
     * 干部招聘
     */
    @Autowired(required = false)
    protected CrsTemplateMapper crsTemplateMapper;
    @Autowired(required = false)
    protected CrsExpertMapper crsExpertMapper;
    @Autowired(required = false)
    protected CrsExpertViewMapper crsExpertViewMapper;
    @Autowired(required = false)
    protected CrsApplicantMapper crsApplicantMapper;
    @Autowired(required = false)
    protected CrsApplicantViewMapper crsApplicantViewMapper;
    @Autowired(required = false)
    protected CrsApplicantStatViewMapper crsApplicantStatViewMapper;
    @Autowired(required = false)
    protected CrsApplicantAdjustMapper crsApplicantAdjustMapper;
    @Autowired(required = false)
    protected CrsApplicantAdjustViewMapper crsApplicantAdjustViewMapper;
    @Autowired(required = false)
    protected CrsApplicantCheckMapper crsApplicantCheckMapper;
    @Autowired(required = false)
    protected CrsPostMapper crsPostMapper;
    @Autowired(required = false)
    protected CrsApplyUserMapper crsApplyUserMapper;
    @Autowired(required = false)
    protected CrsCandidateViewMapper crsCandidateViewMapper;
    @Autowired(required = false)
    protected CrsCandidateMapper crsCandidateMapper;
    @Autowired(required = false)
    protected CrsApplyRuleMapper crsApplyRuleMapper;
    @Autowired(required = false)
    protected CrsShortMsgMapper crsShortMsgMapper;
    @Autowired(required = false)
    protected CrsPostRequireMapper crsPostRequireMapper;
    @Autowired(required = false)
    protected CrsRequireRuleMapper crsRequireRuleMapper;
    @Autowired(required = false)
    protected CrsRuleItemMapper crsRuleItemMapper;
    @Autowired(required = false)
    protected CrsPostExpertMapper crsPostExpertMapper;
    @Autowired(required = false)
    protected CrsPostFileMapper crsPostFileMapper;
    @Autowired(required = false)
    protected ICrsMapper iCrsMapper;
}
