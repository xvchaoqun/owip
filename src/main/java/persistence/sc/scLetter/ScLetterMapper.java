package persistence.sc.scLetter;

import domain.sc.scLetter.ScLetter;
import domain.sc.scLetter.ScLetterExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScLetterMapper {
    long countByExample(ScLetterExample example);

    int deleteByExample(ScLetterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScLetter record);

    int insertSelective(ScLetter record);

    List<ScLetter> selectByExampleWithRowbounds(ScLetterExample example, RowBounds rowBounds);

    List<ScLetter> selectByExample(ScLetterExample example);

    ScLetter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScLetter record, @Param("example") ScLetterExample example);

    int updateByExample(@Param("record") ScLetter record, @Param("example") ScLetterExample example);

    int updateByPrimaryKeySelective(ScLetter record);

    int updateByPrimaryKey(ScLetter record);
}