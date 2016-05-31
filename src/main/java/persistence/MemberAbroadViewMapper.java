package persistence;

import domain.MemberAbroadView;
import domain.MemberAbroadViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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