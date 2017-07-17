package persistence.cis;

import domain.cis.CisObjUnit;
import domain.cis.CisObjUnitExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CisObjUnitMapper {
    int countByExample(CisObjUnitExample example);

    int deleteByExample(CisObjUnitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CisObjUnit record);

    int insertSelective(CisObjUnit record);

    List<CisObjUnit> selectByExampleWithRowbounds(CisObjUnitExample example, RowBounds rowBounds);

    List<CisObjUnit> selectByExample(CisObjUnitExample example);

    CisObjUnit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CisObjUnit record, @Param("example") CisObjUnitExample example);

    int updateByExample(@Param("record") CisObjUnit record, @Param("example") CisObjUnitExample example);

    int updateByPrimaryKeySelective(CisObjUnit record);

    int updateByPrimaryKey(CisObjUnit record);
}