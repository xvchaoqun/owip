package persistence;

import domain.Approver;
import domain.ApproverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApproverMapper {
    int countByExample(ApproverExample example);

    int deleteByExample(ApproverExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Approver record);

    int insertSelective(Approver record);

    List<Approver> selectByExampleWithRowbounds(ApproverExample example, RowBounds rowBounds);

    List<Approver> selectByExample(ApproverExample example);

    Approver selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Approver record, @Param("example") ApproverExample example);

    int updateByExample(@Param("record") Approver record, @Param("example") ApproverExample example);

    int updateByPrimaryKeySelective(Approver record);

    int updateByPrimaryKey(Approver record);
}