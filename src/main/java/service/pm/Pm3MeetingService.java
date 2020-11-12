package service.pm;

import controller.global.OpException;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.party.PartyExample;
import domain.pm.Pm3Meeting;
import domain.pm.Pm3MeetingExample;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.LoginUserService;
import service.common.FreemarkerService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.PmConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Pm3MeetingService extends PmBaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private FreemarkerService freemarkerService;
    @Transactional
    public void insertSelective(Pm3Meeting record){

        record.setStatus(PmConstants.PM_3_STATUS_SAVE);
        record.setIsBack(false);
        record.setIsDelete(false);
        pm3MeetingMapper.insertSelective(record);

        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_PM,
                "添加组织生活", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                null);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        Pm3Meeting record = new Pm3Meeting();
        record.setIsDelete(true);

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pm3MeetingMapper.updateByExampleSelective(record, example);

        for (Integer id : ids) {
            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_PM,
                    "删除", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    null);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(Pm3Meeting record){

        pm3MeetingMapper.updateByPrimaryKeySelective(record);
        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_PM,
                "修改组织生活", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                null);
    }

    public Map<Integer, Pm3Meeting> findAll() {

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<Pm3Meeting> records = pm3MeetingMapper.selectByExample(example);
        Map<Integer, Pm3Meeting> map = new LinkedHashMap<>();
        for (Pm3Meeting record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void submit(Integer id) {

        if (id == null) return;

        Pm3Meeting record = pm3MeetingMapper.selectByPrimaryKey(id);
        if (PartyHelper.isDirectBranch(record.getPartyId())){
            record.setStatus(PmConstants.PM_3_STATUS_OW);
        }else {
            record.setStatus(PmConstants.PM_3_STATUS_PARTY);
        }
        pm3MeetingMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_PM,
                "提交", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                null);

    }

    @Transactional
    public void check(Integer[] ids, boolean check, String checkOpinion) {

        Pm3Meeting record = new Pm3Meeting();
        record.setCheckOpinion(checkOpinion);

        boolean addPermits = ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<Pm3Meeting> pm3MeetingList = pm3MeetingMapper.selectByExample(example);
        List<Integer> partyIdList = pm3MeetingList.stream().map(Pm3Meeting::getPartyId).collect(Collectors.toList());
        boolean isPartyAdmin = adminPartyIdList.containsAll(partyIdList);
        if (!addPermits&&!isPartyAdmin)
            throw new OpException("权限不足");

        if (check){
            if (addPermits){
                record.setStatus(PmConstants.PM_3_STATUS_PASS);
            }else{
                record.setStatus(PmConstants.PM_3_STATUS_OW);
            }
            record.setIsBack(false);
        }else {
            record.setStatus(PmConstants.PM_3_STATUS_SAVE);
            record.setIsBack(true);
        }

        pm3MeetingMapper.updateByExampleSelective(record, example);

        String stage = addPermits?"组织部":"分党委";

        for (Integer id : ids) {
            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_PM,
                    record.getIsBack()?"退回":(stage + "审批通过"), record.getIsBack()?SystemConstants.SYS_APPROVAL_LOG_STATUS_BACK:SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS,
                    checkOpinion);
        }
    }

    public List<MemberView> getMemberList(String absents) {
        if (StringUtils.isBlank(absents)) return null;

        String[] absent = absents.split(",");
        List<Integer> attendUserIdList= new ArrayList();
        for(String a:absent) {
            attendUserIdList.add(Integer.valueOf(a.trim()));
        }
        MemberViewExample example = new MemberViewExample();
        example.createCriteria().andUserIdIn(attendUserIdList);
        return  memberViewMapper.selectByExample(example);

    }

    public void download(int id, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        Pm3Meeting pm3Meeting = pm3MeetingMapper.selectByPrimaryKey(id);
        Party party = pm3Meeting.getParty();
        Branch branch = null;
        if (!PartyHelper.isDirectBranch(party.getId())){
            branch = pm3Meeting.getBranch();
        }
        //输出文件
        String filename = party.getName() + (branch!=null?branch.getName():"") + pm3Meeting.getYear() + "年"
                + pm3Meeting.getMonth() + "月支部组织生活记录" + ".doc";
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
        response.setContentType("application/msword;charset=UTF-8");

        processPm3(id, response.getWriter());
    }

    @Transactional
    public void batchBack(Integer[] ids) {

        Pm3Meeting record = new Pm3Meeting();
        record.setStatus(PmConstants.PM_3_STATUS_SAVE);
        record.setIsBack(true);

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pm3MeetingMapper.updateByExampleSelective(record, example);

        for (Integer id : ids) {
            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_PM,
                    "退回", SystemConstants.SYS_APPROVAL_LOG_STATUS_BACK,
                    null);
        }
    }

    // 输出支部书记信息采集表
    public void processPm3(int id, Writer out) throws IOException, TemplateException {

        Map<String, Object> dataMap = getDataMapOfPm3(id);
        Pm3Meeting pm3Meeting = pm3MeetingMapper.selectByPrimaryKey(id);
        if (pm3Meeting.getType()==PmConstants.PM_3_BRANCH_COMMITTEE){
            freemarkerService.process("/pm/pm3Meeting_1.ftl", dataMap, out);
        }else {
            freemarkerService.process("/pm/pm3Meeting_2.ftl", dataMap, out);
        }
    }

    private Map<String, Object> getDataMapOfPm3(int id) {

        Map<String, Object> dataMap = new HashMap<>();
        Pm3Meeting bean = pm3MeetingMapper.selectByPrimaryKey(id);
        //${!}
        dataMap.put("type", PmConstants.PM_3_BRANCH_MAP.get(bean.getType()));
        dataMap.put("partyName", bean.getParty().getName());
        dataMap.put("branchName", bean.getBranch().getName());
        dataMap.put("name", bean.getName());
        dataMap.put("year", bean.getYear());
        dataMap.put("month", bean.getMonth());
        dataMap.put("time", DateUtils.formatDate(bean.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM));
        dataMap.put("address", bean.getAddress());
        dataMap.put("presenter", bean.getPresenterUser().getRealname());
        dataMap.put("recorder", bean.getRecorderUser().getRealname());
        dataMap.put("dueNum", bean.getDueNum());
        dataMap.put("attendNum", bean.getAttendNum());
        String absentName = "";
        if (StringUtils.isNotBlank(bean.getAbsents())){
            List<String> realnameList = bean.getAbsentList().stream().map(MemberView::getRealname).collect(Collectors.toList());
            absentName = StringUtils.join(realnameList, ",");
        }else {
            absentName = "无";
        }

        dataMap.put("absent", absentName + "(" + bean.getAbsentReason() + ")");
        dataMap.put("remark", bean.getRemark());
        dataMap.put("content", bean.getContent());

        return dataMap;
    }

    //需要通知的支部
    public List<Integer> getBranchIds(Date meetingMonth){

        if (meetingMonth==null) return null;

        List<Integer> branchIdList = new ArrayList<>();

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria().andYearEqualTo(DateUtils.getYear(meetingMonth)).andMonthEqualTo(DateUtils.getMonth(meetingMonth))
                .andBranchIdIsNotNull().andStatusNotEqualTo(PmConstants.PM_3_STATUS_SAVE).andIsDeleteEqualTo(false);
        List<Pm3Meeting> records = pm3MeetingMapper.selectByExample(example);

        if (records != null && records.size() > 0) {
            List<Integer> _branchIdList = records.stream().map(Pm3Meeting::getBranchId).collect(Collectors.toList());
            BranchExample branchExample = new BranchExample();
            branchExample.createCriteria().andIdNotIn(_branchIdList).andIsDeletedEqualTo(false);
            List<Branch> branchList = branchMapper.selectByExample(branchExample);
            branchIdList = branchList.stream().map(Branch::getId).collect(Collectors.toList());
        }
        return branchIdList;
    }

    //需要通知的直属党支部
    public List<Integer> getPartyIds(Date meetingMonth){

        if (meetingMonth==null) return null;

        List<Integer> partyIdList = new ArrayList<>();

        Pm3MeetingExample example = new Pm3MeetingExample();
        example.createCriteria().andYearEqualTo(DateUtils.getYear(meetingMonth)).andMonthEqualTo(DateUtils.getMonth(meetingMonth))
                .andBranchIdIsNull().andStatusNotEqualTo(PmConstants.PM_3_STATUS_SAVE).andIsDeleteEqualTo(false);
        List<Pm3Meeting> records = pm3MeetingMapper.selectByExample(example);

        if (records != null && records.size() > 0) {
            List<Integer> _partyIdList = records.stream().map(Pm3Meeting::getPartyId).collect(Collectors.toList());
            PartyExample partyExample = new PartyExample();
            partyExample.createCriteria().andClassIdEqualTo(CmTag.getMetaTypeByCode("mt_direct_branch").getId()).andIsDeletedEqualTo(false)
                    .andIdNotIn(_partyIdList);
            List<Party> partyList = partyMapper.selectByExample(partyExample);
            partyIdList = partyList.stream().map(Party::getId).collect(Collectors.toList());
        }
        return partyIdList;
    }
}
