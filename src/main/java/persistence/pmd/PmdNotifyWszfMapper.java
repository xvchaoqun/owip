package persistence.pmd;

import domain.pmd.PmdNotifyWszf;
import domain.pmd.PmdNotifyWszfExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdNotifyWszfMapper {
    long countByExample(PmdNotifyWszfExample example);

    int deleteByExample(PmdNotifyWszfExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdNotifyWszf record);

    int insertSelective(PmdNotifyWszf record);

    List<PmdNotifyWszf> selectByExampleWithRowbounds(PmdNotifyWszfExample example, RowBounds rowBounds);

    List<PmdNotifyWszf> selectByExample(PmdNotifyWszfExample example);

    PmdNotifyWszf selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdNotifyWszf record, @Param("example") PmdNotifyWszfExample example);

    int updateByExample(@Param("record") PmdNotifyWszf record, @Param("example") PmdNotifyWszfExample example);

    int updateByPrimaryKeySelective(PmdNotifyWszf record);

    int updateByPrimaryKey(PmdNotifyWszf record);
}