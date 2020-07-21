package controller.dr.user;

import controller.dr.DrBaseController;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.dr.common.DrTempResult;
import sys.constants.DrConstants;
import sys.constants.SystemConstants;
import sys.helper.DrHelper;
import sys.utils.FormUtils;
import sys.utils.HttpRequestDeviceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user/dr")
@Controller
public class UserDrOnlineController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //登录地址
    @RequestMapping("/login")
    public String login(String username, String passwd,
                              HttpServletRequest request, ModelMap modelMap){

        if(HttpRequestDeviceUtils.isMobileDevice(request)) {

            if (StringUtils.isNotBlank(username)) {
                DrOnlineInspector inspector = drOnlineInspectorService.tryLogin(StringUtils.trimToNull(username), StringUtils.trimToNull(passwd));
                if (inspector == null) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，账号或密码错误！"));
                    modelMap.put("error", "账号或密码错误");
                }
                if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，用户已完成测评！"));
                    modelMap.put("error", "该账号已完成推荐");
                }
            }

            return "dr/drOnline/mobile/login";
        }

        return "dr/drOnline/user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> do_login(String username, String passwd,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {

        if (StringUtils.isNotBlank(username)){

            DrOnlineInspector inspector = drOnlineInspectorService.tryLogin(StringUtils.trimToNull(username), StringUtils.trimToNull(passwd));
            if (inspector == null){
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_NET, false, "登录失败，账号或密码错误！"));
                return failed("账号或密码错误");
            }else {
                if (inspector.getDrOnline().getStatus() == DrConstants.DR_ONLINE_NOT_RELEASE){
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号对应的民主推荐未发布！"));
                    return failed("民主推荐未发布");
                }else if (inspector.getDrOnline().getStatus() == DrConstants.DR_ONLINE_WITHDRAW) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号对应的民主推荐暂时撤回！"));
                    return failed("民主推荐未开始");
                }else if (inspector.getDrOnline().getStatus() == DrConstants.DR_ONLINE_FINISH) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号对应的民主推荐已完成！"));
                    return failed("民主推荐已结束");
                }else if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_ABOLISH){
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号已作废！"));
                    return failed("该账号已作废");
                }else if (inspector.getPubStatus() == DrConstants.INSPECTOR_PUB_STATUS_NOT_RELEASE) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号还未发布！"));
                    return failed("该账号未发布");
                }else if (inspector.getStatus() == DrConstants.INSPECTOR_STATUS_FINISH) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，该账号已完成民主推荐！"));
                    return failed("该账号已完成推荐");
                }else if (inspector.getDrOnline().getEndTime().before(new Date())) {
                    logger.info(sysLoginLogService.log(null, username,
                            SystemConstants.LOGIN_TYPE_NET, false, "登录失败，民主推荐已截止！"));
                    return failed("民主推荐已结束");
                }
            }
            logger.info(sysLoginLogService.log(null, username,
                    SystemConstants.LOGIN_TYPE_NET, false, "登录成功！"));
            DrHelper.setDrInspector(request, inspector);
        }

        String successUrl=request.getContextPath() + "/user/dr/index";

        Map<String, Object> resultMap = success("登录成功");
        resultMap.put("url", successUrl);
        return resultMap;
    }

    @RequestMapping("/logout")
    public String logout(Byte isMobile, HttpServletRequest request) {

        DrOnlineInspector inspector =DrHelper.drInspector_logout(request);

        logger.debug("logout success. {}", (inspector != null) ? inspector.getUsername() : "");

        return "redirect:/user/dr/login";
    }

    @RequestMapping("/index")
    public String index(Byte isMobile, ModelMap modelMap, HttpServletRequest request){

        DrOnlineInspector _inspector = DrHelper.getDrInspector(request);
        //获取最新数据
        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(_inspector.getId());

        if (inspector != null) {
            Integer onlineId = inspector.getOnlineId();
            modelMap.put("inspector", inspector);
            modelMap.put("drOnline", inspector.getDrOnline());
            List<DrOnlinePostView> postViews = drOnlinePostService.getNeedRecommend(inspector);
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
                return "dr/drOnline/mobile/login";

            return "dr/drOnline/user/login";
        }
        return "dr/drOnline/user/index";
    }

    @RequestMapping("/changePasswd")
    @ResponseBody
    public Map drOnline_changePasswd(String oldPasswd,
                                     String passwd,
                                     HttpServletRequest request){

        DrOnlineInspector inspector = DrHelper.getDrInspector(request);

        if (inspector == null){
            return failed(FormUtils.ILLEGAL);
        }
        if (!drOnlineInspectorService.checkStatus(inspector))
            return failed("修改密码失败，请重新登录");
        if (!StringUtils.equalsIgnoreCase(inspector.getPasswd(), oldPasswd)){
            return failed(FormUtils.WRONG);
        }

        drOnlineInspectorService.changePasswd(inspector.getId(), passwd, DrConstants.INSPECTOR_PASSWD_CHANGE_TYPE_SELF);

        return success(FormUtils.SUCCESS);
    }

      @RequestMapping(value = "/agree", method = RequestMethod.POST)
    @ResponseBody
    public Map agree(@RequestParam(defaultValue = "false") Boolean agree,
                     Byte isMobile,
                     HttpServletRequest request){

        DrOnlineInspector _inspector = DrHelper.getDrInspector(request);

        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(_inspector.getId());

        DrTempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());

        tempResult.setInspectorId(inspector.getId());
        tempResult.setAgree(agree);
        tempResult.setMobileAgree((isMobile != null && isMobile == 1) ? agree : false);

        DrOnlineInspector record = new DrOnlineInspector();
        String tempData = drCommonService.getStringTemp(tempResult);

        record.setId(inspector.getId());
        record.setTempdata(tempData);
        record.setStatus(DrConstants.INSPECTOR_STATUS_SAVE);
        record.setIsMobile((isMobile != null && isMobile == 1) ? agree : false);

        drOnlineInspectorService.updateByExampleSelectiveBeforeSubmit(record);

        logger.info(String.format("%s已阅读说明", inspector.getUsername()));
        return success(FormUtils.SUCCESS);
    }

    //处理-保存/提交推荐数据
    @RequestMapping(value = "/doTempSave", method = RequestMethod.POST)
    @ResponseBody
    public Map tempSaveSurvey(@RequestParam(required = false, value = "datas[]") String[] datas,
                              @RequestParam(required = false, value = "others[]") String[] others,
                              Boolean isMoblie,
                              Integer inspectorId,
                              Integer isSubmit,
                              Integer onlineId, HttpServletRequest request) throws Exception {

        DrOnlineInspector _inspector = DrHelper.getDrInspector(request);
        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(_inspector.getId());
        inspectorId = inspector.getId();

        //投票时，防止管理员操作，实时读取批次的状态
        if (!drOnlineInspectorService.checkStatus(inspector))
            return failed("操作失败，请重新登录");

        //临时数据
        DrTempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());

        //得到票数
        Integer postId = null;
        Integer userId = null;
        Integer option = null;
        Map<String, Integer> optionMap = new HashMap<>();
        if (datas != null && datas.length > 0) {
            for (String data : datas) {
                String[] results = StringUtils.split(data, "_");
                postId = Integer.valueOf(results[0]);
                userId = Integer.valueOf(results[1]);
                option = Integer.valueOf(results[2]);

                optionMap.put(postId + "_" + userId, option);
            }
            tempResult.setRawOptionMap(optionMap);
        }

        if (others != null && others.length > 0) {
            Map<Integer, String> otherResultMap = drOnlineResultService.consoleOthers(others, datas);
            tempResult.setOtherResultMap(otherResultMap);
        }else{
            if(null != tempResult.getOtherResultMap() && tempResult.getOtherResultMap().size() > 0)
                tempResult.getOtherResultMap().clear();
        }

        //格式转化
        DrOnlineInspector record = new DrOnlineInspector();
        String tempData = drCommonService.getStringTemp(tempResult);

        record.setId(inspectorId);
        record.setTempdata(tempData);
        record.setStatus(DrConstants.INSPECTOR_STATUS_SAVE);
        record.setIsMobile(isMoblie);

        if (isSubmit == 1) {
            logger.info(String.format("%s保存并提交批次为%s的测评结果", inspector.getUsername(), inspector.getDrOnline().getCode()));
        }else {
            logger.info(String.format("%s保存批次为%s的测评结果", inspector.getUsername(), inspector.getDrOnline().getCode()));
        }

        return drOnlineResultService.saveOrSubmit(isMoblie, isSubmit, inspectorId, record, request) ? success(FormUtils.SUCCESS) : failed(FormUtils.FAILED);
    }
}
