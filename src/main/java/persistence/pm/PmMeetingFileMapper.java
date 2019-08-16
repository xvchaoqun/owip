package persistence.pm;

import domain.pm.PmMeetingFile;
import domain.pm.PmMeetingFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmMeetingFileMapper {
    long countByExample(PmMeetingFileExample example);

    int deleteByExample(PmMeetingFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmMeetingFile record);

    int insertSelective(PmMeetingFile record);

    List<PmMeetingFile> selectByExampleWithRowbounds(PmMeetingFileExample example, RowBounds rowBounds);

    List<PmMeetingFile> selectByExample(PmMeetingFileExample example);

    PmMeetingFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmMeetingFile record, @Param("example") PmMeetingFileExample example);

    int updateByExample(@Param("record") PmMeetingFile record, @Param("example") PmMeetingFileExample example);

    int updateByPrimaryKeySelective(PmMeetingFile record);

    int updateByPrimaryKey(PmMeetingFile record);
}