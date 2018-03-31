package persistence.cet;

import domain.cet.CetSpecial;
import domain.cet.CetSpecialExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetSpecialMapper {
    long countByExample(CetSpecialExample example);

    int deleteByExample(CetSpecialExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetSpecial record);

    int insertSelective(CetSpecial record);

    List<CetSpecial> selectByExampleWithRowbounds(CetSpecialExample example, RowBounds rowBounds);

    List<CetSpecial> selectByExample(CetSpecialExample example);

    CetSpecial selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetSpecial record, @Param("example") CetSpecialExample example);

    int updateByExample(@Param("record") CetSpecial record, @Param("example") CetSpecialExample example);

    int updateByPrimaryKeySelective(CetSpecial record);

    int updateByPrimaryKey(CetSpecial record);
}