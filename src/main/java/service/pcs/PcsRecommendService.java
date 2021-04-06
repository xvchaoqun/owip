package service.pcs;

import controller.global.OpException;
import controller.pcs.cm.PcsCandidateFormBean;
import domain.member.Member;
import domain.pcs.PcsCandidate;
import domain.pcs.PcsConfig;
import domain.pcs.PcsRecommend;
import domain.pcs.PcsRecommendExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.PcsBranchBean;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.PcsConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class PcsRecommendService extends PcsBaseMapper {

    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PcsPartyService pcsPartyService;
    @Autowired
    private PcsCandidateService pcsCandidateService;
    @Autowired
    private PcsOwService pcsOwService;

    // 获取一个已经推荐的票
    public PcsBranchBean get(int partyId, Integer branchId, int configId, byte stage) {

        List<PcsBranchBean> pcsRecommends = iPcsMapper.selectPcsBranchBeanList(configId, stage,
                partyId, branchId, null,null, new RowBounds());

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
                       Integer expectMemberCount,
                       Integer actualMemberCount,
                       Boolean isFinish, List<PcsCandidateFormBean> formBeans) {

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = pcsConfig.getId();

        // 只有干部管理员可以直接修改
        // for test
        if(!ShiroHelper.isPermitted(RoleConstants.PERMISSION_CADREADMIN)) {
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
        if(pcsBranchBean.getRecommendId()==null){
            pcsRecommendMapper.insertSelective(record);
        }else{
            if(BooleanUtils.isTrue(pcsBranchBean.getIsFinished()))
                record.setIsFinished(null);
            record.setId(pcsBranchBean.getRecommendId());
            pcsRecommendMapper.updateByPrimaryKeySelective(record);
        }

        int recommendId = record.getId();

        // 上一阶段已下发名单
        Set<Integer> dwIssueUserIdSet = new HashSet<>();
        Set<Integer> jwIssueUserIdSet = new HashSet<>();
        if(stage == PcsConstants.PCS_STAGE_SECOND || stage == PcsConstants.PCS_STAGE_THIRD){

            byte _stage = (stage == PcsConstants.PCS_STAGE_SECOND)?
                    PcsConstants.PCS_STAGE_FIRST: PcsConstants.PCS_STAGE_SECOND;
            /*List<IPcsCandidate> dwCandidates =
                    iPcsMapper.selectPartyCandidateList(null, true, configId,
                            _stage, PcsConstants.PCS_USER_TYPE_DW, new RowBounds());
            for (IPcsCandidate dwCandidate : dwCandidates) {
                dwIssueUserIdSet.add(dwCandidate.getUserId());
            }
            List<IPcsCandidate> jwCandidates =
                    iPcsMapper.selectPartyCandidateList(null, true, configId,
                            _stage, PcsConstants.PCS_USER_TYPE_JW, new RowBounds());
            for (IPcsCandidate jwCandidate : jwCandidates) {
                jwIssueUserIdSet.add(jwCandidate.getUserId());
            }
            */
            List<Integer> dwUserIds = pcsOwService.getCandidateUserIds(configId, _stage, PcsConstants.PCS_USER_TYPE_DW);
            dwIssueUserIdSet.addAll(dwUserIds);
            List<Integer> jwUserIds = pcsOwService.getCandidateUserIds(configId, _stage, PcsConstants.PCS_USER_TYPE_JW);
            jwIssueUserIdSet.addAll(jwUserIds);
        }
        Date now = new Date();

        // 先清空两委委员
        pcsCandidateService.clear(recommendId, PcsConstants.PCS_USER_TYPE_DW);
        pcsCandidateService.clear(recommendId, PcsConstants.PCS_USER_TYPE_JW);

        for (PcsCandidateFormBean formBean : formBeans) {

            int userId = formBean.getUserId();
            if(BooleanUtils.isTrue(isFinish)){ // 提交时校验是否是正式党员

                Member member = memberMapper.selectByPrimaryKey(userId);
                if(member==null || member.getPoliticalStatus() != MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE){

                    SysUserView uv = CmTag.getUserById(userId);
                    throw new OpException("{0}（工号：{1}）不是系统党员库中的正式党员", uv.getRealname(), uv.getCode());
                }
            }

            byte type = formBean.getType();
            PcsCandidate _pcsCandidate = new PcsCandidate();
            _pcsCandidate.setRecommendId(recommendId);
            _pcsCandidate.setUserId(userId);
            _pcsCandidate.setType(type);
            _pcsCandidate.setVote(formBean.getVote());
            _pcsCandidate.setPositiveVote(formBean.getPositiveVote());
            _pcsCandidate.setAddTime(now);

            if(_pcsCandidate.getPositiveVote()!=null && _pcsCandidate.getVote()!=null &&
                    _pcsCandidate.getPositiveVote().intValue()>_pcsCandidate.getVote()){

                SysUserView uv = CmTag.getUserById(userId);
                throw new OpException("数据有误，委员（{0}）推荐提名的正式党员数大于推荐提名的党员数", uv.getRealname());
            }

            if(formBean.getType()==PcsConstants.PCS_USER_TYPE_DW){  // 添加党委委员
                _pcsCandidate.setIsFromStage(dwIssueUserIdSet.contains(userId));
            }else if(formBean.getType()==PcsConstants.PCS_USER_TYPE_JW){ // 添加纪委委员
                _pcsCandidate.setIsFromStage(jwIssueUserIdSet.contains(userId));
            }

            pcsCandidateService.insertSelective(_pcsCandidate);
        }
    }
}
