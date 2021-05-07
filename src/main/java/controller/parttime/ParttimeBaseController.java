package controller.parttime;

import ext.service.ShortMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import service.base.CountryService;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.leader.LeaderService;
import service.parttime.*;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
import sys.HttpResponseMethod;

public class ParttimeBaseController extends ParttimeBaseMapper implements HttpResponseMethod {
    
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
    protected ParttimeApproverTypeService parttimeApproverTypeService;
    @Autowired
    protected ParttimeApplicatTypeService parttimeApplicatTypeService;
    @Autowired
    protected ParttimeApprovalOrderService parttimeApprovalOrderService;

    @Autowired
    protected ParttimeApplyService parttimeApplyService;
    @Autowired
    protected ParttimeApprovalLogService parttimeApprovalLogService;
    @Autowired
    protected ParttimeExportService parttimeExportService;
}
