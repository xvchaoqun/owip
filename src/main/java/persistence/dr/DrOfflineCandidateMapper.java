package persistence.dr;

import domain.dr.DrOfflineCandidate;
import domain.dr.DrOfflineCandidateExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DrOfflineCandidateMapper {
    long countByExample(DrOfflineCandidateExample example);

    int deleteByExample(DrOfflineCandidateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOfflineCandidate record);

    int insertSelective(DrOfflineCandidate record);

    List<DrOfflineCandidate> selectByExampleWithRowbounds(DrOfflineCandidateExample example, RowBounds rowBounds);

    List<DrOfflineCandidate> selectByExample(DrOfflineCandidateExample example);

    DrOfflineCandidate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOfflineCandidate record, @Param("example") DrOfflineCandidateExample example);

    int updateByExample(@Param("record") DrOfflineCandidate record, @Param("example") DrOfflineCandidateExample example);

    int updateByPrimaryKeySelective(DrOfflineCandidate record);

    int updateByPrimaryKey(DrOfflineCandidate record);
}