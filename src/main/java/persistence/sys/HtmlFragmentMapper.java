package persistence.sys;

import domain.sys.HtmlFragment;
import domain.sys.HtmlFragmentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface HtmlFragmentMapper {
    int countByExample(HtmlFragmentExample example);

    int deleteByExample(HtmlFragmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HtmlFragment record);

    int insertSelective(HtmlFragment record);

    List<HtmlFragment> selectByExampleWithRowbounds(HtmlFragmentExample example, RowBounds rowBounds);

    List<HtmlFragment> selectByExample(HtmlFragmentExample example);

    HtmlFragment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HtmlFragment record, @Param("example") HtmlFragmentExample example);

    int updateByExample(@Param("record") HtmlFragment record, @Param("example") HtmlFragmentExample example);

    int updateByPrimaryKeySelective(HtmlFragment record);

    int updateByPrimaryKey(HtmlFragment record);
}