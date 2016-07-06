package persistence.dispatch;

import domain.dispatch.Dispatch;
import domain.dispatch.DispatchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DispatchMapper {
    int countByExample(DispatchExample example);

    int deleteByExample(DispatchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Dispatch record);

    int insertSelective(Dispatch record);

    List<Dispatch> selectByExampleWithRowbounds(DispatchExample example, RowBounds rowBounds);

    List<Dispatch> selectByExample(DispatchExample example);

    Dispatch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Dispatch record, @Param("example") DispatchExample example);

    int updateByExample(@Param("record") Dispatch record, @Param("example") DispatchExample example);

    int updateByPrimaryKeySelective(Dispatch record);

    int updateByPrimaryKey(Dispatch record);
}