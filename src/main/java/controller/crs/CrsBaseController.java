package controller.crs;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.sc.IScMapper;
import service.base.ContentTplService;
import service.cadre.CadreService;
import service.crs.*;
import service.sys.SysUserService;
import service.unit.UnitPostService;
import service.unit.UnitService;
import sys.HttpResponseMethod;

/**
 * Created by lm on 2017/9/20.
 */
public class CrsBaseController extends CrsBaseMapper implements HttpResponseMethod {
    
    
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected ContentTplService contentTplService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected UnitPostService unitPostService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected IScMapper iScMapper;
    
    @Autowired
    protected CrsPostService crsPostService;
    @Autowired
    protected CrsCandidateService crsCandidateService;
    @Autowired
    protected CrsApplyUserService crsApplyUserService;
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
