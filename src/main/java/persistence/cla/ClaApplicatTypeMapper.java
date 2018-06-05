package persistence.cla;

import domain.cla.ClaApplicatType;
import domain.cla.ClaApplicatTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApplicatTypeMapper {
    long countByExample(ClaApplicatTypeExample example);

    int deleteByExample(ClaApplicatTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApplicatType record);

    int insertSelective(ClaApplicatType record);

    List<ClaApplicatType> selectByExampleWithRowbounds(ClaApplicatTypeExample example, RowBounds rowBounds);

    List<ClaApplicatType> selectByExample(ClaApplicatTypeExample example);

    ClaApplicatType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApplicatType record, @Param("example") ClaApplicatTypeExample example);

    int updateByExample(@Param("record") ClaApplicatType record, @Param("example") ClaApplicatTypeExample example);

    int updateByPrimaryKeySelective(ClaApplicatType record);

    int updateByPrimaryKey(ClaApplicatType record);
}