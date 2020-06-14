package persistence.cet;

import domain.cet.CetRecord;
import domain.cet.CetRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetRecordMapper {
    long countByExample(CetRecordExample example);

    int deleteByExample(CetRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetRecord record);

    int insertSelective(CetRecord record);

    List<CetRecord> selectByExampleWithRowbounds(CetRecordExample example, RowBounds rowBounds);

    List<CetRecord> selectByExample(CetRecordExample example);

    CetRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetRecord record, @Param("example") CetRecordExample example);

    int updateByExample(@Param("record") CetRecord record, @Param("example") CetRecordExample example);

    int updateByPrimaryKeySelective(CetRecord record);

    int updateByPrimaryKey(CetRecord record);
}