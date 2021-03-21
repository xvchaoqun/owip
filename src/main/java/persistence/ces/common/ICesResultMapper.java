package persistence.ces.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICesResultMapper {

    // 干部年终考核结果
    List<Integer> getCesResultYear(@Param("cadreIds") Integer[] cadreIds);
}
