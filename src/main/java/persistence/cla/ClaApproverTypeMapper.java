package persistence.cla;

import domain.cla.ClaApproverType;
import domain.cla.ClaApproverTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApproverTypeMapper {
    long countByExample(ClaApproverTypeExample example);

    int deleteByExample(ClaApproverTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApproverType record);

    int insertSelective(ClaApproverType record);

    List<ClaApproverType> selectByExampleWithRowbounds(ClaApproverTypeExample example, RowBounds rowBounds);

    List<ClaApproverType> selectByExample(ClaApproverTypeExample example);

    ClaApproverType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApproverType record, @Param("example") ClaApproverTypeExample example);

    int updateByExample(@Param("record") ClaApproverType record, @Param("example") ClaApproverTypeExample example);

    int updateByPrimaryKeySelective(ClaApproverType record);

    int updateByPrimaryKey(ClaApproverType record);
}