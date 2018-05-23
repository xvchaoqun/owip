package persistence.crs;

import domain.crs.CrsApplicant;
import domain.crs.CrsApplicantExample;
import domain.crs.CrsApplicantWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrsApplicantMapper {
    long countByExample(CrsApplicantExample example);

    int deleteByExample(CrsApplicantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsApplicantWithBLOBs record);

    int insertSelective(CrsApplicantWithBLOBs record);

    List<CrsApplicantWithBLOBs> selectByExampleWithBLOBsWithRowbounds(CrsApplicantExample example, RowBounds rowBounds);

    List<CrsApplicantWithBLOBs> selectByExampleWithBLOBs(CrsApplicantExample example);

    List<CrsApplicant> selectByExampleWithRowbounds(CrsApplicantExample example, RowBounds rowBounds);

    List<CrsApplicant> selectByExample(CrsApplicantExample example);

    CrsApplicantWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsApplicantWithBLOBs record, @Param("example") CrsApplicantExample example);

    int updateByExampleWithBLOBs(@Param("record") CrsApplicantWithBLOBs record, @Param("example") CrsApplicantExample example);

    int updateByExample(@Param("record") CrsApplicant record, @Param("example") CrsApplicantExample example);

    int updateByPrimaryKeySelective(CrsApplicantWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(CrsApplicantWithBLOBs record);

    int updateByPrimaryKey(CrsApplicant record);
}