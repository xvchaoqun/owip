package persistence.ext;

import domain.ext.ExtJzgSalary;
import domain.ext.ExtJzgSalaryExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ExtJzgSalaryMapper {
    long countByExample(ExtJzgSalaryExample example);

    int deleteByExample(ExtJzgSalaryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtJzgSalary record);

    int insertSelective(ExtJzgSalary record);

    List<ExtJzgSalary> selectByExampleWithRowbounds(ExtJzgSalaryExample example, RowBounds rowBounds);

    List<ExtJzgSalary> selectByExample(ExtJzgSalaryExample example);

    ExtJzgSalary selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExtJzgSalary record, @Param("example") ExtJzgSalaryExample example);

    int updateByExample(@Param("record") ExtJzgSalary record, @Param("example") ExtJzgSalaryExample example);

    int updateByPrimaryKeySelective(ExtJzgSalary record);

    int updateByPrimaryKey(ExtJzgSalary record);
}