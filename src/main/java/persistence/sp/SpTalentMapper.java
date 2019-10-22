package persistence.sp;

import domain.sp.SpTalent;
import domain.sp.SpTalentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SpTalentMapper {
    long countByExample(SpTalentExample example);

    int deleteByExample(SpTalentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpTalent record);

    int insertSelective(SpTalent record);

    List<SpTalent> selectByExampleWithRowbounds(SpTalentExample example, RowBounds rowBounds);

    List<SpTalent> selectByExample(SpTalentExample example);

    SpTalent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpTalent record, @Param("example") SpTalentExample example);

    int updateByExample(@Param("record") SpTalent record, @Param("example") SpTalentExample example);

    int updateByPrimaryKeySelective(SpTalent record);

    int updateByPrimaryKey(SpTalent record);
}