package persistence.cet;

import domain.cet.CetCourseFile;
import domain.cet.CetCourseFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetCourseFileMapper {
    long countByExample(CetCourseFileExample example);

    int deleteByExample(CetCourseFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetCourseFile record);

    int insertSelective(CetCourseFile record);

    List<CetCourseFile> selectByExampleWithRowbounds(CetCourseFileExample example, RowBounds rowBounds);

    List<CetCourseFile> selectByExample(CetCourseFileExample example);

    CetCourseFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetCourseFile record, @Param("example") CetCourseFileExample example);

    int updateByExample(@Param("record") CetCourseFile record, @Param("example") CetCourseFileExample example);

    int updateByPrimaryKeySelective(CetCourseFile record);

    int updateByPrimaryKey(CetCourseFile record);
}