package persistence.dr;

import domain.dr.DrVoterTypeTpl;
import domain.dr.DrVoterTypeTplExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrVoterTypeTplMapper {
    long countByExample(DrVoterTypeTplExample example);

    int deleteByExample(DrVoterTypeTplExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrVoterTypeTpl record);

    int insertSelective(DrVoterTypeTpl record);

    List<DrVoterTypeTpl> selectByExampleWithRowbounds(DrVoterTypeTplExample example, RowBounds rowBounds);

    List<DrVoterTypeTpl> selectByExample(DrVoterTypeTplExample example);

    DrVoterTypeTpl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrVoterTypeTpl record, @Param("example") DrVoterTypeTplExample example);

    int updateByExample(@Param("record") DrVoterTypeTpl record, @Param("example") DrVoterTypeTplExample example);

    int updateByPrimaryKeySelective(DrVoterTypeTpl record);

    int updateByPrimaryKey(DrVoterTypeTpl record);
}