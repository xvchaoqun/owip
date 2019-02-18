package persistence.cadre.common;

import controller.analysis.CadreCategorySearchBean;
import domain.cadre.*;
import domain.crp.CrpRecord;
import domain.sys.SysUserView;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import sys.constants.CadreConstants;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICadreMapper {

    @ResultMap("persistence.cadre.CadreViewMapper.BaseResultMap")
    @Select("select * from cadre_view where id=#{cadreId}")
    CadreView getCadre(@Param("cadreId") int cadreId);
    
    // 无此记录
    @Select("select ${columnName} from cadre_info_check where cadre_id=#{cadreId}")
    Boolean cadreInfoCheck(@Param("cadreId") int cadreId, @Param("columnName") String columnName );
    @Update("update cadre_info_check set ${columnName}=#{isChecked} where cadre_id=#{cadreId}")
    void cadreInfoCheckUpdate(@Param("cadreId") int cadreId,
                              @Param("columnName") String columnName,
                              @Param("isChecked") Boolean isChecked );
    @Update("insert into cadre_info_check(cadre_id, ${columnName}) values(#{cadreId}, #{isChecked})")
    void cadreInfoCheckInsert(@Param("cadreId") int cadreId,
                              @Param("columnName") String columnName,
                              @Param("isChecked") Boolean isChecked );


    List<ICadreEdu> selectCadreEduList(@Param("schoolType") byte schoolType,
                                       @Param("searchBean") CadreCategorySearchBean searchBean, RowBounds rowBounds);
    int countCadreEduList(@Param("schoolType") byte schoolType,
                          @Param("searchBean") CadreCategorySearchBean searchBean);

    // 主要工作经历修改为其间工作经历（结束时间不为空）
    @ResultMap("persistence.cadre.CadreWorkMapper.BaseResultMap")
    @Select("select * from cadre_work where cadre_id=#{cadreId} and fid is null and id!=#{id} and " +
            "start_time <= #{endTime} and (end_time is null or end_time >= #{endTime});")
    List<CadreWork> findTopCadreWorks(@Param("id") int id,
                                      @Param("cadreId") int cadreId, @Param("endTime") Date endTime);
    // 主要工作经历修改为其间工作经历（结束时间为空）
    @ResultMap("persistence.cadre.CadreWorkMapper.BaseResultMap")
    @Select("select * from cadre_work where cadre_id=#{cadreId} and fid is null and id!=#{id} and " +
            "end_time is null")
    List<CadreWork> findUnendCadreWorks(@Param("id") int id, @Param("cadreId") int cadreId);

    /* 分类查找干部工作经历 */
    List<ICadreWork> selectCadreWorkList(@Param("workType") int workType,
                                         @Param("searchBean") CadreCategorySearchBean searchBean, RowBounds rowBounds);
    int countCadreWorkList(@Param("workType") int workType,
                           @Param("searchBean") CadreCategorySearchBean searchBean);

    List<CrpRecord> selectCrpRecordList(@Param("type") byte type,
                                        @Param("searchBean") CadreCategorySearchBean searchBean, RowBounds rowBounds);
    int countCrpRecordList(@Param("type") byte type,
                           @Param("searchBean") CadreCategorySearchBean searchBean);
    // 6、具有人才/荣誉称号的干部
    List<ICarde> selectTalentCadreList(@Param("searchBean") CadreCategorySearchBean searchBean, RowBounds rowBounds);
    int countTalentCadreList(@Param("searchBean") CadreCategorySearchBean searchBean);

    
    // 根据账号、姓名、学工号查找干部
    List<Cadre> selectCadreList(@Param("search") String search,
                                @Param("cadreStatusList")Set<Byte> cadreStatusList,
                                @Param("unitIds") Integer[] unitIds,
                                @Param("isCommitteeMember") Boolean isCommitteeMember, RowBounds rowBounds);
    int countCadreList(@Param("search") String search,
                       @Param("cadreStatusList") Set<Byte> cadreStatusList,
                       @Param("unitIds") Integer[] unitIds,
                       @Param("isCommitteeMember") Boolean isCommitteeMember);

    // 根据账号、姓名、学工号查找 不是 干部的用户
    List<SysUserView> selectNotCadreList(@Param("query") String query,
                                         @Param("cadreStatusList")Set<Byte> cadreStatusList, @Param("regRoleStr") String regRoleStr, RowBounds rowBounds);
    int countNotCadreList(@Param("query") String query,
                          @Param("cadreStatusList") Set<Byte> cadreStatusList, @Param("regRoleStr") String regRoleStr);

    @Select("select  type, status, count(*) as num from cadre_reserve group by type, status")
    List<CadreReserveCount> selectCadreReserveCount();

      /* // 根据账号、姓名、学工号 查找所在单位和兼职单位 都关联该单位的干部
    List<Cadre> selectCadreByUnitIdList(@Param("search") String search,
                                        @Param("cadreStatusList")List<Byte> cadreStatusList,
                                        @Param("unitId")int unitId, RowBounds rowBounds);
    int countCadreByUnitIdList(@Param("search") String search,
                           @Param("cadreStatusList")List<Byte> cadreStatusList,
                           @Param("unitId")int unitId);*/

    // 获取主职、兼职在某单位的现任干部
    @ResultMap("persistence.cadre.CadrePostMapper.BaseResultMap")
    @Select("select cp.* from cadre_post cp , cadre c where cp.unit_id=#{unitId} and cp.cadre_id=c.id and " +
            "c.status in(" + CadreConstants.CADRE_STATUS_MIDDLE + "," + CadreConstants.CADRE_STATUS_LEADER + ") " +
            "order by c.sort_order desc, cp.is_main_post desc, cp.sort_order desc")
    public List<CadrePost> findCadrePosts(@Param("unitId") int unitId);

    // 根据单位类型（jg/xy/fs)获取主职、兼职(占职数)的现任干部
    @ResultMap("persistence.cadre.CadrePostMapper.BaseResultMap")
    @Select("select cp.* from cadre_post cp , unit u, base_meta_type ut, cadre c " +
            "where (cp.is_main_post=1 or (cp.is_main_post=0 and cp.is_cpc=1)) " +
            "and cp.is_main_post=#{isMainPost} and cp.admin_level=#{adminLevel} " +
            "and exists(select 1 from unit_post_count_view where unit_id=cp.unit_id) " +
            "and cp.unit_id=u.id and u.type_id=ut.id and ut.extra_attr=#{unitType} and cp.cadre_id=c.id " +
            "and c.status in(" + CadreConstants.CADRE_STATUS_MIDDLE + "," + CadreConstants.CADRE_STATUS_LEADER + ") " +
            "order by c.sort_order desc, cp.is_main_post desc, cp.sort_order desc")
    public List<CadrePost> findCadrePostsByUnitType(@Param("adminLevel") Integer adminLevel,
                                                    @Param("isMainPost") boolean isMainPost,
                                                    @Param("unitType") String unitType);

    // 获取2013年以来离任干部
    /*@ResultMap("persistence.cadre.CadreViewMapper.BaseResultMap")
    @Select("select cv.* from cadre_view cv, dispatch_cadre_view dcv where cv.id=dcv.cadre_id and cv.status=3 and cv.unit_id=#{unitId} and dcv.type=2 and dcv.year between 2013 and 2017")
    List<CadreView> leaveCadres(@Param("unitId")int unitId);*/

    // 查询干部家庭成员
    List<CadreFamily> getCadreFamilys(@Param("cadreIds") Integer[] cadreIds, @Param("status") Byte status);

    // 查询干部学习经历
    List<CadreEdu> getCadreEdus(@Param("cadreIds") Integer[] cadreIds, @Param("status") Byte status);

    // 统计干部兼职情况  干部-兼职类别-数量
    @ResultType(java.util.HashMap.class)
    @Select("select cadre_id, type, count(*) as num from cadre_company_view " +
            "where cadre_status in(${cadreStatus}) group by cadre_id, type " +
            "order by field(cadre_status, 2,5,3,1,4,6) desc, cadre_sort_order desc")
    List<Map> cadreCompany_statMap(@Param("cadreStatus") String cadreStatus);

    // 干部兼职汇总   兼职类型、是否双肩挑
    @ResultType(java.util.HashMap.class)
    @Select("select is_double, type, count(*) num, count(distinct cadre_id) as person_num " +
            "from cadre_company_view cc where cadre_status=1 group by is_double, type")
    List<Map> cadreCompany_doubleStatMap();

    // 干部兼职汇总   兼职类型、单位类型
    @ResultType(java.util.HashMap.class)
    @Select("select unit_type_attr, type, count(*) num, count(distinct cadre_id) as person_num " +
            "from cadre_company_view cc where cadre_status=1 and unit_type_attr in('xy','jg','fs') " +
            "group by unit_type_attr, type")
    List<Map> cadreCompany_unitTypeStatMap();

    // 干部兼职汇总   兼职类型、行政级别
    @ResultType(java.util.HashMap.class)
    @Select("select admin_level, type, count(*) num, count(distinct cadre_id) as person_num " +
            "from cadre_company_view cc where cadre_status=1 " +
            "and admin_level_code in('mt_admin_level_main','mt_admin_level_vice','mt_admin_level_none') " +
            "group by admin_level, type")
    List<Map> cadreCompany_adminLevelStatMap();

    // 干部兼职汇总   合计
    @ResultType(java.util.HashMap.class)
    @Select("select type, count(*) num, count(distinct cadre_id) as person_num " +
            "from cadre_company_view cc where cadre_status=1 group by  type")
    List<Map> cadreCompany_typeStatMap();

}


