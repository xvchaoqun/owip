package persistence.pcs;

import domain.pcs.PcsProposalSeconder;
import domain.pcs.PcsProposalSeconderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsProposalSeconderMapper {
    long countByExample(PcsProposalSeconderExample example);

    int deleteByExample(PcsProposalSeconderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsProposalSeconder record);

    int insertSelective(PcsProposalSeconder record);

    List<PcsProposalSeconder> selectByExampleWithRowbounds(PcsProposalSeconderExample example, RowBounds rowBounds);

    List<PcsProposalSeconder> selectByExample(PcsProposalSeconderExample example);

    PcsProposalSeconder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsProposalSeconder record, @Param("example") PcsProposalSeconderExample example);

    int updateByExample(@Param("record") PcsProposalSeconder record, @Param("example") PcsProposalSeconderExample example);

    int updateByPrimaryKeySelective(PcsProposalSeconder record);

    int updateByPrimaryKey(PcsProposalSeconder record);
}