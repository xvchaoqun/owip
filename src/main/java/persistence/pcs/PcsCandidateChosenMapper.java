package persistence.pcs;

import domain.pcs.PcsCandidateChosen;
import domain.pcs.PcsCandidateChosenExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsCandidateChosenMapper {
    long countByExample(PcsCandidateChosenExample example);

    int deleteByExample(PcsCandidateChosenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsCandidateChosen record);

    int insertSelective(PcsCandidateChosen record);

    List<PcsCandidateChosen> selectByExampleWithRowbounds(PcsCandidateChosenExample example, RowBounds rowBounds);

    List<PcsCandidateChosen> selectByExample(PcsCandidateChosenExample example);

    PcsCandidateChosen selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsCandidateChosen record, @Param("example") PcsCandidateChosenExample example);

    int updateByExample(@Param("record") PcsCandidateChosen record, @Param("example") PcsCandidateChosenExample example);

    int updateByPrimaryKeySelective(PcsCandidateChosen record);

    int updateByPrimaryKey(PcsCandidateChosen record);
}