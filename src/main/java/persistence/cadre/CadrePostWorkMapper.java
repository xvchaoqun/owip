package persistence.cadre;

import domain.cadre.CadrePostWork;
import domain.cadre.CadrePostWorkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadrePostWorkMapper {
    long countByExample(CadrePostWorkExample example);

    int deleteByExample(CadrePostWorkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadrePostWork record);

    int insertSelective(CadrePostWork record);

    List<CadrePostWork> selectByExampleWithRowbounds(CadrePostWorkExample example, RowBounds rowBounds);

    List<CadrePostWork> selectByExample(CadrePostWorkExample example);

    CadrePostWork selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadrePostWork record, @Param("example") CadrePostWorkExample example);

    int updateByExample(@Param("record") CadrePostWork record, @Param("example") CadrePostWorkExample example);

    int updateByPrimaryKeySelective(CadrePostWork record);

    int updateByPrimaryKey(CadrePostWork record);
}