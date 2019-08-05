package ext.persistence;

import ext.domain.ExtBks;
import ext.domain.ExtBksExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExtBksMapper {
    long countByExample(ExtBksExample example);

    int deleteByExample(ExtBksExample example);

    int deleteByPrimaryKey(String xh);

    int insert(ExtBks record);

    int insertSelective(ExtBks record);

    List<ExtBks> selectByExampleWithRowbounds(ExtBksExample example, RowBounds rowBounds);

    List<ExtBks> selectByExample(ExtBksExample example);

    ExtBks selectByPrimaryKey(String xh);

    int updateByExampleSelective(@Param("record") ExtBks record, @Param("example") ExtBksExample example);

    int updateByExample(@Param("record") ExtBks record, @Param("example") ExtBksExample example);

    int updateByPrimaryKeySelective(ExtBks record);

    int updateByPrimaryKey(ExtBks record);
}