package persistence.cet;

import domain.cet.CetUnitProject;
import domain.cet.CetUnitProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetUnitProjectMapper {
    long countByExample(CetUnitProjectExample example);

    int deleteByExample(CetUnitProjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetUnitProject record);

    int insertSelective(CetUnitProject record);

    List<CetUnitProject> selectByExampleWithRowbounds(CetUnitProjectExample example, RowBounds rowBounds);

    List<CetUnitProject> selectByExample(CetUnitProjectExample example);

    CetUnitProject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetUnitProject record, @Param("example") CetUnitProjectExample example);

    int updateByExample(@Param("record") CetUnitProject record, @Param("example") CetUnitProjectExample example);

    int updateByPrimaryKeySelective(CetUnitProject record);

    int updateByPrimaryKey(CetUnitProject record);
}