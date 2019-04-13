package persistence.base;

import domain.base.ShortMsg;
import domain.base.ShortMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ShortMsgMapper {
    long countByExample(ShortMsgExample example);

    int deleteByExample(ShortMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShortMsg record);

    int insertSelective(ShortMsg record);

    List<ShortMsg> selectByExampleWithRowbounds(ShortMsgExample example, RowBounds rowBounds);

    List<ShortMsg> selectByExample(ShortMsgExample example);

    ShortMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShortMsg record, @Param("example") ShortMsgExample example);

    int updateByExample(@Param("record") ShortMsg record, @Param("example") ShortMsgExample example);

    int updateByPrimaryKeySelective(ShortMsg record);

    int updateByPrimaryKey(ShortMsg record);
}