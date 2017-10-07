package persistence.cadre;

import domain.cadre.CadrePaper;
import domain.cadre.CadrePaperExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadrePaperMapper {
    long countByExample(CadrePaperExample example);

    int deleteByExample(CadrePaperExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadrePaper record);

    int insertSelective(CadrePaper record);

    List<CadrePaper> selectByExampleWithRowbounds(CadrePaperExample example, RowBounds rowBounds);

    List<CadrePaper> selectByExample(CadrePaperExample example);

    CadrePaper selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadrePaper record, @Param("example") CadrePaperExample example);

    int updateByExample(@Param("record") CadrePaper record, @Param("example") CadrePaperExample example);

    int updateByPrimaryKeySelective(CadrePaper record);

    int updateByPrimaryKey(CadrePaper record);
}