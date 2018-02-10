package persistence.pmd;

import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdMemberMapper {
    long countByExample(PmdMemberExample example);

    int deleteByExample(PmdMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdMember record);

    int insertSelective(PmdMember record);

    List<PmdMember> selectByExampleWithRowbounds(PmdMemberExample example, RowBounds rowBounds);

    List<PmdMember> selectByExample(PmdMemberExample example);

    PmdMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdMember record, @Param("example") PmdMemberExample example);

    int updateByExample(@Param("record") PmdMember record, @Param("example") PmdMemberExample example);

    int updateByPrimaryKeySelective(PmdMember record);

    int updateByPrimaryKey(PmdMember record);
}