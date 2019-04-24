package persistence.member;

import domain.member.ApplySn;
import domain.member.ApplySnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApplySnMapper {
    long countByExample(ApplySnExample example);

    int deleteByExample(ApplySnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplySn record);

    int insertSelective(ApplySn record);

    List<ApplySn> selectByExampleWithRowbounds(ApplySnExample example, RowBounds rowBounds);

    List<ApplySn> selectByExample(ApplySnExample example);

    ApplySn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplySn record, @Param("example") ApplySnExample example);

    int updateByExample(@Param("record") ApplySn record, @Param("example") ApplySnExample example);

    int updateByPrimaryKeySelective(ApplySn record);

    int updateByPrimaryKey(ApplySn record);
}