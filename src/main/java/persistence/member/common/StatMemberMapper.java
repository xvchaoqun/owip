package persistence.member.common;

import bean.StatByteBean;
import bean.StatIntBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fafa on 2016/8/1.
 */
public interface StatMemberMapper {

    // 统计党员分布情况（预备、正式）
    List<StatByteBean> member_groupByPoliticalStatus(@Param("partyId")Integer partyId, @Param("branchId")Integer branchId);

    // 统计预备/正式党员类别分布情况
    List<StatByteBean> member_groupByType(@Param("politicalStatus")Byte politicalStatus,
                                                     @Param("partyId")Integer partyId,
                                                     @Param("branchId")Integer branchId);

    // 统计教职工党员年龄分布情况
    List<StatIntBean> member_teatcherGroupByBirth(@Param("partyId")Integer partyId, @Param("branchId")Integer branchId);

    // 统计学生党员年龄分布情况
    List<StatIntBean> member_studentGroupByBirth(@Param("partyId")Integer partyId, @Param("branchId")Integer branchId);

    // 统计党员发展各阶段情况
    List<StatByteBean> memberApply_groupByStage(@Param("stage")Byte stage,
                                                @Param("partyId")Integer partyId, @Param("branchId")Integer branchId);

    // 统计各分党委党员人数
    List<MemberStatByPartyBean> memberApply_groupByPartyId(@Param("top")int top);

    // 统计分党委下各党支部党员人数
    List<StatIntBean> memberApply_groupByBranchId(@Param("partyId")int partyId);

    //统计支部类型
    @Select("select types from ow_branch where is_deleted = 0")
    List<String> getBranchTypes();

    // 按性别统计党员人数
    List<StatIntBean> member_countGroupByGender();

    // 统计党员中汉族的人数
    @Select("select count(*) from ow_member_view where status=1 and nation like '汉%'")
    Integer countHan();

    @Select("select count(*) from ow_member_view where status=1 and nation not like '汉%' and nation is not null")
    Integer countMinority();

    @Select("select count(*) from ow_member_view where status=1 and nation is null")
    Integer countNull();
}
