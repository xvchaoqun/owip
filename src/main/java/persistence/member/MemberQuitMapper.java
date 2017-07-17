package persistence.member;

import domain.member.MemberQuit;
import domain.member.MemberQuitExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberQuitMapper {
    int countByExample(MemberQuitExample example);

    int deleteByExample(MemberQuitExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(MemberQuit record);

    int insertSelective(MemberQuit record);

    List<MemberQuit> selectByExampleWithRowbounds(MemberQuitExample example, RowBounds rowBounds);

    List<MemberQuit> selectByExample(MemberQuitExample example);

    MemberQuit selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") MemberQuit record, @Param("example") MemberQuitExample example);

    int updateByExample(@Param("record") MemberQuit record, @Param("example") MemberQuitExample example);

    int updateByPrimaryKeySelective(MemberQuit record);

    int updateByPrimaryKey(MemberQuit record);
}