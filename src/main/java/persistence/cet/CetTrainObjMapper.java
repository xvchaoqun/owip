package persistence.cet;

import domain.cet.CetTrainObj;
import domain.cet.CetTrainObjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainObjMapper {
    long countByExample(CetTrainObjExample example);

    int deleteByExample(CetTrainObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainObj record);

    int insertSelective(CetTrainObj record);

    List<CetTrainObj> selectByExampleWithRowbounds(CetTrainObjExample example, RowBounds rowBounds);

    List<CetTrainObj> selectByExample(CetTrainObjExample example);

    CetTrainObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainObj record, @Param("example") CetTrainObjExample example);

    int updateByExample(@Param("record") CetTrainObj record, @Param("example") CetTrainObjExample example);

    int updateByPrimaryKeySelective(CetTrainObj record);

    int updateByPrimaryKey(CetTrainObj record);
}