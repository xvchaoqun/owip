package persistence.pmd;

import domain.pmd.PmdPartyAdmin;
import domain.pmd.PmdPartyAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdPartyAdminMapper {
    long countByExample(PmdPartyAdminExample example);

    int deleteByExample(PmdPartyAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdPartyAdmin record);

    int insertSelective(PmdPartyAdmin record);

    List<PmdPartyAdmin> selectByExampleWithRowbounds(PmdPartyAdminExample example, RowBounds rowBounds);

    List<PmdPartyAdmin> selectByExample(PmdPartyAdminExample example);

    PmdPartyAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdPartyAdmin record, @Param("example") PmdPartyAdminExample example);

    int updateByExample(@Param("record") PmdPartyAdmin record, @Param("example") PmdPartyAdminExample example);

    int updateByPrimaryKeySelective(PmdPartyAdmin record);

    int updateByPrimaryKey(PmdPartyAdmin record);
}