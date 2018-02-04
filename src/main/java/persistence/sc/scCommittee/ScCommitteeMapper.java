package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommittee;
import domain.sc.scCommittee.ScCommitteeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScCommitteeMapper {
    long countByExample(ScCommitteeExample example);

    int deleteByExample(ScCommitteeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScCommittee record);

    int insertSelective(ScCommittee record);

    List<ScCommittee> selectByExampleWithRowbounds(ScCommitteeExample example, RowBounds rowBounds);

    List<ScCommittee> selectByExample(ScCommitteeExample example);

    ScCommittee selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScCommittee record, @Param("example") ScCommitteeExample example);

    int updateByExample(@Param("record") ScCommittee record, @Param("example") ScCommitteeExample example);

    int updateByPrimaryKeySelective(ScCommittee record);

    int updateByPrimaryKey(ScCommittee record);
}