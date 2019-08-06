package persistence.cg;

import domain.cg.CgLeader;
import domain.cg.CgLeaderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CgLeaderMapper {
    long countByExample(CgLeaderExample example);

    int deleteByExample(CgLeaderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CgLeader record);

    int insertSelective(CgLeader record);

    List<CgLeader> selectByExampleWithRowbounds(CgLeaderExample example, RowBounds rowBounds);

    List<CgLeader> selectByExample(CgLeaderExample example);

    CgLeader selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CgLeader record, @Param("example") CgLeaderExample example);

    int updateByExample(@Param("record") CgLeader record, @Param("example") CgLeaderExample example);

    int updateByPrimaryKeySelective(CgLeader record);

    int updateByPrimaryKey(CgLeader record);
}