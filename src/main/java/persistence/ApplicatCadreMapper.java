package persistence;

import domain.ApplicatCadre;
import domain.ApplicatCadreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApplicatCadreMapper {
    int countByExample(ApplicatCadreExample example);

    int deleteByExample(ApplicatCadreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplicatCadre record);

    int insertSelective(ApplicatCadre record);

    List<ApplicatCadre> selectByExampleWithRowbounds(ApplicatCadreExample example, RowBounds rowBounds);

    List<ApplicatCadre> selectByExample(ApplicatCadreExample example);

    ApplicatCadre selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplicatCadre record, @Param("example") ApplicatCadreExample example);

    int updateByExample(@Param("record") ApplicatCadre record, @Param("example") ApplicatCadreExample example);

    int updateByPrimaryKeySelective(ApplicatCadre record);

    int updateByPrimaryKey(ApplicatCadre record);
}