package persistence.cet;

import domain.cet.CetProjectTraineeType;
import domain.cet.CetProjectTraineeTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectTraineeTypeMapper {
    long countByExample(CetProjectTraineeTypeExample example);

    int deleteByExample(CetProjectTraineeTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetProjectTraineeType record);

    int insertSelective(CetProjectTraineeType record);

    List<CetProjectTraineeType> selectByExampleWithRowbounds(CetProjectTraineeTypeExample example, RowBounds rowBounds);

    List<CetProjectTraineeType> selectByExample(CetProjectTraineeTypeExample example);

    CetProjectTraineeType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetProjectTraineeType record, @Param("example") CetProjectTraineeTypeExample example);

    int updateByExample(@Param("record") CetProjectTraineeType record, @Param("example") CetProjectTraineeTypeExample example);

    int updateByPrimaryKeySelective(CetProjectTraineeType record);

    int updateByPrimaryKey(CetProjectTraineeType record);
}