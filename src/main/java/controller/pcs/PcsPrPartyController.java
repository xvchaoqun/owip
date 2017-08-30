package controller.pcs;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.party.PartyView;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrRecommend;
import domain.sys.StudentInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.common.bean.IPcsCandidateView;
import persistence.common.bean.PcsBranchBean;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.gson.GsonUtils;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrPartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_export")
    public String pcsPrParty_export(String file, byte stage, Byte type, HttpServletResponse response) throws IOException {

        return null;
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty")
    public String pcsPrParty(@RequestParam(required = false, defaultValue = "1") byte cls,
                             @RequestParam(required = false,
                                     defaultValue = SystemConstants.PCS_USER_TYPE_DW + "") byte type,
                             Integer userId,
                             ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/pcsPrParty_candidate_table_page";
        } else if (cls == 3) {
            return "forward:/pcsPrParty_report_page";
        }
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        modelMap.put("type", type);

        return "pcs/pcsPrParty/pcsPrParty_candidate_page";
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_candidate_table_page")
    public String pcsPrParty_candidate_table_page(byte stage, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsAdmin.getConfigId();

        List<PcsBranchBean> records = iPcsMapper.selectPcsBranchBeans(configId, stage, partyId, null, new RowBounds());
        modelMap.put("records", records);

        return "pcs/pcsPrParty/pcsPrParty_candidate_table_page";
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_report_page")
    public String pcsPrParty_report_page(byte stage, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());

        modelMap.put("hasReport", pcsPrPartyService.hasReport(pcsAdmin.getPartyId(), pcsAdmin.getConfigId(), stage));

        return "pcs/pcsPrParty/pcsPrParty_report_page";
    }

    @RequiresPermissions("pcsPrParty:report")
    @RequestMapping(value = "/pcsPrParty_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrParty_report(byte stage) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if (pcsPrPartyService.hasReport(partyId, configId, stage)) {
            return failed("您所在分党委已经上报或组织部已下发名单。");
        }

        pcsAdminService.report(partyId, configId, stage);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "[分党委管理员]上报-%s(%s)", currentPcsConfig.getName(),
                SystemConstants.PCS_STAGE_MAP.get(stage)));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_candidate_page")
    public String pcsPrParty_candidate_page(byte stage, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }

        modelMap.put("hasReport", pcsPrPartyService.hasReport(pcsAdmin.getPartyId(), pcsAdmin.getConfigId(), stage));
        return "pcs/pcsPrParty/pcsPrParty_candidate_page";
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_candidate_data")
    public void pcsPrParty_statCandidate_data(HttpServletResponse response,
                                              byte stage,
                                              Integer userId,
                                              Integer pageSize, Integer pageNo) throws IOException {


        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        int count = iPcsMapper.countPcsPrPartyCandidates(userId, configId, stage, partyId);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<IPcsCandidateView> records = iPcsMapper.selectPcsPrPartyCandidates(userId, configId, stage, partyId,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsPrParty:edit")
    @RequestMapping(value = "/pcsPrParty_candidate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrParty_candidate_au(byte stage,
                                          PcsPrRecommend recommend,
                                          String items,
                                          HttpServletRequest request) throws UnsupportedEncodingException {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsAdmin.getConfigId();

        if(pcsPrPartyService.hasReport(partyId, pcsAdmin.getConfigId(), stage)){
            return failed("已上报数据或已下发名单，不可修改。");
        }

        List<PcsPrCandidateFormBean> records = GsonUtils.toBeans(items, PcsPrCandidateFormBean.class);
        pcsPrPartyService.submit(configId, stage, partyId, recommend, records);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "分党委提交党代表名单：%s-%s-%s", configId, stage, partyId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrParty:edit")
    @RequestMapping("/pcsPrParty_candidate_au")
    public String pcsPrParty_candidate_au(byte stage, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = pcsConfig.getId();

        PartyView partyView = partyService.getPartyView(partyId);
        modelMap.put("partyView", partyView);

        PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, stage, partyId);
        modelMap.put("pcsPrRecommend", pcsPrRecommend);

        // 读取三类代表
        Map<Byte, List<PcsPrCandidateView>> candidatesMap = new HashMap<>();
        for (Byte prType : SystemConstants.PCS_PR_TYPE_MAP.keySet()) {
            candidatesMap.put(prType, pcsPrCandidateService.find(configId, stage, prType, partyId));
        }
        modelMap.put("candidatesMap", candidatesMap);

        modelMap.put("hasReport", pcsPrPartyService.hasReport(partyId, pcsAdmin.getConfigId(), stage));

        return "pcs/pcsPrParty/pcsPrParty_candidate_au";
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_candidates")
    public String pcsRecommend_candidates(byte stage, byte type, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int configId = pcsAdmin.getConfigId();
        int partyId = pcsAdmin.getPartyId();

        List<PcsPrCandidateView> candidates = pcsPrCandidateService.find(configId, stage, type, partyId);

        modelMap.put("candidates", candidates);

        return "pcs/pcsPrParty/pcsPrParty_candidates";
    }

    @RequiresPermissions("pcsPrParty:edit")
    @RequestMapping(value = "/pcsPrParty_selectUser", method = RequestMethod.POST)
    public void do_pcsPrParty_selectUser(@RequestParam(value = "userIds[]") Integer[] userIds,
                                         HttpServletResponse response) throws IOException {

        List<PcsPrCandidateView> candidates = new ArrayList<>();
        if (userIds != null) {
            for (Integer userId : userIds) {

                SysUserView uv = sysUserService.findById(userId);

                PcsPrCandidateView candidate = new PcsPrCandidateView();
                candidate.setUserId(uv.getUserId());
                candidate.setCode(uv.getCode());
                candidate.setRealname(uv.getRealname());
                candidate.setGender(uv.getGender());
                candidate.setNation(uv.getNation());
                candidate.setBirth(uv.getBirth());

                if(uv.getType()==SystemConstants.USER_TYPE_JZG){

                    TeacherInfo teacherInfo = teacherService.get(userId);
                    CadreView cv = cadreService.dbFindByUserId(userId);
                    if(cv!=null && SystemConstants.CADRE_STATUS_NOW_SET.contains(cv.getStatus())){
                        // 是干部
                        candidate.setUserType(SystemConstants.PCS_PR_USER_TYPE_CADRE);
                        candidate.setEduId(cv.getEduId());
                        candidate.setWorkTime(cv.getWorkTime());
                        candidate.setPost(cv.getPost());
                    }else{
                        // 是普通教师
                        candidate.setUserType(SystemConstants.PCS_PR_USER_TYPE_TEACHER);
                        candidate.setEducation(teacherInfo.getEducation());
                        candidate.setWorkTime(teacherInfo.getWorkTime());
                        candidate.setIsRetire(teacherInfo.getIsRetire());
                        candidate.setProPost(teacherInfo.getProPost());
                    }
                }else{
                    StudentInfo studentInfo = studentService.get(userId);
                    // 学生
                    candidate.setUserType(SystemConstants.PCS_PR_USER_TYPE_STU);
                    candidate.setEduLevel(studentInfo.getEduLevel());
                }

                Member member = memberService.get(userId);
                if(member==null || member.getPoliticalStatus()
                        != SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE){
                    throw new OpException(uv.getRealname() + "不是正式党员。");
                }
                candidate.setGrowTime(member.getGrowTime());

                candidates.add(candidate);
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("candidates", candidates);
        JSONUtils.write(response, resultMap);
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_form_download")
    public String pcsPrParty_form_download(ModelMap modelMap) {

        return "pcs/pcsPrParty/pcsPrParty_form_download";
    }
}
