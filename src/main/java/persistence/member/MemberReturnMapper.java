package persistence.member;

import domain.member.MemberReturn;
import domain.member.MemberReturnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberReturnMapper {
    int countByExample(MemberReturnExample example);

    int deleteByExample(MemberReturnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberReturn record);

    int insertSelective(MemberReturn record);

    List<MemberReturn> selectByExampleWithRowbounds(MemberReturnExample example, RowBounds rowBounds);

    List<MemberReturn> selectByExample(MemberReturnExample example);

    MemberReturn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberReturn record, @Param("example") MemberReturnExample example);

    int updateByExample(@Param("record") MemberReturn record, @Param("example") MemberReturnExample example);

    int updateByPrimaryKeySelective(MemberReturn record);

    int updateByPrimaryKey(MemberReturn record);
}