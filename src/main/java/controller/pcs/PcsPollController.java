package controller.pcs;

import controller.global.OpException;
import domain.pcs.*;
import domain.pcs.PcsPollExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import sys.constants.LogConstants;
import sys.constants.PcsConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/pcs")
public class PcsPollController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPoll:list")
    @RequestMapping("/pcsPoll")
    public String pcsPoll(@RequestParam(required = false, defaultValue = "1") int cls, // cls=5 已作废
                          Byte stage,
                          Integer partyId,
                          Integer branchId,
                          Integer userId,
                          ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (partyId != null) {
            modelMap.put("party", partyMapper.selectByPrimaryKey(partyId));
        }
        if (branchId != null){
            modelMap.put("branch", branchMapper.selectByPrimaryKey(branchId));
        }

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        if (pcsConfig == null){
            throw new OpException("请先设置当前党代会");
        }

        PcsPollExample example = new PcsPollExample();
        PcsPollExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(true).andConfigIdEqualTo(pcsConfig.getId());
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        modelMap.put("cancelCount", pcsPollMapper.countByExample(example));

        if (cls == 1 || cls==5){
            return "/pcs/pcsPoll/pcsPoll_page";
        }

        //汇总结果
        if (stage != null) {
            modelMap.put("stage", stage);
        }
        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        return "/pcs/pcsPoll/pcsPollReport/pcsPollReport_list_page";
    }

    @RequiresPermissions("pcsPoll:list")
    @RequestMapping("/pcsPoll_data")
    @ResponseBody
    public void pcsPoll_data(HttpServletResponse response,
                             String name,
                             Integer partyId,
                             Integer branchId,
                             Byte stage,
                             Boolean hasReport,
                             @RequestParam(required = false, defaultValue = "1") int cls,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             Integer[] ids, // 导出的记录
                             Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        if (pcsConfig == null){
            throw new OpException("请先设置当前党代会");
        }
        PcsPollExample example = new PcsPollExample();
        Criteria criteria = example.createCriteria().andConfigIdEqualTo(pcsConfig.getId());
        example.setOrderByClause("stage desc,party_id desc,branch_id desc,id desc");

        criteria.andIsDeletedEqualTo(cls == 5);

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.trimLike(name));
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (stage != null){
            criteria.andStageEqualTo(stage);
        }
        if (hasReport != null){
            criteria.andHasReportEqualTo(hasReport);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsPoll_export(example, response);
            return;
        }

        long count = pcsPollMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsPoll> records= pcsPollMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsPoll.class, pcsPollMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPoll_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPoll_au(PcsPoll record, HttpServletRequest request) {

        Integer id = record.getId();

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        int partyId = record.getPartyId();
        PcsParty pcsParty = pcsPartyService.get(configId, partyId);
        Byte currentStage = pcsParty.getCurrentStage();

        if(currentStage==null || !PcsConstants.PCS_STAGE_MAP.containsKey(currentStage)){
            return failed("投票未开启");
        }

        record.setPartyName(pcsParty.getName());
        record.setConfigId(configId); // 默认当前党代会
        record.setStage(currentStage); // 默认为当前阶段

        if(record.getBranchId()!=null) {
            PcsBranch pcsBranch = pcsBranchService.get(configId, partyId, record.getBranchId());
            record.setBranchName(pcsBranch.getName());
        }

        if (currentStage == PcsConstants.PCS_POLL_THIRD_STAGE){
            record.setPrNum(0);
        }else {
            record.setPrNum(pcsPrAllocateService.getPrMaxCount(configId, partyId));
        }

        try {
            record.setDwNum(CmTag.getIntProperty("pcs_poll_dw_num"));
            record.setJwNum(CmTag.getIntProperty("pcs_poll_jw_num"));
        }catch(Exception e){
            throw new OpException("参数设置错误");
        }

        if (id == null) {

            Integer branchId = record.getBranchId();
            PcsPoll pcsPoll = pcsPollService.get(configId, currentStage, partyId, branchId);

            if (pcsPoll!=null){
                throw new OpException("创建投票重复，每个党支部在每个阶段只允许创建一次投票");
            }

            pcsPollService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PCS, "添加党代会投票：{0}", record.getId()));
        } else {

            pcsPollService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PCS, "更新党代会投票：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping("/pcsPoll_au")
    public String pcsPoll_au(Integer id, ModelMap modelMap) {

        if (id != null) {

            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
            modelMap.put("pcsPoll", pcsPoll);
            int configId = pcsPoll.getConfigId();
            int partyId = pcsPoll.getPartyId();
            Integer branchId = pcsPoll.getBranchId();

            modelMap.put("pcsParty", pcsPartyService.get(configId, partyId));
            modelMap.put("pcsBranch", pcsBranchService.get(configId, partyId, branchId));
        }

        return "pcs/pcsPoll/pcsPoll_au";
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping("/pcsPoll_stage")
    @ResponseBody
    public Byte pcsPoll_stage(Integer partyId, ModelMap modelMap) {

        if (partyId != null) {

            PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
            int configId = currentPcsConfig.getId();
            PcsParty pcsParty = pcsPartyService.get(configId, partyId);

            if(pcsParty!=null) {
                Byte currentStage = pcsParty.getCurrentStage();
                return currentStage == null ? 0 : currentStage;
            }
        }

        return -1;
    }

    @RequiresPermissions("pcsPoll:open")
    @RequestMapping("/pcsPoll_open")
    public String pcsPoll_open() {

        return "pcs/pcsPoll/pcsPoll_open";
    }

    @RequiresPermissions("pcsPoll:open")
    @RequestMapping(value = "/pcsPoll_open", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPoll_open(int partyId, ModelMap modelMap) {

        if(!RoleConstants.isOwAdmin()){

            PcsAdmin pcsAdmin = pcsAdminService.getPartyAdmin(ShiroHelper.getCurrentUserId());
            if(pcsAdmin==null ||pcsAdmin.getPartyId()!=partyId){
                return failed("没有权限");
            }
        }

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        PcsParty pcsParty = pcsPartyService.get(configId, partyId);
        Byte currentStage = pcsParty.getCurrentStage();

        if(currentStage==null){
            currentStage = 1;
        }else{
            currentStage = (byte)(currentStage + 1);
        }

        if(currentStage>3){
            return failed("所在"+CmTag.getStringProperty("partyName")+"的支部投票已全部启动");
        }

        if(currentStage==2){
            // 验证是否可以开启二下阶段投票：二下的两委名单已下发且一上的代表名单已审核通过

            boolean hasIssue = pcsOwService.hasIssue(configId, PcsConstants.PCS_STAGE_FIRST);
            PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, PcsConstants.PCS_STAGE_FIRST, partyId);

            if(!hasIssue || pcsPrRecommend==null
                    || pcsPrRecommend.getStatus()!=PcsConstants.PCS_PR_RECOMMEND_STATUS_PASS){

                return failed("请等待学校党委下发两委委员的“二下名单”并审核通过代表的“二下名单”，再开启二下投票");
            }

        }else if(currentStage==3){
            // 验证是否可以开启三下阶段投票：三下的两委名单已下发且二上的代表名单已审核通过

            boolean hasIssue = pcsOwService.hasIssue(configId, PcsConstants.PCS_STAGE_SECOND);
            PcsPrRecommend pcsPrRecommend = pcsPrPartyService.getPcsPrRecommend(configId, PcsConstants.PCS_STAGE_SECOND, partyId);

            if(!hasIssue || pcsPrRecommend==null
                    || pcsPrRecommend.getStatus()!=PcsConstants.PCS_PR_RECOMMEND_STATUS_PASS){

                return failed("请等待学校党委下发两委委员的“三下名单”，再开启二下投票");
            }
        }

        PcsParty record = new PcsParty();
        record.setId(pcsParty.getId());
        record.setCurrentStage(currentStage);
        pcsPartyMapper.updateByPrimaryKeySelective(record);

        return success();
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping("/pcsPoll_report")
    public String pcsPoll_report(Integer id, ModelMap modelMap) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
        modelMap.put("pcsPoll", pcsPoll);

        Byte stage = pcsPoll.getStage();
        modelMap.put("stage", stage);

        int configId = pcsPoll.getConfigId();
        int partyId = pcsPoll.getPartyId();
        Integer branchId = pcsPoll.getBranchId();
        PcsBranch pcsBranch =  pcsBranchService.get(configId, partyId, branchId);
        modelMap.put("pcsBranch", pcsBranch);

        List<Integer> pollIdList = new ArrayList<>();
        pollIdList.add(id);

        //获取各个推荐人类型的候选人数
        Map<Byte, Integer> candidateCountMap = new HashMap<>();
        Map<Byte, Integer> hasCountMap = new HashMap<>();
        int candidateCount = 0;//候选人数
        for (Byte key : PcsConstants.PCS_USER_TYPE_MAP.keySet()){

            List<PcsPollReport> pcsPollReportList = pcsPollReportService.getReport(key, pcsPoll.getConfigId(),
                    stage, pcsPoll.getPartyId(), pcsPoll.getBranchId());
            candidateCount = pcsPollReportList.size();
            candidateCountMap.put(key, candidateCount);
        }

        int prNum = candidateCountMap.get(PcsConstants.PCS_USER_TYPE_PR);
        int dwNum = candidateCountMap.get(PcsConstants.PCS_USER_TYPE_DW);
        int jwNum = candidateCountMap.get(PcsConstants.PCS_USER_TYPE_JW);
        modelMap.put("prNum", prNum);
        modelMap.put("dwNum", dwNum);
        modelMap.put("jwNum", jwNum);

        return "pcs/pcsPoll/pcsPoll_report";
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPoll_report", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPoll_report(HttpServletRequest request, int id, Integer expectMemberCount, Integer actualMemberCount) {

        //权限判断
        pcsPollService.checkPollEditAuth(id);

        pcsPollService.report(id, expectMemberCount, actualMemberCount);
        logger.info(log(LogConstants.LOG_PCS, "报送党代会投票：{0}", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:abolish")
    @RequestMapping(value = "/pcsPoll_reportBack", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPoll_reportBack(HttpServletRequest request,  Integer[] ids) {

        if (null != ids && ids.length>0){

            //权限判断
            pcsPollService.checkPollEditAuth(Arrays.asList(ids));

            pcsPollService.reportBack(ids);
            logger.info(log( LogConstants.LOG_PCS, "批量回退报送投票结果：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("pcsPoll:abolish")
    @RequestMapping(value = "/pcsPoll_batchCancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPoll_batchCancel(HttpServletRequest request, Integer[] ids, boolean isDeleted) {

        if (null != ids && ids.length>0){

            //权限判断
            pcsPollService.checkPollEditAuth(Arrays.asList(ids));

            pcsPollService.batchCancel(ids, isDeleted);
            logger.info(log( LogConstants.LOG_PCS, "批量{1}党代会投票：{0}", StringUtils.join(ids, ",")), isDeleted?"作废":"取消作废");
        }

        return success(FormUtils.SUCCESS);
    }

    // 删除投票
    @RequiresPermissions("pcsPoll:del")
    @RequestMapping(value = "/pcsPoll_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPoll_batchDel(HttpServletRequest request, Integer[] ids) {

        if (null != ids && ids.length>0){

            //权限判断
            pcsPollService.checkPollEditAuth(Arrays.asList(ids));

            pcsPollService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PCS, "批量删除党代会投票：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

/*    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPoll_noticeEdit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPoll_noticeEdit(int id, String notice, Integer isMobile, HttpServletRequest request) {


        PcsPoll record = new PcsPoll();
        record.setId(id);
        if (isMobile == 1){
            record.setMobileNotice(notice);
        }else {
            record.setNotice(notice);
        }

        pcsPollService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_PCS, "更新党代会投票说明：{0}", record.getId() + "_" + record.getName()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping("/pcsPoll_noticeEdit")
    public String pcsPoll_noticeEdit(Integer id, Integer isMobile, ModelMap modelMap){

        if (id != null) {
            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
            modelMap.put("pcsPoll", pcsPoll);
        }
        if (isMobile == null){
            return "/pcs/pcsPoll/inspector_notice";
        }

        return "/pcs/pcsPoll/pcsPoll_noticeEdit";
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/inspectorNotice", method = RequestMethod.POST)
    @ResponseBody
    public Map do_inspectorNotice(Integer id, String inspectorNotice, HttpServletRequest request) {

        PcsPoll record = new PcsPoll();
        record.setId(id);
        if (StringUtils.isNotBlank(inspectorNotice)){
            record.setInspectorNotice(inspectorNotice);
        }

        pcsPollService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_PCS, "更新党代会投票说明（纸质票）：{0}", record.getId() + "_" + record.getName()));
        return success(FormUtils.SUCCESS);
    }*/

    public void pcsPoll_export(PcsPollExample example, HttpServletResponse response) {

        List<PcsPoll> records = pcsPollMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属二级二级党组织|100","所属支部|100","投票名称|100","党代会|100","投票阶段 0一下阶段 1二下阶段|100","是否报送|100","代表最大推荐人数|100","党委委员最大推荐人数|100","纪委委员最大推荐人数|100","参评人数量|100","pc端投票说明|100","手机端投票说明|100","账号分发说明|100","投票起始时间|100","投票截止时间|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsPoll record = records.get(i);
            String[] values = {
                record.getPartyId()+"",
                record.getBranchId()+"",
                record.getName(),
                record.getConfigId()+"",
                record.getStage()+"",
                record.getHasReport()+"",
                record.getPrNum()+"",
                record.getDwNum()+"",
                record.getJwNum()+"",
                record.getInspectorNum()+"",
                record.getNotice(),
                record.getMobileNotice(),
                record.getInspectorNotice(),
                DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("党代会投票(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
