package service.pcs;

import controller.global.OpException;
import domain.pcs.PcsPoll;
import domain.pcs.PcsPollReport;
import domain.pcs.PcsPollReportExample;
import domain.sys.SysUserView;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.PcsFinalResult;
import sys.constants.PcsConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class PcsPollReportService extends PcsBaseMapper {

    @Autowired
    private PcsPrAlocateService pcsPrAlocateService;

    //
    public List<PcsPollReport> getReport(PcsPoll pcsPoll, Byte type){

        Integer partyId = pcsPoll.getPartyId();
        Integer branchId = pcsPoll.getBranchId();
        Byte stage = pcsPoll.getStage();
        PcsPollReportExample example = new PcsPollReportExample();
        PcsPollReportExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId)
                .andStageEqualTo(stage);
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if(branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        List<PcsPollReport> reportList = pcsPollReportMapper.selectByExample(example);

        return reportList;
    }

    public List<PcsPollReport> getReport(byte type, int configId, byte stage, int partyId, Integer branchId){

        PcsPollReportExample example = new PcsPollReportExample();
        PcsPollReportExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type).andConfigIdEqualTo(configId)
                .andStageEqualTo(stage).andPartyIdEqualTo(partyId);
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        List<PcsPollReport> pcsPollReportList = pcsPollReportMapper.selectByExample(example);
        return pcsPollReportList;
    }

    @Transactional
    public void batchInsert(Integer[] userIds,
                       Boolean isCandidate,
                       int pollId, byte type) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        Integer configId = pcsPoll.getConfigId();
        Integer partyId = pcsPoll.getPartyId();
        Integer branchId = pcsPoll.getBranchId();

        Byte stage = pcsPoll.getStage();

        List<Integer> pollIdList = new ArrayList<>();
        pollIdList.add(pollId);

        if (isCandidate) {//设置为候选人

            //计算候选人是否超过指定数值
            PcsPollReportExample reportExample = new PcsPollReportExample();
            PcsPollReportExample.Criteria reportCriteria = reportExample.createCriteria().andConfigIdEqualTo(configId).
                    andPartyIdEqualTo(partyId).andTypeEqualTo(type).andStageEqualTo(stage);
            if (branchId != null){
                reportCriteria.andBranchIdEqualTo(branchId);
            }
            List<PcsPollReport> reportList = pcsPollReportMapper.selectByExample(reportExample);
            Set<Integer> userIdSet  = new HashSet<>();
            Set<Integer> _userIdSet = new HashSet<>();//用于更新和插入候选人
            for (PcsPollReport _pcsPollReport : reportList){
                userIdSet.add(_pcsPollReport.getUserId());
            }
            _userIdSet.addAll(userIdSet);
            userIdSet.addAll(Arrays.asList(userIds));
            int requiredCount = 0;
            if (type == PcsConstants.PCS_POLL_CANDIDATE_PR){
                requiredCount = pcsPrAlocateService.getPrMaxCount(partyId);
            }else if (type == PcsConstants.PCS_POLL_CANDIDATE_DW){
                requiredCount = CmTag.getIntProperty("pcs_poll_dw_num");
            }else if (type == PcsConstants.PCS_POLL_CANDIDATE_JW){
                requiredCount = CmTag.getIntProperty("pcs_poll_jw_num");
            }
            if (userIdSet.size() > requiredCount){
                throw new OpException("设置失败，超过{0}的最大推荐数量({1})",
                        PcsConstants.PCS_POLL_CANDIDATE_TYPE.get(type), requiredCount);
            }

            for (Integer userId : userIds) {

                SysUserView uv = CmTag.getUserById(userId);

                PcsPollReport record = new PcsPollReport();
                record.setUserId(userId);
                record.setCode(uv.getCode());
                record.setRealname(uv.getRealname());
                record.setUnit(uv.getUnit());
                record.setConfigId(configId);
                record.setPartyId(partyId);
                if (branchId != null){
                    record.setBranchId(branchId);
                }
                record.setStage(stage);
                record.setType(type);

                List<PcsFinalResult> pcsFinalResultList = iPcsMapper.selectResultList(type, pollIdList, stage, userId, null, null, null, null, new RowBounds());
                PcsFinalResult finalResult = pcsFinalResultList.get(0);
                record.setBallot(finalResult.getSupportNum());
                record.setPositiveBallot(finalResult.getPositiveBallot());
                record.setGrowBallot(finalResult.getGrowBallot());
                record.setDisagreeBallot(finalResult.getNotSupportNum()==null?0:finalResult.getNotSupportNum());
                record.setAbstainBallot(finalResult.getNotVoteNum()==null?0:finalResult.getNotVoteNum());

                if (_userIdSet.contains(userId)){
                    PcsPollReportExample example = new PcsPollReportExample();
                    PcsPollReportExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(type).andStageEqualTo(stage)
                            .andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId);
                    if (branchId != null){
                        criteria.andBranchIdEqualTo(branchId);
                    }
                    pcsPollReportMapper.updateByExampleSelective(record, example);
                }else {
                    pcsPollReportMapper.insertSelective(record);
                }
            }

        }else {
            PcsPollReportExample example = new PcsPollReportExample();
            PcsPollReportExample.Criteria criteria = example.createCriteria().andUserIdIn(Arrays.asList(userIds))
                    .andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId).andTypeEqualTo(type);
            if (branchId != null){
                criteria.andBranchIdEqualTo(branchId);
            }

            pcsPollReportMapper.deleteByExample(example);
        }
    }
}
