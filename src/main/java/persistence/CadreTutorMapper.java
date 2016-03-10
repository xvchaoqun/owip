package persistence;

import domain.CadreTutor;
import domain.CadreTutorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreTutorMapper {
    int countByExample(CadreTutorExample example);

    int deleteByExample(CadreTutorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreTutor record);

    int insertSelective(CadreTutor record);

    List<CadreTutor> selectByExampleWithRowbounds(CadreTutorExample example, RowBounds rowBounds);

    List<CadreTutor> selectByExample(CadreTutorExample example);

    CadreTutor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreTutor record, @Param("example") CadreTutorExample example);

    int updateByExample(@Param("record") CadreTutor record, @Param("example") CadreTutorExample example);

    int updateByPrimaryKeySelective(CadreTutor record);

    int updateByPrimaryKey(CadreTutor record);
}