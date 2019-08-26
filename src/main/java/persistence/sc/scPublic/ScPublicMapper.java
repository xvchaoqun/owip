package persistence.sc.scPublic;

import domain.sc.scPublic.ScPublic;
import domain.sc.scPublic.ScPublicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScPublicMapper {
    long countByExample(ScPublicExample example);

    int deleteByExample(ScPublicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScPublic record);

    int insertSelective(ScPublic record);

    List<ScPublic> selectByExampleWithRowbounds(ScPublicExample example, RowBounds rowBounds);

    List<ScPublic> selectByExample(ScPublicExample example);

    ScPublic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScPublic record, @Param("example") ScPublicExample example);

    int updateByExample(@Param("record") ScPublic record, @Param("example") ScPublicExample example);

    int updateByPrimaryKeySelective(ScPublic record);

    int updateByPrimaryKey(ScPublic record);
}