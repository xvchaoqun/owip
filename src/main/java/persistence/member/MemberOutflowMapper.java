package persistence.member;

import domain.member.MemberOutflow;
import domain.member.MemberOutflowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberOutflowMapper {
    long countByExample(MemberOutflowExample example);

    int deleteByExample(MemberOutflowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberOutflow record);

    int insertSelective(MemberOutflow record);

    List<MemberOutflow> selectByExampleWithRowbounds(MemberOutflowExample example, RowBounds rowBounds);

    List<MemberOutflow> selectByExample(MemberOutflowExample example);

    MemberOutflow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberOutflow record, @Param("example") MemberOutflowExample example);

    int updateByExample(@Param("record") MemberOutflow record, @Param("example") MemberOutflowExample example);

    int updateByPrimaryKeySelective(MemberOutflow record);

    int updateByPrimaryKey(MemberOutflow record);
}