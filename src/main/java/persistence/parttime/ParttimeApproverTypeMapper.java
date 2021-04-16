package persistence.parttime;

import domain.parttime.ParttimeApproverType;
import domain.parttime.ParttimeApproverTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApproverTypeMapper {
    long countByExample(ParttimeApproverTypeExample example);

    int deleteByExample(ParttimeApproverTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApproverType record);

    int insertSelective(ParttimeApproverType record);

    List<ParttimeApproverType> selectByExampleWithRowbounds(ParttimeApproverTypeExample example, RowBounds rowBounds);

    List<ParttimeApproverType> selectByExample(ParttimeApproverTypeExample example);

    ParttimeApproverType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApproverType record, @Param("example") ParttimeApproverTypeExample example);

    int updateByExample(@Param("record") ParttimeApproverType record, @Param("example") ParttimeApproverTypeExample example);

    int updateByPrimaryKeySelective(ParttimeApproverType record);

    int updateByPrimaryKey(ParttimeApproverType record);
}