package persistence.cet;

import domain.cet.CetProjectFile;
import domain.cet.CetProjectFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectFileMapper {
    long countByExample(CetProjectFileExample example);

    int deleteByExample(CetProjectFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetProjectFile record);

    int insertSelective(CetProjectFile record);

    List<CetProjectFile> selectByExampleWithRowbounds(CetProjectFileExample example, RowBounds rowBounds);

    List<CetProjectFile> selectByExample(CetProjectFileExample example);

    CetProjectFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetProjectFile record, @Param("example") CetProjectFileExample example);

    int updateByExample(@Param("record") CetProjectFile record, @Param("example") CetProjectFileExample example);

    int updateByPrimaryKeySelective(CetProjectFile record);

    int updateByPrimaryKey(CetProjectFile record);
}