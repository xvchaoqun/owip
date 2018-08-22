package persistence.cet;

import domain.cet.CetUpperTrainAdmin;
import domain.cet.CetUpperTrainAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetUpperTrainAdminMapper {
    long countByExample(CetUpperTrainAdminExample example);

    int deleteByExample(CetUpperTrainAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetUpperTrainAdmin record);

    int insertSelective(CetUpperTrainAdmin record);

    List<CetUpperTrainAdmin> selectByExampleWithRowbounds(CetUpperTrainAdminExample example, RowBounds rowBounds);

    List<CetUpperTrainAdmin> selectByExample(CetUpperTrainAdminExample example);

    CetUpperTrainAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetUpperTrainAdmin record, @Param("example") CetUpperTrainAdminExample example);

    int updateByExample(@Param("record") CetUpperTrainAdmin record, @Param("example") CetUpperTrainAdminExample example);

    int updateByPrimaryKeySelective(CetUpperTrainAdmin record);

    int updateByPrimaryKey(CetUpperTrainAdmin record);
}