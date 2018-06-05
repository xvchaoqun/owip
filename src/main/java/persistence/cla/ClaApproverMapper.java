package persistence.cla;

import domain.cla.ClaApprover;
import domain.cla.ClaApproverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApproverMapper {
    long countByExample(ClaApproverExample example);

    int deleteByExample(ClaApproverExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApprover record);

    int insertSelective(ClaApprover record);

    List<ClaApprover> selectByExampleWithRowbounds(ClaApproverExample example, RowBounds rowBounds);

    List<ClaApprover> selectByExample(ClaApproverExample example);

    ClaApprover selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApprover record, @Param("example") ClaApproverExample example);

    int updateByExample(@Param("record") ClaApprover record, @Param("example") ClaApproverExample example);

    int updateByPrimaryKeySelective(ClaApprover record);

    int updateByPrimaryKey(ClaApprover record);
}