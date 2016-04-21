package persistence.common;

import domain.Member;
import domain.MemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by fafa on 2016/1/5.
 */
public interface UpdateMapper {

    @Update("update base_dispatch set file=null, file_name=null where id=#{id}")
    void del_dispatch_file(@Param("id") int id);
    @Update("update base_dispatch set ppt=null, ppt_name=null where id=#{id}")
    void del_dispatch_ppt(@Param("id") int id);

    // 如果修改成直属党支部， 则将支部ID设置为NULL
    @Update("update ${tableName} set party_id=#{partyId}, branch_id=null where ${idName}=#{id}")
    int updateToDirectBranch(@Param("tableName") String tableName, String idName, int id, int partyId);

    // 批量转校内组织关系
    int changeMemberParty(@Param("partyId") Integer partyId, @Param("branchId") Integer branchId,
                          @Param("example") MemberExample example);
}
