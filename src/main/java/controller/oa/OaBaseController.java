package controller.oa;

import org.springframework.beans.factory.annotation.Autowired;
import service.base.ShortMsgService;
import service.cadre.CadreCommonService;
import service.cadre.CadreService;
import service.oa.*;
import service.sys.SysUserService;
import sys.HttpResponseMethod;

/**
 * Created by lm on 2017/9/20.
 */
public class OaBaseController extends OaBaseMapper implements HttpResponseMethod {

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
    protected OaTaskAdminService oaTaskAdminService;
    @Autowired
    protected OaTaskUserService oaTaskUserService;
    @Autowired
    protected OaTaskRemindService oaTaskRemindService;
}
