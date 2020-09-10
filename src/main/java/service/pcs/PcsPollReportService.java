package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
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

    public List<PcsPollReport> getReport(PcsPoll pcsPoll, Byte type) {

        Integer partyId = pcsPoll.getPartyId();
        Integer branchId = pcsPoll.getBranchId();
        Byte stage = pcsPoll.getStage();
        PcsPollReportExample example = new PcsPollReportExample();
        PcsPollReportExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId)
                .andStageEqualTo(stage).andPollIdEqualTo(pcsPoll.getId());
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        List<PcsPollReport> reportList = pcsPollReportMapper.selectByExample(example);

        return reportList;
    }

    public List<PcsPollReport> getReport(byte type, int configId, byte stage, int partyId, Integer branchId) {

        PcsPollExample example1 = new PcsPollExample();
        PcsPollExample.Criteria criteria = example1.createCriteria().andStageEqualTo(stage).andConfigIdEqualTo(configId)
                .andPartyIdEqualTo(partyId);
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        List<PcsPoll> pcsPollList = pcsPollMapper.selectByExample(example1);

        List<PcsPollReport> pcsPollReportList = new ArrayList<>();

        if (pcsPollList.size() > 0) {
            PcsPollReportExample example = new PcsPollReportExample();
            example.setOrderByClause("positive_ballot desc");
            example.createCriteria().andTypeEqualTo(type).andPollIdEqualTo(pcsPollList.get(0).getId());
            pcsPollReportList = pcsPollReportMapper.selectByExample(example);
        }

        return pcsPollReportList;
    }

    @Transactional
    public void batchInsertOrUpdate(Integer[] userIds,
                                    Boolean isCandidate,
                                    int pollId, byte type) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);
        int configId = pcsPoll.getConfigId();
        int partyId = pcsPoll.getPartyId();
        Integer branchId = pcsPoll.getBranchId();

        byte stage = pcsPoll.getStage();

        if (isCandidate) {//设置为候选人

            //计算候选人是否超过指定数值
            PcsPollReportExample reportExample = new PcsPollReportExample();
            reportExample.createCriteria().andPollIdEqualTo(pollId).andTypeEqualTo(type);
            List<PcsPollReport> reportList = pcsPollReportMapper.selectByExample(reportExample);

            Set<Integer> selectedUserIdSet = new HashSet<>(); // 已选推荐人
            for (PcsPollReport _pcsPollReport : reportList) {
                selectedUserIdSet.add(_pcsPollReport.getUserId());
            }
            //使用Set集合判断是否超额推荐
            Set<Integer> _selectedUserIdSet = new HashSet<>();
            _selectedUserIdSet.addAll(selectedUserIdSet);
            _selectedUserIdSet.addAll(Arrays.asList(userIds));

            int requiredCount = 0;
            if (type == PcsConstants.PCS_USER_TYPE_PR) {
                requiredCount = pcsPrAlocateService.getPrMaxCount(configId, partyId);
            } else if (type == PcsConstants.PCS_USER_TYPE_DW) {
                requiredCount = CmTag.getIntProperty("pcs_poll_dw_num");
            } else if (type == PcsConstants.PCS_USER_TYPE_JW) {
                requiredCount = CmTag.getIntProperty("pcs_poll_jw_num");
            }
            if (_selectedUserIdSet.size() > requiredCount) {

                throw new OpException("设置失败，超过{0}的最大推荐数量({1})",
                        PcsConstants.PCS_USER_TYPE_MAP.get(type), requiredCount);
            }

            for (int userId : userIds) {

                SysUserView uv = CmTag.getUserById(userId);

                PcsPollReport record = new PcsPollReport();
                record.setPollId(pollId);
                record.setUserId(userId);
                record.setCode(uv.getCode());
                record.setRealname(uv.getRealname());
                record.setUnit(uv.getUnit());
                record.setConfigId(configId);
                record.setPartyId(partyId);
                if (branchId != null) {
                    record.setBranchId(branchId);
                }
                record.setStage(stage);
                record.setType(type);

                // 读取投票结果
                getPcsPollReportOfVoteResult(record, pollId, stage, type, userId);

                if (selectedUserIdSet.contains(userId)) {
                    PcsPollReportExample example = new PcsPollReportExample();
                    example.createCriteria().andPollIdEqualTo(pollId)
                                    .andUserIdEqualTo(userId).andTypeEqualTo(type);

                    pcsPollReportMapper.updateByExampleSelective(record, example);
                } else {
                    pcsPollReportMapper.insertSelective(record);
                }
            }

        } else {
            PcsPollReportExample example = new PcsPollReportExample();
            example.createCriteria()
                    .andPollIdEqualTo(pollId).andTypeEqualTo(type)
                    .andUserIdIn(Arrays.asList(userIds));

            pcsPollReportMapper.deleteByExample(example);
        }
    }

    // 读取每个候选人的得票情况，存入对象PcsPollReport
    public void getPcsPollReportOfVoteResult(PcsPollReport record, int pollId, byte stage, byte type, int userId){

        List<PcsFinalResult> pcsFinalResultList = new ArrayList<>();
        if (stage == PcsConstants.PCS_POLL_FIRST_STAGE) {
            pcsFinalResultList = iPcsMapper.selectResultList(pollId, type, userId, new RowBounds());
        } else {
            pcsFinalResultList = iPcsMapper.selectSecondResultList(pollId, type, userId, new RowBounds());
        }
        if(pcsFinalResultList==null || pcsFinalResultList.size()==0){
            record.setBallot(0);
            record.setPositiveBallot(0);
            record.setGrowBallot(0);
            record.setDisagreeBallot(0);
            record.setAbstainBallot(0);
        }else {
            PcsFinalResult finalResult = pcsFinalResultList.get(0);
            record.setBallot(finalResult.getSupportNum());
            record.setPositiveBallot(finalResult.getPositiveBallot());
            record.setGrowBallot(finalResult.getGrowBallot());
            record.setDisagreeBallot(finalResult.getNotSupportNum() == null ? 0 : finalResult.getNotSupportNum());
            record.setAbstainBallot(finalResult.getNotVoteNum() == null ? 0 : finalResult.getNotVoteNum());
        }
    }

    public int bacthImport(List<PcsPollReport> records, Integer pollId, Byte type) {

        int addCount = 0;
        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);

        PcsPollReportExample example = new PcsPollReportExample();
        example.createCriteria().andPollIdEqualTo(pollId).andTypeEqualTo(type);
        pcsPollReportMapper.deleteByExample(example);
        for (PcsPollReport record : records) {

            record.setConfigId(pcsPoll.getConfigId());
            record.setPartyId(pcsPoll.getPartyId());
            record.setBranchId(pcsPoll.getBranchId());
            record.setStage(pcsPoll.getStage());

            pcsPollReportMapper.insertSelective(record);
            addCount++;
        }

        return addCount;
    }
}
