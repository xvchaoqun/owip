package persistence;

import domain.DispatchCadre;
import domain.DispatchCadreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DispatchCadreMapper {
    int countByExample(DispatchCadreExample example);

    int deleteByExample(DispatchCadreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchCadre record);

    int insertSelective(DispatchCadre record);

    List<DispatchCadre> selectByExampleWithRowbounds(DispatchCadreExample example, RowBounds rowBounds);

    List<DispatchCadre> selectByExample(DispatchCadreExample example);

    DispatchCadre selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchCadre record, @Param("example") DispatchCadreExample example);

    int updateByExample(@Param("record") DispatchCadre record, @Param("example") DispatchCadreExample example);

    int updateByPrimaryKeySelective(DispatchCadre record);

    int updateByPrimaryKey(DispatchCadre record);
}