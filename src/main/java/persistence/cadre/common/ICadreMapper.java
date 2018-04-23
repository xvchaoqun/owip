package persistence.cadre.common;

import controller.analysis.CadreCategorySearchBean;
import domain.cadre.Cadre;
import domain.cadre.CadreFamliy;
import domain.cadre.CadreLeader;
import domain.cadre.CadrePost;
import domain.crp.CrpRecord;
import domain.sys.SysUserView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import sys.constants.CadreConstants;

import java.util.List;
import java.util.Set;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICadreMapper {

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
                                @Param("isCommitteeMember") Boolean isCommitteeMember, RowBounds rowBounds);
    int countCadreList(@Param("search") String search,
                       @Param("cadreStatusList") Set<Byte> cadreStatusList,
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
            "and cp.is_main_post=#{isMainPost} and cp.admin_level_id=#{adminLevelId} " +
            "and cp.unit_id in(select distinct unit_id from cpc_allocation) " +
            "and cp.unit_id=u.id and u.type_id=ut.id and ut.extra_attr=#{unitType} and cp.cadre_id=c.id " +
            "and c.status in(" + CadreConstants.CADRE_STATUS_MIDDLE + "," + CadreConstants.CADRE_STATUS_LEADER + ") " +
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
}


