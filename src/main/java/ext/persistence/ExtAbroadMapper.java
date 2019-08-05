package ext.persistence;

import ext.domain.ExtAbroad;
import ext.domain.ExtAbroadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExtAbroadMapper {
    long countByExample(ExtAbroadExample example);

    int deleteByExample(ExtAbroadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtAbroad record);

    int insertSelective(ExtAbroad record);

    List<ExtAbroad> selectByExampleWithRowbounds(ExtAbroadExample example, RowBounds rowBounds);

    List<ExtAbroad> selectByExample(ExtAbroadExample example);

    ExtAbroad selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExtAbroad record, @Param("example") ExtAbroadExample example);

    int updateByExample(@Param("record") ExtAbroad record, @Param("example") ExtAbroadExample example);

    int updateByPrimaryKeySelective(ExtAbroad record);

    int updateByPrimaryKey(ExtAbroad record);
}