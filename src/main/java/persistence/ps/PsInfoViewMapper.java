package persistence.ps;

import domain.ps.PsInfoView;
import domain.ps.PsInfoViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PsInfoViewMapper {
    long countByExample(PsInfoViewExample example);

    int deleteByExample(PsInfoViewExample example);

    int insert(PsInfoView record);

    int insertSelective(PsInfoView record);

    List<PsInfoView> selectByExampleWithRowbounds(PsInfoViewExample example, RowBounds rowBounds);

    List<PsInfoView> selectByExample(PsInfoViewExample example);

    int updateByExampleSelective(@Param("record") PsInfoView record, @Param("example") PsInfoViewExample example);

    int updateByExample(@Param("record") PsInfoView record, @Param("example") PsInfoViewExample example);
}