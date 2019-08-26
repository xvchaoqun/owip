package persistence.sc.scPublic;

import domain.sc.scPublic.ScPublicUser;
import domain.sc.scPublic.ScPublicUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScPublicUserMapper {
    long countByExample(ScPublicUserExample example);

    int deleteByExample(ScPublicUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScPublicUser record);

    int insertSelective(ScPublicUser record);

    List<ScPublicUser> selectByExampleWithRowbounds(ScPublicUserExample example, RowBounds rowBounds);

    List<ScPublicUser> selectByExample(ScPublicUserExample example);

    ScPublicUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScPublicUser record, @Param("example") ScPublicUserExample example);

    int updateByExample(@Param("record") ScPublicUser record, @Param("example") ScPublicUserExample example);

    int updateByPrimaryKeySelective(ScPublicUser record);

    int updateByPrimaryKey(ScPublicUser record);
}