package persistence.sc.scDispatch;

import domain.sc.scDispatch.ScDispatchCommittee;
import domain.sc.scDispatch.ScDispatchCommitteeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScDispatchCommitteeMapper {
    long countByExample(ScDispatchCommitteeExample example);

    int deleteByExample(ScDispatchCommitteeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScDispatchCommittee record);

    int insertSelective(ScDispatchCommittee record);

    List<ScDispatchCommittee> selectByExampleWithRowbounds(ScDispatchCommitteeExample example, RowBounds rowBounds);

    List<ScDispatchCommittee> selectByExample(ScDispatchCommitteeExample example);

    ScDispatchCommittee selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScDispatchCommittee record, @Param("example") ScDispatchCommitteeExample example);

    int updateByExample(@Param("record") ScDispatchCommittee record, @Param("example") ScDispatchCommitteeExample example);

    int updateByPrimaryKeySelective(ScDispatchCommittee record);

    int updateByPrimaryKey(ScDispatchCommittee record);
}