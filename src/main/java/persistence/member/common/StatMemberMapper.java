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
    List<MemberStatByBranchBean> memberApply_groupByBranchId(@Param("partyId")int partyId);

    //统计支部类型
    List<String> getBranchTypes(@Param("partyId")Integer partyId);

    // 按性别统计党员人数
    List<StatIntBean> member_countGroupByGender(@Param("partyId")Integer partyId, @Param("branchId")Integer branchId);

    // 统计党员中汉族的人数
    Integer countHan(@Param("partyId")Integer partyId, @Param("branchId")Integer branchId);

    //统计党员中少数民族的人数
    Integer countMinority(@Param("partyId")Integer partyId, @Param("branchId")Integer branchId);

    //统计党员中民族为空的人数
    Integer countNull(@Param("partyId")Integer partyId, @Param("branchId")Integer branchId);

    //组织部年统数据表中统计党支部数量
    @Select("select t1.c1 + t2.c2 " +
            "from (select ifnull(count(id),0) as c1 from ow_branch where find_in_set(#{metaId},types) AND is_deleted = 0) t1," +
            "(select ifnull(count(id),0) as c2 from ow_party where branch_type is not null and find_in_set(#{metaId},branch_type) AND is_deleted = 0) t2")
    Integer getBranchCountByType(@Param("metaId")Integer metaId);

    //分党委年统数据表中统计党支部数量
    @Select("select t1.c1 + t2.c2 " +
            "from (select ifnull(count(id),0) as c1 from ow_branch where find_in_set(#{metaId},types) AND is_deleted = 0 and party_id=#{partyId}) t1," +
            "(select ifnull(count(id),0) as c2 from ow_party where branch_type is not null and find_in_set(#{metaId},branch_type) AND is_deleted = 0 and id=#{partyId}) t2")
    Integer getBCByPartyId(@Param("metaId")Integer metaId, @Param("partyId")Integer partyId);

    //年统数据表中统计党员数量
    Integer getMemberCount(@Param("type") Byte type,
                           @Param("userType") Byte userType,
                           @Param("isRetire") Boolean isRetire,
                           @Param("proPostLevel") String proPostLevel,
                           @Param("branchIdList") List<Integer> branchIdList,
                           @Param("proPostLevel1") String proPostLevel1,
                           @Param("proPostLevel2") String proPostLevel2,
                           @Param("partyId") Integer partyId);

    @Select("select count(*) from ow_member_view where status=1 and type =2 and user_type = 3 and edu_level like '%博士%'")
    Integer getOwBsMemberCount();

    @Select("select count(*) from ow_member_view where party_id=#{partyId} and status=1 and type =2 and user_type = 3 and edu_level like '%博士%'")
    Integer getPartyBsMemberCount(@Param("partyId") Integer partyId);

}
