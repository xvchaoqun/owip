package persistence.cadre.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICadreWorkMapper {

    //根据工作类型查询干部ID
    List<Integer> getCadreIdsOfWorkTypes(@Param("workTypes")List<Integer> workTypes,
                                         @Param("andWorkTypes") boolean andWorkTypes,
                                         @Param("cadreStatus") Byte cadreStatus);

    //根据工作经历查询干部ID
    List<Integer> getCadreIdsOfWorkDetail(@Param("detailList")List<String> detailList, @Param("cadreStatus") Byte cadreStatus);

    //根据所学专业查询干部ID
    List<Integer> getCadreIdsOfMajor(@Param("majorList")List<String> majorList, @Param("cadreStatus") Byte cadreStatus);
}