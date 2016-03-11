package persistence;

import domain.SafeBox;
import domain.SafeBoxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SafeBoxMapper {
    int countByExample(SafeBoxExample example);

    int deleteByExample(SafeBoxExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SafeBox record);

    int insertSelective(SafeBox record);

    List<SafeBox> selectByExampleWithRowbounds(SafeBoxExample example, RowBounds rowBounds);

    List<SafeBox> selectByExample(SafeBoxExample example);

    SafeBox selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SafeBox record, @Param("example") SafeBoxExample example);

    int updateByExample(@Param("record") SafeBox record, @Param("example") SafeBoxExample example);

    int updateByPrimaryKeySelective(SafeBox record);

    int updateByPrimaryKey(SafeBox record);
}