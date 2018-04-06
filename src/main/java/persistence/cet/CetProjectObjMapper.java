package persistence.cet;

import domain.cet.CetProjectObj;
import domain.cet.CetProjectObjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectObjMapper {
    long countByExample(CetProjectObjExample example);

    int deleteByExample(CetProjectObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetProjectObj record);

    int insertSelective(CetProjectObj record);

    List<CetProjectObj> selectByExampleWithRowbounds(CetProjectObjExample example, RowBounds rowBounds);

    List<CetProjectObj> selectByExample(CetProjectObjExample example);

    CetProjectObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetProjectObj record, @Param("example") CetProjectObjExample example);

    int updateByExample(@Param("record") CetProjectObj record, @Param("example") CetProjectObjExample example);

    int updateByPrimaryKeySelective(CetProjectObj record);

    int updateByPrimaryKey(CetProjectObj record);
}