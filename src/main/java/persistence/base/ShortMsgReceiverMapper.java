package persistence.base;

import domain.base.ShortMsgReceiver;
import domain.base.ShortMsgReceiverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ShortMsgReceiverMapper {
    int countByExample(ShortMsgReceiverExample example);

    int deleteByExample(ShortMsgReceiverExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShortMsgReceiver record);

    int insertSelective(ShortMsgReceiver record);

    List<ShortMsgReceiver> selectByExampleWithRowbounds(ShortMsgReceiverExample example, RowBounds rowBounds);

    List<ShortMsgReceiver> selectByExample(ShortMsgReceiverExample example);

    ShortMsgReceiver selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShortMsgReceiver record, @Param("example") ShortMsgReceiverExample example);

    int updateByExample(@Param("record") ShortMsgReceiver record, @Param("example") ShortMsgReceiverExample example);

    int updateByPrimaryKeySelective(ShortMsgReceiver record);

    int updateByPrimaryKey(ShortMsgReceiver record);
}