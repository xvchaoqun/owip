package persistence.party.common;

import domain.party.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface IPartyMapper {

    // 组织员所在分组ID列表
    @Select("select distinct group_id from ow_organizer_group_user where user_id=#{userId}")
    List<Integer> getOrganizerGroupIds(@Param("userId")Integer userId);

    // 组织员的联系单位所在分组ID列表
    @Select("select distinct group_id from ow_organizer_group_unit where unit_id=#{unitId}")
    List<Integer> getUnitGroupIds(@Param("unitId")Integer unitId);

    // 组织员查询列表
    List<Organizer> selectOrganizerList(@Param("query")String query,
                                        @Param("type") Byte type,
                                        @Param("status") Byte status,
                                        RowBounds rowBounds);
    int countOrganizerList(@Param("query")String query,
                           @Param("type") Byte type,
                           @Param("status") Byte status);

    // 所有分党委管理员（当前有效管理员）
    List<OwAdmin> selectPartyAdminList(@Param("oa")OwAdmin owAdmin, RowBounds rowBounds);
    int countPartyAdminList(@Param("oa")OwAdmin owAdmin);

    // 所有支部管理员（当前有效管理员）
    List<OwAdmin> selectBranchAdminList(@Param("oa")OwAdmin owAdmin, RowBounds rowBounds);
    int countBranchAdminList(@Param("oa")OwAdmin owAdmin);

    // 根据委员类别查询所有的现任支部委员会委员
    @ResultMap("persistence.party.BranchMemberMapper.BaseResultMap")
    @Select("select bm.* from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bmg.is_deleted=0 and bm.is_history=0 and  bm.types like concat('%',#{metaTypeId},'%') and bmg.branch_id = #{branchId} and bm.group_id=bmg.id")
    List<BranchMember> findBranchMembers(@Param("metaTypeId") int metaTypeId, @Param("branchId") int branchId);

    // 根据委员类别、用户ID查询现任支部委员会委员
    @ResultMap("persistence.party.BranchMemberMapper.BaseResultMap")
    @Select("select bm.* from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bmg.is_deleted=0 and bm.is_history=0 and  bm.user_id=#{userId} and  bm.types like concat('%',#{metaTypeId},'%') and bmg.branch_id = #{branchId} and bm.group_id=bmg.id")
    BranchMember findBranchMember(@Param("metaTypeId") int metaTypeId,
                                  @Param("branchId") int branchId,
                                  @Param("userId") int userId);

    // 更新支部转移次数
    @Update("update ow_branch ob , (select branch_id, count(*) num from ow_branch_transfer_log " +
            "where branch_id in(${brachIds}) group by branch_id) tmp set ob.transfer_count=tmp.num where ob.id=tmp.branch_id")
    void updateBranchTransferCount(@Param("brachIds") String brachIds);

    // 根据委员类别、用户ID查询现任支部委员会委员
    @Select("SELECT types FROM ow_branch_member WHERE user_id=${userId}" +
            " UNION ALL SELECT post_id FROM ow_party_member WHERE user_id=${userId}")
    List<String> findIsMember(@Param("userId") int userId);

    // 信息不完整的支部数量
    @Select("select count(*) from ow_branch_view where party_id =${partyId} and is_deleted = 0 and integrity != 1")
    int countBranchNotIntegrity(@Param("partyId")Integer partyId);

    // 根据名称正则查找现运行党支部id
    @Select("select id from ow_branch where name REGEXP #{nameRegExps} and is_deleted=0")
    List<Integer> findBranchIdList(@Param("nameRegExps") String nameRegExps);

    // 根据名称正则查找现运行分党委id
    @Select("select id from ow_party where name REGEXP #{nameRegExps} and is_deleted=0")
    List<Integer> findPartyIdList(@Param("nameRegExps") String nameRegExps);
}
