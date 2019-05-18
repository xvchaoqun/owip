package controller.abroad;

import org.springframework.beans.factory.annotation.Autowired;
import service.abroad.*;
import service.base.CountryService;
import service.base.MetaTypeService;
import service.base.ShortMsgService;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.leader.LeaderService;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
import sys.HttpResponseMethod;

/**
 * Created by lm on 2017/9/20.
 */
public class AbroadBaseController extends AbroadBaseMapper implements HttpResponseMethod {
    
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
    protected MetaTypeService metaTypeService;
    
    @Autowired
    protected AbroadService abroadService;
    @Autowired
    protected ApplicatTypeService applicatTypeService;
    @Autowired
    protected ApprovalOrderService approvalOrderService;
    @Autowired
    protected ApproverService approverService;
    @Autowired
    protected ApproverBlackListService approverBlackListService;
    @Autowired
    protected ApproverTypeService approverTypeService;
    
    @Autowired
    protected AbroadAdditionalPostService abroadAdditionalPostService;
    
    @Autowired
    protected PassportDrawService passportDrawService;
    @Autowired
    protected ApprovalLogService approvalLogService;
    @Autowired
    protected ApplySelfService applySelfService;
    @Autowired
    protected PassportApplyService passportApplyService;
    @Autowired
    protected PassportService passportService;
    @Autowired
    protected SafeBoxService safeBoxService;
    @Autowired
    protected TaiwanRecordService taiwanRecordService;
    @Autowired
    protected AbroadExportService abroadExportService;
    
    @Autowired
    protected AbroadShortMsgService abroadShortMsgService;
}
