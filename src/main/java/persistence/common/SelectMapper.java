package persistence.common;

import domain.DispatchCadre;
import domain.Passport;
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

    List<Passport> selectPassportList(@Param("cadreId") Integer cadreId,
                                      @Param("classId") Integer classId,
                                      @Param("code") String code,
                                      @Param("type") Byte type,
                                      @Param("abolish") Boolean abolish, RowBounds rowBounds);
    Integer countPassport(@Param("cadreId") Integer cadreId,
                              @Param("classId") Integer classId,
                              @Param("code") String code,
                              @Param("type") Byte type,
                              @Param("abolish") Boolean abolish);
    // 获取干部证件
   // List<Passport> selectCadrePassports(@Param("cadreId") Integer cadreId);
}
