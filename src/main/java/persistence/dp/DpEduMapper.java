package persistence.dp;

import domain.dp.DpEdu;
import domain.dp.DpEduExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DpEduMapper {
    long countByExample(DpEduExample example);

    int deleteByExample(DpEduExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpEdu record);

    int insertSelective(DpEdu record);

    List<DpEdu> selectByExampleWithRowbounds(DpEduExample example, RowBounds rowBounds);

    List<DpEdu> selectByExample(DpEduExample example);

    DpEdu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpEdu record, @Param("example") DpEduExample example);

    int updateByExample(@Param("record") DpEdu record, @Param("example") DpEduExample example);

    int updateByPrimaryKeySelective(DpEdu record);

    int updateByPrimaryKey(DpEdu record);
}