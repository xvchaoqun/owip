package persistence.pmd;

import domain.pmd.PmdNotifyCampuscard;
import domain.pmd.PmdNotifyCampuscardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdNotifyCampuscardMapper {
    long countByExample(PmdNotifyCampuscardExample example);

    int deleteByExample(PmdNotifyCampuscardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdNotifyCampuscard record);

    int insertSelective(PmdNotifyCampuscard record);

    List<PmdNotifyCampuscard> selectByExampleWithRowbounds(PmdNotifyCampuscardExample example, RowBounds rowBounds);

    List<PmdNotifyCampuscard> selectByExample(PmdNotifyCampuscardExample example);

    PmdNotifyCampuscard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdNotifyCampuscard record, @Param("example") PmdNotifyCampuscardExample example);

    int updateByExample(@Param("record") PmdNotifyCampuscard record, @Param("example") PmdNotifyCampuscardExample example);

    int updateByPrimaryKeySelective(PmdNotifyCampuscard record);

    int updateByPrimaryKey(PmdNotifyCampuscard record);
}