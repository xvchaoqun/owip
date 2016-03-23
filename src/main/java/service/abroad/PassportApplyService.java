package service.abroad;

import domain.PassportApply;
import domain.PassportApplyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;

@Service
public class PassportApplyService extends BaseMapper {

    @Transactional
    public int insertSelective(PassportApply record){

        Integer cadreId = record.getCadreId();
        Integer classId = record.getClassId();
        PassportApplyExample example = new PassportApplyExample();
        // 【审批通过且没作废 或 没开始审批 ，则不可以申请同类型证件】
        example.or(example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_INIT).andClassIdEqualTo(classId));
        example.or(example.createCriteria().andCadreIdEqualTo(cadreId)
                .andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_PASS).andAbolishEqualTo(false).andClassIdEqualTo(classId));
        if(passportApplyMapper.countByExample(example)>0){
            throw new RuntimeException("您已经申请办理了"+record.getPassportClass().getName() +",请不要重复申请");
        }

        return passportApplyMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        passportApplyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportApplyExample example = new PassportApplyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        passportApplyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PassportApply record){

        return passportApplyMapper.updateByPrimaryKeySelective(record);
    }
}
