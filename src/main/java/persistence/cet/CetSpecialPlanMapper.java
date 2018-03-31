package persistence.cet;

import domain.cet.CetSpecialPlan;
import domain.cet.CetSpecialPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetSpecialPlanMapper {
    long countByExample(CetSpecialPlanExample example);

    int deleteByExample(CetSpecialPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetSpecialPlan record);

    int insertSelective(CetSpecialPlan record);

    List<CetSpecialPlan> selectByExampleWithBLOBsWithRowbounds(CetSpecialPlanExample example, RowBounds rowBounds);

    List<CetSpecialPlan> selectByExampleWithBLOBs(CetSpecialPlanExample example);

    List<CetSpecialPlan> selectByExampleWithRowbounds(CetSpecialPlanExample example, RowBounds rowBounds);

    List<CetSpecialPlan> selectByExample(CetSpecialPlanExample example);

    CetSpecialPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetSpecialPlan record, @Param("example") CetSpecialPlanExample example);

    int updateByExampleWithBLOBs(@Param("record") CetSpecialPlan record, @Param("example") CetSpecialPlanExample example);

    int updateByExample(@Param("record") CetSpecialPlan record, @Param("example") CetSpecialPlanExample example);

    int updateByPrimaryKeySelective(CetSpecialPlan record);

    int updateByPrimaryKeyWithBLOBs(CetSpecialPlan record);

    int updateByPrimaryKey(CetSpecialPlan record);
}