package persistence;

import domain.ApproverType;
import domain.ApproverTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApproverTypeMapper {
    int countByExample(ApproverTypeExample example);

    int deleteByExample(ApproverTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApproverType record);

    int insertSelective(ApproverType record);

    List<ApproverType> selectByExampleWithRowbounds(ApproverTypeExample example, RowBounds rowBounds);

    List<ApproverType> selectByExample(ApproverTypeExample example);

    ApproverType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApproverType record, @Param("example") ApproverTypeExample example);

    int updateByExample(@Param("record") ApproverType record, @Param("example") ApproverTypeExample example);

    int updateByPrimaryKeySelective(ApproverType record);

    int updateByPrimaryKey(ApproverType record);
}