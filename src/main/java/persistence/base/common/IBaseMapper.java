package persistence.base.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lm on 2018/4/24.
 */
public interface IBaseMapper {

    @Select("select user_id from base_short_msg_receiver where tpl_id=#{tplId} and status=#{status}")
    List<Integer> getShorMsgReceiverUserIds(@Param("tplId") int tplId, @Param("status") byte status);
}
