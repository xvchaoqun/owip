package persistence.abroad;

import domain.abroad.TaiwanRecord;
import domain.abroad.TaiwanRecordExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TaiwanRecordMapper {
    long countByExample(TaiwanRecordExample example);

    int deleteByExample(TaiwanRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TaiwanRecord record);

    int insertSelective(TaiwanRecord record);

    List<TaiwanRecord> selectByExampleWithRowbounds(TaiwanRecordExample example, RowBounds rowBounds);

    List<TaiwanRecord> selectByExample(TaiwanRecordExample example);

    TaiwanRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TaiwanRecord record, @Param("example") TaiwanRecordExample example);

    int updateByExample(@Param("record") TaiwanRecord record, @Param("example") TaiwanRecordExample example);

    int updateByPrimaryKeySelective(TaiwanRecord record);

    int updateByPrimaryKey(TaiwanRecord record);
}