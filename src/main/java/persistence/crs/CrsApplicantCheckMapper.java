package persistence.crs;

import domain.crs.CrsApplicantCheck;
import domain.crs.CrsApplicantCheckExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrsApplicantCheckMapper {
    long countByExample(CrsApplicantCheckExample example);

    int deleteByExample(CrsApplicantCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsApplicantCheck record);

    int insertSelective(CrsApplicantCheck record);

    List<CrsApplicantCheck> selectByExampleWithRowbounds(CrsApplicantCheckExample example, RowBounds rowBounds);

    List<CrsApplicantCheck> selectByExample(CrsApplicantCheckExample example);

    CrsApplicantCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsApplicantCheck record, @Param("example") CrsApplicantCheckExample example);

    int updateByExample(@Param("record") CrsApplicantCheck record, @Param("example") CrsApplicantCheckExample example);

    int updateByPrimaryKeySelective(CrsApplicantCheck record);

    int updateByPrimaryKey(CrsApplicantCheck record);
}