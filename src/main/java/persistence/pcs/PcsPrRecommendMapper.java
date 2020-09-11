package persistence.pcs;

import domain.pcs.PcsPrRecommend;
import domain.pcs.PcsPrRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPrRecommendMapper {
    long countByExample(PcsPrRecommendExample example);

    int deleteByExample(PcsPrRecommendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPrRecommend record);

    int insertSelective(PcsPrRecommend record);

    List<PcsPrRecommend> selectByExampleWithRowbounds(PcsPrRecommendExample example, RowBounds rowBounds);

    List<PcsPrRecommend> selectByExample(PcsPrRecommendExample example);

    PcsPrRecommend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPrRecommend record, @Param("example") PcsPrRecommendExample example);

    int updateByExample(@Param("record") PcsPrRecommend record, @Param("example") PcsPrRecommendExample example);

    int updateByPrimaryKeySelective(PcsPrRecommend record);

    int updateByPrimaryKey(PcsPrRecommend record);
}