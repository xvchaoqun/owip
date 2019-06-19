package persistence.ps;

import domain.ps.PsInfo;
import domain.ps.PsInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PsInfoMapper {
    long countByExample(PsInfoExample example);

    int deleteByExample(PsInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PsInfo record);

    int insertSelective(PsInfo record);

    List<PsInfo> selectByExampleWithRowbounds(PsInfoExample example, RowBounds rowBounds);

    List<PsInfo> selectByExample(PsInfoExample example);

    PsInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PsInfo record, @Param("example") PsInfoExample example);

    int updateByExample(@Param("record") PsInfo record, @Param("example") PsInfoExample example);

    int updateByPrimaryKeySelective(PsInfo record);

    int updateByPrimaryKey(PsInfo record);
}