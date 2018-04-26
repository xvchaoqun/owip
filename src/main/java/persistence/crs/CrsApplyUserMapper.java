package persistence.crs;

import domain.crs.CrsApplyUser;
import domain.crs.CrsApplyUserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsApplyUserMapper {
    long countByExample(CrsApplyUserExample example);

    int deleteByExample(CrsApplyUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsApplyUser record);

    int insertSelective(CrsApplyUser record);

    List<CrsApplyUser> selectByExampleWithRowbounds(CrsApplyUserExample example, RowBounds rowBounds);

    List<CrsApplyUser> selectByExample(CrsApplyUserExample example);

    CrsApplyUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsApplyUser record, @Param("example") CrsApplyUserExample example);

    int updateByExample(@Param("record") CrsApplyUser record, @Param("example") CrsApplyUserExample example);

    int updateByPrimaryKeySelective(CrsApplyUser record);

    int updateByPrimaryKey(CrsApplyUser record);
}