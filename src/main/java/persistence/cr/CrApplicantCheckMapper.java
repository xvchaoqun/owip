package persistence.cr;

import domain.cr.CrApplicantCheck;
import domain.cr.CrApplicantCheckExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrApplicantCheckMapper {
    long countByExample(CrApplicantCheckExample example);

    int deleteByExample(CrApplicantCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrApplicantCheck record);

    int insertSelective(CrApplicantCheck record);

    List<CrApplicantCheck> selectByExampleWithRowbounds(CrApplicantCheckExample example, RowBounds rowBounds);

    List<CrApplicantCheck> selectByExample(CrApplicantCheckExample example);

    CrApplicantCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrApplicantCheck record, @Param("example") CrApplicantCheckExample example);

    int updateByExample(@Param("record") CrApplicantCheck record, @Param("example") CrApplicantCheckExample example);

    int updateByPrimaryKeySelective(CrApplicantCheck record);

    int updateByPrimaryKey(CrApplicantCheck record);
}