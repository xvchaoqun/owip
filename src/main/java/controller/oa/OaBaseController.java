package controller.oa;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.oa.OaTaskMsgService;
import service.oa.OaTaskRemindService;
import service.oa.OaTaskService;
import service.oa.OaTaskUserService;

/**
 * Created by lm on 2017/9/20.
 */
public class OaBaseController extends BaseController {

    @Autowired
    protected OaTaskService oaTaskService;
    @Autowired
    protected OaTaskMsgService oaTaskMsgService;
    @Autowired
    protected OaTaskUserService oaTaskUserService;
    @Autowired
    protected OaTaskRemindService oaTaskRemindService;
}
