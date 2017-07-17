package persistence.verify;

import domain.verify.VerifyWorkTime;
import domain.verify.VerifyWorkTimeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface VerifyWorkTimeMapper {
    int countByExample(VerifyWorkTimeExample example);

    int deleteByExample(VerifyWorkTimeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VerifyWorkTime record);

    int insertSelective(VerifyWorkTime record);

    List<VerifyWorkTime> selectByExampleWithRowbounds(VerifyWorkTimeExample example, RowBounds rowBounds);

    List<VerifyWorkTime> selectByExample(VerifyWorkTimeExample example);

    VerifyWorkTime selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VerifyWorkTime record, @Param("example") VerifyWorkTimeExample example);

    int updateByExample(@Param("record") VerifyWorkTime record, @Param("example") VerifyWorkTimeExample example);

    int updateByPrimaryKeySelective(VerifyWorkTime record);

    int updateByPrimaryKey(VerifyWorkTime record);
}