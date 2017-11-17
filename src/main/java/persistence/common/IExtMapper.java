package persistence.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * Created by lm on 2017/11/15.
 */
public interface IExtMapper {

    @Select("select ltxf from ext_retire_salary where zgh = #{code} order by rq desc limit 1")
    BigDecimal getLtxf(@Param("code") String code);
}
