package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeView;
import domain.sc.scCommittee.ScCommitteeViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScCommitteeViewMapper {
    long countByExample(ScCommitteeViewExample example);

    List<ScCommitteeView> selectByExampleWithRowbounds(ScCommitteeViewExample example, RowBounds rowBounds);

    List<ScCommitteeView> selectByExample(ScCommitteeViewExample example);
}