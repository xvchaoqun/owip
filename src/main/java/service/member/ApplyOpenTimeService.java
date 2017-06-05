package service.member;

import domain.member.ApplyOpenTime;
import domain.member.ApplyOpenTimeExample;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ApplyOpenTimeService extends BaseMapper {

    public boolean isOpen(Integer partyId, byte type){

        ApplyOpenTimeExample example = new ApplyOpenTimeExample();
        ApplyOpenTimeExample.Criteria criteria = example.or().andTypeEqualTo(type).andPartyIdEqualTo(partyId);
        example.or().andTypeEqualTo(type).andIsGlobalEqualTo(true);

        List<ApplyOpenTime> applyOpenTimes = applyOpenTimeMapper.selectByExample(example);
        for (ApplyOpenTime applyOpenTime : applyOpenTimes) {
            DateTime start = new DateTime(applyOpenTime.getStartTime());
            DateTime end = new DateTime(applyOpenTime.getEndTime());
            if(start.isBeforeNow() && end.isAfterNow()){
                return true;
            }
        }
        return false;
    }

    public int insertSelective(ApplyOpenTime record){

        if(record.getIsGlobal()){
            record.setPartyId(null);
            record.setBranchId(null);
        }

        return applyOpenTimeMapper.insertSelective(record);
    }

    public void del(Integer id){

        applyOpenTimeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApplyOpenTimeExample example = new ApplyOpenTimeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        applyOpenTimeMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ApplyOpenTime record){
        if(record.getIsGlobal()){
            record.setPartyId(null);
            record.setBranchId(null);
            updateMapper.globalApplyOpenTime(record.getId());
        }

        return applyOpenTimeMapper.updateByPrimaryKeySelective(record);
    }
}
