package persistence.oa;

import domain.oa.OaTaskUser;
import domain.oa.OaTaskUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaTaskUserMapper {
    long countByExample(OaTaskUserExample example);

    int deleteByExample(OaTaskUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OaTaskUser record);

    int insertSelective(OaTaskUser record);

    List<OaTaskUser> selectByExampleWithRowbounds(OaTaskUserExample example, RowBounds rowBounds);

    List<OaTaskUser> selectByExample(OaTaskUserExample example);

    OaTaskUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OaTaskUser record, @Param("example") OaTaskUserExample example);

    int updateByExample(@Param("record") OaTaskUser record, @Param("example") OaTaskUserExample example);

    int updateByPrimaryKeySelective(OaTaskUser record);

    int updateByPrimaryKey(OaTaskUser record);
}