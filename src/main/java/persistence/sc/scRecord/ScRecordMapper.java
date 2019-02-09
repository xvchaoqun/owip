package persistence.sc.scRecord;

import domain.sc.scRecord.ScRecord;
import domain.sc.scRecord.ScRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScRecordMapper {
    long countByExample(ScRecordExample example);

    int deleteByExample(ScRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScRecord record);

    int insertSelective(ScRecord record);

    List<ScRecord> selectByExampleWithRowbounds(ScRecordExample example, RowBounds rowBounds);

    List<ScRecord> selectByExample(ScRecordExample example);

    ScRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScRecord record, @Param("example") ScRecordExample example);

    int updateByExample(@Param("record") ScRecord record, @Param("example") ScRecordExample example);

    int updateByPrimaryKeySelective(ScRecord record);

    int updateByPrimaryKey(ScRecord record);
}