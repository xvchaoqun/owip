package persistence.sc;

import domain.sc.ScMatterTransferItem;
import domain.sc.ScMatterTransferItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterTransferItemMapper {
    long countByExample(ScMatterTransferItemExample example);

    int deleteByExample(ScMatterTransferItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMatterTransferItem record);

    int insertSelective(ScMatterTransferItem record);

    List<ScMatterTransferItem> selectByExampleWithRowbounds(ScMatterTransferItemExample example, RowBounds rowBounds);

    List<ScMatterTransferItem> selectByExample(ScMatterTransferItemExample example);

    ScMatterTransferItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMatterTransferItem record, @Param("example") ScMatterTransferItemExample example);

    int updateByExample(@Param("record") ScMatterTransferItem record, @Param("example") ScMatterTransferItemExample example);

    int updateByPrimaryKeySelective(ScMatterTransferItem record);

    int updateByPrimaryKey(ScMatterTransferItem record);
}