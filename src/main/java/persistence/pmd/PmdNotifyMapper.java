package persistence.pmd;

import domain.pmd.PmdNotify;
import domain.pmd.PmdNotifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdNotifyMapper {
    long countByExample(PmdNotifyExample example);

    int deleteByExample(PmdNotifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdNotify record);

    int insertSelective(PmdNotify record);

    List<PmdNotify> selectByExampleWithRowbounds(PmdNotifyExample example, RowBounds rowBounds);

    List<PmdNotify> selectByExample(PmdNotifyExample example);

    PmdNotify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdNotify record, @Param("example") PmdNotifyExample example);

    int updateByExample(@Param("record") PmdNotify record, @Param("example") PmdNotifyExample example);

    int updateByPrimaryKeySelective(PmdNotify record);

    int updateByPrimaryKey(PmdNotify record);
}