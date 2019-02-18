package persistence.cm;

import domain.cm.CmMember;
import domain.cm.CmMemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CmMemberMapper {
    long countByExample(CmMemberExample example);

    int deleteByExample(CmMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CmMember record);

    int insertSelective(CmMember record);

    List<CmMember> selectByExampleWithRowbounds(CmMemberExample example, RowBounds rowBounds);

    List<CmMember> selectByExample(CmMemberExample example);

    CmMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CmMember record, @Param("example") CmMemberExample example);

    int updateByExample(@Param("record") CmMember record, @Param("example") CmMemberExample example);

    int updateByPrimaryKeySelective(CmMember record);

    int updateByPrimaryKey(CmMember record);
}