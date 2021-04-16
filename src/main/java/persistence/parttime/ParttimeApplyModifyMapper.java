package persistence.parttime;

import domain.parttime.ParttimeApplyModify;
import domain.parttime.ParttimeApplyModifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApplyModifyMapper {
    long countByExample(ParttimeApplyModifyExample example);

    int deleteByExample(ParttimeApplyModifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApplyModify record);

    int insertSelective(ParttimeApplyModify record);

    List<ParttimeApplyModify> selectByExampleWithRowbounds(ParttimeApplyModifyExample example, RowBounds rowBounds);

    List<ParttimeApplyModify> selectByExample(ParttimeApplyModifyExample example);

    ParttimeApplyModify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApplyModify record, @Param("example") ParttimeApplyModifyExample example);

    int updateByExample(@Param("record") ParttimeApplyModify record, @Param("example") ParttimeApplyModifyExample example);

    int updateByPrimaryKeySelective(ParttimeApplyModify record);

    int updateByPrimaryKey(ParttimeApplyModify record);
}