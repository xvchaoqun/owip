package persistence;

import domain.RetireApply;
import domain.RetireApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RetireApplyMapper {
    int countByExample(RetireApplyExample example);

    int deleteByExample(RetireApplyExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(RetireApply record);

    int insertSelective(RetireApply record);

    List<RetireApply> selectByExampleWithRowbounds(RetireApplyExample example, RowBounds rowBounds);

    List<RetireApply> selectByExample(RetireApplyExample example);

    RetireApply selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") RetireApply record, @Param("example") RetireApplyExample example);

    int updateByExample(@Param("record") RetireApply record, @Param("example") RetireApplyExample example);

    int updateByPrimaryKeySelective(RetireApply record);

    int updateByPrimaryKey(RetireApply record);
}