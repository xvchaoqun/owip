package persistence.cadre;

import domain.cadre.CadreInfo;
import domain.cadre.CadreInfoExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreInfoMapper {
    int countByExample(CadreInfoExample example);

    int deleteByExample(CadreInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreInfo record);

    int insertSelective(CadreInfo record);

    List<CadreInfo> selectByExampleWithRowbounds(CadreInfoExample example, RowBounds rowBounds);

    List<CadreInfo> selectByExample(CadreInfoExample example);

    CadreInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreInfo record, @Param("example") CadreInfoExample example);

    int updateByExample(@Param("record") CadreInfo record, @Param("example") CadreInfoExample example);

    int updateByPrimaryKeySelective(CadreInfo record);

    int updateByPrimaryKey(CadreInfo record);
}