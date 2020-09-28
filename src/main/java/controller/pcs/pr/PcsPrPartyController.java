package controller.pcs.pr;

import controller.global.OpException;
import controller.pcs.PcsBaseController;
import domain.member.MemberView;
import domain.pcs.*;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.gson.GsonUtils;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static sys.constants.MemberConstants.*;
import static sys.constants.PcsConstants.*;

@Controller
@RequestMapping("/pcs")
public class PcsPrPartyController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_export")
    public String pcsPrParty_export(String file, Byte stage, HttpServletResponse response) throws IOException {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        int partyId = pcsAdmin.getPartyId();
        //Party party = partyService.findAll().get(partyId);

        XSSFWorkbook wb = null;
        String fileName = null;
        switch (file){
            case "1":
                // stage = 1
                wb = pcsPrExportService.exportPartyCandidates1_stage2(configId, partyId);
                fileName = "党员代表大会代表候选人推荐票（党员推荐使用，交由所在党支部汇总）";
                break;
            case "2":
                // stage = 1
                wb = pcsPrExportService.exportPartyCandidates2_stage2(configId, partyId);
                fileName = "党支部酝酿代表候选人提名汇总表（党支部汇总用表，报分党委、党总支）";
                break;
            case "3":
                wb = pcsPrExportService.exportPartyCandidates(configId, stage, partyId);
                fileName = String.format("分党委酝酿党员代表大会代表候选人%s人选名单（“%s”阶段）",
                        stage== PcsConstants.PCS_STAGE_FIRST?"初步":"预备",
                        PcsConstants.PCS_STAGE_MAP.get(stage));
                break;
            case "4":
                wb = pcsPrExportService.exportPartyAllocate(configId, stage, partyId);
                if(stage==PcsConstants.PCS_STAGE_THIRD)
                    fileName = "党代表数据统计表";
                else
                    fileName = String.format("分党委酝酿党员代表大会代表候选人%s人选统计表（“%s”阶段）",
                        stage==PcsConstants.PCS_STAGE_FIRST?"初步":"预备",
                        PcsConstants.PCS_STAGE_MAP.get(stage));
                break;

            case "pl":
                wb = pcsPrExportService.exportPartyList(configId, partyId);
                fileName = "党代表名单";
                break;
        }

        if(wb!=null)
            ExportHelper.output(wb, fileName + ".xlsx", response);

        return null;
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty")
    public String pcsPrParty(@RequestParam(required = false, defaultValue = "1") byte cls,
                             Integer userId,
                             ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/pcs/pcsPrParty_candidate_table_page";
        } else if (cls == 3) {
            return "forward:/pcs/pcsPrParty_report_page";
        }
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        modelMap.put("partyId", pcsAdmin.getPartyId());

        return "pcs/pcsPrParty/pcsPrParty_candidate_page";
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_candidate_table_page")
    public String pcsPrParty_candidate_table_page(byte stage, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        PcsPrAllocate pcsPrAllocate = pcsPrAlocateService.get(configId, partyId);
        modelMap.put("pcsPrAllocate", pcsPrAllocate);

        PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, stage, partyId);
        modelMap.put("pcsPrRecommend", pcsPrRecommend);

        PcsPrAllocate realPcsPrAllocate = iPcsMapper.statRealPcsPrAllocate(configId, stage, partyId, null);
        modelMap.put("realPcsPrAllocate", realPcsPrAllocate);

        return "pcs/pcsPrParty/pcsPrParty_candidate_table_page";
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_report_page")
    public String pcsPrParty_report_page(byte stage, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        int partyId = pcsAdmin.getPartyId();

        PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, stage, partyId);
        modelMap.put("pcsPrRecommend", pcsPrRecommend);

        modelMap.put("allowModify", pcsPrPartyService.allowModify(partyId, configId, stage));

        return "pcs/pcsPrParty/pcsPrParty_report_page";
    }

    @RequiresPermissions("pcsPrParty:report")
    @RequestMapping(value = "/pcsPrParty_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrParty_report(byte stage) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        if (!pcsPrPartyService.allowModify(partyId, configId, stage)) {
            return failed("您所在"+ CmTag.getStringProperty("partyName") + "已经报送或组织部已下发名单。");
        }

        pcsPrPartyService.report(partyId, configId, stage);

        logger.info(addLog(LogConstants.LOG_PCS, "[分党委管理员]报送-%s(%s)", currentPcsConfig.getName(),
                PcsConstants.PCS_STAGE_MAP.get(stage)));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_candidate_page")
    public String pcsPrParty_candidate_page(byte stage, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }

        modelMap.put("allowModify", pcsPrPartyService.allowModify(pcsAdmin.getPartyId(),
                pcsConfigService.getCurrentPcsConfig().getId(), stage));
        return "pcs/pcsPrParty/pcsPrParty_candidate_page";
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_candidate_data")
    public void pcsPrParty_candidate_data(HttpServletResponse response,
                                              byte stage,
                                              Integer userId,
                                              Integer pageSize, Integer pageNo) throws IOException {


        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
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

        PcsPrCandidateExample example = pcsPrCandidateService.createExample(configId, stage, partyId, userId);

        long count = pcsPrCandidateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<PcsPrCandidate> records = pcsPrCandidateMapper.selectByExampleWithRowbounds(example,
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

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        // for test
        if(!pcsPrPartyService.allowModify(partyId, configId, stage)){
            return failed("已报送数据或已下发名单，不可修改。");
        }

        List<PcsPrCandidateFormBean> records = GsonUtils.toBeans(items, PcsPrCandidateFormBean.class);
        pcsPrPartyService.submit(configId, stage, partyId, recommend, records);

        logger.info(addLog(LogConstants.LOG_PCS, "分党委提交党代表名单：%s-%s-%s", configId, stage, partyId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPrParty:edit")
    @RequestMapping("/pcsPrParty_candidate_au")
    public String pcsPrParty_candidate_au(byte stage, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = pcsConfig.getId();

        PcsParty pcsParty = pcsPartyService.get(configId, partyId);
        modelMap.put("pcsParty", pcsParty);

        PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, stage, partyId);
        modelMap.put("pcsPrRecommend", pcsPrRecommend);

        // 读取三类代表
        Map<Byte, List<PcsPrCandidate>> candidatesMap = new HashMap<>();
        for (Byte prType : PcsConstants.PCS_PR_TYPE_MAP.keySet()) {
            candidatesMap.put(prType, pcsPrCandidateService.find(configId, stage, prType, partyId));
        }
        modelMap.put("candidatesMap", candidatesMap);


        modelMap.put("allowModify", pcsPrPartyService.allowModify(partyId,
                pcsConfigService.getCurrentPcsConfig().getId(), stage));
        // for test
        //modelMap.put("allowModify",true);

        return "pcs/pcsPrParty/pcsPrParty_candidate_au";
    }

    @RequiresPermissions("pcsPrParty:list")
    @RequestMapping("/pcsPrParty_candidates")
    public String pcsPrParty_candidates(byte stage, byte type, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();

        List<PcsPrCandidate> candidates = pcsPrCandidateService.find(configId, stage, type, partyId);

        modelMap.put("candidates", candidates);

        return "pcs/pcsPrParty/pcsPrParty_candidates";
    }

    @RequiresPermissions("pcsPrParty:edit")
    @RequestMapping(value = "/pcsPrParty_selectUser", method = RequestMethod.POST)
    public void do_pcsPrParty_selectUser(Integer[] userIds,
                                         byte stage,
                                         HttpServletResponse response) throws IOException {


        List<PcsPrCandidate> candidates = new ArrayList<>();
        if (userIds != null) {
            for (Integer userId : userIds) {

                PcsPrCandidate candidate=pcsPrPartyService.getCandidateInfo(userId,stage);
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


    @RequiresPermissions("pcsPrParty:edit")
    @RequestMapping("/pcsPrParty_candidate_import")
    public String pcsPrParty_candidate_import() {

        return "pcs/pcsPrParty/pcsPrParty_candidate_import";
    }

    // 导入党代表名单
    @RequiresPermissions("pcsPrParty:edit")
    @RequestMapping(value = "/pcsPrParty_candidate_import", method = RequestMethod.POST)
    public String do_pcsPrParty_candidate_import(byte stage,MultipartFile xlsx,
                                                   ModelMap modelMap, HttpServletRequest request) throws IOException, InvalidFormatException {

        PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = pcsConfig.getId();

        Map<String, Byte> pcsPrUserTypeMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : PCS_PR_TYPE_MAP.entrySet()) {

            pcsPrUserTypeMap.put(entry.getValue(), entry.getKey());
        }

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);
        List<PcsPrCandidate> candidates = new ArrayList<>();

        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {
            row++;
            /*String code = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(code)) {
                throw new OpException("第{0}行学工号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null) {
                throw new OpException("第{0}行工号[{1}]不存在", row, code);
            }
            Member member=memberService.get(uv.getId());
            if (member == null||(member.getPartyId()!=pcsParty.getPartyId()
                    ||member.getStatus()==MEMBER_STATUS_QUIT
                    ||member.getPoliticalStatus()!=MEMBER_POLITICAL_STATUS_POSITIVE)) {
                throw new OpException("第{0}行工号[{1}]不符合党代表的基本条件（正式党员）", row, code);
            }*/

            Integer userId = null;
            String realname = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(realname)) {

                logger.warn(JSONUtils.toString(xlsRow, false));
                throw new OpException("第{0}行姓名为空", row);
            }
            realname = ContentUtils.trimAll(realname);
            List<MemberView> members = iMemberMapper.findMembers(realname,
                    null, MEMBER_POLITICAL_STATUS_POSITIVE,
                    new ArrayList<>(Arrays.asList(MEMBER_STATUS_NORMAL, MEMBER_STATUS_TRANSFER)));
            if(members.size()==1){
                userId = members.get(0).getUserId();
            }

            String _type = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.isBlank(_type)) {

                logger.warn(JSONUtils.toString(xlsRow, false));
                throw new OpException("第{0}行推荐人类型为空", row);
            }

            Byte pcsPrUserType = pcsPrUserTypeMap.get(_type);
            if (pcsPrUserType == null) {

                logger.warn(JSONUtils.toString(xlsRow, false));
                throw new OpException("第{0}行推荐人类型[{1}]有误", row, _type);
            }

            String _branchVote = StringUtils.trim(xlsRow.get(2));
            String _vote = StringUtils.trim(xlsRow.get(3));
            String _positiveVote = StringUtils.trim(xlsRow.get(4));

            PcsPrCandidate candidate=pcsPrPartyService.getCandidateInfo(userId, stage);
            candidate.setRealname(realname);

            if(pcsPrUserType==PCS_PR_TYPE_STU){
                candidate.setType(PCS_PR_TYPE_STU);
            }else if(pcsPrUserType==PCS_PR_TYPE_RETIRE){
                candidate.setType(PCS_PR_TYPE_RETIRE);
            }else{
               candidate.setType(PCS_PR_TYPE_PRO);
            }
            candidate.setBranchVote(_branchVote!=null?Integer.valueOf(_branchVote):null);
            candidate.setVote(_vote!=null?Integer.valueOf(_vote):null);
            candidate.setPositiveVote(_positiveVote!=null?Integer.valueOf(_positiveVote):null);

            candidates.add(candidate);
        }

        modelMap.put("candidates", candidates);
        modelMap.put("stage", stage);

        return "pcs/pcsPrParty/pcsPrParty_candidate_import_confirm";
    }

    @RequiresPermissions("pcsPrParty:edit")
    @RequestMapping(value = "/pcsPrParty_candidate_import_confirm", method = RequestMethod.POST)
    @ResponseBody
    public void do_pcsPrParty_candidate_import_confirm(byte stage, String items, HttpServletResponse response,
                                                   ModelMap modelMap) throws IOException, InvalidFormatException {

        List<PcsPrCandidate> candidates = new ArrayList<>();

        List<PcsPrCandidate> pcsCandidates = GsonUtils.toBeans(items, PcsPrCandidate.class);
        for (PcsPrCandidate pcsPrCandidate : pcsCandidates) {

            int userId = pcsPrCandidate.getUserId();
            byte pcsUserType = pcsPrCandidate.getType();
            Integer _brancVote = pcsPrCandidate.getBranchVote();
            Integer _vote = pcsPrCandidate.getVote();
            Integer _positiveVote = pcsPrCandidate.getPositiveVote();

            PcsPrCandidate  candidate= pcsPrPartyService.getCandidateInfo(userId, stage);
            candidate.setType(pcsUserType);

            candidate.setBranchVote(_brancVote!=null?Integer.valueOf(_brancVote):null);
            candidate.setVote(_vote!=null?Integer.valueOf(_vote):null);
            candidate.setPositiveVote(_positiveVote!=null?Integer.valueOf(_positiveVote):null);

            candidates.add(candidate);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("candidates", candidates);
        JSONUtils.write(response, resultMap);
    }
}
