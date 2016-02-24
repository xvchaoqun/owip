package persistence;

import domain.ApprovalIdentity;
import domain.ApprovalIdentityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApprovalIdentityMapper {
    int countByExample(ApprovalIdentityExample example);

    int deleteByExample(ApprovalIdentityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApprovalIdentity record);

    int insertSelective(ApprovalIdentity record);

    List<ApprovalIdentity> selectByExampleWithRowbounds(ApprovalIdentityExample example, RowBounds rowBounds);

    List<ApprovalIdentity> selectByExample(ApprovalIdentityExample example);

    ApprovalIdentity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApprovalIdentity record, @Param("example") ApprovalIdentityExample example);

    int updateByExample(@Param("record") ApprovalIdentity record, @Param("example") ApprovalIdentityExample example);

    int updateByPrimaryKeySelective(ApprovalIdentity record);

    int updateByPrimaryKey(ApprovalIdentity record);
}