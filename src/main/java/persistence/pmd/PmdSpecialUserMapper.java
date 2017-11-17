package persistence.pmd;

import domain.pmd.PmdSpecialUser;
import domain.pmd.PmdSpecialUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdSpecialUserMapper {
    long countByExample(PmdSpecialUserExample example);

    int deleteByExample(PmdSpecialUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdSpecialUser record);

    int insertSelective(PmdSpecialUser record);

    List<PmdSpecialUser> selectByExampleWithRowbounds(PmdSpecialUserExample example, RowBounds rowBounds);

    List<PmdSpecialUser> selectByExample(PmdSpecialUserExample example);

    PmdSpecialUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdSpecialUser record, @Param("example") PmdSpecialUserExample example);

    int updateByExample(@Param("record") PmdSpecialUser record, @Param("example") PmdSpecialUserExample example);

    int updateByPrimaryKeySelective(PmdSpecialUser record);

    int updateByPrimaryKey(PmdSpecialUser record);
}