package service.pm;

import controller.global.OpException;
import domain.pm.PmMeeting2;
import domain.pm.PmMeeting2Example;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.helper.PartyHelper;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static sys.constants.PmConstants.*;

@Service
public class PmMeeting2Service extends PmBaseMapper {

    public boolean idDuplicate(Integer id){

        //Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PmMeeting2Example example = new PmMeeting2Example();
        PmMeeting2Example.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pmMeeting2Mapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PmMeeting2 record){

        if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }

        if(record.getDate()!=null){
            record.setYear(DateUtils.getYear(record.getDate()));
            record.setQuarter(DateUtils.getQuarter(record.getDate()));
            record.setMonth(DateUtils.getMonth(record.getDate()));
        }
        record.setIsDelete(false);

        if (PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),record.getPartyId())) {
            record.setStatus(PM_MEETING_STATUS_PASS);
        }else{
            record.setStatus(PM_MEETING_STATUS_INIT);
        }
        pmMeeting2Mapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        pmMeeting2Mapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        PmMeeting2Example example = new PmMeeting2Example();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmMeeting2Mapper.deleteByExample(example);

    }

    @Transactional
    public void updateByPrimaryKeySelective(PmMeeting2 record){

        if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }

        PmMeeting2 pmMeeting2=pmMeeting2Mapper.selectByPrimaryKey(record.getId());

        if(pmMeeting2.getStatus()==PM_MEETING_STATUS_PASS
                &&!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),pmMeeting2.getPartyId())){
            throw new OpException("该记录已审核通过，不能再次修改");
        }
        pmMeeting2Mapper.updateByPrimaryKeySelective(record);

        if(record.getType2()==null){
            commonMapper.excuteSql("update pm_meeting2 set type2=null,number2=null,time2=null where id=" + record.getId());
        }

    }

    @Transactional
    public void check(Integer[] ids,Byte status,Boolean isBack,String reason){

        PmMeeting2Example example = new PmMeeting2Example();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<PmMeeting2> pmMeetings=pmMeeting2Mapper.selectByExample(example);

        for(PmMeeting2 pmMeeting2:pmMeetings){
            if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),pmMeeting2.getPartyId())){
                continue;
            }

            pmMeeting2.setStatus(status);
            if(status==PM_MEETING_STATUS_DENY||isBack==true){
                pmMeeting2.setReason(reason);
            }
            pmMeeting2Mapper.updateByPrimaryKeySelective(pmMeeting2);
        }
    }
    public Map<Integer, PmMeeting2> findAll() {

        PmMeeting2Example example = new PmMeeting2Example();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PmMeeting2> records = pmMeeting2Mapper.selectByExample(example);
        Map<Integer, PmMeeting2> map = new LinkedHashMap<>();
        for (PmMeeting2 record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
