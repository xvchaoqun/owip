package persistence.pcs;

import domain.pcs.PcsRecommend;
import domain.pcs.PcsRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsRecommendMapper {
    long countByExample(PcsRecommendExample example);

    int deleteByExample(PcsRecommendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsRecommend record);

    int insertSelective(PcsRecommend record);

    List<PcsRecommend> selectByExampleWithRowbounds(PcsRecommendExample example, RowBounds rowBounds);

    List<PcsRecommend> selectByExample(PcsRecommendExample example);

    PcsRecommend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsRecommend record, @Param("example") PcsRecommendExample example);

    int updateByExample(@Param("record") PcsRecommend record, @Param("example") PcsRecommendExample example);

    int updateByPrimaryKeySelective(PcsRecommend record);

    int updateByPrimaryKey(PcsRecommend record);
}