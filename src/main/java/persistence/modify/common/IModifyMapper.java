package persistence.modify.common;

import domain.modify.ModifyBaseItem;
import domain.modify.ModifyCadreAuth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import persistence.cadre.common.ICadreInfoCheck;

import java.util.List;
import java.util.Set;

/**
 * Created by lm on 2017/6/13.
 */
public interface IModifyMapper {

    public List<ModifyCadreAuth> selectModifyCadreAuthList(@Param("userId") Integer userId,
                                                           @Param("statusList") Byte[] statusList, RowBounds rowBounds);
    int countModifyCadreAuthList(@Param("userId") Integer userId,
                                 @Param("statusList") Byte[] statusList);
    // 删除指定类型的干部直接修改的权限
    public void removeAllCadres(@Param("cadreStatusSet") Set<Byte> cadreStatusSet);

    // 删除指定民主党派成员直接修改的权限
    public void removeAllCadreParties(@Param("crowdId") int crowdId);

    // 查找某人名下的申请记录
    @ResultMap("persistence.modify.ModifyBaseItemMapper.BaseResultMap")
    @Select("SELECT mbi.* FROM modify_base_item mbi, " +
            "modify_base_apply mba WHERE mbi.apply_id=mba.id " +
            "AND mbi.code=#{code} AND mba.user_id=#{userId} AND mbi.status=#{status}")
    List<ModifyBaseItem> selectModifyBaseItems(@Param("userId") int userId,
                                               @Param("code") String code,
                                               @Param("status") byte status);

    // 生效中的权限配置
    @Select("select cadre_id from modify_cadre_auth where is_unlimited=1 or " +
            "(is_unlimited=0 and ( (curdate() between start_time and end_time) " +
            "or (start_time is null and curdate() <= end_time) " +
            "or (end_time is null and curdate() >= start_time)))")
    List<Integer> selectValidModifyCadreAuth();

    // 清理已过期规则 或 无效规则 (end_time=null)
    @Update("delete from modify_cadre_auth where is_unlimited=0 and (end_time is null or curdate() > end_time)")
    void clearExpireAuth();

    @Update("update modify_cadre_auth set start_time=null, end_time=null, is_unlimited=1 where id=#{id}")
    void del_ModifyCadreAuth_time(@Param("id") int id);

    // 是否存在某个基本信息字段的修改申请
    @Select("select count(mbi.id) from modify_base_item mbi, modify_base_apply mba " +
            "where mbi.apply_id= mba.id and mba.user_id=#{userId} and " +
            "mbi.table_name=#{tableName} and mbi.code =#{columnName} and mbi.status=0")
    int baseModifyApplyCount(@Param("userId") int userId, @Param("tableName") String tableName,
                             @Param("columnName") String columnName);

    @Select("select sum(if(status=0,1,0)) as formalCount, sum(if(status=1,1,0)) as modifyCount " +
            "from cadre_research where cadre_id=#{cadreId} and research_type=#{researchType} ")
    ICadreInfoCheck cadreResearchCheck(@Param("cadreId") int cadreId, @Param("researchType") Byte researchType);

    @Select("select sum(if(status=0,1,0)) as formalCount, sum(if(status=1,1,0)) as modifyCount " +
            "from cadre_reward where cadre_id=#{cadreId} and reward_type=#{rewardType} ")
    ICadreInfoCheck cadreRewardCheck(@Param("cadreId") int cadreId, @Param("rewardType") Byte rewardType);

    @Select("select sum(if(status=0,1,0)) as formalCount, sum(if(status=1,1,0)) as modifyCount " +
            "from cadre_${tableName} where cadre_id=#{cadreId} ")
    ICadreInfoCheck cadreInfoModifyCheck(@Param("cadreId") int cadreId, @Param("tableName") String tableName);

    @Select("select count(*) as formalCount " +
            "from cadre_${tableName} where cadre_id=#{cadreId}")
    ICadreInfoCheck cadreInfoExistCheck(@Param("cadreId") int cadreId, @Param("tableName") String tableName);

    //unFormalCount：不完整的记录，且未提交修改申请（待审核）， formalCount：正常记录，  modifyCount：修改记录，且还未审核
    @Select("select sum(if(cf.status=0 and cf.realname is not null and cf.birthday is not null and cf.political_status is not null and cf.unit is not null, 1, 0)) as formalCount," +
            "sum(if(cf.status=0 and (cf.realname is null or (cf.with_god=0 and cf.birthday is null) or cf.political_status is null or cf.unit is null) " +
            "and not exists(select 1 from modify_table_apply where original_id=cf.id and table_name='cadre_family' and status=0),1,0)) as unFormalCount, " +
            "sum(if(cf.status=1, 1, 0)) as modifyCount  from cadre_family cf " +
            "where cf.cadre_id=#{cadreId}")
    ICadreInfoCheck familyCheck(@Param("cadreId") int cadreId);


}
