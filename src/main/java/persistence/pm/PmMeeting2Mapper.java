package persistence.pm;

import domain.pm.PmMeeting2;
import domain.pm.PmMeeting2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmMeeting2Mapper {
    long countByExample(PmMeeting2Example example);

    int deleteByExample(PmMeeting2Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmMeeting2 record);

    int insertSelective(PmMeeting2 record);

    List<PmMeeting2> selectByExampleWithRowbounds(PmMeeting2Example example, RowBounds rowBounds);

    List<PmMeeting2> selectByExample(PmMeeting2Example example);

    PmMeeting2 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmMeeting2 record, @Param("example") PmMeeting2Example example);

    int updateByExample(@Param("record") PmMeeting2 record, @Param("example") PmMeeting2Example example);

    int updateByPrimaryKeySelective(PmMeeting2 record);

    int updateByPrimaryKey(PmMeeting2 record);
}