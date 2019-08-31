package persistence.cadre;

import domain.cadre.CadrePunish;
import domain.cadre.CadrePunishExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadrePunishMapper {
    long countByExample(CadrePunishExample example);

    int deleteByExample(CadrePunishExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadrePunish record);

    int insertSelective(CadrePunish record);

    List<CadrePunish> selectByExampleWithRowbounds(CadrePunishExample example, RowBounds rowBounds);

    List<CadrePunish> selectByExample(CadrePunishExample example);

    CadrePunish selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadrePunish record, @Param("example") CadrePunishExample example);

    int updateByExample(@Param("record") CadrePunish record, @Param("example") CadrePunishExample example);

    int updateByPrimaryKeySelective(CadrePunish record);

    int updateByPrimaryKey(CadrePunish record);
}