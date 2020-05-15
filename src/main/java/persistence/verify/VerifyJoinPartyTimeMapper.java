package persistence.verify;

import domain.verify.VerifyJoinPartyTime;
import domain.verify.VerifyJoinPartyTimeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface VerifyJoinPartyTimeMapper {
    long countByExample(VerifyJoinPartyTimeExample example);

    int deleteByExample(VerifyJoinPartyTimeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VerifyJoinPartyTime record);

    int insertSelective(VerifyJoinPartyTime record);

    List<VerifyJoinPartyTime> selectByExampleWithRowbounds(VerifyJoinPartyTimeExample example, RowBounds rowBounds);

    List<VerifyJoinPartyTime> selectByExample(VerifyJoinPartyTimeExample example);

    VerifyJoinPartyTime selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VerifyJoinPartyTime record, @Param("example") VerifyJoinPartyTimeExample example);

    int updateByExample(@Param("record") VerifyJoinPartyTime record, @Param("example") VerifyJoinPartyTimeExample example);

    int updateByPrimaryKeySelective(VerifyJoinPartyTime record);

    int updateByPrimaryKey(VerifyJoinPartyTime record);
}