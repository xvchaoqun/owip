package persistence.base;

import domain.base.ApiKey;
import domain.base.ApiKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApiKeyMapper {
    long countByExample(ApiKeyExample example);

    int deleteByExample(ApiKeyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApiKey record);

    int insertSelective(ApiKey record);

    List<ApiKey> selectByExampleWithRowbounds(ApiKeyExample example, RowBounds rowBounds);

    List<ApiKey> selectByExample(ApiKeyExample example);

    ApiKey selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApiKey record, @Param("example") ApiKeyExample example);

    int updateByExample(@Param("record") ApiKey record, @Param("example") ApiKeyExample example);

    int updateByPrimaryKeySelective(ApiKey record);

    int updateByPrimaryKey(ApiKey record);
}