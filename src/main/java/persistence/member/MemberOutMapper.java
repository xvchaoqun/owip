package persistence.member;

import domain.member.MemberOut;
import domain.member.MemberOutExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberOutMapper {
    int countByExample(MemberOutExample example);

    int deleteByExample(MemberOutExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberOut record);

    int insertSelective(MemberOut record);

    List<MemberOut> selectByExampleWithRowbounds(MemberOutExample example, RowBounds rowBounds);

    List<MemberOut> selectByExample(MemberOutExample example);

    MemberOut selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberOut record, @Param("example") MemberOutExample example);

    int updateByExample(@Param("record") MemberOut record, @Param("example") MemberOutExample example);

    int updateByPrimaryKeySelective(MemberOut record);

    int updateByPrimaryKey(MemberOut record);
}