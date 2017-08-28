package persistence.pcs;

import domain.pcs.PcsIssue;
import domain.pcs.PcsIssueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsIssueMapper {
    long countByExample(PcsIssueExample example);

    int deleteByExample(PcsIssueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsIssue record);

    int insertSelective(PcsIssue record);

    List<PcsIssue> selectByExampleWithRowbounds(PcsIssueExample example, RowBounds rowBounds);

    List<PcsIssue> selectByExample(PcsIssueExample example);

    PcsIssue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsIssue record, @Param("example") PcsIssueExample example);

    int updateByExample(@Param("record") PcsIssue record, @Param("example") PcsIssueExample example);

    int updateByPrimaryKeySelective(PcsIssue record);

    int updateByPrimaryKey(PcsIssue record);
}