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
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
    public String index(boolean isMobile, ModelMap modelMap, HttpServletRequest request){

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

            return (isMobile)?"dr/drOnline/mobile/index":"dr/drOnline/user/index";
        }

        return (isMobile)?"dr/drOnline/mobile/login":"dr/drOnline/user/login";
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

    // 处理-保存/提交推荐数据
    @RequestMapping(value = "/doTempSave", method = RequestMethod.POST)
    @ResponseBody
    public Map tempSaveSurvey(boolean isMobile, boolean isSubmit, HttpServletRequest request) throws Exception {

        DrOnlineInspector _inspector = DrHelper.getDrInspector(request);
        int inspectorId = _inspector.getId();
        DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(inspectorId);
        int onlineId = inspector.getOnlineId();
        // 投票时，防止管理员操作，实时读取批次的状态
        if (!drOnlineInspectorService.checkStatus(inspector))
            return failed("操作失败，请重新登录");

        // 临时数据
        DrTempResult tempResult = drCommonService.getTempResult(inspector.getTempdata());

        Map<String, Byte> candidateMap = tempResult.getCandidateMap();
        candidateMap.clear();
        Map<String, String> otherMap = tempResult.getOtherMap();
        otherMap.clear();
        Map<Integer, Set<String>> realnameSetMap = tempResult.getRealnameSetMap();
        realnameSetMap.clear();

        // 推荐结果数据
        List<DrOnlinePostView> postViews = drOnlinePostService.getNeedRecommend(inspector);
        Map<Integer, List<DrOnlineCandidate>> candidateListMap =  drOnlineCandidateService.findAll(onlineId);

        for (DrOnlinePostView post : postViews) {

            int postId = post.getId();
            List<DrOnlineCandidate> candidateList = candidateListMap.get(postId);

            // 有候选人的推荐结果
            for (DrOnlineCandidate candidate : candidateList) {

                int userId = candidate.getUserId();
                String radioName = postId + "_" + userId;
                String value = request.getParameter(radioName);
                Byte radioValue = (value==null)?null:Byte.valueOf(value);
                if(isSubmit && radioValue==null){
                    return failed("存在未完成推荐的职务（{0}）。", post.getName());
                }
                if(!DrConstants.RESULT_STATUS_MAP.containsKey(radioValue)
                        || radioValue==DrConstants.RESULT_STATUS_OTHER){
                    return failed("数据有误，请重试。");
                }
                if(radioValue!=null) {
                    candidateMap.put(radioName, radioValue);

                    if(radioValue!=1){
                        String otherRealname = request.getParameter(radioName+"_realname");
                        if(isSubmit && StringUtils.isBlank(otherRealname)){
                            return failed("存在未完成推荐的职务（{0}）。", post.getName());
                        }

                        if(otherRealname!=null) {
                            otherMap.put(radioName, otherRealname);
                        }
                    }
                }
            }

            // 无候选人的推荐结果
            for (int i = candidateList.size()+1; i <= post.getCompetitiveNum(); i++) {

                String radioName = postId + "_realname_" + i;
                String realname = request.getParameter(radioName);
                if(isSubmit && StringUtils.isBlank(realname)){
                    return failed("存在未完成推荐的职务（{0}）。", post.getName());
                }

                if(StringUtils.isNotBlank(realname)) {
                    Set<String> realnameSet = realnameSetMap.get(postId);
                    if(realnameSet==null){
                        realnameSet= new LinkedHashSet<>();
                        realnameSetMap.put(postId, realnameSet);
                    }

                    if (realnameSet.contains(realname)) {
                        return failed("推荐人姓名不能相同（{0}）。", post.getName());
                    }
                    realnameSet.add(realname);
                }
            }
        }

        // 格式转化
        String tempData = drCommonService.getStringTemp(tempResult);

        if(isSubmit){

            inspector.setTempdata(tempData);
            inspector.setIsMobile(isMobile);
            inspector.setSubmitIp(IpUtils.getRealIp(request));

            drOnlineResultService.submitResult(inspector);
        }else{

            DrOnlineInspector record = new DrOnlineInspector();
            record.setId(inspectorId);
            record.setStatus(DrConstants.INSPECTOR_STATUS_SAVE);
            record.setTempdata(tempData);
            record.setIsMobile(isMobile);
            record.setSubmitIp(IpUtils.getRealIp(request));

            drOnlineInspectorMapper.updateByPrimaryKeySelective(record);
        }

        logger.info(String.format("%s%s批次为%s的测评结果", inspector.getUsername(),
                isSubmit?"提交":"保存", inspector.getDrOnline().getCode()));

        return success(FormUtils.SUCCESS);
    }
}
