package persistence.cet;

import domain.cet.CetAnnualObj;
import domain.cet.CetAnnualObjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetAnnualObjMapper {
    long countByExample(CetAnnualObjExample example);

    int deleteByExample(CetAnnualObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetAnnualObj record);

    int insertSelective(CetAnnualObj record);

    List<CetAnnualObj> selectByExampleWithRowbounds(CetAnnualObjExample example, RowBounds rowBounds);

    List<CetAnnualObj> selectByExample(CetAnnualObjExample example);

    CetAnnualObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetAnnualObj record, @Param("example") CetAnnualObjExample example);

    int updateByExample(@Param("record") CetAnnualObj record, @Param("example") CetAnnualObjExample example);

    int updateByPrimaryKeySelective(CetAnnualObj record);

    int updateByPrimaryKey(CetAnnualObj record);
}