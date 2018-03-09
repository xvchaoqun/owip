package persistence.cet;

import domain.cet.CetTraineeType;
import domain.cet.CetTraineeTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTraineeTypeMapper {
    long countByExample(CetTraineeTypeExample example);

    int deleteByExample(CetTraineeTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTraineeType record);

    int insertSelective(CetTraineeType record);

    List<CetTraineeType> selectByExampleWithRowbounds(CetTraineeTypeExample example, RowBounds rowBounds);

    List<CetTraineeType> selectByExample(CetTraineeTypeExample example);

    CetTraineeType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTraineeType record, @Param("example") CetTraineeTypeExample example);

    int updateByExample(@Param("record") CetTraineeType record, @Param("example") CetTraineeTypeExample example);

    int updateByPrimaryKeySelective(CetTraineeType record);

    int updateByPrimaryKey(CetTraineeType record);
}