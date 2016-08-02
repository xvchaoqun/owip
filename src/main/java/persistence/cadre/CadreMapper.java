package persistence.cadre;

import domain.cadre.Cadre;
import domain.cadre.CadreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreMapper {
    int countByExample(CadreExample example);

    int deleteByExample(CadreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cadre record);

    int insertSelective(Cadre record);

    List<Cadre> selectByExampleWithRowbounds(CadreExample example, RowBounds rowBounds);

    List<Cadre> selectByExample(CadreExample example);

    Cadre selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cadre record, @Param("example") CadreExample example);

    int updateByExample(@Param("record") Cadre record, @Param("example") CadreExample example);

    int updateByPrimaryKeySelective(Cadre record);

    int updateByPrimaryKey(Cadre record);
}