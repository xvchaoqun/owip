package controller.cla;

import org.springframework.beans.factory.annotation.Autowired;
import service.base.CountryService;
import ext.service.ShortMsgService;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.cla.*;
import service.leader.LeaderService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
import sys.HttpResponseMethod;

public class ClaBaseController extends ClaBaseMapper implements HttpResponseMethod {
    
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected LeaderService cadreLeaderService;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;
    @Autowired
    protected CountryService countryService;
    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected CadreCommonService cadreCommonService;
    
    @Autowired
    protected ClaApplicatTypeService claApplicatTypeService;
    @Autowired
    protected ClaApprovalOrderService claApprovalOrderService;
    @Autowired
    protected ClaApproverService claApproverService;
    @Autowired
    protected ClaApproverBlackListService claApproverBlackListService;
    @Autowired
    protected ClaApproverTypeService claApproverTypeService;
    
    @Autowired
    protected ClaAdditionalPostService claAdditionalPostService;
    
    @Autowired
    protected ClaApprovalLogService claApprovalLogService;
    @Autowired
    protected ClaApplyService claApplyService;
    @Autowired
    protected ClaExportService claExportService;
    
    @Autowired
    protected ClaShortMsgService claShortMsgService;
}
