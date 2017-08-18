package persistence.crs;

import domain.crs.CrsShortMsg;
import domain.crs.CrsShortMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrsShortMsgMapper {
    long countByExample(CrsShortMsgExample example);

    int deleteByExample(CrsShortMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsShortMsg record);

    int insertSelective(CrsShortMsg record);

    List<CrsShortMsg> selectByExampleWithRowbounds(CrsShortMsgExample example, RowBounds rowBounds);

    List<CrsShortMsg> selectByExample(CrsShortMsgExample example);

    CrsShortMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsShortMsg record, @Param("example") CrsShortMsgExample example);

    int updateByExample(@Param("record") CrsShortMsg record, @Param("example") CrsShortMsgExample example);

    int updateByPrimaryKeySelective(CrsShortMsg record);

    int updateByPrimaryKey(CrsShortMsg record);
}