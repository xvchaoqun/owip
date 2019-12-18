package persistence.dp;

import domain.dp.DpWork;
import domain.dp.DpWorkExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DpWorkMapper {
    long countByExample(DpWorkExample example);

    int deleteByExample(DpWorkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpWork record);

    int insertSelective(DpWork record);

    List<DpWork> selectByExampleWithRowbounds(DpWorkExample example, RowBounds rowBounds);

    List<DpWork> selectByExample(DpWorkExample example);

    DpWork selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpWork record, @Param("example") DpWorkExample example);

    int updateByExample(@Param("record") DpWork record, @Param("example") DpWorkExample example);

    int updateByPrimaryKeySelective(DpWork record);

    int updateByPrimaryKey(DpWork record);
}