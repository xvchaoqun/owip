package persistence.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by fafa on 2015/11/16.
 */
public interface CommonMapper {

    Integer getMaxSortOrder(@Param("table") String table,
                            @Param("sortOrder") String sortOrder,
                            @Param("whereSql") String whereSql);

    // 排序列名默认为sort_order
    void downOrder(@Param("table") String table, @Param("whereSql") String whereSql,
                   @Param("baseSortOrder") int baseSortOrder,
                   @Param("targetSortOrder") int targetSortOrder);
    void upOrder(@Param("table") String table, @Param("whereSql") String whereSql,
                 @Param("baseSortOrder") int baseSortOrder,
                 @Param("targetSortOrder") int targetSortOrder);

    // 自定义排序列名
    void downOrder2(@Param("table") String table,
                    @Param("sortOrder") String sortOrder,
                    @Param("whereSql") String whereSql,
                    @Param("baseSortOrder") int baseSortOrder,
                    @Param("targetSortOrder") int targetSortOrder);
    void upOrder2(@Param("table") String table,
                  @Param("sortOrder") String sortOrder,
                  @Param("whereSql") String whereSql,
                  @Param("baseSortOrder") int baseSortOrder,
                  @Param("targetSortOrder") int targetSortOrder);

    @Update("${sql}")
    void excuteSql(@Param("sql") String sql);
}
