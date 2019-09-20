package service.pm;

import controller.global.OpException;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.pm.PmMeeting;
import domain.pm.PmMeetingExample;
import domain.pm.PmMeetingFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.BranchMemberService;
import service.party.MemberService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;

import java.util.*;

import static sys.constants.PmConstants.PM_MEETING_STATUS_INIT;
import static sys.constants.PmConstants.PM_MEETING_STATUS_PASS;

@Service("PmMeetingService")
public class PmMeetingService extends PmBaseMapper {

    @Autowired
    MemberService memberService;
    @Autowired
    BranchMemberService branchMemberService;

    @Transactional
    public void insertSelective(PmMeeting record, List<PmMeetingFile> pmMeetingFiles) throws InterruptedException {

        if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }

         if(record.getDate()!=null){
                record.setYear(getYear(record.getDate()));
                  record.setQuarter(getQuarter(record.getDate()));
         }
        record.setIsBack(false);
        record.setIsDelete(false);

        if (PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),record.getPartyId())) {
            record.setStatus(PM_MEETING_STATUS_PASS);
        }else{
            record.setStatus(PM_MEETING_STATUS_INIT);
        }

        pmMeetingMapper.insertSelective(record);

        if(pmMeetingFiles==null) return;

        for (PmMeetingFile pmMeetingFile : pmMeetingFiles) {
            pmMeetingFile.setMeetingId(record.getId());
            pmMeetingFileMapper.insertSelective(pmMeetingFile);
        }

    }
    public void updateByPrimaryKeySelective(PmMeeting record, List<PmMeetingFile> pmMeetingFiles){

        if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }

        PmMeeting pmMeeting=pmMeetingMapper.selectByPrimaryKey(record.getId());

        if(pmMeeting.getStatus()==PM_MEETING_STATUS_PASS&&!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)){
            throw new OpException("该记录已审核通过，不能再次修改");
        }
        pmMeetingMapper.updateByPrimaryKeySelective(record);

        for (PmMeetingFile pmMeetingFile : pmMeetingFiles) {
            pmMeetingFile.setMeetingId(record.getId());
            pmMeetingFileMapper.insertSelective(pmMeetingFile);
        }
    }

    @Transactional
    public void del(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PmMeetingExample example = new PmMeetingExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmMeetingMapper.deleteByExample(example);

    }

    @Transactional
    public void check(Integer[] ids,Byte status,Boolean isBack,String reason){

        PmMeetingExample example = new PmMeetingExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<PmMeeting> pmMeetings=pmMeetingMapper.selectByExample(example);

        for(PmMeeting pmMeeting:pmMeetings){

            if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),pmMeeting.getPartyId())){
                continue;
            }

            pmMeeting.setStatus(status);
            pmMeeting.setIsBack(isBack);
            if(status==PM_MEETING_STATUS_PASS||isBack==true){
                pmMeeting.setReason(reason);
            }
            pmMeetingMapper.updateByPrimaryKeySelective(pmMeeting);
        }

    }

    // 获取所有参会人
    public List<MemberView> getMemberList(String attendId) {
       if(StringUtils.isNotBlank(attendId)){

           String attend [] = attendId.split(",");
           List<Integer> attendUserIdList= new ArrayList();
            for(String a:attend) {
                attendUserIdList.add(Integer.valueOf(a.trim()));
            }
           MemberViewExample example = new MemberViewExample();
           example.createCriteria().andUserIdIn(attendUserIdList);
           return  memberViewMapper.selectByExample(example);
     }
        return null;
    }
    // 批量导入
    @Transactional
    public int pmMeetingImport(List<PmMeeting> records) throws InterruptedException {

        int addCount = 0;

        for (PmMeeting record : records) {
            insertSelective(record,null);
            addCount++;
        }
        return addCount;
    }

    public  int getYear(Date date){

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return year;
    }
    public  byte getQuarter(Date date){
        byte season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }
}
