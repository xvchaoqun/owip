package service.pm;

import controller.global.OpException;
import domain.pm.PmMeeting2;
import domain.pm.PmMeeting2Example;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.utils.DateUtils;

import java.util.*;

import static sys.constants.PmConstants.*;
import static sys.constants.SystemConstants.*;

@Service
public class PmMeeting2Service extends PmBaseMapper {

    @Autowired
    PartyService partyService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

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

        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.SYS_APPROVAL_LOG_PM,
                "添加三会一课",SYS_APPROVAL_LOG_STATUS_NONEED,
                "新建");
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
    public void delFile(int id, int indexId){

        PmMeeting2 pmMeeting2 = pmMeeting2Mapper.selectByPrimaryKey(id);
        String[] fileNames =pmMeeting2.getFileName().split(";");
        String[] filePaths =pmMeeting2.getFilePath().split(";");

        List<String> fileNameList=new ArrayList<String>(Arrays.asList(fileNames));
        fileNameList.remove(indexId);

        List<String> filePathList=new ArrayList<String>(Arrays.asList(filePaths));
        filePathList.remove(indexId);

        if(filePathList.size()==0){
            commonMapper.excuteSql("update pm_meeting2 set file_name=null,file_path=null where id="+id);
        }else{
            pmMeeting2.setFileName(StringUtils.join(fileNameList, ";") );
            pmMeeting2.setFilePath(StringUtils.join(filePathList, ";") );
            updateByPrimaryKeySelective(pmMeeting2);
        }
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
            if(status==PM_MEETING_STATUS_DENY){
                pmMeeting2.setReason(reason);
            }
            pmMeeting2Mapper.updateByPrimaryKeySelective(pmMeeting2);

            if(isBack){
                sysApprovalLogService.add(pmMeeting2.getId(), ShiroHelper.getCurrentUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                        SystemConstants.SYS_APPROVAL_LOG_PM,
                        "退回三会一课", SYS_APPROVAL_LOG_STATUS_BACK,
                        "退回原因："+reason);
            }else{
                sysApprovalLogService.add(pmMeeting2.getId(), ShiroHelper.getCurrentUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                        SystemConstants.SYS_APPROVAL_LOG_PM,
                        "审核三会一课", status==PM_MEETING_STATUS_DENY?SYS_APPROVAL_LOG_STATUS_DENY:SYS_APPROVAL_LOG_STATUS_PASS,
                        reason);
            }
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

    // 批量导入
    @Transactional
    public int pmMeeting2Import(List<PmMeeting2> records) throws InterruptedException {

        int addCount = 0;

        for (PmMeeting2 record : records) {
            insertSelective(record);
            addCount++;
        }
        return addCount;
    }
}
