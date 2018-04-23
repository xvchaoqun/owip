package persistence.cadre.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fafa on 2017/1/18.
 *
 * 中层干部统计
 */
public interface StatCadreMapper {

    int countCadre(@Param("unitTypeAttr") String unitTypeAttr);

    // 行政级别
    List<StatCadreBean> cadre_stat_adminLevel(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_adminLevel_gender(@Param("unitTypeAttr") String unitTypeAttr);

    // 性别
    List<StatCadreBean> cadre_stat_gender(@Param("unitTypeAttr") String unitTypeAttr);

    // 民族
    List<StatCadreBean> cadre_stat_nation(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_nation_adminLevel(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_nation_gender(@Param("unitTypeAttr") String unitTypeAttr);

    // 党派
    StatCadreBean cadre_stat_dp(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_dp_adminLevel(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_dp_gender(@Param("unitTypeAttr") String unitTypeAttr);

    // 年龄
    StatCadreBean cadre_stat_age(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_age_adminLevel(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_age_gender(@Param("unitTypeAttr") String unitTypeAttr);

    // 职称
    StatCadreBean cadre_stat_post(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_post_adminLevel(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_post_gender(@Param("unitTypeAttr") String unitTypeAttr);

    // 学历
    List<StatCadreBean> cadre_stat_edu(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_edu_adminLevel(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_edu_gender(@Param("unitTypeAttr") String unitTypeAttr);

    // 专职
    StatCadreBean cadre_stat_double(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_double_adminLevel(@Param("unitTypeAttr") String unitTypeAttr);
    List<StatCadreBean> cadre_stat_double_gender(@Param("unitTypeAttr") String unitTypeAttr);
}
