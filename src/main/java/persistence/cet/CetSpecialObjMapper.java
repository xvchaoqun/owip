package persistence.cet;

import domain.cet.CetSpecialObj;
import domain.cet.CetSpecialObjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetSpecialObjMapper {
    long countByExample(CetSpecialObjExample example);

    int deleteByExample(CetSpecialObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetSpecialObj record);

    int insertSelective(CetSpecialObj record);

    List<CetSpecialObj> selectByExampleWithRowbounds(CetSpecialObjExample example, RowBounds rowBounds);

    List<CetSpecialObj> selectByExample(CetSpecialObjExample example);

    CetSpecialObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetSpecialObj record, @Param("example") CetSpecialObjExample example);

    int updateByExample(@Param("record") CetSpecialObj record, @Param("example") CetSpecialObjExample example);

    int updateByPrimaryKeySelective(CetSpecialObj record);

    int updateByPrimaryKey(CetSpecialObj record);
}