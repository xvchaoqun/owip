package persistence.dr;

import domain.dr.DrVoterType;
import domain.dr.DrVoterTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrVoterTypeMapper {
    long countByExample(DrVoterTypeExample example);

    int deleteByExample(DrVoterTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrVoterType record);

    int insertSelective(DrVoterType record);

    List<DrVoterType> selectByExampleWithRowbounds(DrVoterTypeExample example, RowBounds rowBounds);

    List<DrVoterType> selectByExample(DrVoterTypeExample example);

    DrVoterType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrVoterType record, @Param("example") DrVoterTypeExample example);

    int updateByExample(@Param("record") DrVoterType record, @Param("example") DrVoterTypeExample example);

    int updateByPrimaryKeySelective(DrVoterType record);

    int updateByPrimaryKey(DrVoterType record);
}