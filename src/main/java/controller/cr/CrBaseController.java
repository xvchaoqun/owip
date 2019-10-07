package controller.cr;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.sc.IScMapper;
import service.base.ContentTplService;
import service.cadre.CadreInfoFormService;
import service.cadre.CadreService;
import service.cr.*;
import service.crs.CrsApplicantService;
import service.sys.SysUserService;
import service.unit.UnitPostService;
import service.unit.UnitService;
import sys.HttpResponseMethod;

public class CrBaseController extends CrBaseMapper implements HttpResponseMethod {
    
    
    @Autowired
    protected CadreInfoFormService cadreInfoFormService;
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
    @Autowired(required = false)
    protected IScMapper iScMapper;
    
    @Autowired
    protected CrsApplicantService crsApplicantService;

    @Autowired
    protected CrInfoService crInfoService;
    @Autowired
    protected CrPostService crPostService;
    @Autowired
    protected CrApplicantService crApplicantService;

    @Autowired
    protected CrRequireService crRequireService;
    @Autowired
    protected CrRequireRuleService crRequireRuleService;
    @Autowired
    protected CrRuleItemService crRuleItemService;
    @Autowired
    protected CrApplicantCheckService crApplicantCheckService;
    @Autowired
    protected CrMeetingService crMeetingService;
    @Autowired
    protected CrExportService crExportService;
}
