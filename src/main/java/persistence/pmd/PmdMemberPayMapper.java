package persistence.pmd;

import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMemberPayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdMemberPayMapper {
    long countByExample(PmdMemberPayExample example);

    int deleteByExample(PmdMemberPayExample example);

    int deleteByPrimaryKey(Integer memberId);

    int insert(PmdMemberPay record);

    int insertSelective(PmdMemberPay record);

    List<PmdMemberPay> selectByExampleWithRowbounds(PmdMemberPayExample example, RowBounds rowBounds);

    List<PmdMemberPay> selectByExample(PmdMemberPayExample example);

    PmdMemberPay selectByPrimaryKey(Integer memberId);

    int updateByExampleSelective(@Param("record") PmdMemberPay record, @Param("example") PmdMemberPayExample example);

    int updateByExample(@Param("record") PmdMemberPay record, @Param("example") PmdMemberPayExample example);

    int updateByPrimaryKeySelective(PmdMemberPay record);

    int updateByPrimaryKey(PmdMemberPay record);
}