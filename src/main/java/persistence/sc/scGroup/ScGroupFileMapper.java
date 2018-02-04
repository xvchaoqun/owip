package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroupFile;
import domain.sc.scGroup.ScGroupFileExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScGroupFileMapper {
    long countByExample(ScGroupFileExample example);

    int deleteByExample(ScGroupFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScGroupFile record);

    int insertSelective(ScGroupFile record);

    List<ScGroupFile> selectByExampleWithRowbounds(ScGroupFileExample example, RowBounds rowBounds);

    List<ScGroupFile> selectByExample(ScGroupFileExample example);

    ScGroupFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScGroupFile record, @Param("example") ScGroupFileExample example);

    int updateByExample(@Param("record") ScGroupFile record, @Param("example") ScGroupFileExample example);

    int updateByPrimaryKeySelective(ScGroupFile record);

    int updateByPrimaryKey(ScGroupFile record);
}