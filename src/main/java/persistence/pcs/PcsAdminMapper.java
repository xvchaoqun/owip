package persistence.pcs;

import domain.pcs.PcsAdmin;
import domain.pcs.PcsAdminExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsAdminMapper {
    long countByExample(PcsAdminExample example);

    int deleteByExample(PcsAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsAdmin record);

    int insertSelective(PcsAdmin record);

    List<PcsAdmin> selectByExampleWithRowbounds(PcsAdminExample example, RowBounds rowBounds);

    List<PcsAdmin> selectByExample(PcsAdminExample example);

    PcsAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsAdmin record, @Param("example") PcsAdminExample example);

    int updateByExample(@Param("record") PcsAdmin record, @Param("example") PcsAdminExample example);

    int updateByPrimaryKeySelective(PcsAdmin record);

    int updateByPrimaryKey(PcsAdmin record);
}