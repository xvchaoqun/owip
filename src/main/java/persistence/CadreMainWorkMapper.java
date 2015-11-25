package persistence;

import domain.CadreMainWork;
import domain.CadreMainWorkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreMainWorkMapper {
    int countByExample(CadreMainWorkExample example);

    int deleteByExample(CadreMainWorkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreMainWork record);

    int insertSelective(CadreMainWork record);

    List<CadreMainWork> selectByExampleWithRowbounds(CadreMainWorkExample example, RowBounds rowBounds);

    List<CadreMainWork> selectByExample(CadreMainWorkExample example);

    CadreMainWork selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreMainWork record, @Param("example") CadreMainWorkExample example);

    int updateByExample(@Param("record") CadreMainWork record, @Param("example") CadreMainWorkExample example);

    int updateByPrimaryKeySelective(CadreMainWork record);

    int updateByPrimaryKey(CadreMainWork record);
}