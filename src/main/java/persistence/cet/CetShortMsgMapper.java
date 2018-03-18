package persistence.cet;

import domain.cet.CetShortMsg;
import domain.cet.CetShortMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetShortMsgMapper {
    long countByExample(CetShortMsgExample example);

    int deleteByExample(CetShortMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetShortMsg record);

    int insertSelective(CetShortMsg record);

    List<CetShortMsg> selectByExampleWithRowbounds(CetShortMsgExample example, RowBounds rowBounds);

    List<CetShortMsg> selectByExample(CetShortMsgExample example);

    CetShortMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetShortMsg record, @Param("example") CetShortMsgExample example);

    int updateByExample(@Param("record") CetShortMsg record, @Param("example") CetShortMsgExample example);

    int updateByPrimaryKeySelective(CetShortMsg record);

    int updateByPrimaryKey(CetShortMsg record);
}