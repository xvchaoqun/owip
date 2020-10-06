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

    int countCadre(@Param("s")CadreSearchBean searchBean);

    // 行政级别
    List<StatCadreBean> cadre_stat_adminLevel(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_adminLevel_gender(@Param("s")CadreSearchBean searchBean);

    // 性别
    List<StatCadreBean> cadre_stat_gender(@Param("s")CadreSearchBean searchBean);

    // 民族
    List<StatCadreBean> cadre_stat_nation(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_nation_adminLevel(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_nation_gender(@Param("s")CadreSearchBean searchBean);

    // 党派
    StatCadreBean cadre_stat_dp(@Param("crowdId")int crowdId, @Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_dp_adminLevel(@Param("crowdId")int crowdId, @Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_dp_gender(@Param("crowdId")int crowdId, @Param("s")CadreSearchBean searchBean);

    // 年龄
    StatCadreBean cadre_stat_age(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_age_adminLevel(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_age_gender(@Param("s")CadreSearchBean searchBean);

    // 平均年龄
    StatCadreBean cadre_avg_age(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_avg_age_adminLevel(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_avg_age_gender(@Param("s")CadreSearchBean searchBean);

    // 职称
    StatCadreBean cadre_stat_post(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_post_adminLevel(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_post_gender(@Param("s")CadreSearchBean searchBean);

    // 学位
    List<StatCadreBean> cadre_stat_degree(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_degree_adminLevel(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_degree_gender(@Param("s")CadreSearchBean searchBean);

    // 学历
    List<StatCadreBean> cadre_stat_edu(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_edu_adminLevel(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_edu_gender(@Param("s")CadreSearchBean searchBean);

    // 专职
    StatCadreBean cadre_stat_double(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_double_adminLevel(@Param("s")CadreSearchBean searchBean);
    List<StatCadreBean> cadre_stat_double_gender(@Param("s")CadreSearchBean searchBean);

    //全部类型
    List<CadreView> allCadreList(@Param("s")CadreSearchBean searchBean);

    //行政级别查询
    List<CadreView> adminLevelList(@Param("s")CadreSearchBean searchBean,
                                   @Param("adminLevelCode") String adminLevelCode);
    //根据民族查询
    List<CadreView> nationList(@Param("s")CadreSearchBean searchBean,
                               @Param("isHan") Boolean isHan);

    //根据年龄段查询
    List<CadreView> ageList(@Param("s")CadreSearchBean searchBean,
                            @Param("startAge") Integer startAge,
                            @Param("endAge")Integer endAge);

    //政治面貌
    List<CadreView> psList(@Param("s")CadreSearchBean searchBean,
                           @Param("firstTypeNum") int firstTypeNum,
                           @Param("crowdId")int crowdId);

    //职称分布
    List<CadreView> postLevelList(@Param("s")CadreSearchBean searchBean,
                                  @Param("firstTypeNum") int firstTypeNum);

    //学位
    List<CadreView> degreeList(@Param("s")CadreSearchBean searchBean,
                               @Param("degreeType")Integer degreeType);

    //专职、双肩挑干部
    List<CadreView> isDoubleList(@Param("s")CadreSearchBean searchBean,
                                 @Param("isDouble")Boolean isDouble);

    List<CadreView> educationList(@Param("s")CadreSearchBean searchBean,
                                  @Param("eduId")Integer eduId);
}
