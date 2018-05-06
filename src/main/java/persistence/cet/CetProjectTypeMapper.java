package persistence.cet;

import domain.cet.CetProjectType;
import domain.cet.CetProjectTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectTypeMapper {
    long countByExample(CetProjectTypeExample example);

    int deleteByExample(CetProjectTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetProjectType record);

    int insertSelective(CetProjectType record);

    List<CetProjectType> selectByExampleWithRowbounds(CetProjectTypeExample example, RowBounds rowBounds);

    List<CetProjectType> selectByExample(CetProjectTypeExample example);

    CetProjectType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetProjectType record, @Param("example") CetProjectTypeExample example);

    int updateByExample(@Param("record") CetProjectType record, @Param("example") CetProjectTypeExample example);

    int updateByPrimaryKeySelective(CetProjectType record);

    int updateByPrimaryKey(CetProjectType record);
}