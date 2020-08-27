package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.PcsFinalResult;
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
    private PcsOwService pcsOwService;
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

    // 党支部、分党委投票管理权限验证
    public void checkPollEditAuth(Integer id){
        checkPollEditAuth(Arrays.asList(id));
    }

    public void checkPollEditAuth(List<Integer> ids){

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

        int id = record.getId();

        checkPollEditAuth(id);

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
        if(BooleanUtils.isTrue(pcsPoll.getHasReport())){
            throw new OpException("投票已报送，无法修改");
        }

        // 不可修改所在党组织及状态
        record.setPartyId(null);
        record.setBranchId(null);
        record.setStage(null);
        record.setIsDeleted(null);
        record.setHasReport(null);

        pcsPollMapper.updateByPrimaryKeySelective(record);
    }

    // 党支部报送
    @Transactional
    public void report(int pollId, int expectMemberCount, int actualMemberCount) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);

        checkReportData(pcsPoll);

        // 报送前必须更新一下候选人的相关投票数量（可能撤回报送重新投票了，支部又没做重新设置候选人，所以投票结果不是最新的）
        PcsPollReportExample example = new PcsPollReportExample();
        example.createCriteria().andPollIdEqualTo(pollId);
        List<PcsPollReport> pcsPollReports = pcsPollReportMapper.selectByExample(example);

        for (PcsPollReport report : pcsPollReports) {

            byte stage = report.getStage();
            byte type = report.getType();
            int userId = report.getUserId();

            PcsPollReport record = new PcsPollReport();
            record.setId(report.getId());
            pcsPollReportService.getPcsPollReportOfVoteResult(record, pollId, stage, type, userId);

            pcsPollReportMapper.updateByPrimaryKeySelective(record);
        }



        PcsPoll record = new PcsPoll();
        record.setId(pollId);
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

    // 分党委作废/撤销作废
    @Transactional
    public void batchCancel(Integer[] ids, boolean isDeleted) {

        if(ids==null || ids.length==0) return;

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        for (Integer id : ids) {

            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
            int stage = pcsPoll.getStage();
            int partyId = pcsPoll.getPartyId();
            PcsParty pcsParty = pcsPartyService.get(configId, partyId);
            if(configId!= pcsPoll.getConfigId()|| stage != pcsParty.getCurrentStage()){
                throw new OpException("操作失败，与当前所在的投票阶段不相符");
            }

            if(!isDeleted) { // 撤销作废，要判断是否已有投票
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

    // 分党委退回党支部的报送
    @Transactional
    public void reportBack(Integer[] ids) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        for (Integer id : ids) {

            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
            int stage = pcsPoll.getStage();
            int partyId = pcsPoll.getPartyId();
            PcsParty pcsParty = pcsPartyService.get(configId, partyId);
            if(configId!= pcsPoll.getConfigId()|| stage != pcsParty.getCurrentStage()){
                throw new OpException("操作失败，与当前所在的投票阶段不相符");
            }

            //设置为未报送
            PcsPoll record = new PcsPoll();
            record.setId(pcsPoll.getId());
            record.setHasReport(false);
            pcsPollMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 二下/三下投票 候选人推荐人选名单
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
                candidateUserIds = pcsOwService.getCandidateUserIds(configId, stage, type);
            }
        }

        return candidateUserIds;
    }

    /*
     * @return 结果中需要用到user_id、positive_ballot
     * */
    // 获得支部的候选人结果
    public List<PcsPollReport> getCandidates(int configId, byte stage, byte type, int partyId, Integer branchId){

        List<PcsPollReport> pcsPollReportList = pcsPollReportService.getReport(type, configId, stage, partyId, branchId);

        return pcsPollReportList;
    }

    /*
     * @return 结果中需要用到userId、branchNum、positiveBallot
     * */
    // 获得分党委的推荐汇总结果
    public List<PcsFinalResult> getCandidates(int configId, int partyId, byte type, byte stage){

        List<PcsFinalResult> finalResultList = iPcsMapper.selectReport(type, configId, stage, null, partyId, null, null, null, new RowBounds());

        return finalResultList;
    }
}
