package persistence.abroad;

import domain.abroad.ApprovalOrder;
import domain.abroad.ApprovalOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApprovalOrderMapper {
    int countByExample(ApprovalOrderExample example);

    int deleteByExample(ApprovalOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApprovalOrder record);

    int insertSelective(ApprovalOrder record);

    List<ApprovalOrder> selectByExampleWithRowbounds(ApprovalOrderExample example, RowBounds rowBounds);

    List<ApprovalOrder> selectByExample(ApprovalOrderExample example);

    ApprovalOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApprovalOrder record, @Param("example") ApprovalOrderExample example);

    int updateByExample(@Param("record") ApprovalOrder record, @Param("example") ApprovalOrderExample example);

    int updateByPrimaryKeySelective(ApprovalOrder record);

    int updateByPrimaryKey(ApprovalOrder record);
}