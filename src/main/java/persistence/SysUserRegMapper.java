package persistence;

import domain.SysUserReg;
import domain.SysUserRegExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SysUserRegMapper {
    int countByExample(SysUserRegExample example);

    int deleteByExample(SysUserRegExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysUserReg record);

    int insertSelective(SysUserReg record);

    List<SysUserReg> selectByExampleWithRowbounds(SysUserRegExample example, RowBounds rowBounds);

    List<SysUserReg> selectByExample(SysUserRegExample example);

    SysUserReg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysUserReg record, @Param("example") SysUserRegExample example);

    int updateByExample(@Param("record") SysUserReg record, @Param("example") SysUserRegExample example);

    int updateByPrimaryKeySelective(SysUserReg record);

    int updateByPrimaryKey(SysUserReg record);
}