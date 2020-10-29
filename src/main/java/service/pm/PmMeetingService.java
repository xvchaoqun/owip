package service.pm;

import controller.global.OpException;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.pm.PmMeeting;
import domain.pm.PmMeetingExample;
import domain.pm.PmMeetingFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.common.FreemarkerService;
import service.party.BranchMemberService;
import service.party.MemberService;
import service.party.PartyService;
import service.sys.LogService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import java.io.Writer;
import java.util.*;

import static sys.constants.PmConstants.*;

@Service("PmMeetingService")
public class PmMeetingService extends PmBaseMapper {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PartyService partyService;
    @Autowired
    MemberService memberService;
    @Autowired
    BranchMemberService branchMemberService;
    @Autowired
    FreemarkerService freemarkerService;
    @Autowired
    private LogService logService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Transactional
    public void insertSelective(PmMeeting record, List<PmMeetingFile> pmMeetingFiles) throws InterruptedException {

        if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }

         if(record.getDate()!=null){
                record.setYear(DateUtils.getYear(record.getDate()));
                record.setQuarter(DateUtils.getQuarter(record.getDate()));
                record.setMonth(DateUtils.getMonth(record.getDate()));
         }
        record.setIsBack(false);
        record.setIsDelete(false);

        if (PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),record.getPartyId())) {
            record.setStatus(PM_MEETING_STATUS_PASS);
        }else{
            record.setStatus(PM_MEETING_STATUS_INIT);
        }

        pmMeetingMapper.insertSelective(record);

        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_PM,
                "添加三会一课", record.getStatus(),
                "新建");

        if(pmMeetingFiles==null) return;

        for (PmMeetingFile pmMeetingFile : pmMeetingFiles) {
            pmMeetingFile.setMeetingId(record.getId());
            pmMeetingFileMapper.insertSelective(pmMeetingFile);
        }

    }

    @Transactional
    public void updateByPrimaryKeySelective(PmMeeting record, List<PmMeetingFile> pmMeetingFiles){

        if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }

        PmMeeting pmMeeting=pmMeetingMapper.selectByPrimaryKey(record.getId());

        if(pmMeeting.getStatus()==PM_MEETING_STATUS_PASS&&!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),pmMeeting.getPartyId())){
            throw new OpException("该记录已审核通过，不能再次修改");
        }
        pmMeetingMapper.updateByPrimaryKeySelective(record);

        if (partyService.isDirectBranch(record.getPartyId())){
            commonMapper.excuteSql("update pm_meeting set branch_id=null where id=" + record.getId());
        }

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
        List<PmMeeting> pmMeetings=pmMeetingMapper.selectByExample(example);
        if (pmMeetings.size() > 0) {
            logger.info(logService.log(LogConstants.LOG_PM, "批量删除三会一课记录：" + JSONUtils.toString(pmMeetings, false)));
            pmMeetingMapper.deleteByExample(example);
        }
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
            sysApprovalLogService.add(pmMeeting.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_PM,
                    "审批三会一课", pmMeeting.getStatus(),
                    "审批");
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

    //导出word
    @Transactional
    public void getExportWord(Integer id, Writer out)throws Exception {

        StringBuffer absends=new StringBuffer();
        String path=null;
        Map<String, Object> dataMap = new HashMap<String, Object>();
        PmMeeting pmMeeting=new PmMeeting();

        pmMeeting = pmMeetingMapper.selectByPrimaryKey(id);
        if(pmMeeting.getType()!=PARTY_MEETING_BRANCH_ACTIVITY){
            path="pm/pmExportWord.ftl";
        }else{
            path="pm/pmWordActivity.ftl";
        }
        List<MemberView> records= pmMeeting.getAbsentList();
            if(records!=null){
              for(MemberView record: records) {
                  absends.append(record.getRealname());
                  absends.append(",");
              }
            }
        dataMap.put("absends",absends);
        dataMap.put("pmMeeting",pmMeeting);
        freemarkerService.process(path, dataMap, out);
        out.close();
    }
}
