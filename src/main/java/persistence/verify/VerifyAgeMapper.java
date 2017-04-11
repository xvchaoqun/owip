package persistence.verify;

import domain.verify.VerifyAge;
import domain.verify.VerifyAgeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface VerifyAgeMapper {
    int countByExample(VerifyAgeExample example);

    int deleteByExample(VerifyAgeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VerifyAge record);

    int insertSelective(VerifyAge record);

    List<VerifyAge> selectByExampleWithRowbounds(VerifyAgeExample example, RowBounds rowBounds);

    List<VerifyAge> selectByExample(VerifyAgeExample example);

    VerifyAge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VerifyAge record, @Param("example") VerifyAgeExample example);

    int updateByExample(@Param("record") VerifyAge record, @Param("example") VerifyAgeExample example);

    int updateByPrimaryKeySelective(VerifyAge record);

    int updateByPrimaryKey(VerifyAge record);
}