package persistence;

import domain.CadreInfo;
import domain.CadreInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreInfoMapper {
    int countByExample(CadreInfoExample example);

    int deleteByExample(CadreInfoExample example);

    int deleteByPrimaryKey(Integer cadreId);

    int insert(CadreInfo record);

    int insertSelective(CadreInfo record);

    List<CadreInfo> selectByExampleWithRowbounds(CadreInfoExample example, RowBounds rowBounds);

    List<CadreInfo> selectByExample(CadreInfoExample example);

    CadreInfo selectByPrimaryKey(Integer cadreId);

    int updateByExampleSelective(@Param("record") CadreInfo record, @Param("example") CadreInfoExample example);

    int updateByExample(@Param("record") CadreInfo record, @Param("example") CadreInfoExample example);

    int updateByPrimaryKeySelective(CadreInfo record);

    int updateByPrimaryKey(CadreInfo record);
}