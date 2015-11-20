package service;

import domain.ApplyLog;
import domain.ApplyLogExample;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class ApplyLogService extends BaseMapper {


    public void addApplyLog(int userId, int operatorId, byte stage, String content, String ip){

        ApplyLog record = new ApplyLog();
        record.setUserId(userId);
        record.setOperatorId(operatorId);
        record.setStage(stage);
        record.setContent(content);
        record.setCreateTime(new Date());
        record.setIp(ip);
        try {
            insertSelective(record);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public int insertSelective(ApplyLog record){
        return applyLogMapper.insertSelective(record);
    }
    public void del(Integer id){

        applyLogMapper.deleteByPrimaryKey(id);
    }

    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApplyLogExample example = new ApplyLogExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        applyLogMapper.deleteByExample(example);
    }

    public int updateByPrimaryKeySelective(ApplyLog record){
        return applyLogMapper.updateByPrimaryKeySelective(record);
    }

}
