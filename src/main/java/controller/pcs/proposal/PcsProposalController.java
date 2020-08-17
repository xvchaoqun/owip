package controller.pcs.proposal;

import controller.pcs.PcsBaseController;
import domain.base.MetaType;
import domain.pcs.*;
import domain.pcs.PcsProposalExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class PcsProposalController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@RequiresPermissions("pcsProposal:list")
    @RequestMapping("/pcsProposal")
    public String pcsProposal(
                               Integer userId,
                               Integer[] types,
                              @RequestParam(required = false, defaultValue = "1") byte cls,
                              @RequestParam(required = false, defaultValue = "1") byte module,
                              ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("module", module);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        modelMap.put("pcsConfig", currentPcsConfig);
        if(cls==1){
            if(DateUtils.compareDate(new Date(), currentPcsConfig.getProposalSubmitTime())){

                modelMap.put("proposalClosed", true);
                return "pcs/pcsProposal/pcsProposal_page";
            }
        }else if(cls==2 || cls==3){
            if(DateUtils.compareDate(new Date(), currentPcsConfig.getProposalSupportTime())){

                modelMap.put("supportClosed", true);
                return "pcs/pcsProposal/pcsProposal_page";
            }
        }


        if (NumberUtils.contains(cls, (byte) 1, (byte) 2, (byte) 3)) {
            SecurityUtils.getSubject().checkPermission("pcsProposalPr:*");
        } else {
            SecurityUtils.getSubject().checkPermission("pcsProposalOw:*");
        }

        if (cls == 8 && module != 1) {
            return "pcs/pcsProposal/ow_page";
        }

        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (types != null) {
            modelMap.put("selectTypes", Arrays.asList(types));
        }
        Map<Integer, MetaType> prTypes = metaTypeService.metaTypes("mc_pcs_proposal");
        modelMap.put("prTypes", prTypes.values());

        Integer proposalSupportCount = currentPcsConfig.getProposalSupportCount();
        modelMap.put("proposalSupportCount", NumberUtils.trimToZero(proposalSupportCount));

        return "pcs/pcsProposal/pcsProposal_page";
    }

    //@RequiresPermissions("pcsProposal:list")
    @RequestMapping("/pcsProposal_data")
    public void pcsProposal_data(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "1") byte cls,
                                 @RequestParam(required = false, defaultValue = "1") byte module,
                                 String code,
                                 Integer userId,
                                 Integer[] types,
                                 String name,
                                 String keywords,
                                 Boolean displayInvite,
                                 Byte status,
                                 Byte orderType,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (NumberUtils.contains(cls, (byte) 1, (byte) 2, (byte) 3)) {
            SecurityUtils.getSubject().checkPermission("pcsProposalPr:*");
        } else {
            SecurityUtils.getSubject().checkPermission("pcsProposalOw:*");
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsProposalViewExample example = new PcsProposalViewExample();
        PcsProposalViewExample.Criteria criteria = example.createCriteria();

        String orderStr = null;
        if(orderType==null || orderType==0) {
            if (cls == 2 || cls == 3)
                orderStr = "check_time asc, create_time desc";
            else
                orderStr = "create_time desc";
        }
        if(orderType!=null && orderType==1){ // 按附议人多少排序
                orderStr = " seconder_count desc, " + orderStr;
        }
        example.setOrderByClause(orderStr);

        if (cls == 1) {
            if(module==1) {
                // 撰写提案
                criteria.andUserIdEqualTo(ShiroHelper.getCurrentUserId());
            }else {
                // 自己是附议人的提案
                criteria.andIsSeconder(ShiroHelper.getCurrentUserId());
            }
        } else if (cls == 2 || cls == 3) {  // 征集附议人 & 提案查询
            criteria.andStatusEqualTo(PcsConstants.PCS_PROPOSAL_STATUS_PASS);

            if(BooleanUtils.isTrue(displayInvite)){
                criteria.andIsInvited(ShiroHelper.getCurrentUserId());
            }

        } else if (cls == 8) {  // 提案管理
            if (module == 1)
                criteria.andStatusNotEqualTo(PcsConstants.PCS_PROPOSAL_STATUS_SAVE);
        }

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (StringUtils.isNotBlank(keywords)) {
            criteria.andKeywordsLike(SqlUtils.like(keywords));
        }

        if (types != null) {
            criteria.andTypeIn(Arrays.asList(types));
        }

        if (status != null) {
            criteria.andStatusEqualTo(status);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsProposal_export(example, response);
            return;
        }

        long count = pcsProposalViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsProposalView> records = pcsProposalViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsProposal.class, pcsProposalMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    //@RequiresPermissions("pcsProposal:edit")
    @RequestMapping(value = "/pcsProposal_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsProposal_au(PcsProposal record,
                                 MultipartFile[] _files, // 附件
                                 Integer[] inviteIds, // 邀请附议人
                                 HttpServletRequest request) throws IOException, InterruptedException {

        if (!ShiroHelper.isPermitted("pcsProposalPr:*")
                && !ShiroHelper.isPermitted("pcsProposalOw:*")) {
            throw new UnauthenticatedException();
        }

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        if(DateUtils.compareDate(new Date(), currentPcsConfig.getProposalSubmitTime())){

           return failed("提交提案截止时间为"
                   + DateUtils.formatDate(currentPcsConfig.getProposalSubmitTime(), DateUtils.YYYY_MM_DD_HH_MM));
        }

        Integer id = record.getId();

        if (StringUtils.isNotBlank(record.getCode()) &&
                pcsProposalService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }
        if (record.getStatus() == null
                || !NumberUtils.contains(record.getStatus(),
                PcsConstants.PCS_PROPOSAL_STATUS_SAVE,
                PcsConstants.PCS_PROPOSAL_STATUS_INIT,
                PcsConstants.PCS_PROPOSAL_STATUS_DENY)) {
            return failed("提案状态错误");
        }

        if (_files == null) _files = new MultipartFile[]{};
        if (inviteIds == null) inviteIds = new Integer[]{};

        List<PcsProposalFile> pcsProposalFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {

            String originalFilename = _file.getOriginalFilename();
            String savePath = uploadPdfOrImage(_file, "pcsProposal");

            PcsProposalFile file = new PcsProposalFile();
            file.setUserId(ShiroHelper.getCurrentUserId());
            file.setFileName(originalFilename);
            file.setFilePath(savePath);
            file.setCreateTime(new Date());
            file.setIp(ContextHelper.getRealIp());
            pcsProposalFiles.add(file);
        }

        //PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        record.setConfigId(configId);

        boolean sumitMsgToAdmin = false;
        if(record.getId()==null){
            // 首次提交
            sumitMsgToAdmin = (record.getStatus()==PcsConstants.PCS_PROPOSAL_STATUS_INIT);
        }else{
            // 如果重复提交，不提示
            PcsProposal pcsProposal = pcsProposalMapper.selectByPrimaryKey(record.getId());
            sumitMsgToAdmin = (pcsProposal.getStatus()==PcsConstants.PCS_PROPOSAL_STATUS_SAVE
                    && record.getStatus()==PcsConstants.PCS_PROPOSAL_STATUS_INIT); // 暂存后首次提交
        }

        pcsProposalService.saveOrUpdate(record, pcsProposalFiles, inviteIds);
        logger.info(addLog(LogConstants.LOG_PCS, "添加/更新提案：%s", record.getId()));

        if(sumitMsgToAdmin){
            pcsProposalService.sendPcsProposalSubmitMsgToAdmin(record.getId(), ContextHelper.getRealIp());
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("id", record.getId());
        return resultMap;
    }

    // 选择邀请附议人（全部党代表，不包含自己）
    @RequestMapping("/pcsProposal_candidates")
    public String pcsProposal_candidates(ModelMap modelMap) {

        if (!ShiroHelper.isPermitted("pcsProposalPr:*")
                && !ShiroHelper.isPermitted("pcsProposalOw:*")) {
            throw new UnauthenticatedException();
        }
        int userId = ShiroHelper.getCurrentUserId();
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        PcsPrCandidateExample example = new PcsPrCandidateExample();
        example.createCriteria().andConfigIdEqualTo(configId).andStageEqualTo(PcsConstants.PCS_STAGE_SECOND)
                .andIsChosenEqualTo(true).andIsProposalEqualTo(true)
                .andUserIdNotEqualTo(userId);
        example.setOrderByClause("proposal_sort_order asc");
        List<PcsPrCandidate> candidates = pcsPrCandidateMapper.selectByExample(example);

        modelMap.put("candidates", candidates);

        return "pcs/pcsProposal/pcsProposal_candidates";
    }

    // 查看附议人
    @RequestMapping("/pcsProposal_seconders")
    public String pcsProposal_seconders(int id, ModelMap modelMap) {

        if (!ShiroHelper.isPermitted("pcsProposalPr:*")
                && !ShiroHelper.isPermitted("pcsProposalOw:*")) {
            throw new UnauthenticatedException();
        }
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        PcsProposalView pcsProposal = pcsProposalViewMapper.selectByPrimaryKey(id);
        modelMap.put("pcsProposal", pcsProposal);
        List<PcsPrCandidate> candidates = pcsProposalService.getSeconderCandidates(configId, pcsProposal);
        modelMap.put("candidates", candidates);

        return "pcs/pcsProposal/pcsProposal_seconders";
    }

    //@RequiresPermissions("pcsProposal:edit")
    @RequestMapping("/pcsProposal_au")
    public String pcsProposal_au(Integer id, ModelMap modelMap) {

        if (!ShiroHelper.isPermitted("pcsProposalPr:*")
                && !ShiroHelper.isPermitted("pcsProposalOw:*")) {
            throw new UnauthenticatedException();
        }

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        PcsPrCandidate pcsPrCandidate =
                pcsPrCandidateService.find(ShiroHelper.getCurrentUserId(), configId, PcsConstants.PCS_STAGE_SECOND);
        modelMap.put("candidate", pcsPrCandidate);

        if (id != null) {
            PcsProposalView pcsProposal = pcsProposalViewMapper.selectByPrimaryKey(id);
            modelMap.put("pcsProposal", pcsProposal);

            // 读取已经邀请的附议人
            modelMap.put("candidates", pcsProposalService.getInviteCandidates(configId, pcsProposal));
        }

        Map<Integer, MetaType> prTypes = metaTypeService.metaTypes("mc_pcs_proposal");
        modelMap.put("prTypes", prTypes.values());

        return "pcs/pcsProposal/pcsProposal_au";
    }

    //@RequiresPermissions("pcsProposal:del")
    @RequestMapping(value = "/pcsProposal_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsProposal_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (!ShiroHelper.isPermitted("pcsProposalPr:*")
                && !ShiroHelper.isPermitted("pcsProposalOw:*")) {
            throw new UnauthenticatedException();
        }

        if (null != ids && ids.length > 0) {
            pcsProposalService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PCS, "批量删除提案：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("pcsProposal:del")
    @RequestMapping(value = "/pcsProposal_batchDelFiles", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsProposal_batchDelFiles(HttpServletRequest request,
                                         Integer[] ids, ModelMap modelMap) {

        if (!ShiroHelper.isPermitted("pcsProposalPr:*")
                && !ShiroHelper.isPermitted("pcsProposalOw:*")) {
            throw new UnauthenticatedException();
        }

        if (null != ids && ids.length > 0) {
            pcsProposalService.batchDelFiles(ids);
            logger.info(addLog(LogConstants.LOG_PCS, "批量删除提案附件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void pcsProposal_export(PcsProposalViewExample example, HttpServletResponse response) {

        List<PcsProposalView> records = pcsProposalViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"提案编号|100", "用户|100", "标题|100", "关键字|100", "提案类型|100", "创建时间|100", "状态|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsProposalView record = records.get(i);
            String[] values = {
                    record.getCode(),
                    record.getUserId() + "",
                    record.getName(),
                    record.getKeywords(),
                    record.getType() + "",
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                    record.getStatus() + ""
            };
            valuesList.add(values);
        }
        String fileName = "提案_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/pcsProposal_selects")
    @ResponseBody
    public Map pcsProposal_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsProposalExample example = new PcsProposalExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = pcsProposalMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsProposal> pcsProposals = pcsProposalMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != pcsProposals && pcsProposals.size() > 0) {

            for (PcsProposal pcsProposal : pcsProposals) {

                Select2Option option = new Select2Option();
                option.setText(pcsProposal.getName());
                option.setId(pcsProposal.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }


    // 根据账号或姓名或学工号 查询 党代表
    @RequestMapping("/pcsProposal_pr_selects")
    @ResponseBody
    public Map pcsProposal_pr_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = SqlUtils.like(searchStr);

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        byte stage = PcsConstants.PCS_STAGE_SECOND;

        int count = iPcsMapper.countPrList(configId, stage, searchStr);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsPrCandidate> records =
                iPcsMapper.selectPrList(configId, stage, searchStr, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != records && records.size() > 0) {

            for (PcsPrCandidate candidate : records) {
                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(candidate.getUserId());
                option.put("id", candidate.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("user", userBeanService.get(candidate.getUserId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
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
