package persistence.cg;

import domain.cg.CgMember;
import domain.cg.CgMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CgMemberMapper {
    long countByExample(CgMemberExample example);

    int deleteByExample(CgMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CgMember record);

    int insertSelective(CgMember record);

    List<CgMember> selectByExampleWithRowbounds(CgMemberExample example, RowBounds rowBounds);

    List<CgMember> selectByExample(CgMemberExample example);

    CgMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CgMember record, @Param("example") CgMemberExample example);

    int updateByExample(@Param("record") CgMember record, @Param("example") CgMemberExample example);

    int updateByPrimaryKeySelective(CgMember record);

    int updateByPrimaryKey(CgMember record);
}