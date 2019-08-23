package persistence.dp.common;

import domain.dp.DpMember;
import domain.dp.DpMemberExample;
import domain.dp.DpMemberView;
import domain.sys.SysUserView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2019/8/1.
 */
public interface IDpMemberMapper {

    //根据类别、状态、账号、姓名、学工号查找党派成员
    List<DpMember> selectDpMemberList(@Param("partyId") Integer partyId,
                                    @Param("type") Byte type,
                                    @Param("isRetire") Boolean isRetire,
                                    @Param("politicalStatus") Byte politicalStatus,
                                    @Param("statusList") List<Byte> statusList, @Param("search") String search,
                                    @Param("addPermits") Boolean addPermits,
                                    @Param("adminDpPartyIdList") List<Integer> adminDpPartyIdList, RowBounds rowBounds);

    int countDpMemberList(@Param("partyId") Integer partyId,
                          @Param("type") Byte type,
                          @Param("isRetire") Boolean isRetire,
                          @Param("politicalStatus") Byte politicalStatus,
                          @Param("statusList") List<Byte> statusList,
                          @Param("search") String search,
                          @Param("addPermits") Boolean addPermits,
                          @Param("adminDpPartyIdList") List<Integer> adminDpPartyIdList);

    // 批量转校内组织关系
    int changeDpMemberParty(@Param("partyId") Integer partyId, @Param("example") DpMemberExample example);

    @ResultMap("persistence.dp.DpMemberViewMapper.BaseResultMap")
    @Select("select * from dp_member_view where user_id=#{userId}")
    DpMemberView getDpMemberView(@Param("userId") int userId);

    Map selectDpMemberTeacherCount(@Param("addPermits") Boolean addPermits,
                                 @Param("adminDpPartyIdList") List<Integer> adminDpPartyIdList);

    Map selectDpMemberStudentCount(@Param("addPermits") Boolean addPermits,
                                 @Param("adminDpPartyIdList") List<Integer> adminDpPartyIdList);

    int countNotDpMemberList(@Param("query") String query, @Param("regRoleStr") String regRoleStr);

    // 根据账号、姓名、学工号查找 不是 党派成员和学生的用户
    List<SysUserView> selectNotDpMemberList(@Param("query") String query,
                                          @Param("regRoleStr") String regRoleStr, RowBounds rowBounds);


}