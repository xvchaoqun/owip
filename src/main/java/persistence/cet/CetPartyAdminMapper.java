package persistence.cet;

import domain.cet.CetPartyAdmin;
import domain.cet.CetPartyAdminExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CetPartyAdminMapper {
    long countByExample(CetPartyAdminExample example);

    int deleteByExample(CetPartyAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetPartyAdmin record);

    int insertSelective(CetPartyAdmin record);

    List<CetPartyAdmin> selectByExampleWithRowbounds(CetPartyAdminExample example, RowBounds rowBounds);

    List<CetPartyAdmin> selectByExample(CetPartyAdminExample example);

    CetPartyAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetPartyAdmin record, @Param("example") CetPartyAdminExample example);

    int updateByExample(@Param("record") CetPartyAdmin record, @Param("example") CetPartyAdminExample example);

    int updateByPrimaryKeySelective(CetPartyAdmin record);

    int updateByPrimaryKey(CetPartyAdmin record);
}