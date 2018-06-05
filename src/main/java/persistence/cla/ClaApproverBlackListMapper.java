package persistence.cla;

import domain.cla.ClaApproverBlackList;
import domain.cla.ClaApproverBlackListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApproverBlackListMapper {
    long countByExample(ClaApproverBlackListExample example);

    int deleteByExample(ClaApproverBlackListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApproverBlackList record);

    int insertSelective(ClaApproverBlackList record);

    List<ClaApproverBlackList> selectByExampleWithRowbounds(ClaApproverBlackListExample example, RowBounds rowBounds);

    List<ClaApproverBlackList> selectByExample(ClaApproverBlackListExample example);

    ClaApproverBlackList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApproverBlackList record, @Param("example") ClaApproverBlackListExample example);

    int updateByExample(@Param("record") ClaApproverBlackList record, @Param("example") ClaApproverBlackListExample example);

    int updateByPrimaryKeySelective(ClaApproverBlackList record);

    int updateByPrimaryKey(ClaApproverBlackList record);
}