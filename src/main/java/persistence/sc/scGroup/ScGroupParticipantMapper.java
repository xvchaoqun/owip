package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroupParticipant;
import domain.sc.scGroup.ScGroupParticipantExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScGroupParticipantMapper {
    long countByExample(ScGroupParticipantExample example);

    int deleteByExample(ScGroupParticipantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScGroupParticipant record);

    int insertSelective(ScGroupParticipant record);

    List<ScGroupParticipant> selectByExampleWithRowbounds(ScGroupParticipantExample example, RowBounds rowBounds);

    List<ScGroupParticipant> selectByExample(ScGroupParticipantExample example);

    ScGroupParticipant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScGroupParticipant record, @Param("example") ScGroupParticipantExample example);

    int updateByExample(@Param("record") ScGroupParticipant record, @Param("example") ScGroupParticipantExample example);

    int updateByPrimaryKeySelective(ScGroupParticipant record);

    int updateByPrimaryKey(ScGroupParticipant record);
}