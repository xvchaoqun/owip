package persistence.sys;

import domain.sys.AttachFile;
import domain.sys.AttachFileExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface AttachFileMapper {
    int countByExample(AttachFileExample example);

    int deleteByExample(AttachFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AttachFile record);

    int insertSelective(AttachFile record);

    List<AttachFile> selectByExampleWithRowbounds(AttachFileExample example, RowBounds rowBounds);

    List<AttachFile> selectByExample(AttachFileExample example);

    AttachFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AttachFile record, @Param("example") AttachFileExample example);

    int updateByExample(@Param("record") AttachFile record, @Param("example") AttachFileExample example);

    int updateByPrimaryKeySelective(AttachFile record);

    int updateByPrimaryKey(AttachFile record);
}