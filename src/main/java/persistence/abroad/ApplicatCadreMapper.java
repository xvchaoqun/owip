package persistence.abroad;

import domain.abroad.ApplicatCadre;
import domain.abroad.ApplicatCadreExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

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