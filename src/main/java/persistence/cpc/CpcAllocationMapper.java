package persistence.cpc;

import domain.cpc.CpcAllocation;
import domain.cpc.CpcAllocationExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CpcAllocationMapper {
    long countByExample(CpcAllocationExample example);

    int deleteByExample(CpcAllocationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CpcAllocation record);

    int insertSelective(CpcAllocation record);

    List<CpcAllocation> selectByExampleWithRowbounds(CpcAllocationExample example, RowBounds rowBounds);

    List<CpcAllocation> selectByExample(CpcAllocationExample example);

    CpcAllocation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CpcAllocation record, @Param("example") CpcAllocationExample example);

    int updateByExample(@Param("record") CpcAllocation record, @Param("example") CpcAllocationExample example);

    int updateByPrimaryKeySelective(CpcAllocation record);

    int updateByPrimaryKey(CpcAllocation record);
}