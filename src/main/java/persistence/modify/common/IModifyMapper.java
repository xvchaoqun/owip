package persistence.modify.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import persistence.cadre.common.ICadreInfoCheck;

import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface IModifyMapper {

    @Select("select cadre_id from modify_cadre_auth where is_unlimited=1 or " +
            "(is_unlimited=0 and ( (curdate() between start_time and end_time) " +
            "or (start_time is null and curdate() <= end_time) " +
            "or (end_time is null and curdate() >= start_time)))")
    List<Integer> selectValidModifyCadreAuth();

    @Update("update modify_cadre_auth set start_time=null, end_time=null, is_unlimited=1 where id=#{id}")
    void del_ModifyCadreAuth_time(@Param("id") int id);

    // 是否存在某个基本信息字段的修改申请
    @Select("select count(mbi.id) from modify_base_item mbi, modify_base_apply mba " +
            "where mbi.apply_id= mba.id and mba.user_id=#{userId} and " +
            "mbi.table_name=#{tableName} and mbi.code =#{columnName} and mbi.status=0")
    int baseModifyApplyCount(@Param("userId") int userId, @Param("tableName") String tableName,
                             @Param("columnName") String columnName);

    @ResultType(ICadreInfoCheck.class)
    @Select("select sum(if(status=0,1,0)) as formalCount, sum(if(status=1,1,0)) as modifyCount " +
            "from cadre_research where cadre_id=#{cadreId} and research_type=#{researchType} ")
    ICadreInfoCheck cadreResearchCheck(@Param("cadreId") int cadreId, @Param("researchType") Byte researchType);

    @ResultType(ICadreInfoCheck.class)
    @Select("select sum(if(status=0,1,0)) as formalCount, sum(if(status=1,1,0)) as modifyCount " +
            "from cadre_reward where cadre_id=#{cadreId} and reward_type=#{rewardType} ")
    ICadreInfoCheck cadreRewardCheck(@Param("cadreId") int cadreId, @Param("rewardType") Byte rewardType);

    @ResultType(ICadreInfoCheck.class)
    @Select("select sum(if(status=0,1,0)) as formalCount, sum(if(status=1,1,0)) as modifyCount " +
            "from cadre_${tableName} where cadre_id=#{cadreId} ")
    ICadreInfoCheck cadreInfoModifyCheck(@Param("cadreId") int cadreId, @Param("tableName") String tableName);


    @ResultType(ICadreInfoCheck.class)
    @Select("select count(*) as formalCount " +
            "from cadre_${tableName} where cadre_id=#{cadreId}")
    ICadreInfoCheck cadreInfoExistCheck(@Param("cadreId") int cadreId, @Param("tableName") String tableName);

    //unFormalCount：不完整的记录，且未提交修改申请（待审核）， formalCount：正常记录，  modifyCount：修改记录，且还未审核
    @ResultType(ICadreInfoCheck.class)
    @Select("select sum(if(cf.status=0 and cf.realname is not null and cf.birthday is not null and cf.political_status is not null and cf.unit is not null, 1, 0)) as formalCount," +
            "sum(if(cf.status=0 and (cf.realname is null or (cf.with_god=0 and cf.birthday is null) or cf.political_status is null or cf.unit is null) " +
            "and not exists(select 1 from modify_table_apply where original_id=cf.id and table_name='cadre_family' and status=0),1,0)) as unFormalCount, " +
            "sum(if(cf.status=1, 1, 0)) as modifyCount  from cadre_family cf " +
            "where cf.cadre_id=#{cadreId}")
    ICadreInfoCheck familyCheck(@Param("cadreId") int cadreId);


}
