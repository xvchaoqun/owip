package persistence.dr;

import domain.dr.DrOnlineCandidate;
import domain.dr.DrOnlineCandidateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrOnlineCandidateMapper {
    long countByExample(DrOnlineCandidateExample example);

    int deleteByExample(DrOnlineCandidateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOnlineCandidate record);

    int insertSelective(DrOnlineCandidate record);

    List<DrOnlineCandidate> selectByExampleWithRowbounds(DrOnlineCandidateExample example, RowBounds rowBounds);

    List<DrOnlineCandidate> selectByExample(DrOnlineCandidateExample example);

    DrOnlineCandidate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOnlineCandidate record, @Param("example") DrOnlineCandidateExample example);

    int updateByExample(@Param("record") DrOnlineCandidate record, @Param("example") DrOnlineCandidateExample example);

    int updateByPrimaryKeySelective(DrOnlineCandidate record);

    int updateByPrimaryKey(DrOnlineCandidate record);
}