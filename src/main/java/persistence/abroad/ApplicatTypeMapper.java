package persistence.abroad;

import domain.abroad.ApplicatType;
import domain.abroad.ApplicatTypeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ApplicatTypeMapper {
    int countByExample(ApplicatTypeExample example);

    int deleteByExample(ApplicatTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplicatType record);

    int insertSelective(ApplicatType record);

    List<ApplicatType> selectByExampleWithRowbounds(ApplicatTypeExample example, RowBounds rowBounds);

    List<ApplicatType> selectByExample(ApplicatTypeExample example);

    ApplicatType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplicatType record, @Param("example") ApplicatTypeExample example);

    int updateByExample(@Param("record") ApplicatType record, @Param("example") ApplicatTypeExample example);

    int updateByPrimaryKeySelective(ApplicatType record);

    int updateByPrimaryKey(ApplicatType record);
}