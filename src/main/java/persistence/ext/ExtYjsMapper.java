package persistence.ext;

import domain.ext.ExtYjs;
import domain.ext.ExtYjsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExtYjsMapper {
    long countByExample(ExtYjsExample example);

    int deleteByExample(ExtYjsExample example);

    int deleteByPrimaryKey(String xh);

    int insert(ExtYjs record);

    int insertSelective(ExtYjs record);

    List<ExtYjs> selectByExampleWithRowbounds(ExtYjsExample example, RowBounds rowBounds);

    List<ExtYjs> selectByExample(ExtYjsExample example);

    ExtYjs selectByPrimaryKey(String xh);

    int updateByExampleSelective(@Param("record") ExtYjs record, @Param("example") ExtYjsExample example);

    int updateByExample(@Param("record") ExtYjs record, @Param("example") ExtYjsExample example);

    int updateByPrimaryKeySelective(ExtYjs record);

    int updateByPrimaryKey(ExtYjs record);
}