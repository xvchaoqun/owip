package persistence.cet;

import domain.cet.CetDiscuss;
import domain.cet.CetDiscussExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetDiscussMapper {
    long countByExample(CetDiscussExample example);

    int deleteByExample(CetDiscussExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetDiscuss record);

    int insertSelective(CetDiscuss record);

    List<CetDiscuss> selectByExampleWithRowbounds(CetDiscussExample example, RowBounds rowBounds);

    List<CetDiscuss> selectByExample(CetDiscussExample example);

    CetDiscuss selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetDiscuss record, @Param("example") CetDiscussExample example);

    int updateByExample(@Param("record") CetDiscuss record, @Param("example") CetDiscussExample example);

    int updateByPrimaryKeySelective(CetDiscuss record);

    int updateByPrimaryKey(CetDiscuss record);
}