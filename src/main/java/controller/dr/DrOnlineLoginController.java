package controller.dr;

import persistence.dr.common.DrTempResult;
import domain.dr.DrOnlineCandidate;
import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlinePostView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.DrConstants;
import sys.constants.SystemConstants;
import sys.helper.DrHelper;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/dr/drOnline")
@Controller
public class DrOnlineLoginController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*
        登录地址
        pc 端：localhost:8080/dr/drOnline/login
        手机端：localhost:8080/dr/drOnline/iLogin
    * */
    //手机端登录
    @RequestMapping("/iLogin")
    public String mobileLogin(String username, String passwd,
                              HttpServletRequest request, ModelMap modelMap){

        if (StringUtils.isNotBlank(username)) {
            DrOnlineInspector inspector = drOnlineInspectorService.tryLogin(StringUtils.trimToNull(username), StringUtils.trimToNull(passwd));
            if (inspector == null) {
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_NET, false, "登录失败，账号或密码错误！"));
                modelMap.put("error", "账号或密码错误！");
            }
            if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH) {
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_NET, false, "登录失败，用户已完成测评！"));
                modelMap.put("error", "该账号已测评完成！");
            }
        }

        return "dr/drOnline/mobile/login";
    }

    //pc端登录
    @RequestMapping("/login")
    public String drOnlineLogin(HttpServletRequest request, ModelMap modelMap){

        Map<Integer, DrOnlineInspector> inspectorMap = drOnlineInspectorService.findAll();

        modelMap.put("inspectorMap", inspectorMap);

        return "dr/drOnline/user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> do_drOnlineLogin(String username, String passwd,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {

        if (StringUtils.isNotBlank(username)){

            DrOnlineInspector inspector = drOnlineInspectorService.tryLogin(StringUtils.trimToNull(username), StringUtils.trimToNull(passwd));
            if (inspector == null){
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_NET, false, "登录失败，账号或密码错误！"));
                return failed("账号或密码错误！");
            }else {
                if (inspector.getDrOnline().getStatus() == DrConstants.DR_ONLINE_NOT_RELEASE){
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号对应的民主推荐未发布！"));
                    return failed("该账号对应的民主推荐未发布！");
                }else if (inspector.getDrOnline().getStatus() == DrConstants.DR_ONLINE_WITHDRAW) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号对应的民主推荐暂时撤回！"));
                    return failed("该账号对应的民主推荐未发布！");
                }else if (inspector.getDrOnline().getStatus() == DrConstants.DR_ONLINE_FINISH) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号对应的民主推荐已完成！"));
                    return failed("该账号对应的民主推荐已完成！");
                }else if (inspector.getPubStatus() == DrConstants.INSPECTOR_PUB_STATUS_NOT_RELEASE) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号还未发布！"));
                    return failed("该账号未发布！");
                }else if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_ABOLISH){
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号已作废！"));
                    return failed("该账号已作废！");
                }else if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号已完成民主推荐！"));
                    return failed("该账号已完成民主推荐！");
                }else if (inspector.getDrOnline().getEndTime().before(new Date())) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，民主推荐已截止！"));
                    return failed("该账号对应的民主推荐已截止！");
                }
            }
            logger.info(sysLoginLogService.log(null, username,
                    SystemConstants.LOGIN_TYPE_NET, false, "登录成功！"));
            DrHelper.setDrInspector(request, inspector);
        }

        String successUrl=request.getContextPath() + "/dr/drOnline/drOnlineIndex";

        Map<String, Object> resultMap = success("登录成功");
        resultMap.put("url", successUrl);
        return resultMap;
    }

    @RequestMapping("/logout")
    public String logout(Byte isMobile, HttpServletRequest request) {

        DrOnlineInspector inspector =DrHelper.drInspector_logout(request);

        logger.debug("logout success. {}", (inspector != null) ? inspector.getUsername() : "");

        if (isMobile != null && isMobile == 1){
            return "redirect:/dr/drOnline/iLogin";
        }
        return "redirect:/dr/drOnline/login";
    }

    @RequestMapping("/drOnlineIndex")
    public String drOnlineIndex(Byte isMobile, ModelMap modelMap, HttpServletRequest request){

        DrOnlineInspector _inspector = DrHelper.getDrInspector(request);
        //获取最新数据
        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(_inspector.getId());

        if (inspector != null) {
            Integer onlineId = inspector.getOnlineId();
            modelMap.put("inspector", inspector);
            modelMap.put("drOnline", inspector.getDrOnline());
            List<DrOnlinePostView> postViews = drOnlinePostService.getAllByOnlineId(onlineId);
            modelMap.put("postViews", postViews);
            Map<Integer, List<DrOnlineCandidate>> candidateMap =  drOnlineCandidateService.findAll(onlineId);
            modelMap.put("candidateMap", candidateMap);
            DrTempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());
            modelMap.put("tempResult", tempResult);
            if (isMobile != null && isMobile == 1){
                return "dr/drOnline/mobile/index";
            }
        }else {
            if (isMobile != null && isMobile == 1)
                return "dr/drOnline/mobile/iLogin";

            return "dr/drOnline/user/login";
        }
        return "dr/drOnline/user/drOnlineIndex";
    }

    @RequestMapping("/user/changePasswd")
    @ResponseBody
    public Map drOnline_changePasswd(String oldPasswd,
                                     String passwd,
                                     HttpServletRequest request){

        DrOnlineInspector inspector = DrHelper.getDrInspector(request);

        if (inspector == null){
            return failed(FormUtils.ILLEGAL);
        }
        if (!drOnlineInspectorService.checkStatus(inspector))
            return failed("线上民主推荐内容更新，修改密码失败，请重新登录！");
        if (!StringUtils.equalsIgnoreCase(inspector.getPasswd(), oldPasswd)){
            return failed(FormUtils.WRONG);
        }

        drOnlineInspectorService.changePasswd(inspector.getId(), passwd, DrConstants.INSPECTOR_PASSWD_CHANGE_TYPE_SELF);

        return success(FormUtils.SUCCESS);
    }

}
