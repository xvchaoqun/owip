package controller.pcs.user;

import controller.global.OpException;
import controller.pcs.PcsBaseController;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPoll;
import domain.pcs.PcsPollCandidate;
import domain.pcs.PcsPollInspector;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.helper.PcsHelper;
import sys.tags.CmTag;
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
                if (inspector.getPcsPoll().getIsDeleted()){
                    return failed("党代会投票已作废");
                } else if (inspector.getPcsPoll().getHasReport()) {
                    return failed("党代会投票已报送");
                }else if (inspector.getPcsPoll().getStartTime().after(new Date())){
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
    public String index(@RequestParam(required = false, defaultValue = "2") Byte type,
                        boolean isMobile,
                        ModelMap modelMap,
                        HttpServletRequest request){

        PcsPollInspector inspector = PcsHelper.getSessionInspector(request);

        if (inspector != null) {
            PcsPoll pcsPoll = inspector.getPcsPoll();
            /*if (pcsPoll.getStage() == PcsConstants.PCS_POLL_THIRD_STAGE) {
                type =PcsConstants.PCS_POLL_CANDIDATE_DW;
            }*/
            modelMap.put("type", type);
            int num = 0;
            if (type == PcsConstants.PCS_POLL_CANDIDATE_PR){
                num = pcsPollCandidateService.getPrRequiredCount(pcsPoll.getPartyId());
            }else if (type == PcsConstants.PCS_POLL_CANDIDATE_DW){
                num = CmTag.getIntProperty("pcs_poll_dw_num");
            }else if (type == PcsConstants.PCS_POLL_CANDIDATE_JW){
                num = CmTag.getIntProperty("pcs_poll_jw_num");
            }
            modelMap.put("num", num);

            modelMap.put("inspector", inspector);
            modelMap.put("pcsPoll", pcsPoll);

            PcsTempResult tempResult = pcsPollResultService.getTempResult(inspector.getTempdata());
            modelMap.put("tempResult", tempResult);

            //二下/三下阶段推荐人名单
            if (pcsPoll.getStage() != PcsConstants.PCS_POLL_FIRST_STAGE) {
                Integer pollId = inspector.getPollId();
                List<PcsPollCandidate> cans = pcsPollCandidateService.findAll(pollId, type);
                modelMap.put("cans", cans);
            }
            if (pcsPoll.getStage() != PcsConstants.PCS_POLL_FIRST_STAGE){
                Set<Integer> selectUserIdList = new HashSet<>();
                Map<String, Integer> otherResultMap = tempResult.getOtherResultMap();

                List<PcsPollCandidate> candidatelist = pcsPollCandidateService.findAll(pcsPoll.getId(), type);
                if (candidatelist.size() > 0) {
                    for (PcsPollCandidate candidate : candidatelist) {
                        selectUserIdList.add(candidate.getUserId());
                    }
                }
                if (otherResultMap.size() > 0) {
                    for (Map.Entry<String, Integer> entry : otherResultMap.entrySet()) {

                        Byte _type = Byte.valueOf(entry.getKey().split("_")[0]);
                        if (_type == type) {
                            selectUserIdList.add(entry.getValue());
                        }
                    }
                }
                modelMap.put("selectUserIdList", selectUserIdList);

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
                      Byte type, HttpServletRequest request) {

        PcsPollInspector inspector = PcsHelper.getSessionInspector(request);
        PcsPoll pcsPoll = inspector.getPcsPoll();
        int pollId = inspector.getPollId();

        // 临时数据
        PcsTempResult tempResult = pcsPollResultService.getTempResult(inspector.getTempdata());

        Map<Byte, Set<Integer>> firstResultMap = tempResult.getFirstResultMap();
        Map<String, Byte> secondResultMap = tempResult.getSecondResultMap();
        Map<String, Integer> otherResultMap = tempResult.getOtherResultMap();

        if (isSubmit){//提交

            if (inspector.getIsPositive() == null){
                return failed("请选择投票人身份");
            }

            if (pcsPoll.getStage() == PcsConstants.PCS_POLL_FIRST_STAGE) {

                pcsPollResultService.submitResult(inspector);
            }else {
                //检测推荐人数量是否符合要求
                int prCount = 0;
                int dwCount = 0;
                int jwCount = 0;
                Byte _type= 0;
                for (String key : secondResultMap.keySet()){
                    int count = 0;
                    _type = Byte.valueOf(key.split("_")[0]);

                    if (secondResultMap.get(key) == PcsConstants.RESULT_STATUS_AGREE){
                        count++;
                    }else if (secondResultMap.get(key) == PcsConstants.RESULT_STATUS_DISAGREE){
                        if (otherResultMap.containsKey(key + "_4")){
                            count++;
                        }
                    }

                    if (_type == PcsConstants.PCS_POLL_CANDIDATE_PR){
                        prCount += count;
                    }else if (_type == PcsConstants.PCS_POLL_CANDIDATE_DW) {
                        dwCount += count;
                    }else if (_type == PcsConstants.PCS_POLL_CANDIDATE_JW){
                        jwCount += count;
                    }
                }

                if (pcsPoll.getStage() != PcsConstants.PCS_POLL_THIRD_STAGE && prCount > pcsPollCandidateService.getPrRequiredCount(pcsPoll.getPartyId())) {
                    throw new OpException("推荐的代表超过规定数量，请重新选择");
                }
                if (dwCount > CmTag.getIntProperty("pcs_poll_dw_num")){
                    throw new OpException("推荐的党委委员超过规定数量，请重新选择");
                }
                if (jwCount > CmTag.getIntProperty("pcs_poll_jw_num")){
                    throw new OpException("推荐的纪委委员超过规定数量，请重新选择");
                }

                pcsPollResultService.submitResult(inspector);
            }
        }else {//保存

            inspector.setIsMobile(isMobile);
            if (isPositive != null) {
                inspector.setIsPositive(isPositive);
            }

            if (pcsPoll.getStage() == PcsConstants.PCS_POLL_FIRST_STAGE) {//保存"一下阶段"推荐结果

                Set<Integer> userIdSet = new LinkedHashSet<>();
                if (userIds != null && userIds.length > 0) {
                    userIdSet.addAll(Arrays.asList(userIds));
                }
                firstResultMap.put(type, userIdSet);
                tempResult.setFirstResultMap(firstResultMap);
                String tempdata = pcsPollResultService.getStringTemp(tempResult);
                inspector.setTempdata(tempdata);

                pcsPollInspectorService.updateByPrimaryKeySelective(inspector);

            } else {//保存"二下/三下阶段"推荐结果

                List<PcsPollCandidate> candidates = pcsPollCandidateService.findAll(pollId, type);

                Set<Integer> candidateUserIdSet = new HashSet<>();

                for (PcsPollCandidate candidate : candidates) {

                    int userId = candidate.getUserId();
                    candidateUserIdSet.add(userId);

                    String radioName = type + "_" + userId;

                    String value = request.getParameter(radioName);
                    Byte radioValue = (value == null) ? null : Byte.valueOf(value);

                    if (radioValue != null) {

                        secondResultMap.put(radioName, radioValue);
                        if (radioValue == PcsConstants.RESULT_STATUS_DISAGREE) {

                            String otherUserId = request.getParameter(radioName + "_4");
                            if (StringUtils.isNotBlank(otherUserId)) {
                                otherResultMap.put(radioName + "_4", Integer.valueOf(otherUserId));
                            }
                        }
                    }
                }

                tempResult.setSecondResultMap(secondResultMap);
                tempResult.setOtherResultMap(otherResultMap);
                String tempdata = pcsPollResultService.getStringTemp(tempResult);
                inspector.setTempdata(tempdata);

                pcsPollInspectorService.updateByPrimaryKeySelective(inspector);
            }
        }

        logger.info(addNoLoginLog(null, inspector.getUsername(), LogConstants.LOG_PCS, "{0}投票结果，{1}",
                isSubmit?"提交":"保存", pcsPoll.getName()));

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/submit_info")
    public String pcsPoll_au(ModelMap modelMap, HttpServletRequest request) {

        PcsPollInspector inspector = PcsHelper.getSessionInspector(request);

        if (inspector != null) {

            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(inspector.getPollId());
            modelMap.put("stage", pcsPoll.getStage());
            String tempdata = inspector.getTempdata();
            PcsTempResult tempResult = pcsPollResultService.getTempResult(tempdata);
            int prCount = 0;
            int dwCount = 0;
            int jwCount = 0;
            if (pcsPoll.getStage() == PcsConstants.PCS_POLL_FIRST_STAGE) {
                Map<Byte, Set<Integer>> firstResultMap = tempResult.getFirstResultMap();
                for (Map.Entry<Byte, Set<Integer>> entry : firstResultMap.entrySet()){
                    if (entry.getKey() == PcsConstants.PCS_POLL_CANDIDATE_PR){
                        prCount = entry.getValue().size();
                    }else if (entry.getKey() == PcsConstants.PCS_POLL_CANDIDATE_DW) {
                        dwCount = entry.getValue().size();
                    }else if (entry.getKey() == PcsConstants.PCS_POLL_CANDIDATE_JW){
                        jwCount = entry.getValue().size();
                    }
                }
            }else {
                Map<String, Byte> secondResultMap = tempResult.getSecondResultMap();
                Map<String, Integer> otherResultMap = tempResult.getOtherResultMap();

                Byte _type= 0;
                for (String key : secondResultMap.keySet()){
                    int count = 0;
                    _type = Byte.valueOf(key.split("_")[0]);

                    if (secondResultMap.get(key) == PcsConstants.RESULT_STATUS_AGREE){
                        count++;
                    }else if (secondResultMap.get(key) == PcsConstants.RESULT_STATUS_DISAGREE){
                        if (otherResultMap.containsKey(key + "_4")){
                            count++;
                        }
                    }

                    if (_type == PcsConstants.PCS_POLL_CANDIDATE_PR){
                        prCount += count;
                    }else if (_type == PcsConstants.PCS_POLL_CANDIDATE_DW) {
                        dwCount += count;
                    }else if (_type == PcsConstants.PCS_POLL_CANDIDATE_JW){
                        jwCount += count;
                    }
                }

            }
            modelMap.put("prCount", prCount);
            modelMap.put("dwCount", dwCount);
            modelMap.put("jwCount", jwCount);

        }

        return "pcs/pcsPoll/user/submit_info";
    }

    // 根据类别、状态、账号或姓名或学工号 查询 党员
    @RequestMapping("/member_selects")
    @ResponseBody
    public Map member_selects(Integer pageSize,
                              Integer partyId,
                              Integer branchId,
                              Byte type, // 党员类别
                              Boolean isRetire,
                              Byte politicalStatus,
                              Byte[] status, // 党员状态
                              Boolean noAuth, // 默认需要读取权限
                              @RequestParam(defaultValue = "0", required = false) boolean needPrivate,
                              Integer pageNo,
                              String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberViewExample example = new MemberViewExample();
        MemberViewExample.Criteria criteria = example.createCriteria();

        List<Integer> adminPartyIdList = null;
        List<Integer> adminBranchIdList = null;
        if (BooleanUtils.isNotTrue(noAuth)){
            adminPartyIdList = loginUserService.adminPartyIdList();
            adminBranchIdList = loginUserService.adminBranchIdList();

            criteria.addPermits(adminPartyIdList, adminBranchIdList);
        }

        if(partyId!=null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
        }

        if(type!=null){
            criteria.andTypeEqualTo(type);
        }

        if(isRetire!=null){
            criteria.andIsRetireEqualTo(isRetire);
        }

        if(politicalStatus!=null){
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }

        if(status!=null && status.length>0){
            criteria.andStatusIn(Arrays.asList(status));
        }

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) {
            criteria.andUserLike(searchStr);
        }

        int count = (int) memberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberView> members = memberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != members && members.size() > 0) {

            for (MemberView member : members) {
                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(member.getUserId());
                option.put("id", member.getUserId() + "");
                option.put("text", member.getRealname());

                option.put("username", member.getUsername());
                option.put("locked", uv.getLocked());
                option.put("code", member.getCode());
                option.put("realname", member.getRealname());
                option.put("gender", member.getGender());
                option.put("birth", member.getBirth());
                option.put("nation", member.getNation());

                if(needPrivate) {
                    option.put("idcard", member.getIdcard());
                    option.put("politicalStatus", member.getPoliticalStatus());
                    option.put("mobile", member.getMobile());
                }
                //option.put("user", userBeanService.get(member.getUserId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("unit", extService.getUnit(uv.getId()));
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
