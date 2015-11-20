package persistence;

import domain.ApplyOpenTime;
import domain.ApplyOpenTimeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApplyOpenTimeMapper {
    int countByExample(ApplyOpenTimeExample example);

    int deleteByExample(ApplyOpenTimeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplyOpenTime record);

    int insertSelective(ApplyOpenTime record);

    List<ApplyOpenTime> selectByExampleWithRowbounds(ApplyOpenTimeExample example, RowBounds rowBounds);

    List<ApplyOpenTime> selectByExample(ApplyOpenTimeExample example);

    ApplyOpenTime selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplyOpenTime record, @Param("example") ApplyOpenTimeExample example);

    int updateByExample(@Param("record") ApplyOpenTime record, @Param("example") ApplyOpenTimeExample example);

    int updateByPrimaryKeySelective(ApplyOpenTime record);

    int updateByPrimaryKey(ApplyOpenTime record);
}