package persistence.pcs;

import domain.pcs.PcsProposal;
import domain.pcs.PcsProposalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsProposalMapper {
    long countByExample(PcsProposalExample example);

    int deleteByExample(PcsProposalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsProposal record);

    int insertSelective(PcsProposal record);

    List<PcsProposal> selectByExampleWithRowbounds(PcsProposalExample example, RowBounds rowBounds);

    List<PcsProposal> selectByExample(PcsProposalExample example);

    PcsProposal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsProposal record, @Param("example") PcsProposalExample example);

    int updateByExample(@Param("record") PcsProposal record, @Param("example") PcsProposalExample example);

    int updateByPrimaryKeySelective(PcsProposal record);

    int updateByPrimaryKey(PcsProposal record);
}