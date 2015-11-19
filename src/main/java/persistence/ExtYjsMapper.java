package persistence;

import domain.ExtYjs;
import domain.ExtYjsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExtYjsMapper {
    int countByExample(ExtYjsExample example);

    int deleteByExample(ExtYjsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtYjs record);

    int insertSelective(ExtYjs record);

    List<ExtYjs> selectByExampleWithRowbounds(ExtYjsExample example, RowBounds rowBounds);

    List<ExtYjs> selectByExample(ExtYjsExample example);

    ExtYjs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExtYjs record, @Param("example") ExtYjsExample example);

    int updateByExample(@Param("record") ExtYjs record, @Param("example") ExtYjsExample example);

    int updateByPrimaryKeySelective(ExtYjs record);

    int updateByPrimaryKey(ExtYjs record);
}