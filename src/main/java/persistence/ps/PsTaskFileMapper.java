package persistence.ps;

import domain.ps.PsTaskFile;
import domain.ps.PsTaskFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PsTaskFileMapper {
    long countByExample(PsTaskFileExample example);

    int deleteByExample(PsTaskFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PsTaskFile record);

    int insertSelective(PsTaskFile record);

    List<PsTaskFile> selectByExampleWithRowbounds(PsTaskFileExample example, RowBounds rowBounds);

    List<PsTaskFile> selectByExample(PsTaskFileExample example);

    PsTaskFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PsTaskFile record, @Param("example") PsTaskFileExample example);

    int updateByExample(@Param("record") PsTaskFile record, @Param("example") PsTaskFileExample example);

    int updateByPrimaryKeySelective(PsTaskFile record);

    int updateByPrimaryKey(PsTaskFile record);
}