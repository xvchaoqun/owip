package controller.oa;

import ext.service.ShortMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import service.LoginUserService;
import service.SpringProps;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.oa.*;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import sys.HttpResponseMethod;

/**
 * Created by lm on 2017/9/20.
 */
public class OaBaseController extends OaBaseMapper implements HttpResponseMethod {

    @Autowired
    protected LoginUserService loginUserService;
    @Autowired
    protected SpringProps springProps;

    @Autowired
    protected OaGridService oaGridService;
    @Autowired
    protected OaGridPartyService oaGridPartyService;
    @Autowired
    protected OaGridPartyDataService oaGridPartyDataService;

    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected CadreService cadreService;
    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected CadreCommonService cadreCommonService;
    
    @Autowired
    protected OaTaskService oaTaskService;
    @Autowired
    protected OaTaskUserService oaTaskUserService;
    @Autowired
    protected OaTaskRemindService oaTaskRemindService;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;
}
