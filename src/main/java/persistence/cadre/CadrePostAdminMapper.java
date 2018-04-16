package persistence.cadre;

import domain.cadre.CadrePostAdmin;
import domain.cadre.CadrePostAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadrePostAdminMapper {
    long countByExample(CadrePostAdminExample example);

    int deleteByExample(CadrePostAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadrePostAdmin record);

    int insertSelective(CadrePostAdmin record);

    List<CadrePostAdmin> selectByExampleWithRowbounds(CadrePostAdminExample example, RowBounds rowBounds);

    List<CadrePostAdmin> selectByExample(CadrePostAdminExample example);

    CadrePostAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadrePostAdmin record, @Param("example") CadrePostAdminExample example);

    int updateByExample(@Param("record") CadrePostAdmin record, @Param("example") CadrePostAdminExample example);

    int updateByPrimaryKeySelective(CadrePostAdmin record);

    int updateByPrimaryKey(CadrePostAdmin record);
}