package persistence.cet;

import domain.cet.CetProject;
import domain.cet.CetProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectMapper {
    long countByExample(CetProjectExample example);

    int deleteByExample(CetProjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetProject record);

    int insertSelective(CetProject record);

    List<CetProject> selectByExampleWithRowbounds(CetProjectExample example, RowBounds rowBounds);

    List<CetProject> selectByExample(CetProjectExample example);

    CetProject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetProject record, @Param("example") CetProjectExample example);

    int updateByExample(@Param("record") CetProject record, @Param("example") CetProjectExample example);

    int updateByPrimaryKeySelective(CetProject record);

    int updateByPrimaryKey(CetProject record);
}