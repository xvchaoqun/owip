package persistence.member;

import domain.member.MemberCertify;
import domain.member.MemberCertifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberCertifyMapper {
    long countByExample(MemberCertifyExample example);

    int deleteByExample(MemberCertifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberCertify record);

    int insertSelective(MemberCertify record);

    List<MemberCertify> selectByExampleWithRowbounds(MemberCertifyExample example, RowBounds rowBounds);

    List<MemberCertify> selectByExample(MemberCertifyExample example);

    MemberCertify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberCertify record, @Param("example") MemberCertifyExample example);

    int updateByExample(@Param("record") MemberCertify record, @Param("example") MemberCertifyExample example);

    int updateByPrimaryKeySelective(MemberCertify record);

    int updateByPrimaryKey(MemberCertify record);
}