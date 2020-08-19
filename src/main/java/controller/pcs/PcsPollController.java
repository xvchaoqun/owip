package controller.pcs;

import controller.global.OpException;
import domain.member.Member;
import domain.pcs.PcsBranch;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPoll;
import domain.pcs.PcsPollExample;
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
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.PcsConstants;
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

        PcsPollExample example = new PcsPollExample();
        PcsPollExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(true);
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        List<PcsPoll> pcsPolls = pcsPollMapper.selectByExample(example);

        modelMap.put("cancelCount", pcsPolls != null ? pcsPolls.size() : "0");
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
        return "/pcs/pcsPoll/pcsPollResult";
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
        example.setOrderByClause("id desc");

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

        if (pcsPollService.isPcsPollExisted(record)){
            throw new OpException("创建投票重复，每个党支部在每个阶段只允许创建一次投票");
        }

        if (record.getStage() == PcsConstants.PCS_POLL_THIRD_STAGE){
            record.setPrNum(0);
        }else {
            record.setPrNum(pcsPollCandidateService.getPrRequiredCount(record.getPartyId()));
        }
        try {
            record.setDwNum(CmTag.getIntProperty("pcs_poll_dw_num"));
            record.setJwNum(CmTag.getIntProperty("pcs_poll_jw_num"));
        }catch(Exception e){
            throw new OpException("属性值错误");
        }

        if (id == null) {
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

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        if (pcsConfig == null){
            throw new OpException("当前党代会为空，请先设置当前党代会。");
        }
        if (id != null) {
            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
            modelMap.put("pcsPoll", pcsPoll);
            Integer partyId = pcsPoll.getPartyId();
            Integer branchId = pcsPoll.getBranchId();
            if (partyId != null){
                modelMap.put("party", partyMapper.selectByPrimaryKey(partyId));
            }
            if (branchId != null){
                modelMap.put("branch", branchMapper.selectByPrimaryKey(branchId));
            }
        }

        modelMap.put("pcsConfig", pcsConfig);

        return "pcs/pcsPoll/pcsPoll_au";
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping("/pcsPoll_report")
    public String pcsPoll_report(Integer id, ModelMap modelMap) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
        Byte stage = pcsPoll.getStage();
        modelMap.put("stage", stage);

        PcsBranch pcsBranch = pcsPollInspectorService.getPcsBranch(pcsPoll);
        modelMap.put("allCount", pcsBranch.getMemberCount());
        modelMap.put("positiveCount", pcsBranch.getPositiveCount());
        modelMap.put("inspectorNum", pcsPoll.getInspectorNum());
        modelMap.put("inspectorFinishNum", pcsPoll.getInspectorFinishNum());
        modelMap.put("positiveFinishNum", pcsPoll.getPositiveFinishNum());

        List<Integer> pollIdList = new ArrayList<>();
        pollIdList.add(id);

        //获取各个推荐人类型的候选人数
        Map<Byte, Integer> candidateCountMap = new HashMap<>();
        Map<Byte, Integer> hasCountMap = new HashMap<>();
        int candidateCount = 0;//候选人数
        for (Byte key : PcsConstants.PCS_POLL_CANDIDATE_TYPE.keySet()){

            /*if (key == PcsConstants.PCS_POLL_CANDIDATE_PR && stage == PcsConstants.PCS_POLL_THIRD_STAGE){
                candidateCountMap.put(key, candidateCount);
                continue;
            }*/

            if (stage == PcsConstants.PCS_POLL_FIRST_STAGE) {
                candidateCount =  iPcsMapper.countResult(key, pollIdList, stage, true, null, null, null, null, null);
            }else{
                candidateCount = iPcsMapper.countSecondResult(key, pollIdList, stage, true, null, null, null, null, null);
            }
            candidateCountMap.put(key, candidateCount);
        }
        int prNum = candidateCountMap.get(PcsConstants.PCS_POLL_CANDIDATE_PR);
        int dwNum = candidateCountMap.get(PcsConstants.PCS_POLL_CANDIDATE_DW);
        int jwNum = candidateCountMap.get(PcsConstants.PCS_POLL_CANDIDATE_JW);
        modelMap.put("prNum", prNum);
        modelMap.put("dwNum", dwNum);
        modelMap.put("jwNum", jwNum);

        return "pcs/pcsPoll/pcsPoll_report";
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPoll_report", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPoll_report(HttpServletRequest request, Integer id) {

        if (id != null){

            //权限判断
            pcsPollService.judgeAuthority(Arrays.asList(id));

            pcsPollService.batchReport(id);
            logger.info(log( LogConstants.LOG_PCS, "批量报送党代会投票：{0}", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:del")
    @RequestMapping(value = "/pcsPoll_batchCancel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPoll_batchCancel(HttpServletRequest request, Integer[] ids, boolean isDeleted) {

        if (null != ids && ids.length>0){

            //权限判断
            pcsPollService.judgeAuthority(Arrays.asList(ids));

            for (Integer id : ids) {
                PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
                if (pcsPoll.getHasReport()){
                    throw new OpException("{0}的{1}党代会投票已报送，不能作废", PcsConstants.PCS_STAGE_MAP.get(pcsPoll.getStage()), pcsPoll.getName());
                }
            }

            pcsPollService.batchCancel(ids, isDeleted);
            logger.info(log( LogConstants.LOG_PCS, "批量{1}党代会投票：{0}", StringUtils.join(ids, ",")), isDeleted?"作废":"取消作废");
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:del")
    @RequestMapping(value = "/pcsPoll_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPoll_batchDel(HttpServletRequest request, Integer[] ids) {

        if (null != ids && ids.length>0){

            //权限判断
            pcsPollService.judgeAuthority(Arrays.asList(ids));

            pcsPollService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PCS, "批量删除党代会投票：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPoll_noticeEdit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPoll_noticeEdit(Integer id, String notice, Integer isMobile, HttpServletRequest request) {

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

    @RequiresPermissions("drOnline:edit")
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
    }

    public void pcsPoll_export(PcsPollExample example, HttpServletResponse response) {

        List<PcsPoll> records = pcsPollMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属二级分党委|100","所属支部|100","投票名称|100","党代会|100","投票阶段 0一下阶段 1二下阶段|100","是否报送|100","代表最大推荐人数|100","党委委员最大推荐人数|100","纪委委员最大推荐人数|100","参评人数量|100","pc端投票说明|100","手机端投票说明|100","账号分发说明|100","投票起始时间|100","投票截止时间|100","备注|100"};
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
