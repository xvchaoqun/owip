package persistence.crs;

import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrsApplicantMapper {
    long countByExample(CrsApplicantExample example);

    int deleteByExample(CrsApplicantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsApplicant record);

    int insertSelective(CrsApplicant record);

    List<CrsApplicant> selectByExampleWithRowbounds(CrsApplicantExample example, RowBounds rowBounds);

    List<CrsApplicant> selectByExample(CrsApplicantExample example);

    CrsApplicant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsApplicant record, @Param("example") CrsApplicantExample example);

    int updateByExample(@Param("record") CrsApplicant record, @Param("example") CrsApplicantExample example);

    int updateByPrimaryKeySelective(CrsApplicant record);

    int updateByPrimaryKey(CrsApplicant record);
}