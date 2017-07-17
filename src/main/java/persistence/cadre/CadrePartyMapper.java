package persistence.cadre;

import domain.cadre.CadreParty;
import domain.cadre.CadrePartyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadrePartyMapper {
    long countByExample(CadrePartyExample example);

    int deleteByExample(CadrePartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreParty record);

    int insertSelective(CadreParty record);

    List<CadreParty> selectByExampleWithRowbounds(CadrePartyExample example, RowBounds rowBounds);

    List<CadreParty> selectByExample(CadrePartyExample example);

    CadreParty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreParty record, @Param("example") CadrePartyExample example);

    int updateByExample(@Param("record") CadreParty record, @Param("example") CadrePartyExample example);

    int updateByPrimaryKeySelective(CadreParty record);

    int updateByPrimaryKey(CadreParty record);
}