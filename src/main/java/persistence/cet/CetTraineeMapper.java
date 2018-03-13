package persistence.cet;

import domain.cet.CetTrainee;
import domain.cet.CetTraineeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTraineeMapper {
    long countByExample(CetTraineeExample example);

    int deleteByExample(CetTraineeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainee record);

    int insertSelective(CetTrainee record);

    List<CetTrainee> selectByExampleWithRowbounds(CetTraineeExample example, RowBounds rowBounds);

    List<CetTrainee> selectByExample(CetTraineeExample example);

    CetTrainee selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainee record, @Param("example") CetTraineeExample example);

    int updateByExample(@Param("record") CetTrainee record, @Param("example") CetTraineeExample example);

    int updateByPrimaryKeySelective(CetTrainee record);

    int updateByPrimaryKey(CetTrainee record);
}