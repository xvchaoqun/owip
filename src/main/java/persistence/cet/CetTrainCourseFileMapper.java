package persistence.cet;

import domain.cet.CetTrainCourseFile;
import domain.cet.CetTrainCourseFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainCourseFileMapper {
    long countByExample(CetTrainCourseFileExample example);

    int deleteByExample(CetTrainCourseFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainCourseFile record);

    int insertSelective(CetTrainCourseFile record);

    List<CetTrainCourseFile> selectByExampleWithRowbounds(CetTrainCourseFileExample example, RowBounds rowBounds);

    List<CetTrainCourseFile> selectByExample(CetTrainCourseFileExample example);

    CetTrainCourseFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainCourseFile record, @Param("example") CetTrainCourseFileExample example);

    int updateByExample(@Param("record") CetTrainCourseFile record, @Param("example") CetTrainCourseFileExample example);

    int updateByPrimaryKeySelective(CetTrainCourseFile record);

    int updateByPrimaryKey(CetTrainCourseFile record);
}