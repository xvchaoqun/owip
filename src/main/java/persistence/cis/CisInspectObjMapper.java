package persistence.cis;

import domain.cis.CisInspectObj;
import domain.cis.CisInspectObjExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CisInspectObjMapper {
    long countByExample(CisInspectObjExample example);

    int deleteByExample(CisInspectObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CisInspectObj record);

    int insertSelective(CisInspectObj record);

    List<CisInspectObj> selectByExampleWithRowbounds(CisInspectObjExample example, RowBounds rowBounds);

    List<CisInspectObj> selectByExample(CisInspectObjExample example);

    CisInspectObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CisInspectObj record, @Param("example") CisInspectObjExample example);

    int updateByExample(@Param("record") CisInspectObj record, @Param("example") CisInspectObjExample example);

    int updateByPrimaryKeySelective(CisInspectObj record);

    int updateByPrimaryKey(CisInspectObj record);
}