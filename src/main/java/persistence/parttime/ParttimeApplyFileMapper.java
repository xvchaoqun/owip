package persistence.parttime;

import domain.parttime.ParttimeApplyFile;
import domain.parttime.ParttimeApplyFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApplyFileMapper {
    long countByExample(ParttimeApplyFileExample example);

    int deleteByExample(ParttimeApplyFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApplyFile record);

    int insertSelective(ParttimeApplyFile record);

    List<ParttimeApplyFile> selectByExampleWithRowbounds(ParttimeApplyFileExample example, RowBounds rowBounds);

    List<ParttimeApplyFile> selectByExample(ParttimeApplyFileExample example);

    ParttimeApplyFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApplyFile record, @Param("example") ParttimeApplyFileExample example);

    int updateByExample(@Param("record") ParttimeApplyFile record, @Param("example") ParttimeApplyFileExample example);

    int updateByPrimaryKeySelective(ParttimeApplyFile record);

    int updateByPrimaryKey(ParttimeApplyFile record);
}