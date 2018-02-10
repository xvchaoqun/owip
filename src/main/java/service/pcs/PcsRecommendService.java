package service.pcs;

import controller.global.OpException;
import domain.pcs.PcsCandidate;
import domain.pcs.PcsConfig;
import domain.pcs.PcsRecommend;
import domain.pcs.PcsRecommendExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.common.bean.IPcsCandidateView;
import persistence.common.bean.PcsBranchBean;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.PcsConstants;
import sys.constants.RoleConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PcsRecommendService extends BaseMapper {

    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PcsCandidateService pcsCandidateService;
    @Autowired
    private PcsPartyService pcsPartyService;

    // 获取一个已经推荐的票
    public PcsBranchBean get(int partyId, Integer branchId, int configId, byte stage) {

        List<PcsBranchBean> pcsRecommends = iPcsMapper.selectPcsBranchBeans(configId, stage,
                partyId, branchId, null, new RowBounds());

        return (pcsRecommends.size()>0)?pcsRecommends.get(0):null;
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PcsRecommendExample example = new PcsRecommendExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsRecommendMapper.deleteByExample(example);
    }

    @Transactional
    public void submit(byte stage, int partyId,
                       Integer branchId,
                       int expectMemberCount,
                       int actualMemberCount,
                       Boolean isFinish,
                       String[] dwCandidateIds,
                       String[] jwCandidateIds) {

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = pcsConfig.getId();

        // 只有干部管理员可以直接修改
        if(ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)) {
            if (!pcsPartyService.allowModify(partyId, configId, stage)) {
                throw new OpException("已报送数据或已下发名单，不可修改。");
            }
        }

        PcsRecommend record = new PcsRecommend();
        record.setPartyId(partyId);
        record.setBranchId(branchId);
        record.setConfigId(configId);
        record.setExpectMemberCount(expectMemberCount);
        record.setActualMemberCount(actualMemberCount);
        record.setIsFinished(isFinish);
        record.setStage(stage);

        PcsBranchBean pcsBranchBean = get(partyId, branchId, configId, stage);
        if(pcsBranchBean.getId()==null){
            pcsRecommendMapper.insertSelective(record);
        }else{
            if(BooleanUtils.isTrue(pcsBranchBean.getIsFinished()))
                record.setIsFinished(null);
            record.setId(pcsBranchBean.getId());
            pcsRecommendMapper.updateByPrimaryKeySelective(record);
        }

        int recommendId = record.getId();

        // 上一阶段已下发名单
        Set<Integer> dwIssueUserIdSet = new HashSet<>();
        Set<Integer> jwIssueUserIdSet = new HashSet<>();
        if(stage == PcsConstants.PCS_STAGE_SECOND || stage == PcsConstants.PCS_STAGE_THIRD){

            byte _stage = (stage == PcsConstants.PCS_STAGE_SECOND)?
                    PcsConstants.PCS_STAGE_FIRST: PcsConstants.PCS_STAGE_SECOND;
            List<IPcsCandidateView> dwCandidates =
                    iPcsMapper.selectPartyCandidates(null, true, configId,
                            _stage, PcsConstants.PCS_USER_TYPE_DW, new RowBounds());
            for (IPcsCandidateView dwCandidate : dwCandidates) {
                dwIssueUserIdSet.add(dwCandidate.getUserId());
            }
            List<IPcsCandidateView> jwCandidates =
                    iPcsMapper.selectPartyCandidates(null, true, configId,
                            _stage, PcsConstants.PCS_USER_TYPE_JW, new RowBounds());
            for (IPcsCandidateView jwCandidate : jwCandidates) {
                jwIssueUserIdSet.add(jwCandidate.getUserId());
            }
        }
        Date now = new Date();
        // 添加党委委员
        pcsCandidateService.clear(recommendId, PcsConstants.PCS_USER_TYPE_DW);
        if(dwCandidateIds!=null){
            for (String dwCandidateId : dwCandidateIds) {

                String[] idStrs = dwCandidateId.split("-");
                int userId = Integer.valueOf(idStrs[0]);
                boolean isFromStage = false;
                if(idStrs.length==2){
                    isFromStage = StringUtils.equals("1", idStrs[1]);
                }

                PcsCandidate _pcsCandidate = new PcsCandidate();
                _pcsCandidate.setRecommendId(recommendId);
                _pcsCandidate.setUserId(userId);
                _pcsCandidate.setType(PcsConstants.PCS_USER_TYPE_DW);
                _pcsCandidate.setIsFromStage(isFromStage);
                _pcsCandidate.setAddTime(now);
                _pcsCandidate.setIsFromStage(dwIssueUserIdSet.contains(userId));

                pcsCandidateService.insertSelective(_pcsCandidate);
            }
        }

        // 添加纪委委员
        pcsCandidateService.clear(recommendId, PcsConstants.PCS_USER_TYPE_JW);
        if(jwCandidateIds!=null){
            for (String jwCandidateId : jwCandidateIds) {

                String[] idStrs = jwCandidateId.split("-");
                int userId = Integer.valueOf(idStrs[0]);
                boolean isFromStage = false;
                if(idStrs.length==2){
                    isFromStage = StringUtils.equals("1", idStrs[1]);
                }

                PcsCandidate _pcsCandidate = new PcsCandidate();
                _pcsCandidate.setRecommendId(recommendId);
                _pcsCandidate.setUserId(userId);
                _pcsCandidate.setType(PcsConstants.PCS_USER_TYPE_JW);
                _pcsCandidate.setIsFromStage(isFromStage);
                _pcsCandidate.setAddTime(now);
                _pcsCandidate.setIsFromStage(jwIssueUserIdSet.contains(userId));

                pcsCandidateService.insertSelective(_pcsCandidate);
            }
        }

    }
}
