package persistence.ces;

import domain.ces.CesResult;
import domain.ces.CesResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CesResultMapper {
    long countByExample(CesResultExample example);

    int deleteByExample(CesResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CesResult record);

    int insertSelective(CesResult record);

    List<CesResult> selectByExampleWithRowbounds(CesResultExample example, RowBounds rowBounds);

    List<CesResult> selectByExample(CesResultExample example);

    CesResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CesResult record, @Param("example") CesResultExample example);

    int updateByExample(@Param("record") CesResult record, @Param("example") CesResultExample example);

    int updateByPrimaryKeySelective(CesResult record);

    int updateByPrimaryKey(CesResult record);
}