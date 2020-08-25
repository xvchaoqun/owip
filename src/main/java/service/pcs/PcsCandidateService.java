package service.pcs;

import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.member.MemberView;
import domain.pcs.*;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.IPcsCandidate;
import persistence.pcs.common.PcsBranchBean;
import service.cadre.CadreService;
import sys.constants.PcsConstants;
import sys.tags.CmTag;

import java.util.*;

import static sys.constants.PcsConstants.PCS_USER_TYPE_DW;
import static sys.constants.PcsConstants.PCS_USER_TYPE_JW;

@Service
public class PcsCandidateService extends PcsBaseMapper {

    @Autowired
    private CadreService cadreService;
    @Autowired
    private PcsPollService pcsPollService;
    @Autowired
    private PcsRecommendService pcsRecommendService;

    public static final String TABLE_NAME = "pcs_candidate";

    // 读取党支部下的党委委员、纪委委员
    public List<PcsCandidate> find(int partyId, Integer branchId, int configId, byte stage, byte type){

        PcsCandidateExample example = new PcsCandidateExample();
        PcsCandidateExample.Criteria criteria =
                example.createCriteria().andPartyIdEqualTo(partyId)
                        .andConfigIdEqualTo(configId).andStageEqualTo(stage)
                        .andTypeEqualTo(type);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);
        example.setOrderByClause("sort_order asc");
        return pcsCandidateMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int recommendId, int userId, byte type){

        PcsCandidateExample example = new PcsCandidateExample();
        PcsCandidateExample.Criteria criteria = example.createCriteria()
                .andRecommendIdEqualTo(recommendId).andUserIdEqualTo(userId).andTypeEqualTo(type);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pcsCandidateMapper.countByExample(example) > 0;
    }

    // 清空一个党支部推荐的党委或纪委委员
    @Transactional
    public void clear(int recommendId, byte type){

        PcsCandidateExample example = new PcsCandidateExample();
        example.createCriteria().andRecommendIdEqualTo(recommendId).andTypeEqualTo(type);
        pcsCandidateMapper.deleteByExample(example);
    }

    @Transactional
    public void insertSelective(PcsCandidate record){

        // 填充个人基本信息
        int recommendId = record.getRecommendId();
        int userId = record.getUserId();
        PcsRecommend pr = pcsRecommendMapper.selectByPrimaryKey(recommendId);

        Cadre cadre = CmTag.getCadre(userId);
        if(cadre!=null) {
            record.setTitle(cadre.getTitle());
            record.setCadreSortOrder(cadre.getSortOrder());
            record.setCadreStatus(cadre.getStatus());
        }

        SysUserView uv = CmTag.getUserById(userId);
        record.setCode(uv.getCode());
        record.setRealname(uv.getRealname());
        record.setExtUnit(uv.getUnit());
        record.setGender(uv.getGender());
        record.setNation(uv.getNation());
        record.setNativePlace(uv.getNativePlace());
        record.setBirth(uv.getBirth());

        TeacherInfo ti = teacherInfoMapper.selectByPrimaryKey(userId);
        if(ti!=null) {
            record.setWorkTime(ti.getWorkTime());
            record.setProPost(ti.getProPost());
        }
        MemberView mv = iMemberMapper.getMemberView(userId);
        if(mv!=null) {
            record.setGrowTime(mv.getGrowTime());
        }
        record.setPartyId(pr.getPartyId());
        record.setBranchId(pr.getBranchId());
        record.setConfigId(pr.getConfigId());
        record.setStage(pr.getStage());

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "recommend_id=" + record.getRecommendId()
                + " and type=" + record.getType()));
        pcsCandidateMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsCandidateExample example = new PcsCandidateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsCandidateMapper.deleteByExample(example);
    }

    // 升序排列
    @Transactional
    public void changeOrder(int id, int addNum) {

        PcsCandidate entity = pcsCandidateMapper.selectByPrimaryKey(id);
        changeOrder(TABLE_NAME, "recommend_id=" + entity.getRecommendId()
                + " and type=" + entity.getType(), ORDER_BY_ASC, id, addNum);
    }

    // 读取党代表的基本信息
    @Transactional
    public PcsCandidate getCandidateInfo(Integer userId){

            MemberView memberView = iMemberMapper.getMemberView(userId);
            CadreView cv = cadreService.dbFindByUserId(userId);
            SysUserView uv = cv.getUser();
            PcsCandidate candidate = new PcsCandidate();
            candidate.setUserId(memberView.getUserId());
            candidate.setCode(memberView.getCode());
            candidate.setRealname(memberView.getRealname());
            candidate.setTitle(cv==null?null:cv.getTitle());
            candidate.setExtUnit(uv.getUnit());
            candidate.setGender(memberView.getGender());
            candidate.setNation(memberView.getNation());
            candidate.setBirth(memberView.getBirth());
            candidate.setGrowTime(memberView.getGrowTime());
            candidate.setWorkTime(memberView.getWorkTime());
            candidate.setProPost(memberView.getProPost());

            return candidate;
    }

    // 从pcsPoll同步某分党委所有党支部两委委员名单
    public int sync(List<PcsBranch> pcsBranchs,int configId, byte stage,int partyId) {

        int syncCount=0;
        for(PcsBranch pcsBranch:pcsBranchs) {

            Integer branchId = pcsBranch.getBranchId();

            PcsPoll pcsPoll = pcsPollService.get(configId, stage, partyId, branchId);
            List<PcsPollReport> pcsPollReportDws = pcsPollService.getCandidates(configId, stage, PCS_USER_TYPE_DW, partyId, branchId);
            List<PcsPollReport> pcsPollReportJws = pcsPollService.getCandidates(configId, stage, PCS_USER_TYPE_JW, partyId, branchId);
            if (pcsPoll != null && BooleanUtils.isTrue(pcsPoll.getHasReport())
                    && pcsPollReportDws.size() > 0 && pcsPollReportJws.size() > 0) {
                syncCount++;
                PcsRecommend record = new PcsRecommend();
                record.setPartyId(partyId);
                record.setBranchId(branchId);
                record.setConfigId(configId);
                record.setExpectMemberCount(pcsPoll.getExpectMemberCount());
                record.setActualMemberCount(pcsPoll.getActualMemberCount());
                record.setIsFinished(true);
                record.setStage(stage);

                PcsBranchBean pcsBranchBean = pcsRecommendService.get(partyId, branchId, configId, stage);
                if (pcsBranchBean.getRecommendId() == null) {
                    pcsRecommendMapper.insertSelective(record);
                } else {
                    record.setId(pcsBranchBean.getRecommendId());
                    pcsRecommendMapper.updateByPrimaryKeySelective(record);
                }

                int recommendId = record.getId();

                // 上一阶段已下发名单
                Set<Integer> dwIssueUserIdSet = new HashSet<>();
                Set<Integer> jwIssueUserIdSet = new HashSet<>();
                if (stage == PcsConstants.PCS_STAGE_SECOND || stage == PcsConstants.PCS_STAGE_THIRD) {

                    byte _stage = (stage == PcsConstants.PCS_STAGE_SECOND) ?
                            PcsConstants.PCS_STAGE_FIRST : PcsConstants.PCS_STAGE_SECOND;
                    List<IPcsCandidate> dwCandidates =
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
                }
                Date now = new Date();

                // 先清空两委委员
                clear(recommendId, PcsConstants.PCS_USER_TYPE_DW);
                clear(recommendId, PcsConstants.PCS_USER_TYPE_JW);

                for (PcsPollReport pcsPollReportDw : pcsPollReportDws) {

                    int userId = pcsPollReportDw.getUserId();
                    PcsCandidate _pcsCandidate = new PcsCandidate();
                    _pcsCandidate.setRecommendId(recommendId);
                    _pcsCandidate.setUserId(userId);
                    _pcsCandidate.setType(PCS_USER_TYPE_DW);
                    // 推荐提名正式党员的票数
                    _pcsCandidate.setVote(pcsPollReportDw.getPositiveBallot());
                    _pcsCandidate.setAddTime(now);

                    if (_pcsCandidate.getType() == PcsConstants.PCS_USER_TYPE_DW) {  // 添加党委委员
                        _pcsCandidate.setIsFromStage(dwIssueUserIdSet.contains(userId));
                    }

                    insertSelective(_pcsCandidate);
                }

                for (PcsPollReport pcsPollReportJw : pcsPollReportJws) {

                    int userId = pcsPollReportJw.getUserId();
                    PcsCandidate _pcsCandidate = new PcsCandidate();
                    _pcsCandidate.setRecommendId(recommendId);
                    _pcsCandidate.setUserId(userId);
                    _pcsCandidate.setType(PCS_USER_TYPE_JW);
                    // 推荐提名正式党员的票数
                    _pcsCandidate.setVote(pcsPollReportJw.getPositiveBallot());
                    _pcsCandidate.setAddTime(now);

                    if (_pcsCandidate.getType() == PcsConstants.PCS_USER_TYPE_JW) {  // 添加党委委员
                        _pcsCandidate.setIsFromStage(jwIssueUserIdSet.contains(userId));
                    }

                    insertSelective(_pcsCandidate);
                }
            }
        }
        return syncCount;
    }
}
