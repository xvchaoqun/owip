package persistence.parttime;

import domain.parttime.ParttimeApprovalOrder;
import domain.parttime.ParttimeApprovalOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApprovalOrderMapper {
    long countByExample(ParttimeApprovalOrderExample example);

    int deleteByExample(ParttimeApprovalOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApprovalOrder record);

    int insertSelective(ParttimeApprovalOrder record);

    List<ParttimeApprovalOrder> selectByExampleWithRowbounds(ParttimeApprovalOrderExample example, RowBounds rowBounds);

    List<ParttimeApprovalOrder> selectByExample(ParttimeApprovalOrderExample example);

    ParttimeApprovalOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApprovalOrder record, @Param("example") ParttimeApprovalOrderExample example);

    int updateByExample(@Param("record") ParttimeApprovalOrder record, @Param("example") ParttimeApprovalOrderExample example);

    int updateByPrimaryKeySelective(ParttimeApprovalOrder record);

    int updateByPrimaryKey(ParttimeApprovalOrder record);
}