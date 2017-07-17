package persistence.member;

import domain.member.MemberAbroadView;
import domain.member.MemberAbroadViewExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberAbroadViewMapper {
    int countByExample(MemberAbroadViewExample example);

    int deleteByExample(MemberAbroadViewExample example);

    int insert(MemberAbroadView record);

    int insertSelective(MemberAbroadView record);

    List<MemberAbroadView> selectByExampleWithRowbounds(MemberAbroadViewExample example, RowBounds rowBounds);

    List<MemberAbroadView> selectByExample(MemberAbroadViewExample example);

    int updateByExampleSelective(@Param("record") MemberAbroadView record, @Param("example") MemberAbroadViewExample example);

    int updateByExample(@Param("record") MemberAbroadView record, @Param("example") MemberAbroadViewExample example);
}