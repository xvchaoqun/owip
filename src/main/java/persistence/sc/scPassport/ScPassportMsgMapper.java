package persistence.sc.scPassport;

import domain.sc.scPassport.ScPassportMsg;
import domain.sc.scPassport.ScPassportMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScPassportMsgMapper {
    long countByExample(ScPassportMsgExample example);

    int deleteByExample(ScPassportMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScPassportMsg record);

    int insertSelective(ScPassportMsg record);

    List<ScPassportMsg> selectByExampleWithRowbounds(ScPassportMsgExample example, RowBounds rowBounds);

    List<ScPassportMsg> selectByExample(ScPassportMsgExample example);

    ScPassportMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScPassportMsg record, @Param("example") ScPassportMsgExample example);

    int updateByExample(@Param("record") ScPassportMsg record, @Param("example") ScPassportMsgExample example);

    int updateByPrimaryKeySelective(ScPassportMsg record);

    int updateByPrimaryKey(ScPassportMsg record);
}