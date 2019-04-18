package persistence.member;

import domain.member.ApplySn;
import domain.member.ApplySnExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ApplySnMapper {
    long countByExample(ApplySnExample example);

    int deleteByExample(ApplySnExample example);

    int deleteByPrimaryKey(Long sn);

    int insert(ApplySn record);

    int insertSelective(ApplySn record);

    List<ApplySn> selectByExampleWithRowbounds(ApplySnExample example, RowBounds rowBounds);

    List<ApplySn> selectByExample(ApplySnExample example);

    ApplySn selectByPrimaryKey(Long sn);

    int updateByExampleSelective(@Param("record") ApplySn record, @Param("example") ApplySnExample example);

    int updateByExample(@Param("record") ApplySn record, @Param("example") ApplySnExample example);

    int updateByPrimaryKeySelective(ApplySn record);

    int updateByPrimaryKey(ApplySn record);
}