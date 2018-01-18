package persistence.sc;

import domain.sc.ScMatterTransfer;
import domain.sc.ScMatterTransferExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterTransferMapper {
    long countByExample(ScMatterTransferExample example);

    int deleteByExample(ScMatterTransferExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMatterTransfer record);

    int insertSelective(ScMatterTransfer record);

    List<ScMatterTransfer> selectByExampleWithRowbounds(ScMatterTransferExample example, RowBounds rowBounds);

    List<ScMatterTransfer> selectByExample(ScMatterTransferExample example);

    ScMatterTransfer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMatterTransfer record, @Param("example") ScMatterTransferExample example);

    int updateByExample(@Param("record") ScMatterTransfer record, @Param("example") ScMatterTransferExample example);

    int updateByPrimaryKeySelective(ScMatterTransfer record);

    int updateByPrimaryKey(ScMatterTransfer record);
}