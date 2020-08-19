package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.ResultBean;
import sys.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PcsPollCandidateService extends PcsBaseMapper {

    @Autowired
    private PcsPollReportService pcsPollReportService;
    @Autowired
    private PcsPartyService pcsPartyService;
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

        PcsParty pcsParty = pcsPartyService.get(configId, partyId);

        if (branchId == null && BooleanUtils.isNotTrue(pcsParty.getIsDirectBranch())) {
            throw new OpException("请选择所属党支部");
        }else {
            criteria.andBranchIdEqualTo(branchId);
        }

        List<PcsPoll> pcsPollList = pcsPollMapper.selectByExample(example);
        if (pcsPollList.size() == 0){
            throw new OpException("党代会投票不存在");
        }

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

        PcsPollExample example = new PcsPollExample();
        PcsPollExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId)
                .andStageEqualTo(stage).andIsDeletedEqualTo(false).andHasReportEqualTo(true);

        List<PcsPoll> pcsPollList = pcsPollMapper.selectByExample(example);
        if (pcsPollList.size() == 0){
            throw new OpException("没有报送的党代会投票结果");
        }

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
