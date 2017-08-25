package persistence.sys;

import domain.sys.SysConfigLoginMsg;
import domain.sys.SysConfigLoginMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SysConfigLoginMsgMapper {
    long countByExample(SysConfigLoginMsgExample example);

    int deleteByExample(SysConfigLoginMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysConfigLoginMsg record);

    int insertSelective(SysConfigLoginMsg record);

    List<SysConfigLoginMsg> selectByExampleWithRowbounds(SysConfigLoginMsgExample example, RowBounds rowBounds);

    List<SysConfigLoginMsg> selectByExample(SysConfigLoginMsgExample example);

    SysConfigLoginMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysConfigLoginMsg record, @Param("example") SysConfigLoginMsgExample example);

    int updateByExample(@Param("record") SysConfigLoginMsg record, @Param("example") SysConfigLoginMsgExample example);

    int updateByPrimaryKeySelective(SysConfigLoginMsg record);

    int updateByPrimaryKey(SysConfigLoginMsg record);
}