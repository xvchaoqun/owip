package persistence.cadre.common;

import domain.cadre.CadreView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fafa on 2017/1/18.
 *
 * 干部统计
 */
public interface StatCadreMapper {

    int countCadre(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 行政级别
    List<StatCadreBean> cadre_stat_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_adminLevel_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 性别
    List<StatCadreBean> cadre_stat_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 民族
    List<StatCadreBean> cadre_stat_nation(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_nation_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_nation_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 党派
    StatCadreBean cadre_stat_dp(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_dp_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_dp_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 年龄
    StatCadreBean cadre_stat_age(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_age_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_age_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 职称
    StatCadreBean cadre_stat_post(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_post_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_post_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 学位
    List<StatCadreBean> cadre_stat_degree(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_degree_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_degree_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 学历
    List<StatCadreBean> cadre_stat_edu(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_edu_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_edu_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 专职
    StatCadreBean cadre_stat_double(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_double_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_double_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    //全部类型
    List<CadreView> allCadreList(@Param("unitTypeGroup") String unitTypeGroup,
                                 @Param("cadreType")Byte cadreType);

    //行政级别查询
    List<CadreView> adminLevelList(@Param("unitTypeGroup") String unitTypeGroup,
                                   @Param("cadreType")Byte cadreType,
                                   @Param("adminLevelCode") String adminLevelCode);
    //根据民族查询
    List<CadreView> nationList(@Param("unitTypeGroup") String unitTypeGroup,
                               @Param("cadreType")Byte cadreType,
                               @Param("isHan") Boolean isHan);

    //根据年龄段查询
    List<CadreView> ageList(@Param("unitTypeGroup") String unitTypeGroup,
                            @Param("cadreType")Byte cadreType,
                            @Param("startNum") Integer startNum,
                            @Param("endNum")Integer endNum);

    //政治面貌
    List<CadreView> PsList(@Param("unitTypeGroup") String unitTypeGroup,
                           @Param("cadreType")Byte cadreType,
                           @Param("isOw") Boolean isOw,
                           @Param("dpTypeId")Integer dpTypeId);

    //学位
    List<CadreView> degreeList(@Param("unitTypeGroup") String unitTypeGroup,
                               @Param("cadreType")Byte cadreType,
                               @Param("degreeType")Integer degreeType);

    //专职干部
    List<CadreView> isDoubleList(@Param("unitTypeGroup") String unitTypeGroup,
                                 @Param("cadreType")Byte cadreType,
                                 @Param("isDouble")Boolean isDouble);

    List<CadreView> educationList(@Param("unitTypeGroup") String unitTypeGroup,
                                  @Param("cadreType")Byte cadreType,
                                  @Param("eduId")Integer eduId);
}
