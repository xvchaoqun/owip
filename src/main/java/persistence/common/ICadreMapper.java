package persistence.common;

import bean.CadreReserveCount;
import domain.cadre.*;
import domain.sys.SysUserView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import sys.constants.SystemConstants;

import java.util.List;
import java.util.Set;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICadreMapper {

    //  1、查找（机关）干部的（院系）工作经历  2、查找（院系）干部的（机关）工作经历
    @ResultMap("persistence.cadre.CadreWorkMapper.BaseResultMap")
    @Select("select  cw.* from cadre_work cw, cadre_view c where cw.cadre_id=c.id " +
            "and c.unit_type_id=#{unitTypeId} and cw.workType=#{workTYpe} " +
            "and c.status ="+SystemConstants.CADRE_STATUS_MIDDLE)
    List<CadreWork> findCadreWorks(@Param("unitTypeId") int unitTypeId, @Param("workType") int workType);

    // 查找干部的（境外）学习经历
    @ResultMap("persistence.cadre.CadreEduMapper.BaseResultMap")
    @Select("select * from cadre_edu ce , cadre_view c where ce.cadre_id=c.id " +
            "and ce.school_type =" + SystemConstants.CADRE_SCHOOL_TYPE_ABROAD +
            " and c.status ="+SystemConstants.CADRE_STATUS_MIDDLE)
    List<CadreEdu> findCadreEdus();



    // 根据账号、姓名、学工号查找干部
    List<Cadre> selectCadreList(@Param("search") String search,
                                @Param("cadreStatusList")Set<Byte> cadreStatusList, RowBounds rowBounds);
    int countCadre(@Param("search") String search,
                   @Param("cadreStatusList")Set<Byte> cadreStatusList);

    // 根据账号、姓名、学工号查找 不是 干部的用户
    List<SysUserView> selectNotCadreList(@Param("search") String search,
                                         @Param("cadreStatusList")Set<Byte> cadreStatusList, @Param("regRoleStr") String regRoleStr, RowBounds rowBounds);
    int countNotCadre(@Param("search") String search,
                      @Param("cadreStatusList")Set<Byte> cadreStatusList, @Param("regRoleStr") String regRoleStr);

    @Select("select  type, status, count(*) as num from cadre_reserve group by type, status")
    List<CadreReserveCount> selectCadreReserveCount();

      /* // 根据账号、姓名、学工号 查找所在单位和兼职单位 都关联该单位的干部
    List<Cadre> selectCadreByUnitIdList(@Param("search") String search,
                                        @Param("cadreStatusList")List<Byte> cadreStatusList, @Param("unitId")int unitId, RowBounds rowBounds);
    int countCadreByUnitId(@Param("search") String search,
                           @Param("cadreStatusList")List<Byte> cadreStatusList, @Param("unitId")int unitId);*/

    // 获取主职、兼职在某单位的现任干部
    @ResultMap("persistence.cadre.CadrePostMapper.BaseResultMap")
    @Select("select cp.* from cadre_post cp , cadre c where cp.unit_id=#{unitId} and cp.cadre_id=c.id and " +
            "c.status in(" + SystemConstants.CADRE_STATUS_MIDDLE + "," + SystemConstants.CADRE_STATUS_LEADER + ") " +
            "order by c.sort_order desc, cp.is_main_post desc, cp.sort_order desc")
    public List<CadrePost> findCadrePosts(@Param("unitId") int unitId);

    // 根据单位类型（jg/xy/fs)获取主职、兼职(占职数)的现任干部
    @ResultMap("persistence.cadre.CadrePostMapper.BaseResultMap")
    @Select("select cp.* from cadre_post cp , unit u, base_meta_type ut, cadre c " +
            "where (cp.is_main_post=1 or (cp.is_main_post=0 and cp.is_cpc=1)) " +
            "and cp.is_main_post=#{isMainPost} and cp.admin_level_id=#{adminLevelId} " +
            "and cp.unit_id in(select distinct unit_id from cpc_allocation) " +
            "and cp.unit_id=u.id and u.type_id=ut.id and ut.extra_attr=#{unitType} and cp.cadre_id=c.id " +
            "and c.status in(" + SystemConstants.CADRE_STATUS_MIDDLE + "," + SystemConstants.CADRE_STATUS_LEADER + ") " +
            "order by c.sort_order desc, cp.is_main_post desc, cp.sort_order desc")
    public List<CadrePost> findCadrePostsByUnitType(@Param("adminLevelId") Integer adminLevelId,
                                                    @Param("isMainPost") boolean isMainPost,
                                                    @Param("unitType") String unitType);

    // 获取2013年以来离任干部
    /*@ResultMap("persistence.cadre.CadreViewMapper.BaseResultMap")
    @Select("select cv.* from cadre_view cv, dispatch_cadre_view dcv where cv.id=dcv.cadre_id and cv.status=3 and cv.unit_id=#{unitId} and dcv.type=2 and dcv.year between 2013 and 2017")
    List<CadreView> leaveCadres(@Param("unitId")int unitId);*/

    // 查询干部家庭成员
    List<CadreFamliy> getCadreFamliys(@Param("cadreIds") Integer[] cadreIds, @Param("status") Byte status);

    //查询校领导的分管单位
    @Select("select blu.unit_id from cadre_leader_unit blu, cadre_leader bl " +
            "where  bl.cadre_id = #{cadreId} and blu.leader_id = bl.id and blu.type_id=#{leaderTypeId}")
    List<Integer> getLeaderManagerUnitId(@Param("cadreId") Integer cadreId, @Param("leaderTypeId") Integer leaderTypeId);

    //查询分管当前单位的校领导
    @ResultMap("persistence.cadre.CadreLeaderMapper.BaseResultMap")
    @Select("select bl.* from cadre_leader_unit blu, cadre_leader bl " +
            "where  blu.type_id=#{leaderTypeId} and blu.unit_id = #{unitId} and blu.leader_id = bl.id")
    List<CadreLeader> getManagerUnitLeaders(@Param("unitId") Integer unitId, @Param("leaderTypeId") Integer leaderTypeId);

    @Update("update cadre_edu set degree=null, is_high_degree=null, degree_country=null, degree_unit=null, degree_time=null where id=#{id}")
    void del_caderEdu_hasDegree(@Param("id") int id);

    @Update("update cadre_work set unit_id=null where id=#{id}")
    void del_cadreWork_unitId(@Param("id") int id);

    @Update("update cadre_post set double_unit_id=null where id=#{id}")
    void del_cadrePost_doubleUnitId(@Param("id") int id);
}


