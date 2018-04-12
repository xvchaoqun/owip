package persistence.cet;

import domain.cet.CetDiscussGroupObj;
import domain.cet.CetDiscussGroupObjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetDiscussGroupObjMapper {
    long countByExample(CetDiscussGroupObjExample example);

    int deleteByExample(CetDiscussGroupObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetDiscussGroupObj record);

    int insertSelective(CetDiscussGroupObj record);

    List<CetDiscussGroupObj> selectByExampleWithRowbounds(CetDiscussGroupObjExample example, RowBounds rowBounds);

    List<CetDiscussGroupObj> selectByExample(CetDiscussGroupObjExample example);

    CetDiscussGroupObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetDiscussGroupObj record, @Param("example") CetDiscussGroupObjExample example);

    int updateByExample(@Param("record") CetDiscussGroupObj record, @Param("example") CetDiscussGroupObjExample example);

    int updateByPrimaryKeySelective(CetDiscussGroupObj record);

    int updateByPrimaryKey(CetDiscussGroupObj record);
}