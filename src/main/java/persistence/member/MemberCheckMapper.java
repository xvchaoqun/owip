package persistence.member;

import domain.member.MemberCheck;
import domain.member.MemberCheckExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberCheckMapper {
    long countByExample(MemberCheckExample example);

    int deleteByExample(MemberCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberCheck record);

    int insertSelective(MemberCheck record);

    List<MemberCheck> selectByExampleWithRowbounds(MemberCheckExample example, RowBounds rowBounds);

    List<MemberCheck> selectByExample(MemberCheckExample example);

    MemberCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberCheck record, @Param("example") MemberCheckExample example);

    int updateByExample(@Param("record") MemberCheck record, @Param("example") MemberCheckExample example);

    int updateByPrimaryKeySelective(MemberCheck record);

    int updateByPrimaryKey(MemberCheck record);
}