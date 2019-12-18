package persistence.dp;

import domain.dp.DpFamily;
import domain.dp.DpFamilyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpFamilyMapper {
    long countByExample(DpFamilyExample example);

    int deleteByExample(DpFamilyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpFamily record);

    int insertSelective(DpFamily record);

    List<DpFamily> selectByExampleWithRowbounds(DpFamilyExample example, RowBounds rowBounds);

    List<DpFamily> selectByExample(DpFamilyExample example);

    DpFamily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpFamily record, @Param("example") DpFamilyExample example);

    int updateByExample(@Param("record") DpFamily record, @Param("example") DpFamilyExample example);

    int updateByPrimaryKeySelective(DpFamily record);

    int updateByPrimaryKey(DpFamily record);
}