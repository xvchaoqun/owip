package persistence.cla;

import domain.cla.ClaApprovalOrder;
import domain.cla.ClaApprovalOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApprovalOrderMapper {
    long countByExample(ClaApprovalOrderExample example);

    int deleteByExample(ClaApprovalOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApprovalOrder record);

    int insertSelective(ClaApprovalOrder record);

    List<ClaApprovalOrder> selectByExampleWithRowbounds(ClaApprovalOrderExample example, RowBounds rowBounds);

    List<ClaApprovalOrder> selectByExample(ClaApprovalOrderExample example);

    ClaApprovalOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApprovalOrder record, @Param("example") ClaApprovalOrderExample example);

    int updateByExample(@Param("record") ClaApprovalOrder record, @Param("example") ClaApprovalOrderExample example);

    int updateByPrimaryKeySelective(ClaApprovalOrder record);

    int updateByPrimaryKey(ClaApprovalOrder record);
}