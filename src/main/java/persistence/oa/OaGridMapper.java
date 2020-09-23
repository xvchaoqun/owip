package persistence.oa;

import domain.oa.OaGrid;
import domain.oa.OaGridExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaGridMapper {
    long countByExample(OaGridExample example);

    int deleteByExample(OaGridExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OaGrid record);

    int insertSelective(OaGrid record);

    List<OaGrid> selectByExampleWithRowbounds(OaGridExample example, RowBounds rowBounds);

    List<OaGrid> selectByExample(OaGridExample example);

    OaGrid selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OaGrid record, @Param("example") OaGridExample example);

    int updateByExample(@Param("record") OaGrid record, @Param("example") OaGridExample example);

    int updateByPrimaryKeySelective(OaGrid record);

    int updateByPrimaryKey(OaGrid record);
}