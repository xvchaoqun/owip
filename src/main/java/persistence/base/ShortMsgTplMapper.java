package persistence.base;

import domain.base.ShortMsgTpl;
import domain.base.ShortMsgTplExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ShortMsgTplMapper {
    int countByExample(ShortMsgTplExample example);

    int deleteByExample(ShortMsgTplExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShortMsgTpl record);

    int insertSelective(ShortMsgTpl record);

    List<ShortMsgTpl> selectByExampleWithRowbounds(ShortMsgTplExample example, RowBounds rowBounds);

    List<ShortMsgTpl> selectByExample(ShortMsgTplExample example);

    ShortMsgTpl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShortMsgTpl record, @Param("example") ShortMsgTplExample example);

    int updateByExample(@Param("record") ShortMsgTpl record, @Param("example") ShortMsgTplExample example);

    int updateByPrimaryKeySelective(ShortMsgTpl record);

    int updateByPrimaryKey(ShortMsgTpl record);
}