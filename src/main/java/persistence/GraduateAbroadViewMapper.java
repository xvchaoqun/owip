package persistence;

import domain.GraduateAbroadView;
import domain.GraduateAbroadViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GraduateAbroadViewMapper {
    int countByExample(GraduateAbroadViewExample example);

    int deleteByExample(GraduateAbroadViewExample example);

    int insert(GraduateAbroadView record);

    int insertSelective(GraduateAbroadView record);

    List<GraduateAbroadView> selectByExampleWithRowbounds(GraduateAbroadViewExample example, RowBounds rowBounds);

    List<GraduateAbroadView> selectByExample(GraduateAbroadViewExample example);

    int updateByExampleSelective(@Param("record") GraduateAbroadView record, @Param("example") GraduateAbroadViewExample example);

    int updateByExample(@Param("record") GraduateAbroadView record, @Param("example") GraduateAbroadViewExample example);
}