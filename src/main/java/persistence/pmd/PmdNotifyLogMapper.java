package persistence.pmd;

import domain.pmd.PmdNotifyLog;
import domain.pmd.PmdNotifyLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdNotifyLogMapper {
    long countByExample(PmdNotifyLogExample example);

    int deleteByExample(PmdNotifyLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdNotifyLog record);

    int insertSelective(PmdNotifyLog record);

    List<PmdNotifyLog> selectByExampleWithRowbounds(PmdNotifyLogExample example, RowBounds rowBounds);

    List<PmdNotifyLog> selectByExample(PmdNotifyLogExample example);

    PmdNotifyLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdNotifyLog record, @Param("example") PmdNotifyLogExample example);

    int updateByExample(@Param("record") PmdNotifyLog record, @Param("example") PmdNotifyLogExample example);

    int updateByPrimaryKeySelective(PmdNotifyLog record);

    int updateByPrimaryKey(PmdNotifyLog record);
}