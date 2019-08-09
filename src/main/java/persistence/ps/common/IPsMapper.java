package persistence.ps.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IPsMapper {
    Map count(@Param("partyIdList") List partyIdList);

    /**
     * 获取用户（当前的二级党校管理员或院系级管理员）管理的当前的二级党校ID
     *
     * @param userId
     * @param type 类型：二级党校管理员或院系级管理员
     * @return
     */
    @Select("SELECT distinct ps_id FROM ps_admin pa, ps_info ps " +
            "WHERE pa.ps_id=ps.id AND pa.is_history=0 " +
            "AND ps.is_history=0 AND ps.is_deleted=0 AND pa.user_id=#{userId} and pa.type=#{type}")
    public List<Integer> getAdminPsIds(@Param("userId") int userId, @Param("type") int type);

    @Select("SELECT distinct ps_id FROM ps_admin pa, ps_info ps " +
            "WHERE pa.ps_id=ps.id AND pa.is_history=0 " +
            "AND ps.is_history=0 AND ps.is_deleted=0 AND pa.user_id=#{userId}")
    public List<Integer> getAllAdminPsIds(@Param("userId") int userId);

    @Select("select count(*) from ps_admin_party where admin_id = #{id} and is_history = 0")
    public Integer getCountParty(@Param("id") Integer id);

    @Select("select count(*) from ps_task_file where task_id = #{id}")
    public Integer getCountFile(@Param("id") Integer id);

}
