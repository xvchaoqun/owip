package persistence.common;

import domain.DispatchCadre;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SelectMapper {

    List<DispatchCadre> selectDispatchCadrePage(
            @Param("dispatchId") Integer dispatchId,
            @Param("wayId") Integer wayId,
            @Param("procedureId") Integer procedureId,
            @Param("cadreId") Integer cadreId,
            @Param("adminLevelId") Integer adminLevelId,
            @Param("unitId") Integer unitId, RowBounds rowBounds);

        @Select("select distinct parent_code from base_location order by parent_code asc")
        List<Integer> selectDistinctLocationParentCode();
}
