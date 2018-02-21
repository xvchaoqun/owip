package persistence.sc.scAd;

import domain.sc.scAd.ScAdArchiveVote;
import domain.sc.scAd.ScAdArchiveVoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScAdArchiveVoteMapper {
    long countByExample(ScAdArchiveVoteExample example);

    int deleteByExample(ScAdArchiveVoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScAdArchiveVote record);

    int insertSelective(ScAdArchiveVote record);

    List<ScAdArchiveVote> selectByExampleWithRowbounds(ScAdArchiveVoteExample example, RowBounds rowBounds);

    List<ScAdArchiveVote> selectByExample(ScAdArchiveVoteExample example);

    ScAdArchiveVote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScAdArchiveVote record, @Param("example") ScAdArchiveVoteExample example);

    int updateByExample(@Param("record") ScAdArchiveVote record, @Param("example") ScAdArchiveVoteExample example);

    int updateByPrimaryKeySelective(ScAdArchiveVote record);

    int updateByPrimaryKey(ScAdArchiveVote record);
}