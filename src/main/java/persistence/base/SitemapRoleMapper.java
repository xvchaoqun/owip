package persistence.base;

import domain.base.SitemapRole;
import domain.base.SitemapRoleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SitemapRoleMapper {
    int countByExample(SitemapRoleExample example);

    int deleteByExample(SitemapRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SitemapRole record);

    int insertSelective(SitemapRole record);

    List<SitemapRole> selectByExampleWithRowbounds(SitemapRoleExample example, RowBounds rowBounds);

    List<SitemapRole> selectByExample(SitemapRoleExample example);

    SitemapRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SitemapRole record, @Param("example") SitemapRoleExample example);

    int updateByExample(@Param("record") SitemapRole record, @Param("example") SitemapRoleExample example);

    int updateByPrimaryKeySelective(SitemapRole record);

    int updateByPrimaryKey(SitemapRole record);
}