package persistence.ps;

import domain.ps.PsMember;
import domain.ps.PsMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PsMemberMapper {
    long countByExample(PsMemberExample example);

    int deleteByExample(PsMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PsMember record);

    int insertSelective(PsMember record);

    List<PsMember> selectByExampleWithRowbounds(PsMemberExample example, RowBounds rowBounds);

    List<PsMember> selectByExample(PsMemberExample example);

    PsMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PsMember record, @Param("example") PsMemberExample example);

    int updateByExample(@Param("record") PsMember record, @Param("example") PsMemberExample example);

    int updateByPrimaryKeySelective(PsMember record);

    int updateByPrimaryKey(PsMember record);
}