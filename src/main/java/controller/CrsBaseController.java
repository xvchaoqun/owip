package controller;

import org.springframework.beans.factory.annotation.Autowired;
import service.crs.CrsApplicantCheckService;
import service.crs.CrsApplicantService;
import service.crs.CrsApplyRuleService;
import service.crs.CrsCandidateService;
import service.crs.CrsExpertService;
import service.crs.CrsExportService;
import service.crs.CrsPostExpertService;
import service.crs.CrsPostFileService;
import service.crs.CrsPostRequireService;
import service.crs.CrsPostService;
import service.crs.CrsRequireRuleService;
import service.crs.CrsRuleItemService;
import service.crs.CrsShortMsgService;
import service.crs.CrsTemplateService;

/**
 * Created by lm on 2017/9/20.
 */
public class CrsBaseController extends BaseController {

    @Autowired
    protected CrsPostService crsPostService;
    @Autowired
    protected CrsCandidateService crsCandidateService;
    @Autowired
    protected CrsApplyRuleService crsApplyRuleService;
    @Autowired
    protected CrsShortMsgService crsShortMsgService;
    @Autowired
    protected CrsPostRequireService crsPostRequireService;
    @Autowired
    protected CrsRequireRuleService crsRequireRuleService;
    @Autowired
    protected CrsRuleItemService crsRuleItemService;
    @Autowired
    protected CrsPostExpertService crsPostExpertService;
    @Autowired
    protected CrsTemplateService crsTemplateService;
    @Autowired
    protected CrsExpertService crsExpertService;
    @Autowired
    protected CrsApplicantService crsApplicantService;
    @Autowired
    protected CrsExportService crsExportService;
    @Autowired
    protected CrsApplicantCheckService crsApplicantCheckService;
    @Autowired
    protected CrsPostFileService crsPostFileService;
}
