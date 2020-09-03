package controller.pcs.cm;

import controller.global.OpException;
import controller.pcs.PcsBaseController;
import domain.member.Member;
import domain.pcs.*;
import domain.sys.SysUserView;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import persistence.pcs.common.IPcsCandidate;
import persistence.pcs.common.PcsBranchBean;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.gson.GsonUtils;
import sys.tool.paging.CommonList;
import sys.utils.ExcelUtils;
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

import static sys.constants.MemberConstants.*;
import static sys.constants.PcsConstants.PCS_USER_TYPE_MAP;

@Controller
@RequestMapping("/pcs")
public class PcsRecommendController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend")
    public String pcsRecommend( byte stage,Integer branchId, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        PcsParty pcsParty = pcsPartyService.get(pcsConfig.getId(), partyId);
        if(pcsParty!=null) {
            modelMap.put("isDirectBranch", pcsParty.getIsDirectBranch());
        }
        modelMap.put("partyId", partyId);

        if(branchId!=null){
            modelMap.put("branch", branchService.findAll().get(branchId));
        }

        modelMap.put("allowModify", pcsPartyService.allowModify(partyId, pcsConfig.getId(), stage));

        return "pcs/pcsRecommend/pcsRecommend_page";
    }

    @RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend_data")
    public void pcsRecommend_data(HttpServletResponse response,
                                  byte stage,
                                  Integer branchId,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        int partyId = pcsAdmin.getPartyId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int count = iPcsMapper.countPcsBranchBeanList(configId, stage, partyId, branchId, null);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsBranchBean> records =
                iPcsMapper.selectPcsBranchBeanList(configId, stage, partyId, branchId, null,
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

    //@RequiresPermissions("pcsRecommend:edit")
    @RequestMapping(value = "/pcsRecommend_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsRecommend_au(byte stage, int partyId, Integer branchId,
                                  int expectMemberCount, int actualMemberCount, Boolean isFinish,
                                  String items,
                                  HttpServletRequest request) throws UnsupportedEncodingException {

        if(ShiroHelper.isPermitted("pcsOw:admin")){
            // 管理员可以修改，但不改变状态
            isFinish = null;

        }else {
            ShiroHelper.checkPermission("pcsRecommend:edit");

            PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
            if (pcsAdmin.getPartyId() != partyId) {
                throw new UnauthorizedException();
            }

            // for test
            if (!pcsPartyService.allowModify(partyId,
                    pcsConfigService.getCurrentPcsConfig().getId(), stage)) {
                return failed("已报送数据或已下发名单，不可修改。");
            }
        }

        if (partyService.isPartyContainBranch(partyId, branchId) == false) {
            return failed("参数有误。");
        }

        List<PcsCandidateFormBean> formBeans = GsonUtils.toBeans(items, PcsCandidateFormBean.class);
        pcsRecommendService.submit(stage, partyId, branchId,
                expectMemberCount, actualMemberCount, isFinish, formBeans);

        logger.info(addLog(LogConstants.LOG_PCS, "党支部推荐情况-提交推荐票：%s-%s-%s", stage, partyId, branchId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsRecommend:edit")
    @RequestMapping("/pcsRecommend_au")
    public String pcsRecommend_au(byte stage, int partyId, Integer branchId, ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if(pcsAdmin.getPartyId() != partyId){
            throw new UnauthorizedException();
        }

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = pcsConfig.getId();

        PcsBranchBean pcsBranchBean = pcsRecommendService.get(partyId, branchId, pcsConfig.getId(), stage);
        modelMap.put("pcsBranchBean", pcsBranchBean);

        // 读取党委委员、纪委委员
        List<PcsCandidate> dwCandidates =
                pcsCandidateService.find(pcsBranchBean.getPartyId(),
                        pcsBranchBean.getBranchId(), configId, stage, PcsConstants.PCS_USER_TYPE_DW);
        List<PcsCandidate> jwCandidates =
                pcsCandidateService.find(pcsBranchBean.getPartyId(),
                        pcsBranchBean.getBranchId(), configId, stage, PcsConstants.PCS_USER_TYPE_JW);
        modelMap.put("dwCandidates", dwCandidates);
        modelMap.put("jwCandidates", jwCandidates);

        modelMap.put("allowModify", pcsPartyService.allowModify(partyId,
                pcsConfigService.getCurrentPcsConfig().getId(), stage));
        // for test
        //modelMap.put("allowModify", true);

        return "pcs/pcsRecommend/pcsRecommend_au";
    }


    //@RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend_candidates")
    public String pcsRecommend_candidates(byte stage, byte type, ModelMap modelMap) {

        if(!ShiroHelper.isPermitted("pcsOw:admin")){
            ShiroHelper.checkPermission("pcsRecommend:list");
        }

        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        List<IPcsCandidate> candidates =
                iPcsMapper.selectPartyCandidateList(null, true, configId, stage, type, null,new RowBounds());

        modelMap.put("candidates", candidates);
        return "pcs/pcsRecommend/pcsRecommend_candidates";
    }

    //@RequiresPermissions("pcsRecommend:edit")
    @RequestMapping(value = "/pcsRecommend_selectUser", method = RequestMethod.POST)
    public void do_pcsRecommend_selectUser(Integer[] userIds,
                                           HttpServletResponse response) throws IOException {

        if(!ShiroHelper.isPermitted("pcsOw:admin")){
            ShiroHelper.checkPermission("pcsRecommend:edit");
        }

        List<PcsCandidate> candidates = new ArrayList<>();
        if(userIds!=null){
            for (Integer userId : userIds) {

                PcsCandidate  candidate= pcsCandidateService.getCandidateInfo(userId);
                candidates.add(candidate);
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("candidates", candidates);
        JSONUtils.write(response, resultMap);
        //logger.info(addLog(LogConstants.LOG_PCS, "党支部推荐情况-选择教职工委员：%s-%s", type, userId));
    }

    @RequiresPermissions("pcsRecommend:list")
    @RequestMapping("/pcsRecommend_form_download")
    public String pcsRecommend_form_download(ModelMap modelMap) {

        return "pcs/pcsRecommend/pcsRecommend_form_download";
    }

   // @RequiresPermissions("pcsRecommend:edit")
    @RequestMapping("/pcsRecommend_candidate_import")
    public String pcsRecommend_candidate_import(int partyId,byte stage,ModelMap modelMap) {

        return "pcs/pcsRecommend/pcsRecommend_candidate_import";
    }

    // 导入党代表名单
   // @RequiresPermissions("pcsRecommend:edit")
    @RequestMapping(value = "/pcsRecommend_candidate_import", method = RequestMethod.POST)
    @ResponseBody
    public void do_pcsRecommend_candidate_import(int partyId,HttpServletResponse response, HttpServletRequest request) throws IOException, InvalidFormatException {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());

        if (!ShiroHelper.isPermitted("pcsOw:admin")
                &&(pcsAdmin!=null&&pcsAdmin.getPartyId() != partyId)) {
            throw new UnauthorizedException();
        }

        Map<String, Byte> pcsUserTypeMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : PCS_USER_TYPE_MAP.entrySet()) {

            pcsUserTypeMap.put(entry.getValue(), entry.getKey());
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);
        List<PcsCandidate> candidates = new ArrayList<>();

        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {
            row++;
            String code = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(code)) {
                throw new OpException("第{0}行学工号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null) {
                throw new OpException("第{0}行工号[{1}]不存在", row, code);
            }
            Member member=memberService.get(uv.getId());
            if (member == null||(member.getType()!=MEMBER_TYPE_TEACHER
                    ||member.getStatus()==MEMBER_STATUS_QUIT
                    ||member.getPoliticalStatus()!=MEMBER_POLITICAL_STATUS_POSITIVE)) {
                throw new OpException("第{0}行工号[{1}]不符合两委委员的基本条件", row, code);
            }

            String _type = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isBlank(_type)) {
                throw new OpException("第{0}行类型为空", row);
            }

            Byte pcsUserType = pcsUserTypeMap.get(_type);

            if (pcsUserType == null) {
                throw new OpException("第{0}行会议类型[{1}]有误", row, _type);
            }

            String  _vote = StringUtils.trimToNull(xlsRow.get(3));
            String _positiveVote = StringUtils.trimToNull(xlsRow.get(4));

            PcsCandidate  candidate= pcsCandidateService.getCandidateInfo(uv.getUserId());
            candidate.setType(pcsUserType);

            candidate.setVote(_vote!=null?Integer.valueOf(_vote):null);
            candidate.setPositiveVote(_positiveVote!=null?Integer.valueOf(_positiveVote):null);

            candidates.add(candidate);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("candidates", candidates);
        JSONUtils.write(response, resultMap);
    }

    // 分党委管理员同步党支部推荐结果
    @RequiresPermissions("pcsRecommend:edit")
    @RequestMapping(value = "/pcsRecommend_sync", method = RequestMethod.POST)
    @ResponseBody
    public void pcsRecommend_sync(byte stage,HttpServletResponse response) throws IOException {
        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        PcsBranchExample example = new PcsBranchExample();
        example.createCriteria().andConfigIdEqualTo(configId).
                andPartyIdEqualTo(partyId).andIsDeletedEqualTo(false);
        List<PcsBranch> pcsBranchs=pcsBranchMapper.selectByExample(example);
        int branchCount=pcsBranchs.size();

        int syncCount=pcsCandidateService.syncVoteResult(pcsBranchs,configId,stage,partyId);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("branchCount", branchCount);
        resultMap.put("syncCount", syncCount);
        JSONUtils.write(response, resultMap);

    }

}
