package persistence.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import persistence.common.bean.IPcsCandidateView;
import persistence.common.bean.PcsBranchBean;
import persistence.common.bean.PcsPartyBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface IPcsMapper {


    // 全校 应参会党员总数/实参会党员总数
    @ResultType(java.util.HashMap.class)
    @Select("select sum(pr.expect_member_count) as expect, sum(pr.actual_member_count) as actual " +
            "from pcs_recommend pr, pcs_admin_report par where pr.party_id = par.party_id")
    public Map<String, BigDecimal> schoolMemberCount();

    //  分党委推荐汇总情况，configId和stage非搜索字段，仅用于创建视图数据
    public List<PcsPartyBean> selectPcsPartyBeans(
            @Param("configId") int configId,
            @Param("stage") int stage,
            @Param("partyId") Integer partyId,
            RowBounds rowBounds);

    public int countPcsPartyBeans(@Param("partyId") Integer partyId);


    // 党支部推荐汇总情况，configId和stage非搜索字段，仅用于创建视图数据
    public List<PcsBranchBean> selectPcsBranchBeans(
            @Param("configId") int configId,
            @Param("stage") int stage,
            @Param("partyId") int partyId,
            @Param("branchId") Integer branchId,
            RowBounds rowBounds);

    public int countPcsBranchBeans(@Param("partyId") int partyId,
                                   @Param("branchId") Integer branchId);



    // 分党委两委委员推荐提名情况
    public List<IPcsCandidateView> selectPartyCandidates(@Param("userId") Integer userId,
                                                         @Param("isChosen") Boolean isChosen,
                                                  @Param("configId") int configId,
                                                  @Param("stage") int stage,
                                                  @Param("candidateType") int candidateType,
                                                  RowBounds rowBounds);

    public int countPartyCandidates(@Param("userId") Integer userId,
                               @Param("isChosen") Boolean isChosen,
                               @Param("configId") int configId,
                               @Param("stage") int stage,
                               @Param("candidateType") int candidateType);

    // 党支部两委委员推荐提名情况
    public List<IPcsCandidateView> selectBranchCandidates(@Param("userId") Integer userId,
                                                  @Param("configId") int configId,
                                                  @Param("stage") int stage,
                                                  @Param("candidateType") int candidateType,
                                                  @Param("partyId") int partyId,
                                                  RowBounds rowBounds);

    public int countBranchCandidates(@Param("userId") Integer userId,
                               @Param("configId") int configId,
                               @Param("stage") int stage,
                               @Param("candidateType") int candidateType,
                               @Param("partyId") int partyId);
}
