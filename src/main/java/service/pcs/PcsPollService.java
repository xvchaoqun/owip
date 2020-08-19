package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.LoginUserService;
import service.party.PartyService;
import shiro.ShiroHelper;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class PcsPollService extends PcsBaseMapper {

    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private PcsPollInspectorService pcsPollInspectorService;
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

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        if (partyId != null && branchId == null) {
            if (!partyService.isDirectBranch(partyId)) {
                throw new OpException("请选择所属党支部");
            }
        }
        PcsPollExample example = new PcsPollExample();
        PcsPollExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(record.getConfigId())
                .andStageEqualTo(record.getStage()).andIsDeletedEqualTo(false).andPartyIdEqualTo(partyId);
        if (branchId != null){
            criteria.andBranchIdEqualTo(branchId);
        }
        if(record.getId()!=null){
            criteria.andIdNotEqualTo(record.getId());
        }

        return pcsPollMapper.countByExample(example)>0;
    }

    @Transactional
    public void batchReport(Integer id) {

        if(id==null) return;

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);

        //控制参评率（完成投票的数量/账号的数量）
        int inspectorNum = pcsPoll.getInspectorNum();
        int inspectorFinishNum = pcsPoll.getInspectorFinishNum();
        float rate = 0;
        if (inspectorNum != 0){
            rate = inspectorFinishNum/inspectorNum;
        }else {
            throw new OpException("请按要求完成此次投票后再报送");
        }
        if (rate <0.8 || rate >1){
            throw new OpException("参评率未达要求（80%）");
        }

        PcsBranch pcsBranch = pcsPollInspectorService.getPcsBranch(pcsPoll);
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
            if (stage == PcsConstants.PCS_POLL_FIRST_STAGE) {
                candidateCount =  iPcsMapper.countResult(key, pollIdList, stage, true, null, null, null, null, null);
            }else{
                candidateCount = iPcsMapper.countSecondResult(key, pollIdList, stage, true, null, null, null, null, null);
            }
            candidateCountMap.put(key, candidateCount);
        }
        int prCount = candidateCountMap.get(PcsConstants.PCS_POLL_CANDIDATE_PR);
        int dwCount = candidateCountMap.get(PcsConstants.PCS_POLL_CANDIDATE_DW);
        int jwCount = candidateCountMap.get(PcsConstants.PCS_POLL_CANDIDATE_JW);

        try {
            if (prCount > pcsPollCandidateService.getPrRequiredCount(pcsPoll.getPartyId())) {
                throw new OpException("代表中的候选人超过规定数量，不可报送");
            }else if (dwCount > CmTag.getIntProperty("pcs_poll_dw_num")){
                throw new OpException("党委委员中的候选人超过规定数量，不可报送");
            }else if (jwCount > CmTag.getIntProperty("pcs_poll_jw_num")){
                throw new OpException("纪委委员中的候选人超过规定数量，不可报送");
            }
        }catch (Exception e){
            throw new OpException("参数设置错误");
        }

        PcsPoll record = new PcsPoll();
        record.setId(id);
        record.setHasReport(true);
        pcsPollMapper.updateByPrimaryKeySelective(record);
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
