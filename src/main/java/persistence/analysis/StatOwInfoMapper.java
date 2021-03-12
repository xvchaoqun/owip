package persistence.analysis;

import bean.StatByteBean;
import bean.StatIntBean;
import bean.StatOwInfoBean;
import domain.party.Party;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fafa on 2016/8/1.
 */
public interface StatOwInfoMapper {

    // 统计党员分布情况（按预备、正式分类）
    List<StatByteBean> member_groupByPoliticalStatus(@Param("partyId")Integer partyId,
                                                     @Param("branchId")Integer branchId,
                                                     @Param("enrolYear") String enrolYear);

    // 统计正式或预备党员分布情况（按student_level分类）
    List<StatByteBean> member_groupByType(@Param("politicalStatus")Byte politicalStatus,
                                          @Param("partyId")Integer partyId,
                                          @Param("branchId")Integer branchId,
                                          @Param("isRetire") Byte isRetire,
                                          @Param("enrolYear") String enrolYear);

    //统计某阶段各类型发展党员的数量 groupBy为空的为本科生
    List<StatByteBean> memberApply_groupByLevel(@Param("stage") byte stage,
                                                @Param("enrolYear") String enrolYear,
                                                @Param("partyId") Integer partyId,
                                                @Param("branchId") Integer branchId);

    //统计各类学生人数
    List<StatByteBean> selectUser_groupByLevel(@Param("enrolYear") String enrolYear);

    // 获取二级党组织名称
    @Select("SELECT DISTINCT id,(case short_name when '' then NAME ELSE short_name end) AS shortName FROM ow_party where is_deleted = 0 and fid is null")
    List<Party> getSecondPartyName();

    //获取教师 党员统计
    List<StatByteBean> member_teacherSort(@Param("politicalStatus")Byte politicalStatus,
                                          @Param("proPostLevel") String proPostLevel,
                                          @Param("partyId") Integer partyId,
                                          @Param("branchId") Integer branchId);

    //获取教师 发展党员统计
    List<StatByteBean> memberApply_teacherSort(@Param("stage")Byte stage,
                                               @Param("proPostLevel") String proPostLevel,
                                               @Param("partyId") Integer partyId,
                                               @Param("branchId") Integer branchId,
                                               @Param("gender") Byte gender);

    //获取教师（校编教工、专任教师）数量(按is_full_time_teacher分类)
    List<StatByteBean> member_teacherCount(@Param("proPostLevel") String proPostLevel,
                                           @Param("gender")Byte gender);

    //统计全校党支部的数量
    List<StatIntBean> branchCount_groupByType(@Param("types")Byte types,
                               @Param("proPostLevel") String proPostLevel,
                               @Param("partyId") Integer partyId);

    //获取基层党组织信息
    List<StatOwInfoBean> getParty_Branch(@Param("partyId")int id);


    List<StatOwInfoBean> getDirectlyBranch(@Param("directBranchId") Integer directBranchId, @Param("partySecretaryId") int partySecretaryId);
}
