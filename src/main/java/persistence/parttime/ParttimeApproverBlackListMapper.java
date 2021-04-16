package persistence.parttime;

import domain.parttime.ParttimeApproverBlackList;
import domain.parttime.ParttimeApproverBlackListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApproverBlackListMapper {
    long countByExample(ParttimeApproverBlackListExample example);

    int deleteByExample(ParttimeApproverBlackListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApproverBlackList record);

    int insertSelective(ParttimeApproverBlackList record);

    List<ParttimeApproverBlackList> selectByExampleWithRowbounds(ParttimeApproverBlackListExample example, RowBounds rowBounds);

    List<ParttimeApproverBlackList> selectByExample(ParttimeApproverBlackListExample example);

    ParttimeApproverBlackList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApproverBlackList record, @Param("example") ParttimeApproverBlackListExample example);

    int updateByExample(@Param("record") ParttimeApproverBlackList record, @Param("example") ParttimeApproverBlackListExample example);

    int updateByPrimaryKeySelective(ParttimeApproverBlackList record);

    int updateByPrimaryKey(ParttimeApproverBlackList record);
}