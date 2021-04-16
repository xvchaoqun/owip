package persistence.parttime;

import domain.parttime.ParttimeApprover;
import domain.parttime.ParttimeApproverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApproverMapper {
    long countByExample(ParttimeApproverExample example);

    int deleteByExample(ParttimeApproverExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApprover record);

    int insertSelective(ParttimeApprover record);

    List<ParttimeApprover> selectByExampleWithRowbounds(ParttimeApproverExample example, RowBounds rowBounds);

    List<ParttimeApprover> selectByExample(ParttimeApproverExample example);

    ParttimeApprover selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApprover record, @Param("example") ParttimeApproverExample example);

    int updateByExample(@Param("record") ParttimeApprover record, @Param("example") ParttimeApproverExample example);

    int updateByPrimaryKeySelective(ParttimeApprover record);

    int updateByPrimaryKey(ParttimeApprover record);
}