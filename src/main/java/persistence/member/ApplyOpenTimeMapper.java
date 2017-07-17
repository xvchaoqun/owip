package persistence.member;

import domain.member.ApplyOpenTime;
import domain.member.ApplyOpenTimeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

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