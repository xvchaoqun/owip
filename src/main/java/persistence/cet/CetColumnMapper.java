package persistence.cet;

import domain.cet.CetColumn;
import domain.cet.CetColumnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetColumnMapper {
    long countByExample(CetColumnExample example);

    int deleteByExample(CetColumnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetColumn record);

    int insertSelective(CetColumn record);

    List<CetColumn> selectByExampleWithRowbounds(CetColumnExample example, RowBounds rowBounds);

    List<CetColumn> selectByExample(CetColumnExample example);

    CetColumn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetColumn record, @Param("example") CetColumnExample example);

    int updateByExample(@Param("record") CetColumn record, @Param("example") CetColumnExample example);

    int updateByPrimaryKeySelective(CetColumn record);

    int updateByPrimaryKey(CetColumn record);
}