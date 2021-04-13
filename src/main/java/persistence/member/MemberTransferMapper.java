package persistence.member;

import domain.member.MemberTransfer;
import domain.member.MemberTransferExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberTransferMapper {
    long countByExample(MemberTransferExample example);

    int deleteByExample(MemberTransferExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberTransfer record);

    int insertSelective(MemberTransfer record);

    List<MemberTransfer> selectByExampleWithRowbounds(MemberTransferExample example, RowBounds rowBounds);

    List<MemberTransfer> selectByExample(MemberTransferExample example);

    MemberTransfer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberTransfer record, @Param("example") MemberTransferExample example);

    int updateByExample(@Param("record") MemberTransfer record, @Param("example") MemberTransferExample example);

    int updateByPrimaryKeySelective(MemberTransfer record);

    int updateByPrimaryKey(MemberTransfer record);
}