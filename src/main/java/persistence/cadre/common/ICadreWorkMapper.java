package persistence.cadre.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICadreWorkMapper {

    //根据工作类型查询干部信息
    List<Integer> getCadreIdsOfWorkTypes(@Param("workTypes")List<Integer> workTypes);
}