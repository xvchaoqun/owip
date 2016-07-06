package persistence.member;

import domain.member.MemberAbroad;
import domain.member.MemberAbroadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberAbroadMapper {
    int countByExample(MemberAbroadExample example);

    int deleteByExample(MemberAbroadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberAbroad record);

    int insertSelective(MemberAbroad record);

    List<MemberAbroad> selectByExampleWithRowbounds(MemberAbroadExample example, RowBounds rowBounds);

    List<MemberAbroad> selectByExample(MemberAbroadExample example);

    MemberAbroad selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberAbroad record, @Param("example") MemberAbroadExample example);

    int updateByExample(@Param("record") MemberAbroad record, @Param("example") MemberAbroadExample example);

    int updateByPrimaryKeySelective(MemberAbroad record);

    int updateByPrimaryKey(MemberAbroad record);
}