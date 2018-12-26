package persistence.member;

import domain.member.MemberReg;
import domain.member.MemberRegExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberRegMapper {
    long countByExample(MemberRegExample example);

    int deleteByExample(MemberRegExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberReg record);

    int insertSelective(MemberReg record);

    List<MemberReg> selectByExampleWithRowbounds(MemberRegExample example, RowBounds rowBounds);

    List<MemberReg> selectByExample(MemberRegExample example);

    MemberReg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberReg record, @Param("example") MemberRegExample example);

    int updateByExample(@Param("record") MemberReg record, @Param("example") MemberRegExample example);

    int updateByPrimaryKeySelective(MemberReg record);

    int updateByPrimaryKey(MemberReg record);
}