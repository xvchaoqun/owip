package persistence.abroad;

import domain.abroad.PassportDrawFile;
import domain.abroad.PassportDrawFileExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PassportDrawFileMapper {
    int countByExample(PassportDrawFileExample example);

    int deleteByExample(PassportDrawFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PassportDrawFile record);

    int insertSelective(PassportDrawFile record);

    List<PassportDrawFile> selectByExampleWithRowbounds(PassportDrawFileExample example, RowBounds rowBounds);

    List<PassportDrawFile> selectByExample(PassportDrawFileExample example);

    PassportDrawFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PassportDrawFile record, @Param("example") PassportDrawFileExample example);

    int updateByExample(@Param("record") PassportDrawFile record, @Param("example") PassportDrawFileExample example);

    int updateByPrimaryKeySelective(PassportDrawFile record);

    int updateByPrimaryKey(PassportDrawFile record);
}