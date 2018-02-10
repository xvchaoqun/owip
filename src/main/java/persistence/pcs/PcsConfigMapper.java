package persistence.pcs;

import domain.pcs.PcsConfig;
import domain.pcs.PcsConfigExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsConfigMapper {
    long countByExample(PcsConfigExample example);

    int deleteByExample(PcsConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsConfig record);

    int insertSelective(PcsConfig record);

    List<PcsConfig> selectByExampleWithRowbounds(PcsConfigExample example, RowBounds rowBounds);

    List<PcsConfig> selectByExample(PcsConfigExample example);

    PcsConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsConfig record, @Param("example") PcsConfigExample example);

    int updateByExample(@Param("record") PcsConfig record, @Param("example") PcsConfigExample example);

    int updateByPrimaryKeySelective(PcsConfig record);

    int updateByPrimaryKey(PcsConfig record);
}