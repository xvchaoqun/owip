package persistence.cadre.common;

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

    // 学历
    List<StatCadreBean> cadre_stat_edu(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_edu_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_edu_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);

    // 专职
    StatCadreBean cadre_stat_double(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_double_adminLevel(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
    List<StatCadreBean> cadre_stat_double_gender(@Param("unitTypeGroup") String unitTypeGroup, @Param("cadreType")byte cadreType);
}
