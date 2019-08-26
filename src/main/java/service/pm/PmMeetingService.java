package service.pm;

import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.pm.PmMeeting;
import domain.pm.PmMeetingExample;
import domain.pm.PmMeetingFile;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import service.party.BranchMemberService;
import service.party.MemberService;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;

import java.util.*;

import static sys.constants.PmConstants.*;
import static sys.constants.RoleConstants.ROLE_ODADMIN;
import static sys.constants.RoleConstants.ROLE_PARTYADMIN;

@Service("PmMeetingService")
public class PmMeetingService extends PmBaseMapper {

    @Autowired
    MemberService memberService;
    @Autowired
    BranchMemberService branchMemberService;

    @Transactional
    public void insertSelective(PmMeeting record, List<PmMeetingFile> pmMeetingFiles) throws InterruptedException {

        if(!ShiroHelper.hasRole(RoleConstants.ROLE_ODADMIN)&&
                !PartyHelper.isPresentPartyAdmin(ShiroHelper.getCurrentUserId(),record.getPartyId())
                &&!PartyHelper.isPresentBranchAdmin(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }

         if(record.getDate()!=null){
                record.setYear(getYear(record.getDate()));
                  record.setQuarter(getQuarter(record.getDate()));
         }
        record.setIsBack(false);
        record.setIsDelete(false);

        if (ShiroHelper.hasRole(ROLE_ODADMIN)||PartyHelper.isPresentPartyAdmin(ShiroHelper.getCurrentUserId(),record.getPartyId())) {
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

        if(!ShiroHelper.hasRole(RoleConstants.ROLE_ODADMIN)&&
                !PartyHelper.isPresentPartyAdmin(ShiroHelper.getCurrentUserId(),record.getPartyId())
                &&!PartyHelper.isPresentBranchAdmin(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
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
    // 获取所有参会人
    public List<MemberView> getMemberList(String attendId) {
       if(StringUtils.isNotBlank(attendId)){
           attendId.trim();
           String attend [] = attendId.split(",");
           List<MemberView> memberViews= new ArrayList();
            for(int x = 0 ; x < attend.length ; x++) {

                MemberViewExample example = new MemberViewExample();
                example.createCriteria().andUserIdEqualTo(Integer.valueOf(attend[x]));
                List<MemberView> users = memberViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

                if(users.size() > 0){
                    MemberView uv=users.get(0);
                    memberViews.add(uv);
                }
            }
           return memberViews;
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
