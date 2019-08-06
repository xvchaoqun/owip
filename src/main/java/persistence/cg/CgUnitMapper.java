package persistence.cg;

import domain.cg.CgUnit;
import domain.cg.CgUnitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CgUnitMapper {
    long countByExample(CgUnitExample example);

    int deleteByExample(CgUnitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CgUnit record);

    int insertSelective(CgUnit record);

    List<CgUnit> selectByExampleWithRowbounds(CgUnitExample example, RowBounds rowBounds);

    List<CgUnit> selectByExample(CgUnitExample example);

    CgUnit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CgUnit record, @Param("example") CgUnitExample example);

    int updateByExample(@Param("record") CgUnit record, @Param("example") CgUnitExample example);

    int updateByPrimaryKeySelective(CgUnit record);

    int updateByPrimaryKey(CgUnit record);
}