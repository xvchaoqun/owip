package persistence.base;

import domain.base.AnnualType;
import domain.base.AnnualTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AnnualTypeMapper {
    long countByExample(AnnualTypeExample example);

    int deleteByExample(AnnualTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AnnualType record);

    int insertSelective(AnnualType record);

    List<AnnualType> selectByExampleWithRowbounds(AnnualTypeExample example, RowBounds rowBounds);

    List<AnnualType> selectByExample(AnnualTypeExample example);

    AnnualType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AnnualType record, @Param("example") AnnualTypeExample example);

    int updateByExample(@Param("record") AnnualType record, @Param("example") AnnualTypeExample example);

    int updateByPrimaryKeySelective(AnnualType record);

    int updateByPrimaryKey(AnnualType record);
}