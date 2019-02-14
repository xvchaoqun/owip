package persistence.dr;

import domain.dr.DrMember;
import domain.dr.DrMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrMemberMapper {
    long countByExample(DrMemberExample example);

    int deleteByExample(DrMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrMember record);

    int insertSelective(DrMember record);

    List<DrMember> selectByExampleWithRowbounds(DrMemberExample example, RowBounds rowBounds);

    List<DrMember> selectByExample(DrMemberExample example);

    DrMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrMember record, @Param("example") DrMemberExample example);

    int updateByExample(@Param("record") DrMember record, @Param("example") DrMemberExample example);

    int updateByPrimaryKeySelective(DrMember record);

    int updateByPrimaryKey(DrMember record);
}