package persistence.pmd;

import domain.pmd.PmdNotifyWszfLog;
import domain.pmd.PmdNotifyWszfLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdNotifyWszfLogMapper {
    long countByExample(PmdNotifyWszfLogExample example);

    int deleteByExample(PmdNotifyWszfLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdNotifyWszfLog record);

    int insertSelective(PmdNotifyWszfLog record);

    List<PmdNotifyWszfLog> selectByExampleWithRowbounds(PmdNotifyWszfLogExample example, RowBounds rowBounds);

    List<PmdNotifyWszfLog> selectByExample(PmdNotifyWszfLogExample example);

    PmdNotifyWszfLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdNotifyWszfLog record, @Param("example") PmdNotifyWszfLogExample example);

    int updateByExample(@Param("record") PmdNotifyWszfLog record, @Param("example") PmdNotifyWszfLogExample example);

    int updateByPrimaryKeySelective(PmdNotifyWszfLog record);

    int updateByPrimaryKey(PmdNotifyWszfLog record);
}