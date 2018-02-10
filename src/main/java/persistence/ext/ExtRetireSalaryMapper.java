package persistence.ext;

import domain.ext.ExtRetireSalary;
import domain.ext.ExtRetireSalaryExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ExtRetireSalaryMapper {
    long countByExample(ExtRetireSalaryExample example);

    int deleteByExample(ExtRetireSalaryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtRetireSalary record);

    int insertSelective(ExtRetireSalary record);

    List<ExtRetireSalary> selectByExampleWithRowbounds(ExtRetireSalaryExample example, RowBounds rowBounds);

    List<ExtRetireSalary> selectByExample(ExtRetireSalaryExample example);

    ExtRetireSalary selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExtRetireSalary record, @Param("example") ExtRetireSalaryExample example);

    int updateByExample(@Param("record") ExtRetireSalary record, @Param("example") ExtRetireSalaryExample example);

    int updateByPrimaryKeySelective(ExtRetireSalary record);

    int updateByPrimaryKey(ExtRetireSalary record);
}