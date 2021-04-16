package persistence.parttime;

import domain.parttime.ParttimeApplicatCadre;
import domain.parttime.ParttimeApplicatCadreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApplicatCadreMapper {
    long countByExample(ParttimeApplicatCadreExample example);

    int deleteByExample(ParttimeApplicatCadreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApplicatCadre record);

    int insertSelective(ParttimeApplicatCadre record);

    List<ParttimeApplicatCadre> selectByExampleWithRowbounds(ParttimeApplicatCadreExample example, RowBounds rowBounds);

    List<ParttimeApplicatCadre> selectByExample(ParttimeApplicatCadreExample example);

    ParttimeApplicatCadre selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApplicatCadre record, @Param("example") ParttimeApplicatCadreExample example);

    int updateByExample(@Param("record") ParttimeApplicatCadre record, @Param("example") ParttimeApplicatCadreExample example);

    int updateByPrimaryKeySelective(ParttimeApplicatCadre record);

    int updateByPrimaryKey(ParttimeApplicatCadre record);
}