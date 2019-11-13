package persistence.sys;

import domain.sys.SysMsg;
import domain.sys.SysMsgExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SysMsgMapper {
    long countByExample(SysMsgExample example);

    int deleteByExample(SysMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysMsg record);

    int insertSelective(SysMsg record);

    List<SysMsg> selectByExampleWithRowbounds(SysMsgExample example, RowBounds rowBounds);

    List<SysMsg> selectByExample(SysMsgExample example);

    SysMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysMsg record, @Param("example") SysMsgExample example);

    int updateByExample(@Param("record") SysMsg record, @Param("example") SysMsgExample example);

    int updateByPrimaryKeySelective(SysMsg record);

    int updateByPrimaryKey(SysMsg record);
}