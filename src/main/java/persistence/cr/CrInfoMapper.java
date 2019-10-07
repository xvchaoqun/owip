package persistence.cr;

import domain.cr.CrInfo;
import domain.cr.CrInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrInfoMapper {
    long countByExample(CrInfoExample example);

    int deleteByExample(CrInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrInfo record);

    int insertSelective(CrInfo record);

    List<CrInfo> selectByExampleWithRowbounds(CrInfoExample example, RowBounds rowBounds);

    List<CrInfo> selectByExample(CrInfoExample example);

    CrInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrInfo record, @Param("example") CrInfoExample example);

    int updateByExample(@Param("record") CrInfo record, @Param("example") CrInfoExample example);

    int updateByPrimaryKeySelective(CrInfo record);

    int updateByPrimaryKey(CrInfo record);
}