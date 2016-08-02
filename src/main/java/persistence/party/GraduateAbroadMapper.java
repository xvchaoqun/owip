package persistence.party;

import domain.party.GraduateAbroad;
import domain.party.GraduateAbroadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GraduateAbroadMapper {
    int countByExample(GraduateAbroadExample example);

    int deleteByExample(GraduateAbroadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GraduateAbroad record);

    int insertSelective(GraduateAbroad record);

    List<GraduateAbroad> selectByExampleWithRowbounds(GraduateAbroadExample example, RowBounds rowBounds);

    List<GraduateAbroad> selectByExample(GraduateAbroadExample example);

    GraduateAbroad selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GraduateAbroad record, @Param("example") GraduateAbroadExample example);

    int updateByExample(@Param("record") GraduateAbroad record, @Param("example") GraduateAbroadExample example);

    int updateByPrimaryKeySelective(GraduateAbroad record);

    int updateByPrimaryKey(GraduateAbroad record);
}