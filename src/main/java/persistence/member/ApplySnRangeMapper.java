package persistence.member;

import domain.member.ApplySnRange;
import domain.member.ApplySnRangeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApplySnRangeMapper {
    long countByExample(ApplySnRangeExample example);

    int deleteByExample(ApplySnRangeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplySnRange record);

    int insertSelective(ApplySnRange record);

    List<ApplySnRange> selectByExampleWithRowbounds(ApplySnRangeExample example, RowBounds rowBounds);

    List<ApplySnRange> selectByExample(ApplySnRangeExample example);

    ApplySnRange selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplySnRange record, @Param("example") ApplySnRangeExample example);

    int updateByExample(@Param("record") ApplySnRange record, @Param("example") ApplySnRangeExample example);

    int updateByPrimaryKeySelective(ApplySnRange record);

    int updateByPrimaryKey(ApplySnRange record);
}