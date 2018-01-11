package persistence.base;

import domain.base.OneSend;
import domain.base.OneSendExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface OneSendMapper {
    long countByExample(OneSendExample example);

    int deleteByExample(OneSendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OneSend record);

    int insertSelective(OneSend record);

    List<OneSend> selectByExampleWithRowbounds(OneSendExample example, RowBounds rowBounds);

    List<OneSend> selectByExample(OneSendExample example);

    OneSend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OneSend record, @Param("example") OneSendExample example);

    int updateByExample(@Param("record") OneSend record, @Param("example") OneSendExample example);

    int updateByPrimaryKeySelective(OneSend record);

    int updateByPrimaryKey(OneSend record);
}