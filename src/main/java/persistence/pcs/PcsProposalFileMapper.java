package persistence.pcs;

import domain.pcs.PcsProposalFile;
import domain.pcs.PcsProposalFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsProposalFileMapper {
    long countByExample(PcsProposalFileExample example);

    int deleteByExample(PcsProposalFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsProposalFile record);

    int insertSelective(PcsProposalFile record);

    List<PcsProposalFile> selectByExampleWithRowbounds(PcsProposalFileExample example, RowBounds rowBounds);

    List<PcsProposalFile> selectByExample(PcsProposalFileExample example);

    PcsProposalFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsProposalFile record, @Param("example") PcsProposalFileExample example);

    int updateByExample(@Param("record") PcsProposalFile record, @Param("example") PcsProposalFileExample example);

    int updateByPrimaryKeySelective(PcsProposalFile record);

    int updateByPrimaryKey(PcsProposalFile record);
}