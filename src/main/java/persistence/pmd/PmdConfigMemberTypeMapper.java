package persistence.pmd;

import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdConfigMemberTypeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdConfigMemberTypeMapper {
    long countByExample(PmdConfigMemberTypeExample example);

    int deleteByExample(PmdConfigMemberTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdConfigMemberType record);

    int insertSelective(PmdConfigMemberType record);

    List<PmdConfigMemberType> selectByExampleWithRowbounds(PmdConfigMemberTypeExample example, RowBounds rowBounds);

    List<PmdConfigMemberType> selectByExample(PmdConfigMemberTypeExample example);

    PmdConfigMemberType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdConfigMemberType record, @Param("example") PmdConfigMemberTypeExample example);

    int updateByExample(@Param("record") PmdConfigMemberType record, @Param("example") PmdConfigMemberTypeExample example);

    int updateByPrimaryKeySelective(PmdConfigMemberType record);

    int updateByPrimaryKey(PmdConfigMemberType record);
}