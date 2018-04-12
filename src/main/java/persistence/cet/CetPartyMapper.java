package persistence.cet;

import domain.cet.CetParty;
import domain.cet.CetPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetPartyMapper {
    long countByExample(CetPartyExample example);

    int deleteByExample(CetPartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetParty record);

    int insertSelective(CetParty record);

    List<CetParty> selectByExampleWithRowbounds(CetPartyExample example, RowBounds rowBounds);

    List<CetParty> selectByExample(CetPartyExample example);

    CetParty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetParty record, @Param("example") CetPartyExample example);

    int updateByExample(@Param("record") CetParty record, @Param("example") CetPartyExample example);

    int updateByPrimaryKeySelective(CetParty record);

    int updateByPrimaryKey(CetParty record);
}