package persistence.cet;

import domain.cet.CetDiscussGroup;
import domain.cet.CetDiscussGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetDiscussGroupMapper {
    long countByExample(CetDiscussGroupExample example);

    int deleteByExample(CetDiscussGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetDiscussGroup record);

    int insertSelective(CetDiscussGroup record);

    List<CetDiscussGroup> selectByExampleWithRowbounds(CetDiscussGroupExample example, RowBounds rowBounds);

    List<CetDiscussGroup> selectByExample(CetDiscussGroupExample example);

    CetDiscussGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetDiscussGroup record, @Param("example") CetDiscussGroupExample example);

    int updateByExample(@Param("record") CetDiscussGroup record, @Param("example") CetDiscussGroupExample example);

    int updateByPrimaryKeySelective(CetDiscussGroup record);

    int updateByPrimaryKey(CetDiscussGroup record);
}