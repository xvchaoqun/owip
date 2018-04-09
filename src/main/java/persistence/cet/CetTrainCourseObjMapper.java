package persistence.cet;

import domain.cet.CetTrainCourseObj;
import domain.cet.CetTrainCourseObjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainCourseObjMapper {
    long countByExample(CetTrainCourseObjExample example);

    int deleteByExample(CetTrainCourseObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainCourseObj record);

    int insertSelective(CetTrainCourseObj record);

    List<CetTrainCourseObj> selectByExampleWithRowbounds(CetTrainCourseObjExample example, RowBounds rowBounds);

    List<CetTrainCourseObj> selectByExample(CetTrainCourseObjExample example);

    CetTrainCourseObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainCourseObj record, @Param("example") CetTrainCourseObjExample example);

    int updateByExample(@Param("record") CetTrainCourseObj record, @Param("example") CetTrainCourseObjExample example);

    int updateByPrimaryKeySelective(CetTrainCourseObj record);

    int updateByPrimaryKey(CetTrainCourseObj record);
}