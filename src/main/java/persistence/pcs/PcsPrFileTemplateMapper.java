package persistence.pcs;

import domain.pcs.PcsPrFileTemplate;
import domain.pcs.PcsPrFileTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPrFileTemplateMapper {
    long countByExample(PcsPrFileTemplateExample example);

    int deleteByExample(PcsPrFileTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPrFileTemplate record);

    int insertSelective(PcsPrFileTemplate record);

    List<PcsPrFileTemplate> selectByExampleWithRowbounds(PcsPrFileTemplateExample example, RowBounds rowBounds);

    List<PcsPrFileTemplate> selectByExample(PcsPrFileTemplateExample example);

    PcsPrFileTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPrFileTemplate record, @Param("example") PcsPrFileTemplateExample example);

    int updateByExample(@Param("record") PcsPrFileTemplate record, @Param("example") PcsPrFileTemplateExample example);

    int updateByPrimaryKeySelective(PcsPrFileTemplate record);

    int updateByPrimaryKey(PcsPrFileTemplate record);
}