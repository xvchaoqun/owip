package persistence.oa;

import domain.oa.OaTaskAdmin;
import domain.oa.OaTaskAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaTaskAdminMapper {
    long countByExample(OaTaskAdminExample example);

    int deleteByExample(OaTaskAdminExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(OaTaskAdmin record);

    int insertSelective(OaTaskAdmin record);

    List<OaTaskAdmin> selectByExampleWithRowbounds(OaTaskAdminExample example, RowBounds rowBounds);

    List<OaTaskAdmin> selectByExample(OaTaskAdminExample example);

    OaTaskAdmin selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") OaTaskAdmin record, @Param("example") OaTaskAdminExample example);

    int updateByExample(@Param("record") OaTaskAdmin record, @Param("example") OaTaskAdminExample example);

    int updateByPrimaryKeySelective(OaTaskAdmin record);

    int updateByPrimaryKey(OaTaskAdmin record);
}