package persistence.abroad;

import domain.abroad.ApplySelf;
import domain.abroad.ApplySelfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApplySelfMapper {
    long countByExample(ApplySelfExample example);

    int deleteByExample(ApplySelfExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplySelf record);

    int insertSelective(ApplySelf record);

    List<ApplySelf> selectByExampleWithRowbounds(ApplySelfExample example, RowBounds rowBounds);

    List<ApplySelf> selectByExample(ApplySelfExample example);

    ApplySelf selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplySelf record, @Param("example") ApplySelfExample example);

    int updateByExample(@Param("record") ApplySelf record, @Param("example") ApplySelfExample example);

    int updateByPrimaryKeySelective(ApplySelf record);

    int updateByPrimaryKey(ApplySelf record);
}