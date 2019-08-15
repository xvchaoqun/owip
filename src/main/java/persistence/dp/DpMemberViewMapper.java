package persistence.dp;

import domain.dp.DpMemberView;
import domain.dp.DpMemberViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpMemberViewMapper {
    long countByExample(DpMemberViewExample example);

    int deleteByExample(DpMemberViewExample example);

    int insert(DpMemberView record);

    int insertSelective(DpMemberView record);

    List<DpMemberView> selectByExampleWithRowbounds(DpMemberViewExample example, RowBounds rowBounds);

    List<DpMemberView> selectByExample(DpMemberViewExample example);

    int updateByExampleSelective(@Param("record") DpMemberView record, @Param("example") DpMemberViewExample example);

    int updateByExample(@Param("record") DpMemberView record, @Param("example") DpMemberViewExample example);
}