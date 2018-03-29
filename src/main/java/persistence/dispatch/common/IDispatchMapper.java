package persistence.dispatch.common;

import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreView;
import domain.dispatch.DispatchUnit;
import domain.dispatch.DispatchWorkFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by fafa on 2015/11/16.
 */
public interface IDispatchMapper {

    // 根据发文号查找单位发文
    List<DispatchUnit> selectDispatchUnitByCodeList(@Param("search") String code, @Param("unitId") int unitId, RowBounds rowBounds);

    int countDispatchByCodeUnit(@Param("search") String code, @Param("unitId") int unitId);

    // 查找干部发文（与干部任免排序规则相同） type:1任命 2免职 NULL 全部
    List<DispatchCadre> selectDispatchCadreList(@Param("cadreId") int cadreId, @Param("type") Byte type);

    // 根据所属单位查找干部发文（按发文时间排序）
    @ResultMap("persistence.dispatch.DispatchCadreMapper.BaseResultMap")
    @Select("select distinct dc.* from dispatch_cadre dc, dispatch d " +
            "where dc.dispatch_id=d.id and dc.unit_id=#{unitId} order by d.pub_time desc")
    List<DispatchCadre> selectDispatchCadreByUnitIdList(@Param("unitId") int unitId);

    // 查找单位发文（按发文时间排序）
    @ResultMap("persistence.dispatch.DispatchUnitMapper.BaseResultMap")
    @Select("select distinct du.* from dispatch_unit du, dispatch d " +
            "where du.dispatch_id=d.id and du.unit_id=#{unitId} order by d.pub_time desc")
    List<DispatchUnit> selectDispatchUnitList(@Param("unitId") int unitId);

    // 根据允许查看的职务属性等条件， 查询干部工作文件列表
    public List<DispatchWorkFile> findDispatchWorkFiles(
            @Param("isAdmin") boolean isAdmin,
            @Param("postIds") List<Integer> postIds,
            @Param("type") Byte type,
            @Param("status") Boolean status,
            @Param("unitTypes") Integer[] unitTypes,
            @Param("startYear") Integer startYear,
            @Param("endYear") Integer endYear,
            @Param("workTypes") Integer[] workTypes,
            @Param("privacyTypes") Integer[] privacyTypes,
            RowBounds rowBounds);
    int countDispatchWorkFiles(
            @Param("isAdmin") boolean isAdmin,
            @Param("postIds") List<Integer> postIds,
            @Param("type") Byte type,
            @Param("status") Boolean status,
            @Param("unitTypes") Integer[] unitTypes,
            @Param("startYear") Integer startYear,
            @Param("endYear") Integer endYear,
            @Param("workTypes") Integer[] workTypes,
            @Param("privacyTypes") Integer[] privacyTypes);

    @ResultMap("persistence.dispatch.DispatchCadreViewMapper.BaseResultMap")
    @Select("select dcv.* from dispatch_cadre_view dcv left join cadre_view cv on cv.id=dcv.cadre_id where dcv.unit_id=#{unitId} and dcv.type=2 and dcv.year between 2013 and 2017 order by  dcv.year desc, cv.sort_order desc")
    List<DispatchCadreView> leaveDispatchCadres(@Param("unitId") int unitId);

    // 更新发文提交的干部任免数量
    /*@Update("update dispatch bd, (select dispatch_id, sum(IF(type=1, 1, 0)) as real_appoint_count, sum(IF(type=2, 1, 0)) as real_dismiss_count from dispatch_cadre group by dispatch_id) bdc set bd.real_appoint_count= bdc.real_appoint_count, " +
            "bd.real_dismiss_count=bdc.real_dismiss_count where bd.id=bdc.dispatch_id")
    void update_dispatch_real_count();*/
    @Update("update dispatch d left join (select dispatch_id, sum(IF(type=1, 1, 0)) as real_appoint_count, "+
            "sum(IF(type=2, 1, 0)) as real_dismiss_count from dispatch_cadre group by dispatch_id) dc on dc.dispatch_id=d.id "+
            "set d.real_appoint_count=dc.real_appoint_count, d.real_dismiss_count=dc.real_dismiss_count")
    void update_dispatch_real_count();
    @Update("update dispatch set file=null, file_name=null where id=#{id}")
    void del_dispatch_file(@Param("id") int id);
    @Update("update dispatch set ppt=null, ppt_name=null where id=#{id}")
    void del_dispatch_ppt(@Param("id") int id);
}
