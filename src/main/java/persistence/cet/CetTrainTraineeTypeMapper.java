package persistence.cet;

import domain.cet.CetTrainTraineeType;
import domain.cet.CetTrainTraineeTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainTraineeTypeMapper {
    long countByExample(CetTrainTraineeTypeExample example);

    int deleteByExample(CetTrainTraineeTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainTraineeType record);

    int insertSelective(CetTrainTraineeType record);

    List<CetTrainTraineeType> selectByExampleWithRowbounds(CetTrainTraineeTypeExample example, RowBounds rowBounds);

    List<CetTrainTraineeType> selectByExample(CetTrainTraineeTypeExample example);

    CetTrainTraineeType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainTraineeType record, @Param("example") CetTrainTraineeTypeExample example);

    int updateByExample(@Param("record") CetTrainTraineeType record, @Param("example") CetTrainTraineeTypeExample example);

    int updateByPrimaryKeySelective(CetTrainTraineeType record);

    int updateByPrimaryKey(CetTrainTraineeType record);
}