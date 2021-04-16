package persistence.parttime;

import domain.parttime.ParttimeApplicatType;
import domain.parttime.ParttimeApplicatTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApplicatTypeMapper {
    long countByExample(ParttimeApplicatTypeExample example);

    int deleteByExample(ParttimeApplicatTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApplicatType record);

    int insertSelective(ParttimeApplicatType record);

    List<ParttimeApplicatType> selectByExampleWithRowbounds(ParttimeApplicatTypeExample example, RowBounds rowBounds);

    List<ParttimeApplicatType> selectByExample(ParttimeApplicatTypeExample example);

    ParttimeApplicatType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApplicatType record, @Param("example") ParttimeApplicatTypeExample example);

    int updateByExample(@Param("record") ParttimeApplicatType record, @Param("example") ParttimeApplicatTypeExample example);

    int updateByPrimaryKeySelective(ParttimeApplicatType record);

    int updateByPrimaryKey(ParttimeApplicatType record);
}