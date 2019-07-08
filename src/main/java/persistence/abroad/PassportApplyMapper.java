package persistence.abroad;

import domain.abroad.PassportApply;
import domain.abroad.PassportApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PassportApplyMapper {
    long countByExample(PassportApplyExample example);

    int deleteByExample(PassportApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PassportApply record);

    int insertSelective(PassportApply record);

    List<PassportApply> selectByExampleWithRowbounds(PassportApplyExample example, RowBounds rowBounds);

    List<PassportApply> selectByExample(PassportApplyExample example);

    PassportApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PassportApply record, @Param("example") PassportApplyExample example);

    int updateByExample(@Param("record") PassportApply record, @Param("example") PassportApplyExample example);

    int updateByPrimaryKeySelective(PassportApply record);

    int updateByPrimaryKey(PassportApply record);
}