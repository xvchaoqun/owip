package controller.pm;

import controller.global.OpException;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.party.*;
import domain.pm.PmMeeting;
import domain.pm.PmMeetingExample;
import domain.pm.PmMeetingFile;
import domain.pm.PmMeetingFileExample;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import persistence.party.BranchViewMapper;
import persistence.party.PartyViewMapper;
import persistence.pm.common.PmMeetingStat;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PmConstants;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static sys.constants.MemberConstants.MEMBER_STATUS_NORMAL;
import static sys.constants.PmConstants.*;
import static sys.helper.PartyHelper.isDirectBranch;

@Controller

public class PmMeetingController extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmMeeting:list")
    @RequestMapping("/pmMeeting")
    public String pmMeeting(@RequestParam(defaultValue = "1") Integer cls,Byte type ,
                               Integer partyId,
                               Integer branchId,ModelMap modelMap) {

        boolean addPermits = !ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        Map pmInitCount = iPmMapper.selectPmInitCount(type,addPermits,adminPartyIdList, adminBranchIdList);
        if (pmInitCount != null) {
            modelMap.putAll(pmInitCount);
        }

        modelMap.put("type", type);
        modelMap.put("cls", cls);
        modelMap.put("adminPartyIdList", adminPartyIdList);
        modelMap.put("addPermits", addPermits);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchMap.get(branchId));

        return "pm/pmMeeting/pmMeeting_page";
    }
    @RequiresPermissions("pmMeeting:list")
    @RequestMapping("/pmMeeting_user")
    public String pmMeeting_user(Integer id,Byte type,ModelMap modelMap) {

        PmMeeting pmMeeting=pmMeetingMapper.selectByPrimaryKey(id);
        modelMap.put("pmMeeting",pmMeeting);
        return "pm/pmMeeting/pmMeeting_user";
    }
    @RequiresPermissions("pmMeeting:list")
    @RequestMapping("/pmMeeting_data")
    public void partyMeeting_data(Byte type,
                                  @RequestParam(defaultValue = "1") Integer cls,
                                  Integer partyId,
                                  Integer branchId,
                                  String name,
                                  String issue,
                                  Integer year,
                                  Byte quarter,
                                  @RequestDateRange DateRange _meetingDate,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  Integer[] ids, // 导出的记录
                                  HttpServletResponse response, Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmMeetingExample example = new PmMeetingExample();
        PmMeetingExample.Criteria criteria = example.createCriteria();
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        criteria.andIsDeleteNotEqualTo(true);
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (StringUtils.isNotBlank(issue)) {
            criteria.andIssueLike(SqlUtils.like(issue));
        }
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (quarter != null) {
            criteria.andQuarterEqualTo(quarter);
        }
        if (_meetingDate.getStart() != null) {
            criteria.andDateGreaterThanOrEqualTo(_meetingDate.getStart());
        }

        if (_meetingDate.getEnd() != null) {
            criteria.andDateLessThanOrEqualTo(_meetingDate.getEnd());
        }
        switch (cls) {
            case 1:
                criteria.andStatusEqualTo(PmConstants.PM_MEETING_STATUS_INIT).andIsBackNotEqualTo(true);
                break;
            case 2:
                criteria.andIsBackEqualTo(true);
                break;
            case 3:
                criteria.andStatusEqualTo(PmConstants.PM_MEETING_STATUS_PASS);
                break;
            case 4:
                criteria.andStatusEqualTo(PmConstants.PM_MEETING_STATUS_DENY);
                break;
            default:
                break;
        }
        example.setOrderByClause("id desc");

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pmMeeting_export(example,type ,response);
            return;
        }

        long count = pmMeetingMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmMeeting> records= pmMeetingMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partySchool.class, partySchoolMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmMeeting:edit")
    @RequestMapping("/pmMeeting_au")
    public String partyBranchMeeting_au(Integer id, Byte type,Boolean edit,Byte reedit, ModelMap modelMap) {    //reedit 重新编辑
        PmMeeting pmMeeting=new PmMeeting();
        if(id!=null){
            pmMeeting =pmMeetingMapper.selectByPrimaryKey(id);

            PmMeetingFileExample example = new PmMeetingFileExample();
            example.createCriteria().andMeetingIdEqualTo(id);
            List<PmMeetingFile> pmMeetingFiles=pmMeetingFileMapper.selectByExample(example);
            modelMap.put("pmMeetingFiles",pmMeetingFiles);

        } else {
            boolean odAdmin = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
            if (!odAdmin) {

                List<Integer> adminPartyIds = loginUserService.adminPartyIdList();
                List<Integer> adminBranchIds = loginUserService.adminBranchIdList();
                if(adminPartyIds.size()==0&&adminBranchIds.size()==1){
                    pmMeeting.setPartyId(CmTag.getBranch(adminBranchIds.get(0)).getPartyId());
                    pmMeeting.setBranchId(adminBranchIds.get(0));
                    modelMap.put("adminOnePartyOrBranch",true);

                    BranchViewMapper branchViewMapper = CmTag.getBean(BranchViewMapper.class);
                    BranchViewExample example = new BranchViewExample();
                    example.createCriteria().andIdEqualTo(pmMeeting.getBranchId()).andPartyIdEqualTo(pmMeeting.getPartyId());
                    List<BranchView> branchViews= branchViewMapper.selectByExample(example);
                    modelMap.put("memberCount",branchViews.get(0).getMemberCount());

                }else if(adminPartyIds.size()==1&&adminBranchIds.size()==0&&isDirectBranch(adminPartyIds.get(0))){
                    pmMeeting.setPartyId(adminPartyIds.get(0));
                    modelMap.put("adminOnePartyOrBranch",true);

                    PartyViewMapper partyViewMapper = CmTag.getBean(PartyViewMapper.class);
                    PartyViewExample example = new PartyViewExample();
                    example.createCriteria().andIdEqualTo(pmMeeting.getPartyId());
                    List<PartyView> partyViews= partyViewMapper.selectByExample(example);
                    modelMap.put("memberCount",partyViews.get(0).getMemberCount());
                }else{
                    modelMap.put("adminOnePartyOrBranch",false);
                }
            }
        }

        modelMap.put("pmMeeting",pmMeeting);
        modelMap.put("edit",edit);
        return "pm/pmMeeting/pmMeeting_au";
    }

    @RequiresPermissions("pmMeeting:edit")
    @RequestMapping(value = "/pmMeeting_au", method = RequestMethod.POST)
    @ResponseBody
    public Map partyBranchMeeting_au(PmMeeting record,Byte type,@RequestParam(defaultValue = "0")Byte reedit,    //reedit 重新编辑
                                     MultipartFile[] _files, // 附件
                                     String attendIds,//参会人员
                                     String absentIds,//请假人员
                                     HttpServletRequest request) throws InterruptedException, IOException {
        Integer id = record.getId();
        if (_files == null) _files = new MultipartFile[]{};

        List<PmMeetingFile> pmMeetingFiles = new ArrayList<>();
        for (MultipartFile _file : _files) {

            String originalFilename = _file.getOriginalFilename();
            String savePath = upload(_file, "pmMeetingFile");

            PmMeetingFile file = new PmMeetingFile();

            file.setFileName(originalFilename);
            file.setFilePath(savePath);
            pmMeetingFiles.add(file);
        }
        if (id == null) {
            record.setAttends(attendIds);
            record.setAbsents(absentIds);
            record.setType(type);

            pmMeetingService.insertSelective(record,pmMeetingFiles);
            logger.info(addLog(LogConstants.LOG_PM, "添加三会一课：%s",record.getId()));

        } else {
            if (reedit == 1) {

                record.setStatus(PM_MEETING_STATUS_INIT);
                record.setIsBack(false);
            }
            if(record.getDate()!=null){
                record.setYear(DateUtils.getYear(record.getDate()));
                record.setQuarter(DateUtils.getQuarter(record.getDate()));
                record.setMonth(DateUtils.getMonth(record.getDate()));
            }
            record.setAttends(attendIds);
            record.setAbsents(absentIds);
            pmMeetingService.updateByPrimaryKeySelective(record,pmMeetingFiles);
            logger.info(addLog(LogConstants.LOG_PM, "更新三会一课：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmMeeting:edit")
    @RequestMapping(value = "/pmMeeting_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyBranchMeeting_del(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {
        if (null != ids && ids.length>0){
            pmMeetingService.del(ids);
            logger.info(addLog(LogConstants.LOG_PM, "批量删除三会一课：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmMeeting:edit")
    @RequestMapping("/pmMeeting_member")
    public String pmMeeting_member(Integer partyId,Integer branchId,Byte type,HttpServletRequest request, ModelMap modelMap) {

        if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),partyId,branchId)){
            throw new UnauthorizedException();
        }
        MemberViewExample example = new MemberViewExample();
        MemberViewExample.Criteria criteria = example.createCriteria();
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        criteria.andStatusEqualTo(MEMBER_STATUS_NORMAL);
        List<MemberView> membersViews=memberViewMapper.selectByExample(example);
        modelMap.put("membersViews",membersViews);
        return "pm/pmMeeting/pmMeeting_member";
    }

    @RequiresPermissions("pmMeeting:approve")
    @RequestMapping( "/pmMeeting_check")
    public String pmMeeting_check(Boolean check,HttpServletRequest request, ModelMap modelMap) {

        return "pm/pmMeeting/pmMeeting_check";
    }

    @RequiresPermissions("pmMeeting:approve")
    @RequestMapping(value = "/pmMeeting_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmMeeting_check(Integer[] ids,Boolean check,Boolean hasPass,String reason,HttpServletRequest request) {

        Byte status=PM_MEETING_STATUS_INIT;
        Boolean isBack=false;

        if(BooleanUtils.isTrue(check)){
            status=hasPass==null?PM_MEETING_STATUS_DENY:PM_MEETING_STATUS_PASS;
        }else{
            isBack=true;
        }

        pmMeetingService.check(ids,status,isBack,reason);

        logger.info(addLog(LogConstants.LOG_PM, "审核三会一课：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmMeeting:approve")
    @RequestMapping("/pmMeeting_import")
    public String member_import(ModelMap modelMap) {

        return "pm/pmMeeting/pmMeeting_import";
    }

    // 导入会议
    @RequiresPermissions("pmMeeting:approve")
    @RequestMapping(value = "/pmMeeting_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_import(Byte type,HttpServletRequest request) throws InvalidFormatException, IOException, InterruptedException {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<String, Party> runPartyMap = new HashMap<>();
        for (Party party : partyMap.values()) {
            if (BooleanUtils.isNotTrue(party.getIsDeleted())) {
                runPartyMap.put(party.getCode(), party);
            }
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<String, Branch> runBranchMap = new HashMap<>();
        for (Branch branch : branchMap.values()) {
            if (BooleanUtils.isNotTrue(branch.getIsDeleted())) {
                runBranchMap.put(branch.getCode(), branch);
            }
        }

        Map<String, Byte> partyMeetingMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : PARTY_MEETING_MAP.entrySet()) {

            partyMeetingMap.put(entry.getValue(), entry.getKey());
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        Map<String, Object> resultMap = null;
       if(type!=PARTY_MEETING_BRANCH_ACTIVITY){
          resultMap = importMeeting(xlsRows, runPartyMap, runBranchMap, partyMeetingMap);
       }else{
           resultMap = importMeeting_Activity(xlsRows, runPartyMap, runBranchMap);
       }
        int successCount = (int) resultMap.get("successCount");
        int totalCount = (int) resultMap.get("total");

        logger.info(log(LogConstants.LOG_PM,
                "导入会议成功，总共{0}条记录，其中成功导入{1}条记录",
                totalCount, successCount));

        return resultMap;
    }

    private Map<String, Object> importMeeting(List<Map<Integer, String>> xlsRows,
                                                  Map<String, Party> runPartyMap,
                                                  Map<String, Branch> runBranchMap,
                                                  Map<String, Byte> partyMeetingMap) throws InterruptedException {

        //Date now = new Date();
        List<PmMeeting> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            PmMeeting record = new PmMeeting();

            String partyCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行分党委编码为空", row);
            }
            Party party = runPartyMap.get(partyCode);
            if (party == null) {
                throw new OpException("第{0}行分党委编码[{1}]不存在", row, partyCode);
            }
            if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),party.getId())){
                throw new OpException("您没有权限导入第{0}行党支部数据", row);
            }
            record.setPartyId(party.getId());

            String branchCode = StringUtils.trim(xlsRow.get(2));
            Branch branch = runBranchMap.get(branchCode);
            if (!partyService.isDirectBranch(party.getId())) {

                if (StringUtils.isBlank(branchCode)) {
                    throw new OpException("第{0}行党支部编码为空", row);
                }
                if (branch == null) {
                    throw new OpException("第{0}行党支部编码[{1}]不存在", row, branchCode);
                }
                record.setBranchId(branch.getId());
            }

            String _partyMeetingType = StringUtils.trimToNull(xlsRow.get(4));
            if (StringUtils.isBlank(_partyMeetingType)) {
                throw new OpException("第{0}行会议类型为空", row);
            }

            Byte partyMeetingType = partyMeetingMap.get(_partyMeetingType);
            if (partyMeetingType == null) {
                throw new OpException("第{0}行会议类型[{1}]有误", row, _partyMeetingType);
            }
            record.setType(partyMeetingType);

            String presenterCode = StringUtils.trim(xlsRow.get(5));
            if (StringUtils.isBlank(presenterCode)) {
                throw new OpException("第{0}行主持人学工号为空", row);
            }
            SysUserView presenter = sysUserService.findByCode(presenterCode);
            if (presenter==null||!memberService.isMember(presenter.getId(),party.getId(),branch.getId())) {
                throw new OpException("第{0}行主持人学工号[{1}]不属于该党支部的成员", row, presenterCode);
            }
            record.setPresenter(presenter.getId());

            String recorderCode = StringUtils.trim(xlsRow.get(7));
            if (StringUtils.isBlank(recorderCode)) {
                throw new OpException("第{0}行记录人学工号为空", row);
            }
            SysUserView recorder = sysUserService.findByCode(recorderCode);
            if (recorder==null||!memberService.isMember(recorder.getId(),party.getId(),branch.getId())) {
                throw new OpException("第{0}行记录人学工号[{1}]不属于该党支部的成员", row, recorderCode);
            }
            record.setRecorder(recorder.getId());

            int col = 9;
            record.setName(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPlanDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            //record.setCreateTime(now);
            record.setAddress(StringUtils.trimToNull(xlsRow.get(col++)));

            String _dueNum = StringUtils.trimToNull(xlsRow.get(col++));
            if(_dueNum==null && !NumberUtils.isDigits(_dueNum)){
                throw new OpException("第{0}行应到人数有误（必须是整数）", row, _dueNum);
            }
            record.setDueNum(Integer.valueOf(_dueNum));

            String _attendNum = StringUtils.trimToNull(xlsRow.get(col++));
            if(_attendNum==null && !NumberUtils.isDigits(_attendNum)){
                throw new OpException("第{0}行实到人数有误（必须是整数）", row, _attendNum);
            }
            record.setAttendNum(Integer.valueOf(_attendNum));

            String _absentNum = StringUtils.trimToNull(xlsRow.get(col++));
            if(_absentNum==null && !NumberUtils.isDigits(_absentNum)){
                throw new OpException("第{0}行请假人数有误（必须是整数）", row, _absentNum);
            }
            record.setAbsentNum(Integer.valueOf(_absentNum));

            String _invitee =  StringUtils.trimToNull(xlsRow.get(col++));
            if(_invitee!=null&&_invitee.length()>200){
                throw new OpException("第{0}行列席人员（长度过长）", row, _invitee);
            }
            record.setInvitee(_invitee);

            record.setIssue(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setContent(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setDecision(StringUtils.trimToNull(xlsRow.get(col++)));

            records.add(record);
        }

        int successCount = pmMeetingService.pmMeetingImport(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }


    private Map<String, Object> importMeeting_Activity(List<Map<Integer, String>> xlsRows,
                                              Map<String, Party> runPartyMap,
                                              Map<String, Branch> runBranchMap) throws InterruptedException {

        //Date now = new Date();
        List<PmMeeting> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            PmMeeting record = new PmMeeting();
            record.setType(PARTY_MEETING_BRANCH_ACTIVITY);
            String partyCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行分党委编码为空", row);
            }
            Party party = runPartyMap.get(partyCode);
            if (party == null) {
                throw new OpException("第{0}行分党委编码[{1}]不存在", row, partyCode);
            }
            if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),party.getId())){
                throw new OpException("您没有权限导入第{0}行党支部数据", row);
            }
            record.setPartyId(party.getId());

            String branchCode = StringUtils.trim(xlsRow.get(2));
            Branch branch = runBranchMap.get(branchCode);
            if (!partyService.isDirectBranch(party.getId())) {


                if (StringUtils.isBlank(branchCode)) {
                    throw new OpException("第{0}行党支部编码为空", row);
                }

                if (branch == null) {
                    throw new OpException("第{0}行党支部编码[{1}]不存在", row, branchCode);
                }
                record.setBranchId(branch.getId());
            }

            String recorderCode = StringUtils.trim(xlsRow.get(4));
            if (StringUtils.isBlank(recorderCode)) {
                throw new OpException("第{0}行记录人学工号为空", row);
            }
            SysUserView recorder = sysUserService.findByCode(recorderCode);
            if (recorder==null||!memberService.isMember(recorder.getId(),party.getId(),branch.getId())) {
                throw new OpException("第{0}行记录人学工号[{1}]不属于该党支部的成员", row, recorderCode);
            }
            record.setRecorder(recorder.getId());

            int col = 5;
            record.setName(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPlanDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setDate(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setAddress(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setIssue(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setContent(StringUtils.trimToNull(xlsRow.get(col++)));

            String _dueNum = StringUtils.trimToNull(xlsRow.get(col++));
            if(_dueNum==null && !NumberUtils.isDigits(_dueNum)){
                throw new OpException("第{0}行应到人数有误（必须是整数）", row, _dueNum);
            }
            record.setDueNum(Integer.valueOf(_dueNum));

            String _attendNum = StringUtils.trimToNull(xlsRow.get(col++));
            if(_attendNum==null && !NumberUtils.isDigits(_attendNum)){
                throw new OpException("第{0}行实到人数有误（必须是整数）", row, _attendNum);
            }
            record.setAttendNum(Integer.valueOf(_attendNum));

            String _absentNum = StringUtils.trimToNull(xlsRow.get(col++));
            if(_absentNum==null && !NumberUtils.isDigits(_absentNum)){
                throw new OpException("第{0}行请假人数有误（必须是整数）", row, _absentNum);
            }
            record.setAbsentNum(Integer.valueOf(_absentNum));

            String _invitee =  StringUtils.trimToNull(xlsRow.get(col++));
            if(_invitee!=null&&_invitee.length()>200){
                throw new OpException("第{0}行列席人员（长度过长）", row, _invitee);
            }
            record.setInvitee(_invitee);

            records.add(record);
        }

        int successCount = pmMeetingService.pmMeetingImport(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    public void pmMeeting_export(PmMeetingExample example,Byte type, HttpServletResponse response) {

        List<PmMeeting> records = pmMeetingMapper.selectByExample(example);
        int rownum = records.size();
        List<String[]> valuesList = new ArrayList<>();

        if(type!=null&&type!=PARTY_MEETING_BRANCH_ACTIVITY){
            String[] titles = {"年度","季度","所属"+CmTag.getStringProperty("partyName")+"|250|left","所属党支部|250|left","计划时间|100","实际时间|100","会议名称|150|left","会议议题|250|left","会议地点|100","审核情况|100",
                    "主持人","记录人","应到人数","实到人数","请假人数"};
            for (int i = 0; i < rownum; i++) {
                PmMeeting record = records.get(i);

                String[] values = {
                        String.valueOf(record.getYear()),
                        String.valueOf(record.getQuarter()),
                        CmTag.getParty(record.getPartyId()).getName(),
                        record.getBranchId()==null?"":CmTag.getBranch(record.getBranchId()).getName(),
                        DateUtils.formatDate(record.getPlanDate(), DateUtils.YYYY_MM_DD_HH_MM),
                        DateUtils.formatDate(record.getDate(), DateUtils.YYYY_MM_DD_HH_MM),
                        record.getName(),
                        record.getIssue(),
                        record.getAddress(),
                        PM_MEETING_STATUS_MAP.get(record.getStatus()),
                        CmTag.getUserById(record.getPresenter()).getRealname(),
                        CmTag.getUserById(record.getRecorder()).getRealname(),
                        record.getDueNum()==null?"": String.valueOf(record.getDueNum()),
                        record.getAttendNum()==null?"": String.valueOf(record.getAttendNum()),
                        record.getAbsentNum()==null?"":String.valueOf(record.getAbsentNum()),
                      //  DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYYMM),
                };

                valuesList.add(values);
            }
            String fileName = "会议列表(" + DateUtils.formatDate(new Date(), "yyyyMMddHH") + ")";
            ExportHelper.export(titles, valuesList, fileName, response);
        }else{
            String[] titles= {"年度","季度","所属"+CmTag.getStringProperty("partyName")+"|250|left","所属党支部|250|left","计划时间|100","实际时间|100","活动地点|100","主题党日活动名称|150|left","活动主题|250|left","主要内容及特色|250|left","审核情况|100",
                    "记录人","应到人数","实到人数","请假人数"};
            for (int i = 0; i < rownum; i++) {
                PmMeeting record = records.get(i);

                String[] values = {
                        String.valueOf(record.getYear()),
                        String.valueOf(record.getQuarter()),
                        CmTag.getParty(record.getPartyId()).getName(),
                        record.getBranchId()==null?"":CmTag.getBranch(record.getBranchId()).getName(),
                        DateUtils.formatDate(record.getPlanDate(), DateUtils.YYYY_MM_DD_HH_MM),
                        DateUtils.formatDate(record.getDate(), DateUtils.YYYY_MM_DD_HH_MM),
                        record.getAddress(),
                        record.getName(),
                        record.getIssue(),
                        record.getContent(),
                        PM_MEETING_STATUS_MAP.get(record.getStatus()),
                        CmTag.getUserById(record.getRecorder()).getRealname(),
                        String.valueOf(record.getDueNum()),
                        String.valueOf(record.getAttendNum()),
                        String.valueOf(record.getAbsentNum()),
                        //  DateUtils.formatDate(record.getFoundTime(), DateUtils.YYYYMM),
                };

                valuesList.add(values);
            }
            String fileName = "主题党日活动列表(" + DateUtils.formatDate(new Date(), "yyyyMMddHH") + ")";
            ExportHelper.export(titles, valuesList, fileName, response);
        }

    }
    // 导出工作记录
    @RequiresPermissions("pmMeeting:edit")
    @RequestMapping("/pmMeeting_exportWord")
    @ResponseBody
    public void pmMeeting_exportWord(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String partyName="";
        String branchName="";
        PmMeeting pmMeeting = pmMeetingMapper.selectByPrimaryKey(id);
        partyName=pmMeeting.getParty().getName();
        if(pmMeeting.getBranch()!=null){
          branchName=pmMeeting.getBranch().getName();
        }
        //输出文件
        String filename = String.format("党支部工作记录(%s)", partyName+branchName);
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");

        pmMeetingService.getExportWord(id,response.getWriter());

    }

    @RequiresPermissions("pmMeetingStat:list")
    @RequestMapping("/pmMeetingStat")
    public String pmMeeting2Stat(@RequestParam(defaultValue = "1") Integer cls, Integer partyId,
                                 Integer branchId,ModelMap modelMap) {

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchMap.get(branchId));
        modelMap.put("cls", cls);
        return "pm/pmMeeting/pmMeeting_stat";
    }

    @RequiresPermissions("pmMeetingStat:list")
    @RequestMapping("/pmMeeting_stat")
    @ResponseBody
    public void pmMeeting2_stat(HttpServletResponse response,
                                Byte cls,
                                Integer year,
                                Byte quarter,
                                Integer month,
                                Integer partyId,
                                Integer branchId,
                                @RequestParam(required = false, defaultValue = "0") int export,
                                Integer[] ids, // 导出的记录
                                Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        boolean addPermits = !ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        int count = iPmMapper.countPmMeetingStat(cls,year,quarter,month,partyId,branchId,PM_MEETING_STATUS_PASS, addPermits, adminPartyIdList, adminBranchIdList);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        /* List<PmMeeting2View> records= pmMeeting2ViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));*/
        List<PmMeetingStat> records= iPmMapper.selectPmMeetingStat(cls,year,quarter,month,partyId,branchId,PM_MEETING_STATUS_PASS,addPermits, adminPartyIdList, adminBranchIdList,new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmMeeting2.class, pmMeeting2Mixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmMeeting:list")
    @RequestMapping("/pmMeeting_count")
    public String pmMeeting2_count(Integer cls,
                                   Integer year,
                                   Byte quarter,
                                   Integer month,
                                   Integer partyId,
                                   Integer branchId,
                                   Byte type,ModelMap modelMap) {

        return "pm/pmMeeting/pmMeeting_count";
    }
}
