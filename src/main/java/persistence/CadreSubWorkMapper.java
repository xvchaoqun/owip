package persistence;

import domain.CadreSubWork;
import domain.CadreSubWorkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreSubWorkMapper {
    int countByExample(CadreSubWorkExample example);

    int deleteByExample(CadreSubWorkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreSubWork record);

    int insertSelective(CadreSubWork record);

    List<CadreSubWork> selectByExampleWithRowbounds(CadreSubWorkExample example, RowBounds rowBounds);

    List<CadreSubWork> selectByExample(CadreSubWorkExample example);

    CadreSubWork selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreSubWork record, @Param("example") CadreSubWorkExample example);

    int updateByExample(@Param("record") CadreSubWork record, @Param("example") CadreSubWorkExample example);

    int updateByPrimaryKeySelective(CadreSubWork record);

    int updateByPrimaryKey(CadreSubWork record);
}