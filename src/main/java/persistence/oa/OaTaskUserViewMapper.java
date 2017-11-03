package persistence.oa;

import domain.oa.OaTaskUserView;
import domain.oa.OaTaskUserViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaTaskUserViewMapper {
    long countByExample(OaTaskUserViewExample example);

    int deleteByExample(OaTaskUserViewExample example);

    int insert(OaTaskUserView record);

    int insertSelective(OaTaskUserView record);

    List<OaTaskUserView> selectByExampleWithBLOBsWithRowbounds(OaTaskUserViewExample example, RowBounds rowBounds);

    List<OaTaskUserView> selectByExampleWithBLOBs(OaTaskUserViewExample example);

    List<OaTaskUserView> selectByExampleWithRowbounds(OaTaskUserViewExample example, RowBounds rowBounds);

    List<OaTaskUserView> selectByExample(OaTaskUserViewExample example);

    int updateByExampleSelective(@Param("record") OaTaskUserView record, @Param("example") OaTaskUserViewExample example);

    int updateByExampleWithBLOBs(@Param("record") OaTaskUserView record, @Param("example") OaTaskUserViewExample example);

    int updateByExample(@Param("record") OaTaskUserView record, @Param("example") OaTaskUserViewExample example);
}