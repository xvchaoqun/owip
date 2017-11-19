package persistence.pcs;

import domain.pcs.PcsVoteGroup;
import domain.pcs.PcsVoteGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsVoteGroupMapper {
    long countByExample(PcsVoteGroupExample example);

    int deleteByExample(PcsVoteGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsVoteGroup record);

    int insertSelective(PcsVoteGroup record);

    List<PcsVoteGroup> selectByExampleWithRowbounds(PcsVoteGroupExample example, RowBounds rowBounds);

    List<PcsVoteGroup> selectByExample(PcsVoteGroupExample example);

    PcsVoteGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsVoteGroup record, @Param("example") PcsVoteGroupExample example);

    int updateByExample(@Param("record") PcsVoteGroup record, @Param("example") PcsVoteGroupExample example);

    int updateByPrimaryKeySelective(PcsVoteGroup record);

    int updateByPrimaryKey(PcsVoteGroup record);
}