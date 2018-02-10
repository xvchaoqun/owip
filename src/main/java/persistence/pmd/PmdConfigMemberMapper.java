package persistence.pmd;

import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdConfigMemberMapper {
    long countByExample(PmdConfigMemberExample example);

    int deleteByExample(PmdConfigMemberExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(PmdConfigMember record);

    int insertSelective(PmdConfigMember record);

    List<PmdConfigMember> selectByExampleWithRowbounds(PmdConfigMemberExample example, RowBounds rowBounds);

    List<PmdConfigMember> selectByExample(PmdConfigMemberExample example);

    PmdConfigMember selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") PmdConfigMember record, @Param("example") PmdConfigMemberExample example);

    int updateByExample(@Param("record") PmdConfigMember record, @Param("example") PmdConfigMemberExample example);

    int updateByPrimaryKeySelective(PmdConfigMember record);

    int updateByPrimaryKey(PmdConfigMember record);
}