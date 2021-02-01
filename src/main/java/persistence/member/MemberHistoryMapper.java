package persistence.member;


import domain.member.MemberHistory;
import domain.member.MemberHistoryExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberHistoryMapper {
    long countByExample(MemberHistoryExample example);

    int deleteByExample(MemberHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberHistory record);

    int insertSelective(MemberHistory record);

    List<MemberHistory> selectByExampleWithRowbounds(MemberHistoryExample example, RowBounds rowBounds);

    List<MemberHistory> selectByExample(MemberHistoryExample example);

    MemberHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberHistory record, @Param("example") MemberHistoryExample example);

    int updateByExample(@Param("record") MemberHistory record, @Param("example") MemberHistoryExample example);

    int updateByPrimaryKeySelective(MemberHistory record);

    int updateByPrimaryKey(MemberHistory record);
}