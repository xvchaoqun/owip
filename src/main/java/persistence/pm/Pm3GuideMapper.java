package persistence.pm;

import domain.pm.Pm3Guide;
import domain.pm.Pm3GuideExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface Pm3GuideMapper {
    long countByExample(Pm3GuideExample example);

    int deleteByExample(Pm3GuideExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Pm3Guide record);

    int insertSelective(Pm3Guide record);

    List<Pm3Guide> selectByExampleWithRowbounds(Pm3GuideExample example, RowBounds rowBounds);

    List<Pm3Guide> selectByExample(Pm3GuideExample example);

    Pm3Guide selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Pm3Guide record, @Param("example") Pm3GuideExample example);

    int updateByExample(@Param("record") Pm3Guide record, @Param("example") Pm3GuideExample example);

    int updateByPrimaryKeySelective(Pm3Guide record);

    int updateByPrimaryKey(Pm3Guide record);
}