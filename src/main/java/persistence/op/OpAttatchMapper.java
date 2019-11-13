package persistence.op;

import domain.op.OpAttatch;
import domain.op.OpAttatchExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface OpAttatchMapper {
    long countByExample(OpAttatchExample example);

    int deleteByExample(OpAttatchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpAttatch record);

    int insertSelective(OpAttatch record);

    List<OpAttatch> selectByExampleWithRowbounds(OpAttatchExample example, RowBounds rowBounds);

    List<OpAttatch> selectByExample(OpAttatchExample example);

    OpAttatch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpAttatch record, @Param("example") OpAttatchExample example);

    int updateByExample(@Param("record") OpAttatch record, @Param("example") OpAttatchExample example);

    int updateByPrimaryKeySelective(OpAttatch record);

    int updateByPrimaryKey(OpAttatch record);
}