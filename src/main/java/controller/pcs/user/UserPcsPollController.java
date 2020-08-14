package controller.pcs.user;

import controller.pcs.PcsBaseController;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPoll;
import domain.pcs.PcsPollCandidate;
import domain.pcs.PcsPollInspector;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.pcs.common.PcsTempResult;
import service.sys.SysLoginLogService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.helper.PcsHelper;
import sys.utils.FormUtils;
import sys.utils.HttpRequestDeviceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequestMapping("/user/pcs")
@Controller
public class UserPcsPollController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysLoginLogService sysLoginLogService;

    //登录地址
    @RequestMapping("/login")
    public String login(String u, String p,
                              HttpServletRequest request, ModelMap modelMap){

        if(HttpRequestDeviceUtils.isMobileDevice(request)) {

            return "pcs/pcsPoll/mobile/login";
        }

        return "pcs/pcsPoll/user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> do_login(String username, String passwd, String captcha,
                                                boolean isMobile,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {

        if(!isMobile) {
            try {
                ShiroHelper.validateCaptcha(request, captcha);
            } catch (Exception e) {
                return failed("验证码错误");
            }
        }

        if (StringUtils.isNotBlank(username)){

            PcsPollInspector inspector = pcsPollInspectorService.tryLogin(StringUtils.trimToNull(username), StringUtils.trimToNull(passwd));
            String limitCacheKey = "pcs_"+username;

            if (inspector == null){

                try {
                    cacheService.limitCache(limitCacheKey, 10);
                }catch (Exception e){

                    logger.info(addNoLoginLog(null, username, LogConstants.LOG_PCS,"登录过于频繁"));
                    return failed("登录过于频繁，请稍后再试");
                }
                return failed("账号或密码错误");
            }else {
                cacheService.clearLimitCache(limitCacheKey);
                PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
                if (pcsConfig != null && inspector.getPcsPoll().getConfigId() != pcsConfig.getId()){
                    return failed("该账号的党代会投票已过期");
                }
                if (inspector.getPcsPoll().getStartTime().after(new Date())){
                    return failed("党代会投票未开始");
                }else if (inspector.getPcsPoll().getEndTime().before(new Date())){
                    return failed("党代会投票已结束");
                }else if (inspector.getIsFinished()){
                    return failed("该账号已完成投票");
                }
            }
            logger.info(addNoLoginLog(null, username, LogConstants.LOG_PCS,"登录成功"));
            PcsHelper.setSession(request, inspector.getId());
        }

        String successUrl=request.getContextPath() + "/user/pcs/index";

        Map<String, Object> resultMap = success("登录成功");
        resultMap.put("url", successUrl);
        return resultMap;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {

        PcsPollInspector inspector = PcsHelper.doLogout(request);

        logger.debug(addNoLoginLog(null, inspector.getUsername(), LogConstants.LOG_PCS,"退出系统"));

        return "redirect:/user/pcs/login";
    }

    @RequestMapping(value = "/agree", method = RequestMethod.POST)
    @ResponseBody
    public Map agree(@RequestParam(defaultValue = "false") Boolean agree,
                     Byte isMobile,
                     HttpServletRequest request){

        PcsPollInspector inspector = PcsHelper.getSessionInspector(request);

        PcsTempResult tempResult = pcsPollResultService.getTempResult(inspector.getTempdata());

        tempResult.setInspectorId(inspector.getId());
        tempResult.setAgree(agree);
        tempResult.setMobileAgree((isMobile != null && isMobile == 1) ? agree : false);

        PcsPollInspector record = new PcsPollInspector();
        String tempData = pcsPollResultService.getStringTemp(tempResult);

        record.setId(inspector.getId());
        record.setTempdata(tempData);
        record.setIsMobile((isMobile != null && isMobile == 1) ? agree : false);

        pcsPollInspectorService.updateByPrimaryKeySelective(record);

        logger.info(addNoLoginLog(null, inspector.getUsername(), LogConstants.LOG_PCS,"已阅读说明"));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/index")
    public String index(@RequestParam(required = false, defaultValue = "1") Byte type,
                        boolean isPositive,
                        boolean isMobile,
                        ModelMap modelMap,
                        HttpServletRequest request){

        PcsPollInspector inspector = PcsHelper.getSessionInspector(request);

        if (inspector != null) {
            PcsPoll pcsPoll = inspector.getPcsPoll();
            modelMap.put("type", type);
            if (type == 1){
                modelMap.put("num", pcsPoll.getPrNum());
            }else if (type == 2){
                modelMap.put("num", pcsPoll.getDwNum());
            }else if (type == 3){
                modelMap.put("num", pcsPoll.getJwNum());
            }

            modelMap.put("inspector", inspector);
            modelMap.put("pcsPoll", pcsPoll);

            PcsTempResult tempResult = pcsPollResultService.getTempResult(inspector.getTempdata());
            modelMap.put("tempResult", tempResult);

            //二下阶段推荐人名单
            if (pcsPoll.getIsSecond()) {
                Integer pollId = inspector.getPollId();
                List<PcsPollCandidate> cans = pcsPollCandidateService.findAll(pollId, type);
                modelMap.put("cans", cans);
            }
            if (pcsPoll.getIsSecond()){
                return (isMobile) ? "pcs/pcsPoll/mobile/indexSecond" : "pcs/pcsPoll/user/indexSecond";
            }else {
                return (isMobile) ? "pcs/pcsPoll/mobile/index" : "pcs/pcsPoll/user/index";
            }
        }

        return (isMobile)?"pcs/pcsPoll/mobile/login":"pcs/pcsPoll/user/login";
    }

    // 保存/提交推荐数据
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Map submit(Boolean isPositive,
                      boolean isMobile,
                      boolean isSubmit,
                      Integer[] userIds,
                      Byte _type, HttpServletRequest request) {

        PcsPollInspector inspector = PcsHelper.getSessionInspector(request);
        PcsPoll pcsPoll = inspector.getPcsPoll();
        int pollId = inspector.getPollId();

        // 临时数据
        PcsTempResult tempResult = pcsPollResultService.getTempResult(inspector.getTempdata());

        inspector.setIsMobile(isMobile);
        if (isPositive != null) {
            inspector.setIsPositive(isPositive);
        }else {
            if (isSubmit) return failed("请选择投票人身份");
        }

        if (!pcsPoll.getIsSecond()) {//保存"一下阶段"推荐结果

            Map<Byte, List<Integer>> firstResultMap = tempResult.getFirstResultMap();
            List<Integer> userIdList = new ArrayList<>();
            if (userIds != null && userIds.length > 0) {
                userIdList = Arrays.asList(userIds);
            }
            firstResultMap.put(_type, userIdList);
            tempResult.setFirstResultMap(firstResultMap);
            String tempdata = pcsPollResultService.getStringTemp(tempResult);
            inspector.setTempdata(tempdata);


            if (isSubmit){

                int prCount = firstResultMap.get(PcsConstants.PCS_POLL_CANDIDATE_PR) == null ? 0 : firstResultMap.get(PcsConstants.PCS_POLL_CANDIDATE_PR).size();
                int dwCount = firstResultMap.get(PcsConstants.PCS_POLL_CANDIDATE_DW) == null ? 0 : firstResultMap.get(PcsConstants.PCS_POLL_CANDIDATE_DW).size();
                int jwCount = firstResultMap.get(PcsConstants.PCS_POLL_CANDIDATE_JW) == null ? 0 : firstResultMap.get(PcsConstants.PCS_POLL_CANDIDATE_JW).size();

                if (prCount < pcsPoll.getPrNum() || dwCount < pcsPoll.getDwNum() || jwCount < pcsPoll.getJwNum()){
                    return failed("未完成规定的推荐名额");
                }

                pcsPollResultService.submitResult(inspector);
            }else {
                pcsPollInspectorService.updateByPrimaryKeySelective(inspector);
            }

        }else {

            Map<String, Byte> secondResultMap = tempResult.getSecondResultMap();
            Map<String, Integer> otherResultMap = tempResult.getOtherResultMap();

            List<PcsPollCandidate> cans = pcsPollCandidateService.findAll(pollId, _type);

            for (PcsPollCandidate can : cans) {
                int userId = can.getUserId();
                String radioName = _type + "_" + userId;

                String value = request.getParameter(radioName);
                Byte radioValue = (value == null)?null:Byte.valueOf(value);

                if(radioValue != null) {

                    secondResultMap.put(radioName, radioValue);
                    if (radioValue == PcsConstants.RESULT_STATUS_DISAGREE){

                        String otherUserId = request.getParameter(radioName+"_4");
                        if (StringUtils.isNotBlank(otherUserId)){
                            otherResultMap.put(radioName+"_4", Integer.valueOf(otherUserId));
                        }
                    }
                }
            }

            tempResult.setSecondResultMap(secondResultMap);
            tempResult.setOtherResultMap(otherResultMap);
            String tempdata = pcsPollResultService.getStringTemp(tempResult);
            inspector.setTempdata(tempdata);

            if (isSubmit){

                int count = 0;
                for (String key : secondResultMap.keySet()){

                    if (secondResultMap.get(key) == PcsConstants.RESULT_STATUS_AGREE
                            || secondResultMap.get(key) == PcsConstants.RESULT_STATUS_ABSTAIN){
                        count++;
                    }
                }
                count = count + otherResultMap.size();
                /*if (count < (pcsPoll.getPrNum() + pcsPoll.getDwNum() + pcsPoll.getJwNum())) {
                    return failed("未完成规定的推荐名额");
                }*/
                List<PcsPollCandidate> allCan = pcsPollCandidateService.findAll(pollId, null);
                if (allCan != null && count < allCan.size()) {
                    return failed("未完成规定的推荐名额");
                }

                pcsPollResultService.submitResult(inspector);
            }else {
                pcsPollInspectorService.updateByPrimaryKeySelective(inspector);
            }

        }

        logger.info(addNoLoginLog(null, inspector.getUsername(), LogConstants.LOG_PCS, "{0}投票结果，{1}",
                isSubmit?"提交":"保存", pcsPoll.getName()));

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/candidate_selects")
    @ResponseBody
    public Map sysUser_selects(Integer pageSize,
                               Integer pageNo,
                               Boolean isSecond,
                               Byte type,
                               Integer pollId,
                               Integer[] userIds,
                               HttpServletRequest request) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        SysUserViewExample example = new SysUserViewExample();
        SysUserViewExample.Criteria criteria = example.createCriteria();

        long count = sysUserViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysUserView> uvs = sysUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != uvs && uvs.size() > 0) {
            for (SysUserView uv : uvs) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", uv.getId() + "");
                option.put("text", uv.getRealname() + "-" + SystemConstants.USER_TYPE_MAP.get(uv.getType()));
                option.put("del", uv.getLocked());
                option.put("username", uv.getUsername());
                option.put("code", uv.getCode());
                option.put("locked", uv.getLocked());
                option.put("realname", uv.getRealname());
                option.put("gender", uv.getGender());
                option.put("birth", uv.getBirth());
                option.put("nation", uv.getNation());
                option.put("unit", uv.getUnit());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
