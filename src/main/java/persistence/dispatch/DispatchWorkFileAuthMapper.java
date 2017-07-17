package persistence.dispatch;

import domain.dispatch.DispatchWorkFileAuth;
import domain.dispatch.DispatchWorkFileAuthExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DispatchWorkFileAuthMapper {
    long countByExample(DispatchWorkFileAuthExample example);

    int deleteByExample(DispatchWorkFileAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchWorkFileAuth record);

    int insertSelective(DispatchWorkFileAuth record);

    List<DispatchWorkFileAuth> selectByExampleWithRowbounds(DispatchWorkFileAuthExample example, RowBounds rowBounds);

    List<DispatchWorkFileAuth> selectByExample(DispatchWorkFileAuthExample example);

    DispatchWorkFileAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchWorkFileAuth record, @Param("example") DispatchWorkFileAuthExample example);

    int updateByExample(@Param("record") DispatchWorkFileAuth record, @Param("example") DispatchWorkFileAuthExample example);

    int updateByPrimaryKeySelective(DispatchWorkFileAuth record);

    int updateByPrimaryKey(DispatchWorkFileAuth record);
}