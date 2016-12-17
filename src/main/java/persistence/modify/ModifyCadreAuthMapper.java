package persistence.modify;

import domain.modify.ModifyCadreAuth;
import domain.modify.ModifyCadreAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ModifyCadreAuthMapper {
    int countByExample(ModifyCadreAuthExample example);

    int deleteByExample(ModifyCadreAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ModifyCadreAuth record);

    int insertSelective(ModifyCadreAuth record);

    List<ModifyCadreAuth> selectByExampleWithRowbounds(ModifyCadreAuthExample example, RowBounds rowBounds);

    List<ModifyCadreAuth> selectByExample(ModifyCadreAuthExample example);

    ModifyCadreAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ModifyCadreAuth record, @Param("example") ModifyCadreAuthExample example);

    int updateByExample(@Param("record") ModifyCadreAuth record, @Param("example") ModifyCadreAuthExample example);

    int updateByPrimaryKeySelective(ModifyCadreAuth record);

    int updateByPrimaryKey(ModifyCadreAuth record);
}