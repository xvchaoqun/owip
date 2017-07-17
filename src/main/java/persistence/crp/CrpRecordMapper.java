package persistence.crp;

import domain.crp.CrpRecord;
import domain.crp.CrpRecordExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrpRecordMapper {
    long countByExample(CrpRecordExample example);

    int deleteByExample(CrpRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrpRecord record);

    int insertSelective(CrpRecord record);

    List<CrpRecord> selectByExampleWithRowbounds(CrpRecordExample example, RowBounds rowBounds);

    List<CrpRecord> selectByExample(CrpRecordExample example);

    CrpRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrpRecord record, @Param("example") CrpRecordExample example);

    int updateByExample(@Param("record") CrpRecord record, @Param("example") CrpRecordExample example);

    int updateByPrimaryKeySelective(CrpRecord record);

    int updateByPrimaryKey(CrpRecord record);
}