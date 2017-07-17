package persistence.abroad;

import domain.abroad.PassportApplyView;
import domain.abroad.PassportApplyViewExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PassportApplyViewMapper {
    int countByExample(PassportApplyViewExample example);

    int deleteByExample(PassportApplyViewExample example);

    int insert(PassportApplyView record);

    int insertSelective(PassportApplyView record);

    List<PassportApplyView> selectByExampleWithRowbounds(PassportApplyViewExample example, RowBounds rowBounds);

    List<PassportApplyView> selectByExample(PassportApplyViewExample example);

    int updateByExampleSelective(@Param("record") PassportApplyView record, @Param("example") PassportApplyViewExample example);

    int updateByExample(@Param("record") PassportApplyView record, @Param("example") PassportApplyViewExample example);
}