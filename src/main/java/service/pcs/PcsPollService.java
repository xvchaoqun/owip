package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.LoginUserService;
import shiro.ShiroHelper;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class PcsPollService extends PcsBaseMapper {

    @Autowired
    private PcsPollReportService pcsPollReportService;
    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PcsPartyService pcsPartyService;
    @Autowired
    private PcsBranchService pcsBranchService;
    @Autowired
    private PcsPollCandidateService pcsPollCandidateService;
    @Autowired
    private LoginUserService loginUserService;

    //删除和作废的权限判断
    public void judgeAuthority(List<Integer> ids){

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)){
            List<Integer> partyIdList = loginUserService.adminPartyIdList();
            List<Integer> branchIdList = loginUserService.adminBranchIdList();
            for (Integer id : ids) {
                PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
                Integer partyId = pcsPoll.getPartyId();
                Integer branchId = pcsPoll.getBranchId();
                if (branchId != null && !branchIdList.contains(branchId)){
                    if (partyIdList.contains(partyId))
                        break;
                    throw new OpException("没有权限操作[{0}]的党代会投票信息", branchMapper.selectByPrimaryKey(branchId).getName());
                } else if (branchId != null && branchIdList.contains(branchId)) {
                    break;
                } else if (!partyIdList.contains(partyId)){
                    throw new OpException("没有权限操作[{0}]的党代会投票信息", partyMapper.selectByPrimaryKey(partyId).getName());
                }
            }
        }
    }

    @Transactional
    public void insertSelective(PcsPoll record){

        pcsPollMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsPollResultExample resultExample = new PcsPollResultExample();
        resultExample.createCriteria().andPollIdIn(Arrays.asList(ids));
        pcsPollResultMapper.deleteByExample(resultExample);

        PcsPollInspectorExample inspectorExample = new PcsPollInspectorExample();
        inspectorExample.createCriteria().andPollIdIn(Arrays.asList(ids));
        pcsPollInspectorMapper.deleteByExample(inspectorExample);

        PcsPollCandidateExample candidateExample = new PcsPollCandidateExample();
        candidateExample.createCriteria().andPollIdIn(Arrays.asList(ids));
        pcsPollCandidateMapper.deleteByExample(candidateExample);

        PcsPollExample example = new PcsPollExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsPoll record){

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
    public void report(Integer id) {

        if(id==null) return;

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);

        checkReportData(pcsPoll);

        PcsPoll record = new PcsPoll();
        record.setId(id);
        record.setHasReport(true);
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
        for (Byte key : PcsConstants.PCS_POLL_CANDIDATE_TYPE.keySet()){
            List<PcsPollReport> pcsPollReportList = pcsPollReportService.getReport(key, pcsPoll.getConfigId(),
                    stage, pcsPoll.getPartyId(), pcsPoll.getBranchId());
            candidateCount = pcsPollReportList.size();
            candidateCountMap.put(key, candidateCount);
        }
        int prCount = candidateCountMap.get(PcsConstants.PCS_POLL_CANDIDATE_PR);
        int dwCount = candidateCountMap.get(PcsConstants.PCS_POLL_CANDIDATE_DW);
        int jwCount = candidateCountMap.get(PcsConstants.PCS_POLL_CANDIDATE_JW);

        if (pcsPoll.getStage() != PcsConstants.PCS_POLL_THIRD_STAGE) {
            if (dwCount == 0 || jwCount == 0 || prCount == 0) {
                throw new OpException("党委委员、纪委委员、代表分别至少选1人，否则无法报送");
            }
        }else {
            if (dwCount == 0 || jwCount == 0) {
                throw new OpException("党委委员、纪委委员分别至少选1人，否则无法报送");
            }
        }
        int prMaxCount = pcsPollCandidateService.getPrMaxCount(pcsPoll.getPartyId());
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
    public List<Integer> getCurrentPcsPollId() {

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

        PcsPoll record = new PcsPoll();
        record.setIsDeleted(isDeleted);

        PcsPollExample example = new PcsPollExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollMapper.updateByExampleSelective(record, example);

    }
}
