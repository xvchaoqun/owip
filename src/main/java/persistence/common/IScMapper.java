package persistence.common;

import domain.sc.scMatter.ScMatterAccess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by lm on 2018/1/23.
 */
public interface IScMapper {

    @ResultMap("persistence.sc.scMatter.ScMatterAccessMapper.BaseResultMap")
    @Select("select sma.* from sc_matter_access sma, sc_matter_access_item smai " +
            "where smai.matter_item_id=#{matterItemId} and sma.is_deleted=0 and sma.id=smai.access_id order by sma.access_date desc, sma.id desc")
    public List<ScMatterAccess> selectScMatterAccessList(@Param("matterItemId") int matterItemId, RowBounds rowBounds);
    @Select("select count(distinct sma.id) from sc_matter_access sma, sc_matter_access_item smai " +
            "where smai.matter_item_id=#{matterItemId} and sma.is_deleted=0 and sma.id=smai.access_id")
    int countScMatterAccess(@Param("matterItemId") int matterItemId);
}
