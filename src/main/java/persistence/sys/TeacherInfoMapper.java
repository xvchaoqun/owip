package persistence.sys;

import domain.sys.TeacherInfo;
import domain.sys.TeacherInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TeacherInfoMapper {
    int countByExample(TeacherInfoExample example);

    int deleteByExample(TeacherInfoExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(TeacherInfo record);

    int insertSelective(TeacherInfo record);

    List<TeacherInfo> selectByExampleWithRowbounds(TeacherInfoExample example, RowBounds rowBounds);

    List<TeacherInfo> selectByExample(TeacherInfoExample example);

    TeacherInfo selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") TeacherInfo record, @Param("example") TeacherInfoExample example);

    int updateByExample(@Param("record") TeacherInfo record, @Param("example") TeacherInfoExample example);

    int updateByPrimaryKeySelective(TeacherInfo record);

    int updateByPrimaryKey(TeacherInfo record);
}