package persistence.pcs.common;

import domain.pcs.PcsAdmin;
import domain.pcs.PcsPrAllocate;
import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsVoteCandidate;
import domain.pcs.PcsVoteGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import persistence.common.bean.IPcsCandidateView;
import persistence.common.bean.PcsBranchBean;
import persistence.common.bean.PcsPartyBean;
import persistence.common.bean.PcsPrAllocateBean;
import persistence.common.bean.PcsPrPartyBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface IPcsMapper {

    // 两委选举 小组统计汇总
    public List<PcsVoteCandidate> selectVoteCandidateStatList(@Param("type") byte type,
                                                              @Param("userId") Integer userId,
                                                              @Param("isFromStage") Boolean isFromStage);

    @ResultMap("persistence.pcs.PcsVoteGroupMapper.BaseResultMap")
    @Select("select sum(vote) as vote, sum(valid) as valid, sum(invalid) as invalid " +
            "from pcs_vote_group where has_report=1 and type=#{type}")
    public PcsVoteGroup statPcsVoteGroup(@Param("type") byte type);

    // 根据账号、姓名、学工号查找党代表
    List<PcsPrCandidateView> selectPrList( @Param("configId") int configId,
                                           @Param("stage") byte stage,
                                           @Param("search") String search, RowBounds rowBounds);
    int countPr(@Param("configId") int configId,
                @Param("stage") byte stage, @Param("search") String search);

    // 还未报送党代表数据的分党委管理员
    @ResultMap("persistence.pcs.PcsAdminMapper.BaseResultMap")
    @Select("select * from pcs_admin where type = #{adminType} and party_id not in(select party_id from " +
            "pcs_pr_recommend where config_id  = #{configId} and stage = #{stage} and has_report=1)")
    public List<PcsAdmin> hasNotReportPcsPrAdmins(@Param("configId") int configId,
                                                  @Param("stage") byte stage,
                                                  @Param("adminType") byte adminType);

    //  （党代表）分党委推荐汇总情况，configId和stage非搜索字段，仅用于创建视图数据
    public List<PcsPrPartyBean> selectPcsPrPartyBeans(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("hasReport") Boolean hasReport,
            @Param("recommendStatus") Byte recommendStatus,
            RowBounds rowBounds);

    public int countPcsPrPartyBeans(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("hasReport") Boolean hasReport,
            @Param("recommendStatus") Byte recommendStatus);

    // 全校 应选代表情况
    public PcsPrAllocate schoolPcsPrAllocate(@Param("configId") int configId);

    // 候选人初步人选数据统计
    public PcsPrAllocate statRealPcsPrAllocate(@Param("configId") int configId,
                                               @Param("stage") byte stage,
                                               @Param("partyId") Integer partyId,
                                               @Param("isChosen") Boolean isChosen);

    // 党代表分配方案，configId是非搜索字段，仅用于创建视图数据
    public List<PcsPrAllocateBean> selectPcsPrAllocateBeans(
            @Param("configId") int configId,
            @Param("partyId") Integer partyId,
            RowBounds rowBounds);

    public int countPcsPrAllocateBeans(@Param("configId") int configId,
                                       @Param("partyId") Integer partyId);
/*
    // 党代表候选人初步人选名单（分党委）
    public List<IPcsCandidateView> selectPcsPrPartyCandidates(@Param("userId") Integer userId,
                                                          @Param("configId") int configId,
                                                          @Param("stage") byte stage,
                                                          @Param("partyId") int partyId,
                                                          RowBounds rowBounds);

    public int countPcsPrPartyCandidates(@Param("userId") Integer userId,
                                     @Param("configId") int configId,
                                     @Param("stage") byte stage,
                                     @Param("partyId") int partyId);*/


    // 还未报送两委委员数据的分党委管理员
    @ResultMap("persistence.pcs.PcsAdminMapper.BaseResultMap")
    @Select("select * from pcs_admin where type = #{adminType} and party_id not in(select party_id from " +
            "pcs_admin_report where config_id  = #{configId} and stage = #{stage})")
    public List<PcsAdmin> hasNotReportPcsAdmins(@Param("configId") int configId,
                                                @Param("stage") byte stage, @Param("adminType") byte adminType);

    // 全校 应参会党员总数/实参会党员总数
    @ResultType(java.util.HashMap.class)
    @Select("select sum(pr.expect_member_count) as expect, sum(pr.actual_member_count) as actual " +
            "from pcs_recommend pr where config_id  = #{configId} and stage = #{stage} " +
            "and pr.party_id in(select party_id from pcs_admin_report where config_id  = #{configId} and stage = #{stage})")
    public Map<String, BigDecimal> schoolMemberCount(@Param("configId") int configId,
                                                     @Param("stage") byte stage);

    //  分党委推荐汇总情况，configId和stage非搜索字段，仅用于创建视图数据
    public List<PcsPartyBean> selectPcsPartyBeans(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("hasReport") Boolean hasReport,
            RowBounds rowBounds);

    public int countPcsPartyBeans(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("hasReport") Boolean hasReport);


    // 党支部推荐汇总情况，configId和stage非搜索字段，仅用于创建视图数据
    public List<PcsBranchBean> selectPcsBranchBeans(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("branchId") Integer branchId,
            @Param("isFinished") Boolean isFinished,
            RowBounds rowBounds);

    public int countPcsBranchBeans(
                @Param("configId") int configId,
                @Param("stage") byte stage,
                @Param("partyId") Integer partyId,
                                   @Param("branchId") Integer branchId,
                                   @Param("isFinished") Boolean isFinished);

    // 获取被推荐人都有哪些支部推荐了（除直属党支部）
    List<Integer> selectCandidateBranchIds(@Param("userId") int userId,
                             @Param("configId") int configId,
                             @Param("stage") byte stage,
                             @Param("candidateType") int candidateType);
    // 分党委两委委员推荐提名情况
    public List<IPcsCandidateView> selectPartyCandidates(@Param("userId") Integer userId,
                                                         @Param("isChosen") Boolean isChosen,
                                                         @Param("configId") int configId,
                                                         @Param("stage") byte stage,
                                                         @Param("candidateType") int candidateType,
                                                         RowBounds rowBounds);

    public int countPartyCandidates(@Param("userId") Integer userId,
                                    @Param("isChosen") Boolean isChosen,
                                    @Param("configId") int configId,
                                    @Param("stage") byte stage,
                                    @Param("candidateType") int candidateType);

    // 党支部两委委员推荐提名情况
    public List<IPcsCandidateView> selectBranchCandidates(@Param("userId") Integer userId,
                                                          @Param("configId") int configId,
                                                          @Param("stage") byte stage,
                                                          @Param("candidateType") int candidateType,
                                                          @Param("partyId") int partyId,
                                                          RowBounds rowBounds);

    public int countBranchCandidates(@Param("userId") Integer userId,
                                     @Param("configId") int configId,
                                     @Param("stage") byte stage,
                                     @Param("candidateType") int candidateType,
                                     @Param("partyId") int partyId);
}
