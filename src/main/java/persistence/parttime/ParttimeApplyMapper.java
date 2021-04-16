package persistence.parttime;

import domain.parttime.ParttimeApply;
import domain.parttime.ParttimeApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApplyMapper {
    long countByExample(ParttimeApplyExample example);

    int deleteByExample(ParttimeApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApply record);

    int insertSelective(ParttimeApply record);

    List<ParttimeApply> selectByExampleWithRowbounds(ParttimeApplyExample example, RowBounds rowBounds);

    List<ParttimeApply> selectByExample(ParttimeApplyExample example);

    ParttimeApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApply record, @Param("example") ParttimeApplyExample example);

    int updateByExample(@Param("record") ParttimeApply record, @Param("example") ParttimeApplyExample example);

    int updateByPrimaryKeySelective(ParttimeApply record);

    int updateByPrimaryKey(ParttimeApply record);
}