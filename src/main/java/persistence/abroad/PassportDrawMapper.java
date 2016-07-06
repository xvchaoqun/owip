package persistence.abroad;

import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PassportDrawMapper {
    int countByExample(PassportDrawExample example);

    int deleteByExample(PassportDrawExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PassportDraw record);

    int insertSelective(PassportDraw record);

    List<PassportDraw> selectByExampleWithRowbounds(PassportDrawExample example, RowBounds rowBounds);

    List<PassportDraw> selectByExample(PassportDrawExample example);

    PassportDraw selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PassportDraw record, @Param("example") PassportDrawExample example);

    int updateByExample(@Param("record") PassportDraw record, @Param("example") PassportDrawExample example);

    int updateByPrimaryKeySelective(PassportDraw record);

    int updateByPrimaryKey(PassportDraw record);
}