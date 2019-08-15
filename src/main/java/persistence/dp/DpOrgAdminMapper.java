package persistence.dp;

import domain.dp.DpOrgAdmin;
import domain.dp.DpOrgAdminExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DpOrgAdminMapper {
    long countByExample(DpOrgAdminExample example);

    int deleteByExample(DpOrgAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpOrgAdmin record);

    int insertSelective(DpOrgAdmin record);

    List<DpOrgAdmin> selectByExampleWithRowbounds(DpOrgAdminExample example, RowBounds rowBounds);

    List<DpOrgAdmin> selectByExample(DpOrgAdminExample example);

    DpOrgAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpOrgAdmin record, @Param("example") DpOrgAdminExample example);

    int updateByExample(@Param("record") DpOrgAdmin record, @Param("example") DpOrgAdminExample example);

    int updateByPrimaryKeySelective(DpOrgAdmin record);

    int updateByPrimaryKey(DpOrgAdmin record);
}