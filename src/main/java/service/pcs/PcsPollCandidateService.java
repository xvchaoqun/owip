package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.PcsFinalResult;
import persistence.pcs.common.ResultBean;
import service.party.PartyService;
import sys.constants.PcsConstants;
import sys.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PcsPollCandidateService extends PcsBaseMapper {

    @Autowired
    private PartyService partyService;
    @Autowired
    private PcsPrAlocateService pcsPrAlocateService;
    @Autowired
    private PcsConfigService pcsConfigService;

    @Transactional
    public void insertSelective(PcsPollCandidate record){
        record.setSortOrder(getNextSortOrder("pcs_poll_candidate", null));
        pcsPollCandidateMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsPollCandidateExample example = new PcsPollCandidateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollCandidateMapper.deleteByExample(example);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        PcsPollCandidate candidate = pcsPollCandidateMapper.selectByPrimaryKey(id);
        changeOrder("pcs_poll_candidate", "poll_id=" + candidate.getPollId(), ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public int bacthImport(List<PcsPollCandidate> records, Integer pollId, Byte type) {
        int addCount = 0;

        PcsPollCandidateExample example = new PcsPollCandidateExample();
        example.createCriteria().andPollIdEqualTo(pollId).andTypeEqualTo(type);
        pcsPollCandidateMapper.deleteByExample(example);
        for (PcsPollCandidate record : records) {

            insertSelective(record);
            addCount++;
        }

        return addCount;
    }

    //List<PcsPollCandidate> -- 二下/三下推荐人
    public List<PcsPollCandidate> findAll(Integer pollId, Byte type){

        PcsPollCandidateExample example = new PcsPollCandidateExample();
        example.createCriteria().andPollIdEqualTo(pollId).andTypeEqualTo(type);

        example.setOrderByClause("sort_order desc");
        List<PcsPollCandidate> pcsPollCandidates = pcsPollCandidateMapper.selectByExample(example);

        return pcsPollCandidates;
    }

    //读取分党委的代表最大推荐数量
    public int getPrMaxCount(int partyId) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        PcsPrAllocate pcsPrAllocate = pcsPrAlocateService.get(configId, partyId);

        return NumberUtils.trimToZero(pcsPrAllocate.getCandidateCount());
    }

    /*
    * @return 结果中得票数为提名正式党员数
    * */
    // 获得支部的推荐汇总结果
    public List<ResultBean> getCandidates(int configId, int partyId, Integer branchId, byte type, byte stage){

        PcsPollExample example = new PcsPollExample();
        PcsPollExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId)
                .andStageEqualTo(stage).andIsDeletedEqualTo(false).andHasReportEqualTo(true);
        if (branchId == null){
            if (!partyService.isDirectBranch(partyId)) {
                throw new OpException("请选择所属党支部");
            }
        }else {
            criteria.andBranchIdEqualTo(branchId);
        }

        List<PcsPoll> pcsPollList = pcsPollMapper.selectByExample(example);
        if (pcsPollList.size() == 0){
            throw new OpException("党代会投票不存在");
        }
        List<Integer> pollIdList = new ArrayList<>();
        pollIdList.add(pcsPollList.get(0).getId());
        List<PcsFinalResult> pcsFinalResults = new ArrayList<>();
        if (stage != PcsConstants.PCS_POLL_FIRST_STAGE) {
            pcsFinalResults = iPcsMapper.selectSecondResultList(type, pollIdList, stage, null, null, partyId, branchId, null, null,
                    new RowBounds());
        }else {
            pcsFinalResults = iPcsMapper.selectResultList(type, pollIdList, stage, null, null, partyId, branchId, null, null,
                    new RowBounds());
        }

        List<ResultBean> resultBeans = new ArrayList<>();
        if (pcsFinalResults.size() > 0){
            for (PcsFinalResult pcsFinalResult : pcsFinalResults) {
                ResultBean bean = new ResultBean();
                bean.setUserId(pcsFinalResult.getUserId());
                bean.setBallot(pcsFinalResult.getPositiveBallot());

                resultBeans.add(bean);
            }
        }

        return resultBeans;
    }

    // 获得分党委的推荐汇总结果
    public List<ResultBean> getCandidates(int configId, int partyId, byte type, byte stage){

        PcsPollExample example = new PcsPollExample();
        PcsPollExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId)
                .andStageEqualTo(stage).andIsDeletedEqualTo(false).andHasReportEqualTo(true);

        List<PcsPoll> pcsPollList = pcsPollMapper.selectByExample(example);
        if (pcsPollList.size() == 0){
            throw new OpException("没有报送的党代会投票结果");
        }
        List<Integer> pollIdList = new ArrayList<>();
        pollIdList.add(pcsPollList.get(0).getId());
        List<PcsFinalResult> pcsFinalResults = new ArrayList<>();
        if (stage != PcsConstants.PCS_POLL_FIRST_STAGE) {
            pcsFinalResults = iPcsMapper.selectSecondResultList(type, pollIdList, stage, null, null, partyId, null, null, null,
                    new RowBounds());
        }else {
            pcsFinalResults = iPcsMapper.selectResultList(type, pollIdList, stage, null, null, partyId, null, null, null,
                    new RowBounds());
        }

        List<ResultBean> resultBeans = new ArrayList<>();
        if (pcsFinalResults.size() > 0){
            for (PcsFinalResult pcsFinalResult : pcsFinalResults) {
                ResultBean bean = new ResultBean();
                bean.setUserId(pcsFinalResult.getUserId());
                bean.setBranchNum(pcsFinalResult.getBranchNum());
                bean.setBallot(pcsFinalResult.getPositiveBallot());

                resultBeans.add(bean);
            }
        }

        return resultBeans;
    }

}
