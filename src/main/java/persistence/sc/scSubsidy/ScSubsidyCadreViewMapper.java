package persistence.sc.scSubsidy;

import domain.sc.scSubsidy.ScSubsidyCadreView;
import domain.sc.scSubsidy.ScSubsidyCadreViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScSubsidyCadreViewMapper {
    long countByExample(ScSubsidyCadreViewExample example);

    List<ScSubsidyCadreView> selectByExampleWithRowbounds(ScSubsidyCadreViewExample example, RowBounds rowBounds);

    List<ScSubsidyCadreView> selectByExample(ScSubsidyCadreViewExample example);
}