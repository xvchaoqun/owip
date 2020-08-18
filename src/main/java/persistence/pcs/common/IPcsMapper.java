package persistence.pcs.common;

import domain.pcs.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface IPcsMapper {

    // 读取当前系统中应参与党代会的分党委（未删除、党员总数大于0的）
    List<PcsParty> expectPcsPartyList(@Param("configId") int configId);

    // 读取当前系统中应参与党代会的党支部（含直属党支部，未删除、党员总数大于0的）
    List<PcsParty> expectPcsBranchList(@Param("configId") int configId);



    // 更新党代会投票中的参评人数量
    @Update("update pcs_poll pp left join (select poll_id, count(*) as count,SUM(if(is_finished,1,0)) as finished_count," +
            "SUM(if(is_finished=1 and is_positive=1,1,0)) as positive_count from pcs_poll_inspector group by poll_id) tmp on tmp.poll_id=pp.id " +
            "set inspector_num=ifnull(COUNT,0),inspector_finish_num=ifnull(finished_count,0),positive_finish_num=ifnull(positive_count,0) where pp.id=#{pollId}")
    int updatePollInspectorCount(@Param("pollId") Integer pollId);

    // 两委选举 小组统计汇总
    public List<PcsVoteCandidate> selectVoteCandidateStatList(@Param("type") byte type,
                                                              @Param("userId") Integer userId,
                                                              @Param("isFromStage") Boolean isFromStage);

    @ResultMap("persistence.pcs.PcsVoteGroupMapper.BaseResultMap")
    @Select("select sum(vote) as vote, sum(valid) as valid, sum(invalid) as invalid " +
            "from pcs_vote_group where has_report=1 and type=#{type}")
    public PcsVoteGroup statPcsVoteGroup(@Param("type") byte type);

    // 根据账号、姓名、学工号查找党代表
    List<PcsPrCandidate> selectPrList( @Param("configId") int configId,
                                           @Param("stage") byte stage,
                                           @Param("search") String search, RowBounds rowBounds);
    int countPrList(@Param("configId") int configId,
                    @Param("stage") byte stage, @Param("search") String search);

    // 还未报送党代表数据的分党委管理员
    @ResultMap("persistence.pcs.PcsAdminMapper.BaseResultMap")
    @Select("select * from pcs_admin pa where pa.type = #{adminType} and not exists(select 1 from " +
            "pcs_pr_recommend where party_id =pa.party_id and config_id  = #{configId} and stage = #{stage} and has_report=1)")
    public List<PcsAdmin> hasNotReportPcsPrAdmins(@Param("configId") int configId,
                                                  @Param("stage") byte stage,
                                                  @Param("adminType") byte adminType);

    //  （党代表）分党委推荐汇总情况，configId和stage非搜索字段，仅用于创建视图数据
    public List<PcsPrPartyBean> selectPcsPrPartyBeanList(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("hasReport") Boolean hasReport,
            @Param("recommendStatus") Byte recommendStatus,
            RowBounds rowBounds);

    public int countPcsPrPartyBeanList(
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
    public List<PcsPrAllocateBean> selectPcsPrAllocateBeanList(
            @Param("configId") int configId,
            @Param("partyId") Integer partyId,
            RowBounds rowBounds);
    public int countPcsPrAllocateBeanList(@Param("configId") int configId,
                                          @Param("partyId") Integer partyId);
/*
    // 党代表候选人初步人选名单（分党委）
    public List<IPcsCandidate> selectPcsPrPartyCandidateList(@Param("userId") Integer userId,
                                                          @Param("configId") int configId,
                                                          @Param("stage") byte stage,
                                                          @Param("partyId") int partyId,
                                                          RowBounds rowBounds);

    public int countPcsPrPartyCandidateList(@Param("userId") Integer userId,
                                     @Param("configId") int configId,
                                     @Param("stage") byte stage,
                                     @Param("partyId") int partyId);*/


    // 还未报送两委委员数据的分党委管理员
    @ResultMap("persistence.pcs.PcsAdminMapper.BaseResultMap")
    @Select("select * from pcs_admin pa where pa.type = #{adminType} and not exists(select 1 from " +
            "pcs_admin_report where party_id=pa.party_id and config_id  = #{configId} and stage = #{stage})")
    public List<PcsAdmin> hasNotReportPcsAdmins(@Param("configId") int configId,
                                                @Param("stage") byte stage, @Param("adminType") byte adminType);

    // 全校 应参会党员总数/实参会党员总数
    @Select("select sum(pr.expect_member_count) as expect, sum(pr.actual_member_count) as actual " +
            "from pcs_recommend pr where config_id  = #{configId} and stage = #{stage} " +
            "and exists(select 1 from pcs_admin_report where party_id=pr.party_id and config_id  = #{configId} and stage = #{stage})")
    public Map<String, BigDecimal> schoolMemberCount(@Param("configId") int configId,
                                                     @Param("stage") byte stage);

    //  分党委推荐汇总情况
    public List<PcsPartyBean> selectPcsPartyBeanList(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("hasReport") Boolean hasReport,
            RowBounds rowBounds);
    public int countPcsPartyBeanList(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("hasReport") Boolean hasReport);


    // 党支部推荐汇总情况（含直属党支部）
    public List<PcsBranchBean> selectPcsBranchBeanList(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("branchId") Integer branchId,
            @Param("isFinished") Boolean isFinished,
            RowBounds rowBounds);
    public int countPcsBranchBeanList(
            @Param("configId") int configId,
            @Param("stage") byte stage,
            @Param("partyId") Integer partyId,
            @Param("branchId") Integer branchId,
            @Param("isFinished") Boolean isFinished);

    // 获取被推荐人都有哪些支部推荐了（只统计已上报的党支部，除直属党支部）
    List<Integer> selectCandidateBranchIds(@Param("userId") int userId,
                             @Param("configId") int configId,
                             @Param("stage") byte stage,
                             @Param("candidateType") int candidateType);
    // 分党委两委委员推荐提名情况（只统计已上报）
    public List<IPcsCandidate> selectPartyCandidateList(@Param("userId") Integer userId,
                                                        @Param("isChosen") Boolean isChosen,
                                                        @Param("configId") int configId,
                                                        @Param("stage") byte stage,
                                                        @Param("candidateType") int candidateType,
                                                        RowBounds rowBounds);

    public int countPartyCandidateList(@Param("userId") Integer userId,
                                       @Param("isChosen") Boolean isChosen,
                                       @Param("configId") int configId,
                                       @Param("stage") byte stage,
                                       @Param("candidateType") int candidateType);

    // 党支部两委委员推荐提名情况
    public List<IPcsCandidate> selectBranchCandidateList(@Param("userId") Integer userId,
                                                         @Param("configId") int configId,
                                                         @Param("stage") byte stage,
                                                         @Param("candidateType") int candidateType,
                                                         @Param("partyId") int partyId,
                                                         RowBounds rowBounds);

    public int countBranchCandidateList(@Param("userId") Integer userId,
                                        @Param("configId") int configId,
                                        @Param("stage") byte stage,
                                        @Param("candidateType") int candidateType,
                                        @Param("partyId") int partyId);

    /*
    * @des 党代会投票  除了必填的，其他参数均用来查询或者设置候选人
    * @param type 必填 推荐人类型
    * @param pollIdList 必填 党代会投票id
    * @param stage 必填 党代会阶段
    * @param isCandidate 设置候选人时使用
    * */
    public int countResult(@Param("type") Byte type,
                           @Param("pollIdList") List<Integer> pollIdList,
                           @Param("stage") Byte stage,
                           @Param("isCandidate") Boolean isCandidate,
                           @Param("userId") Integer userId,
                           @Param("partyId") Integer partyId,
                           @Param("branchId") Integer branchId,
                           @Param("partyIdList") List<Integer> partyIdList,
                           @Param("branchIdList") List<Integer> branchIdList);

    public List<PcsFinalResult> selectResultList(@Param("type") Byte type,
                                                 @Param("pollIdList") List<Integer> pollIdList,
                                                 @Param("stage") Byte stage,
                                                 @Param("isCandidate") Boolean isCandidate,
                                                 @Param("userId") Integer userId,
                                                 @Param("partyId") Integer partyId,
                                                 @Param("branchId") Integer branchId,
                                                 @Param("partyIdList") List<Integer> partyIdList,
                                                 @Param("branchIdList") List<Integer> branchIdList,
                                                 RowBounds rowBounds);

    public int countSecondResult(@Param("type") Byte type,
                                 @Param("pollIdList") List<Integer> pollIdList,
                                 @Param("stage") Byte stage,
                                 @Param("isCandidate") Boolean isCandidate,
                                 @Param("userId") Integer userId,
                                 @Param("partyId") Integer partyId,
                                 @Param("branchId") Integer branchId,
                                 @Param("partyIdList") List<Integer> partyIdList,
                                 @Param("branchIdList") List<Integer> branchIdList);

    public List<PcsFinalResult> selectSecondResultList(@Param("type") Byte type,
                                                       @Param("pollIdList") List<Integer> pollIdList,
                                                       @Param("stage") Byte stage,
                                                       @Param("isCandidate") Boolean isCandidate,
                                                       @Param("userId") Integer userId,
                                                       @Param("partyId") Integer partyId,
                                                       @Param("branchId") Integer branchId,
                                                       @Param("partyIdList") List<Integer> partyIdList,
                                                       @Param("branchIdList") List<Integer> branchIdList,
                                                       RowBounds rowBounds);

}
