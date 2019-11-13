package persistence.op;

import domain.op.OpRecord;
import domain.op.OpRecordExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface OpRecordMapper {
    long countByExample(OpRecordExample example);

    int deleteByExample(OpRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpRecord record);

    int insertSelective(OpRecord record);

    List<OpRecord> selectByExampleWithRowbounds(OpRecordExample example, RowBounds rowBounds);

    List<OpRecord> selectByExample(OpRecordExample example);

    OpRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpRecord record, @Param("example") OpRecordExample example);

    int updateByExample(@Param("record") OpRecord record, @Param("example") OpRecordExample example);

    int updateByPrimaryKeySelective(OpRecord record);

    int updateByPrimaryKey(OpRecord record);
}