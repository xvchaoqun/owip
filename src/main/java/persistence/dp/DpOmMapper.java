package persistence.dp;

import domain.dp.DpOm;
import domain.dp.DpOmExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DpOmMapper {
    long countByExample(DpOmExample example);

    int deleteByExample(DpOmExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpOm record);

    int insertSelective(DpOm record);

    List<DpOm> selectByExampleWithRowbounds(DpOmExample example, RowBounds rowBounds);

    List<DpOm> selectByExample(DpOmExample example);

    DpOm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpOm record, @Param("example") DpOmExample example);

    int updateByExample(@Param("record") DpOm record, @Param("example") DpOmExample example);

    int updateByPrimaryKeySelective(DpOm record);

    int updateByPrimaryKey(DpOm record);
}