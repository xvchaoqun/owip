package persistence.sc.scSubsidy;

import domain.sc.scSubsidy.ScSubsidyCadre;
import domain.sc.scSubsidy.ScSubsidyCadreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScSubsidyCadreMapper {
    long countByExample(ScSubsidyCadreExample example);

    int deleteByExample(ScSubsidyCadreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScSubsidyCadre record);

    int insertSelective(ScSubsidyCadre record);

    List<ScSubsidyCadre> selectByExampleWithRowbounds(ScSubsidyCadreExample example, RowBounds rowBounds);

    List<ScSubsidyCadre> selectByExample(ScSubsidyCadreExample example);

    ScSubsidyCadre selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScSubsidyCadre record, @Param("example") ScSubsidyCadreExample example);

    int updateByExample(@Param("record") ScSubsidyCadre record, @Param("example") ScSubsidyCadreExample example);

    int updateByPrimaryKeySelective(ScSubsidyCadre record);

    int updateByPrimaryKey(ScSubsidyCadre record);
}