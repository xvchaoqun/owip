package persistence.dispatch;

import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DispatchWorkFileMapper {
    long countByExample(DispatchWorkFileExample example);

    int deleteByExample(DispatchWorkFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchWorkFile record);

    int insertSelective(DispatchWorkFile record);

    List<DispatchWorkFile> selectByExampleWithRowbounds(DispatchWorkFileExample example, RowBounds rowBounds);

    List<DispatchWorkFile> selectByExample(DispatchWorkFileExample example);

    DispatchWorkFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchWorkFile record, @Param("example") DispatchWorkFileExample example);

    int updateByExample(@Param("record") DispatchWorkFile record, @Param("example") DispatchWorkFileExample example);

    int updateByPrimaryKeySelective(DispatchWorkFile record);

    int updateByPrimaryKey(DispatchWorkFile record);
}