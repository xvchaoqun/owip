package persistence.ps;

import domain.ps.PsTask;
import domain.ps.PsTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PsTaskMapper {
    long countByExample(PsTaskExample example);

    int deleteByExample(PsTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PsTask record);

    int insertSelective(PsTask record);

    List<PsTask> selectByExampleWithRowbounds(PsTaskExample example, RowBounds rowBounds);

    List<PsTask> selectByExample(PsTaskExample example);

    PsTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PsTask record, @Param("example") PsTaskExample example);

    int updateByExample(@Param("record") PsTask record, @Param("example") PsTaskExample example);

    int updateByPrimaryKeySelective(PsTask record);

    int updateByPrimaryKey(PsTask record);
}