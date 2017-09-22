package persistence.pcs;

import domain.pcs.PcsProposalView;
import domain.pcs.PcsProposalViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsProposalViewMapper {

    PcsProposalView selectByPrimaryKey(Integer id);

    long countByExample(PcsProposalViewExample example);

    List<PcsProposalView> selectByExampleWithRowbounds(PcsProposalViewExample example, RowBounds rowBounds);

    List<PcsProposalView> selectByExample(PcsProposalViewExample example);
}