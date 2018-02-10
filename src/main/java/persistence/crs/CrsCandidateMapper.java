package persistence.crs;

import domain.crs.CrsCandidate;
import domain.crs.CrsCandidateExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsCandidateMapper {
    long countByExample(CrsCandidateExample example);

    int deleteByExample(CrsCandidateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsCandidate record);

    int insertSelective(CrsCandidate record);

    List<CrsCandidate> selectByExampleWithRowbounds(CrsCandidateExample example, RowBounds rowBounds);

    List<CrsCandidate> selectByExample(CrsCandidateExample example);

    CrsCandidate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsCandidate record, @Param("example") CrsCandidateExample example);

    int updateByExample(@Param("record") CrsCandidate record, @Param("example") CrsCandidateExample example);

    int updateByPrimaryKeySelective(CrsCandidate record);

    int updateByPrimaryKey(CrsCandidate record);
}