package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.IPcsCandidate;
import persistence.pcs.common.ResultBean;
import service.LoginUserService;
import shiro.ShiroHelper;
import sys.constants.PcsConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PcsPollService extends PcsBaseMapper {

    @Autowired
    private PcsPollReportService pcsPollReportService;
    @Autowired
    private PcsPrCandidateService pcsPrCandidateService;
    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PcsPartyService pcsPartyService;
    @Autowired
    private PcsBranchService pcsBranchService;
    @Autowired
    private PcsPrAlocateService pcsPrAlocateService;
    @Autowired
    private LoginUserService loginUserService;

    // 得到某支部的有效投票记录
    public PcsPoll get(int configId, byte stage, int partyId, Integer branchId){

        PcsPollExample example = new PcsPollExample();
        PcsPollExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(stage).andPartyIdEqualTo(partyId).andIsDeletedEqualTo(false);
        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
        }

        List<PcsPoll> pcsPolls = pcsPollMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return pcsPolls.size()>0?pcsPolls.get(0):null;
    }

    //删除和作废的权限判断
    public void judgeAuthority(List<Integer> ids){

        if (ShiroHelper.lackRole(RoleConstants.ROLE_PCS_ADMIN)){

            List<Integer> partyIdList = loginUserService.adminPartyIdList();
            List<Integer> branchIdList = loginUserService.adminBranchIdList();

            for (int id : ids) {

                PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
                int partyId = pcsPoll.getPartyId();
                Integer branchId = pcsPoll.getBranchId();

                if(!partyIdList.contains(partyId) && (branchId==null || !branchIdList.contains(branchId))){

                    throw new OpException("没有权限操作");
                }
            }
        }
    }

    @Transactional
    public void insertSelective(PcsPoll record){

        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setCreateTime(new Date());
        pcsPollMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsPollExample example = new PcsPollExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsPoll record){

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(record.getId());
        if(BooleanUtils.isTrue(pcsPoll.getHasReport())){
            throw new OpException("投票已报送，无法修改");
        }

        pcsPollMapper.updateByPrimaryKeySelective(record);
    }

    //党代会投票是否已存在
    public Boolean isPcsPollExisted(PcsPoll record) {

        int configId = record.getConfigId();
        byte stage = record.getStage();
        int partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        PcsParty pcsParty = pcsPartyService.get(configId, partyId);

        if (branchId == null && BooleanUtils.isNotTrue(pcsParty.getIsDirectBranch())) {
            throw new OpException("请选择所属党支部");
        }
        PcsPollExample example = new PcsPollExample();
        PcsPollExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(record.getConfigId())
                .andStageEqualTo(stage).andPartyIdEqualTo(partyId).andIsDeletedEqualTo(false);
        if (branchId != null){
            criteria.andBranchIdEqualTo(branchId);
        }
        if(record.getId()!=null){
            criteria.andIdNotEqualTo(record.getId());
        }

        return pcsPollMapper.countByExample(example)>0;
    }

    // 党支部报送
    @Transactional
    public void report(int id, int expectMemberCount, int actualMemberCount) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);

        checkReportData(pcsPoll);

        PcsPoll record = new PcsPoll();
        record.setId(id);
        record.setHasReport(true);
        record.setExpectMemberCount(expectMemberCount);
        record.setActualMemberCount(actualMemberCount);
        record.setReportDate(new Date());

        pcsPollMapper.updateByPrimaryKeySelective(record);
    }

    // 检查是否可以报送
    public void checkReportData(PcsPoll pcsPoll){

        int id = pcsPoll.getId();
        //控制参评率（完成投票的数量/账号的数量）
        int inspectorNum = pcsPoll.getInspectorNum();
        int inspectorFinishNum = pcsPoll.getInspectorFinishNum();
        float rate = 0;
        if (inspectorNum != 0){
            rate = inspectorFinishNum/inspectorNum;
        }else {
            throw new OpException("还没有投票结果数据，不可报送");
        }
        if (rate < 0.8){
            throw new OpException("参评率未达要求（80%），不可报送");
        }

        int configId = pcsPoll.getConfigId();
        int partyId = pcsPoll.getPartyId();
        Integer branchId = pcsPoll.getBranchId();
        PcsBranch pcsBranch =  pcsBranchService.get(configId, partyId, branchId);

        int positiveCount = pcsBranch.getPositiveCount();
        int positiveFinishNum = pcsPoll.getPositiveFinishNum();
        if (positiveCount < positiveFinishNum){
            throw new OpException("推荐提名的正式党员数量大于本支部正式党员数量，不可报送");
        }

        List<Integer> pollIdList = new ArrayList<>();
        pollIdList.add(id);
        Map<Byte, Integer> candidateCountMap = new HashMap<>();
        int candidateCount = 0;//候选人数
        Byte stage = pcsPoll.getStage();
        for (Byte key : PcsConstants.PCS_USER_TYPE_MAP.keySet()){
            List<PcsPollReport> pcsPollReportList = pcsPollReportService.getReport(key, pcsPoll.getConfigId(),
                    stage, pcsPoll.getPartyId(), pcsPoll.getBranchId());
            candidateCount = pcsPollReportList.size();
            candidateCountMap.put(key, candidateCount);
        }
        int dwCount = candidateCountMap.get(PcsConstants.PCS_USER_TYPE_DW);
        int jwCount = candidateCountMap.get(PcsConstants.PCS_USER_TYPE_JW);
        int prCount = candidateCountMap.get(PcsConstants.PCS_USER_TYPE_PR);

        if (pcsPoll.getStage() != PcsConstants.PCS_POLL_THIRD_STAGE) {
            if (dwCount == 0 || jwCount == 0 || prCount == 0) {
                throw new OpException("党委委员、纪委委员、代表分别至少选1人，否则无法报送");
            }
        }else {
            if (dwCount == 0 || jwCount == 0) {
                throw new OpException("党委委员、纪委委员分别至少选1人，否则无法报送");
            }
        }
        int prMaxCount = pcsPrAlocateService.getPrMaxCount(configId, partyId);
        Integer dwMaxCount = CmTag.getIntProperty("pcs_poll_dw_num");
        Integer jwMaxCount = CmTag.getIntProperty("pcs_poll_jw_num");

        if (prCount > prMaxCount) {
            throw new OpException("代表中的候选人超过最大应推荐数量({0})，不可报送", prMaxCount);
        }else if (dwCount > dwMaxCount){
            throw new OpException("党委委员中的候选人超过最大应推荐数量({0})，不可报送", dwMaxCount);
        }else if (jwCount > jwMaxCount){
            throw new OpException("纪委委员中的候选人超过最大应推荐数量({0})，不可报送", jwMaxCount);
        }
    }

    //得到当前党代会投票的ids
    public List<Integer> getCurrentPcsPollId(){

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        Integer configId = pcsConfig.getId();

        PcsPollExample example = new PcsPollExample();
        example.createCriteria().andConfigIdEqualTo(configId).andIsDeletedEqualTo(false).andHasReportEqualTo(true);
        List<PcsPoll> pcsPolls = pcsPollMapper.selectByExample(example);
        List<Integer> pollIdList = new ArrayList<>();
        if (pcsPolls != null && pcsPolls.size() > 0) {
            for (PcsPoll pcsPoll : pcsPolls) {
                pollIdList.add(pcsPoll.getId());
            }
        }

        return pollIdList;
    }

    @Transactional
    public void batchCancel(Integer[] ids, boolean isDeleted) {

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {

            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);

            if(!isDeleted) { // 返回列表，要判断是否已有投票
                PcsPoll _pcsPoll = get(pcsPoll.getConfigId(), pcsPoll.getStage(), pcsPoll.getPartyId(), pcsPoll.getBranchId());
                if(_pcsPoll!=null){
                    throw new OpException("【{0}】已经存在有效的投票记录，无法撤销作废。", StringUtils.defaultString(pcsPoll.getBranchName(), pcsPoll.getPartyName()));
                }
            }

            PcsPoll record = new PcsPoll();
            record.setId(pcsPoll.getId());
            record.setIsDeleted(isDeleted);
            pcsPollMapper.updateByPrimaryKeySelective(record);
        }
    }

    //List<PcsPollCandidate> -- 二下/三下候选人推荐人选名单
    public List<Integer> getCandidateUserIds(int pollId, byte type){

        List<Integer> candidateUserIds = new ArrayList<>();

        // for test
        /*PcsPollCandidateExample example = new PcsPollCandidateExample();
        example.createCriteria().andPollIdEqualTo(pollId).andTypeEqualTo(type);
        example.setOrderByClause("sort_order desc");
        List<PcsPollCandidate> pcsPollCandidates = pcsPollCandidateMapper.selectByExample(example);
        return pcsPollCandidates.stream().map(PcsPollCandidate::getUserId).collect(Collectors.toList());*/

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        int configId = pcsPoll.getConfigId();
        byte stage = pcsPoll.getStage();
        int partyId = pcsPoll.getPartyId();

        if(stage == PcsConstants.PCS_STAGE_SECOND || stage == PcsConstants.PCS_STAGE_THIRD){

            stage = (byte) (stage-1);
            if(type==PcsConstants.PCS_USER_TYPE_PR){ // 代表

                PcsPrCandidateExample example = pcsPrCandidateService.createExample(configId, stage, partyId, null);
                List<PcsPrCandidate> candidates = pcsPrCandidateMapper.selectByExample(example);
                candidateUserIds = candidates.stream().map(PcsPrCandidate::getUserId).collect(Collectors.toList());
            }else{ // 两委委员

                List<IPcsCandidate> candidates = iPcsMapper.selectPartyCandidateList(null, true, configId, stage, type, new RowBounds());
                candidateUserIds = candidates.stream().map(IPcsCandidate::getUserId).collect(Collectors.toList());
            }
        }

        return candidateUserIds;
    }

    /*
    * @return 结果中得票数为提名正式党员数
    * */
    // 获得支部的推荐汇总结果
    public List<ResultBean> getCandidates(int configId, byte stage, byte type, int partyId, Integer branchId){

        List<PcsPollReport> pcsPollReportList = pcsPollReportService.getReport(type, configId, stage, partyId, branchId);

        List<ResultBean> resultBeans = new ArrayList<>();
        if (pcsPollReportList.size() > 0){

            for (PcsPollReport report : pcsPollReportList) {
                ResultBean bean = new ResultBean();
                bean.setUserId(report.getUserId());
                bean.setBallot(report.getPositiveBallot());

                resultBeans.add(bean);
            }
        }

        return resultBeans;
    }

    // 获得分党委的推荐汇总结果
    public List<ResultBean> getCandidates(int configId, int partyId, byte type, byte stage){


        List<PcsPollReport> pcsPollReportList = pcsPollReportService.getReport(type, configId, stage, partyId,null);

        List<ResultBean> resultBeans = new ArrayList<>();
        if (pcsPollReportList.size() > 0){
            for (PcsPollReport report : pcsPollReportList) {

                ResultBean bean = new ResultBean();
                bean.setUserId(report.getUserId());
                bean.setBranchNum(iPcsMapper.getBranchNum(configId, stage, type, partyId, report.getUserId()));
                bean.setBallot(report.getPositiveBallot());

                resultBeans.add(bean);
            }
        }

        return resultBeans;
    }
}
