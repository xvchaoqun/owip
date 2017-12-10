package persistence.pmd;

import domain.pmd.PmdNotifyCampusCardLog;
import domain.pmd.PmdNotifyCampusCardLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdNotifyCampusCardLogMapper {
    long countByExample(PmdNotifyCampusCardLogExample example);

    int deleteByExample(PmdNotifyCampusCardLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdNotifyCampusCardLog record);

    int insertSelective(PmdNotifyCampusCardLog record);

    List<PmdNotifyCampusCardLog> selectByExampleWithRowbounds(PmdNotifyCampusCardLogExample example, RowBounds rowBounds);

    List<PmdNotifyCampusCardLog> selectByExample(PmdNotifyCampusCardLogExample example);

    PmdNotifyCampusCardLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdNotifyCampusCardLog record, @Param("example") PmdNotifyCampusCardLogExample example);

    int updateByExample(@Param("record") PmdNotifyCampusCardLog record, @Param("example") PmdNotifyCampusCardLogExample example);

    int updateByPrimaryKeySelective(PmdNotifyCampusCardLog record);

    int updateByPrimaryKey(PmdNotifyCampusCardLog record);
}