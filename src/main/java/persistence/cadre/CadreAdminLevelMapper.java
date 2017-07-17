package persistence.cadre;

import domain.cadre.CadreAdminLevel;
import domain.cadre.CadreAdminLevelExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreAdminLevelMapper {
    int countByExample(CadreAdminLevelExample example);

    int deleteByExample(CadreAdminLevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreAdminLevel record);

    int insertSelective(CadreAdminLevel record);

    List<CadreAdminLevel> selectByExampleWithRowbounds(CadreAdminLevelExample example, RowBounds rowBounds);

    List<CadreAdminLevel> selectByExample(CadreAdminLevelExample example);

    CadreAdminLevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreAdminLevel record, @Param("example") CadreAdminLevelExample example);

    int updateByExample(@Param("record") CadreAdminLevel record, @Param("example") CadreAdminLevelExample example);

    int updateByPrimaryKeySelective(CadreAdminLevel record);

    int updateByPrimaryKey(CadreAdminLevel record);
}