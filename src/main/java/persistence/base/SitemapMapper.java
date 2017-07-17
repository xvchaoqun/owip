package persistence.base;

import domain.base.Sitemap;
import domain.base.SitemapExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SitemapMapper {
    int countByExample(SitemapExample example);

    int deleteByExample(SitemapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sitemap record);

    int insertSelective(Sitemap record);

    List<Sitemap> selectByExampleWithRowbounds(SitemapExample example, RowBounds rowBounds);

    List<Sitemap> selectByExample(SitemapExample example);

    Sitemap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sitemap record, @Param("example") SitemapExample example);

    int updateByExample(@Param("record") Sitemap record, @Param("example") SitemapExample example);

    int updateByPrimaryKeySelective(Sitemap record);

    int updateByPrimaryKey(Sitemap record);
}