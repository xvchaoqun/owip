package persistence.cet;

import domain.cet.CetTrainCourseObjResult;
import domain.cet.CetTrainCourseObjResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainCourseObjResultMapper {
    long countByExample(CetTrainCourseObjResultExample example);

    int deleteByExample(CetTrainCourseObjResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainCourseObjResult record);

    int insertSelective(CetTrainCourseObjResult record);

    List<CetTrainCourseObjResult> selectByExampleWithRowbounds(CetTrainCourseObjResultExample example, RowBounds rowBounds);

    List<CetTrainCourseObjResult> selectByExample(CetTrainCourseObjResultExample example);

    CetTrainCourseObjResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainCourseObjResult record, @Param("example") CetTrainCourseObjResultExample example);

    int updateByExample(@Param("record") CetTrainCourseObjResult record, @Param("example") CetTrainCourseObjResultExample example);

    int updateByPrimaryKeySelective(CetTrainCourseObjResult record);

    int updateByPrimaryKey(CetTrainCourseObjResult record);
}