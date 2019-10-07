package persistence.cr;

import domain.cr.CrApplicant;
import domain.cr.CrApplicantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrApplicantMapper {
    long countByExample(CrApplicantExample example);

    int deleteByExample(CrApplicantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrApplicant record);

    int insertSelective(CrApplicant record);

    List<CrApplicant> selectByExampleWithRowbounds(CrApplicantExample example, RowBounds rowBounds);

    List<CrApplicant> selectByExample(CrApplicantExample example);

    CrApplicant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrApplicant record, @Param("example") CrApplicantExample example);

    int updateByExample(@Param("record") CrApplicant record, @Param("example") CrApplicantExample example);

    int updateByPrimaryKeySelective(CrApplicant record);

    int updateByPrimaryKey(CrApplicant record);
}