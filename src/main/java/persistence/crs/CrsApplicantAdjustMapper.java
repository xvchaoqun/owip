package persistence.crs;

import domain.crs.CrsApplicantAdjust;
import domain.crs.CrsApplicantAdjustExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsApplicantAdjustMapper {
    long countByExample(CrsApplicantAdjustExample example);

    int deleteByExample(CrsApplicantAdjustExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsApplicantAdjust record);

    int insertSelective(CrsApplicantAdjust record);

    List<CrsApplicantAdjust> selectByExampleWithRowbounds(CrsApplicantAdjustExample example, RowBounds rowBounds);

    List<CrsApplicantAdjust> selectByExample(CrsApplicantAdjustExample example);

    CrsApplicantAdjust selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsApplicantAdjust record, @Param("example") CrsApplicantAdjustExample example);

    int updateByExample(@Param("record") CrsApplicantAdjust record, @Param("example") CrsApplicantAdjustExample example);

    int updateByPrimaryKeySelective(CrsApplicantAdjust record);

    int updateByPrimaryKey(CrsApplicantAdjust record);
}