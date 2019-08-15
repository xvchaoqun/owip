package persistence.dp;

import domain.dp.DpMemberOut;
import domain.dp.DpMemberOutExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpMemberOutMapper {
    long countByExample(DpMemberOutExample example);

    int deleteByExample(DpMemberOutExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpMemberOut record);

    int insertSelective(DpMemberOut record);

    List<DpMemberOut> selectByExampleWithRowbounds(DpMemberOutExample example, RowBounds rowBounds);

    List<DpMemberOut> selectByExample(DpMemberOutExample example);

    DpMemberOut selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpMemberOut record, @Param("example") DpMemberOutExample example);

    int updateByExample(@Param("record") DpMemberOut record, @Param("example") DpMemberOutExample example);

    int updateByPrimaryKeySelective(DpMemberOut record);

    int updateByPrimaryKey(DpMemberOut record);
}