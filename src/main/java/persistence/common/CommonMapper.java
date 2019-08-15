package persistence.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by fafa on 2015/11/16.
 */
public interface CommonMapper {

    Integer getMaxSortOrder(@Param("table") String table,
                            @Param("sortOrder") String sortOrder,
                            @Param("whereSql") String whereSql);

    @Select("select sort_order from ${tableName} where ${idName}=#{id}")
    Integer getSortOrder(@Param("tableName") String tableName, @Param("idName") String idName, @Param("id") Integer id);
    @Select("select count(*) from ${tableName} where ${whereSql}")
    int count(@Param("tableName") String tableName, @Param("whereSql") String whereSql);
    @Select("select sort_order from ${tableName} where ${whereSql}")
    Integer getTargetSortOrder(@Param("tableName") String tableName, @Param("whereSql") String whereSql);

    // 查找有错误的sort_order（为空或重复）的记录ID
    List<Integer> getWrongSortOrderRecordIds(@Param("table") String table,
                                             @Param("idName") String idName,
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
