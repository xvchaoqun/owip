package persistence.ps;

import domain.ps.PsAdmin;
import domain.ps.PsAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PsAdminMapper {
    long countByExample(PsAdminExample example);

    int deleteByExample(PsAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PsAdmin record);

    int insertSelective(PsAdmin record);

    List<PsAdmin> selectByExampleWithRowbounds(PsAdminExample example, RowBounds rowBounds);

    List<PsAdmin> selectByExample(PsAdminExample example);

    PsAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PsAdmin record, @Param("example") PsAdminExample example);

    int updateByExample(@Param("record") PsAdmin record, @Param("example") PsAdminExample example);

    int updateByPrimaryKeySelective(PsAdmin record);

    int updateByPrimaryKey(PsAdmin record);
}