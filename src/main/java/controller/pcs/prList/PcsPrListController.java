package controller.pcs.prList;

import controller.global.OpException;
import controller.pcs.PcsBaseController;
import controller.pcs.pr.PcsPrCandidateFormBean;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.party.Party;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrAllocate;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrRecommend;
import domain.sys.StudentInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
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
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.gson.GsonUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrListController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPrList:list")
    @RequestMapping("/pcsPrList_export")
    public String pcsPrList_export(String file, HttpServletResponse response) throws IOException {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        int partyId = pcsAdmin.getPartyId();
        Party party = partyService.findAll().get(partyId);

        XSSFWorkbook wb = null;
        String fileName = null;
        switch (file){
            case "1":
                wb = pcsPrExportService.exportPartyCandidates1_stage2(configId, partyId);
                fileName = "党代表名单";
                break;
            case "2":
                wb = pcsPrExportService.exportPartyCandidates2_stage2(configId, partyId);
                fileName = "党代表数据统计表";
                break;
        }

        if(wb!=null)
            ExportHelper.output(wb, fileName + ".xlsx", response);

        return null;
    }

    @RequiresPermissions("pcsPrList:list")
    @RequestMapping("/pcsPrList")
    public String pcsPrList(@RequestParam(required = false, defaultValue = "1") byte cls,


                             ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/pcsPrList_table_page";
        } else if (cls == 3) {
            return "forward:/pcsPrList_report_page";
        }

        return "forward:/pcsPrList_page";
    }

    //@RequiresPermissions("pcsPrList:list")
    @RequestMapping("/pcsPrList_page")
    public String pcsPrList_page(Integer partyId, ModelMap modelMap) {

        if(!ShiroHelper.isPermitted("pcsPrListOw:admin")) {

            SecurityUtils.getSubject().checkPermission("pcsPrList:list");
        }

        if(partyId==null){ // 党代会管理员同时也可以是某个分党委管理员

            PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
            if (pcsAdmin == null) {
                throw new UnauthorizedException();
            }
            partyId = pcsAdmin.getPartyId();
        }

        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        List<PcsPrCandidateView> candidates = pcsPrListService.getList2(configId, partyId, true);
        modelMap.put("candidates", candidates);

        boolean hasSort = pcsPrListService.hasSort(configId, partyId);
        modelMap.put("hasSort", hasSort);
        modelMap.put("allowModify", pcsPrPartyService.allowModify(partyId, configId,
                PcsConstants.PCS_STAGE_THIRD) && hasSort);

        return "pcs/pcsPrList/pcsPrList_page";
    }

    @RequiresPermissions("pcsPrList:edit")
    @RequestMapping(value = "/pcsPrList_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrList_au(String items, HttpServletRequest request) throws UnsupportedEncodingException {

        byte stage = PcsConstants.PCS_STAGE_THIRD;

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        if(!pcsPrPartyService.allowModify(partyId, configId, stage)){
            return failed("已报送数据，不可修改。");
        }

        List<PcsPrCandidateFormBean> records = GsonUtils.toBeans(items, PcsPrCandidateFormBean.class);
        pcsPrListService.submit(configId, partyId, records);

        logger.info(addLog(LogConstants.LOG_PCS, "分党委提交党代表名单：%s-%s-%s", configId, stage, partyId));

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("pcsPrList:list")
    @RequestMapping("/pcsPrList_table_page")
    public String pcsPrList_table_page(Integer partyId, ModelMap modelMap) {

        if(!ShiroHelper.isPermitted("pcsPrListOw:admin")) {

            SecurityUtils.getSubject().checkPermission("pcsPrList:list");
        }
        if(partyId==null){
            PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
            if (pcsAdmin == null) {
                throw new UnauthorizedException();
            }
            partyId = pcsAdmin.getPartyId();
        }

        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        PcsPrAllocate pcsPrAllocate = pcsPrAlocateService.get(configId, partyId);
        modelMap.put("pcsPrAllocate", pcsPrAllocate);

        PcsPrAllocate realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId,
                PcsConstants.PCS_STAGE_SECOND, partyId, true);
        modelMap.put("realPcsPrAllocate", realPcsPrAllocate);

        return "pcs/pcsPrList/pcsPrList_table_page";
    }

    @RequiresPermissions("pcsPrList:list")
    @RequestMapping("/pcsPrList_report_page")
    public String pcsPrList_report_page( ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        int partyId = pcsAdmin.getPartyId();

        byte stage = PcsConstants.PCS_STAGE_THIRD;

        PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, stage, partyId);
        modelMap.put("pcsPrRecommend", pcsPrRecommend);


        boolean hasSort = pcsPrListService.hasSort(configId, partyId);
        modelMap.put("hasSort", hasSort);
        modelMap.put("allowModify", pcsPrPartyService.allowModify(partyId, configId, stage) && hasSort);

        return "pcs/pcsPrList/pcsPrList_report_page";
    }

    @RequiresPermissions("pcsPrList:report")
    @RequestMapping(value = "/pcsPrList_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrList_report() {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }

        byte stage = PcsConstants.PCS_STAGE_THIRD;

        int partyId = pcsAdmin.getPartyId();
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if (!pcsPrPartyService.allowModify(partyId, configId, stage)) {
            return failed("您所在分党委已报送数据。");
        }

        // 检查大会材料是否上传完成
        /*if(!pcsPrFileService.isUploadAll(configId, partyId)){
            return failed("请上传全部的大会材料后上报。");
        }
*/
        // 检查是否保存姓名笔画顺序
        if(!pcsPrListService.hasSort(configId, partyId)){
            return failed("请按姓氏笔画排序后保存。");
        }

        if(pcsPrListService.getList(configId, partyId, true).size()==0){
            return failed("请填写党代表名单后上报。");
        }

        pcsPrPartyService.report(partyId, configId, stage);

        logger.info(addLog(LogConstants.LOG_PCS, "[分党委管理员]报送-%s(%s)", currentPcsConfig.getName(),
                PcsConstants.PCS_STAGE_MAP.get(stage)));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrList:list")
    @RequestMapping("/pcsPrList_candidates")
    public String pcsPrList_candidates(ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        // 在第三阶段，共用第二阶段的候选人
        List<PcsPrCandidateView> candidates = pcsPrListService.getList(configId, partyId, null);
        modelMap.put("candidates", candidates);

        return "pcs/pcsPrList/pcsPrList_candidates";
    }

    @RequiresPermissions("pcsPrList:edit")
    @RequestMapping(value = "/pcsPrList_selectUser", method = RequestMethod.POST)
    public void do_pcsPrList_selectUser(@RequestParam(value = "userIds[]") Integer[] userIds,
                                         HttpServletResponse response) throws IOException {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        Map<Integer, PcsPrCandidateView> selectedMap = pcsPrCandidateService.findSelectedMap(configId,
                PcsConstants.PCS_STAGE_SECOND, partyId);

        List<PcsPrCandidateView> candidates = new ArrayList<>();
        if (userIds != null) {
            for (Integer userId : userIds) {

                SysUserView uv = sysUserService.findById(userId);

                PcsPrCandidateView candidate = new PcsPrCandidateView();
                candidate.setUserId(uv.getId());
                candidate.setCode(uv.getCode());
                candidate.setRealname(uv.getRealname());
                candidate.setPartyId(partyId);

                // 手机号 ， 新增
                candidate.setMobile(uv.getMobile());
                // 邮箱 ， 新增
                candidate.setEmail(uv.getEmail());

                PcsPrCandidateView _candidate = selectedMap.get(userId);
                // 读取之前填写的性别、民族、出生年月
                candidate.setType(_candidate.getType());
                candidate.setVote3(_candidate.getVote3());
                candidate.setGender(_candidate.getGender());
                candidate.setNation(_candidate.getNation());
                candidate.setBirth(_candidate.getBirth());

                if(uv.getType()==SystemConstants.USER_TYPE_JZG){

                    TeacherInfo teacherInfo = teacherInfoService.get(userId);
                    CadreView cv = cadreService.dbFindByUserId(userId);
                    if(cv!=null && CadreConstants.CADRE_STATUS_NOW_SET.contains(cv.getStatus())){
                        // 是干部
                        candidate.setUserType(PcsConstants.PCS_PR_USER_TYPE_CADRE);
                        candidate.setEduId(cv.getEduId());
                        candidate.setWorkTime(cv.getWorkTime());
                        candidate.setPost(cv.getPost());
                    }else{
                        // 是普通教师
                        candidate.setUserType(PcsConstants.PCS_PR_USER_TYPE_TEACHER);
                        candidate.setEducation(teacherInfo.getEducation());
                        candidate.setWorkTime(teacherInfo.getWorkTime());
                        candidate.setIsRetire(teacherInfo.getIsRetire());
                        candidate.setProPost(teacherInfo.getProPost());
                    }
                }else{
                    StudentInfo studentInfo = studentInfoService.get(userId);
                    // 学生
                    candidate.setUserType(PcsConstants.PCS_PR_USER_TYPE_STU);
                    candidate.setEduLevel(studentInfo.getEduLevel());
                }

                Member member = memberService.get(userId);
                if(member==null || member.getPoliticalStatus()
                        != MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE){
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

    @RequiresPermissions("pcsPrList:list")
    @RequestMapping("/pcsPrList_form_download")
    public String pcsPrList_form_download(ModelMap modelMap) {

        return "pcs/pcsPrList/pcsPrList_form_download";
    }
}
