package persistence;

import domain.ApproverBlackList;
import domain.ApproverBlackListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApproverBlackListMapper {
    int countByExample(ApproverBlackListExample example);

    int deleteByExample(ApproverBlackListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApproverBlackList record);

    int insertSelective(ApproverBlackList record);

    List<ApproverBlackList> selectByExampleWithRowbounds(ApproverBlackListExample example, RowBounds rowBounds);

    List<ApproverBlackList> selectByExample(ApproverBlackListExample example);

    ApproverBlackList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApproverBlackList record, @Param("example") ApproverBlackListExample example);

    int updateByExample(@Param("record") ApproverBlackList record, @Param("example") ApproverBlackListExample example);

    int updateByPrimaryKeySelective(ApproverBlackList record);

    int updateByPrimaryKey(ApproverBlackList record);
}