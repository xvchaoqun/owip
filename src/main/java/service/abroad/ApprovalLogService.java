package service.abroad;

import domain.ApprovalLog;
import domain.ApprovalLogExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class ApprovalLogService extends BaseMapper {

    @Transactional
    public int insertSelective(ApprovalLog record){

        return approvalLogMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        approvalLogMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApprovalLogExample example = new ApprovalLogExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        approvalLogMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ApprovalLog record){
        return approvalLogMapper.updateByPrimaryKeySelective(record);
    }
}
