package controller.pm;

import domain.member.Member;

import domain.member.MemberView;
import domain.member.MemberViewExample;

import domain.party.Branch;
import domain.party.Party;
import domain.pm.PmMeeting;
import domain.pm.PmMeetingExample;
import domain.pm.PmMeetingFile;

import domain.pm.PmMeetingFileExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

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
import persistence.member.MemberViewMapper;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PmConstants;

import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static sys.constants.PmConstants.*;
import static sys.constants.RoleConstants.ROLE_ADMIN;
import static sys.constants.RoleConstants.ROLE_ODADMIN;


@Controller

public class PmMeetingController extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmMeeting:list")
    @RequestMapping("/pmMeeting")
    public String partyMeeting(@RequestParam(defaultValue = "1") Integer cls,Byte type ,
                               Integer partyId,
                               Integer branchId,ModelMap modelMap) {

        if (ShiroHelper.hasRole(ROLE_ODADMIN)&&!ShiroHelper.hasRole(ROLE_ADMIN)) {
            cls=3;
        }
        modelMap.put("type", type);
        modelMap.put("cls", cls);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchMap.get(branchId));

        return "pm/pmMeeting/pmMeeting_page";
    }

    @RequiresPermissions("pmMeeting:list")
    @RequestMapping("/pmMeeting_page")
    public String pmMeeting_page(Integer branch,ModelMap modelMap) {

            return "pm/pmMeeting/pmMeeting_page";

    }
    @RequiresPermissions("pmMeeting:list")
    @RequestMapping("/pmMeeting_data")
    public void partyMeeting_data(Byte type,
                                  @RequestParam(defaultValue = "1") Integer cls,
                                  Integer partyId,
                                  Integer branchId,
                                  String issue,
                                  Byte quarter,
                                  @RequestDateRange DateRange _meetingDate,
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
        if (StringUtils.isNotBlank(issue)) {
            criteria.andIssueLike(SqlUtils.like(issue));
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
                criteria.andStatusEqualTo(PmConstants.PM_MEETING_STATUS_INIT);
                break;
            case 2:
                criteria.andStatusEqualTo(PmConstants.PM_MEETING_STATUS_DENY);
                break;
            case 3:
                criteria.andStatusEqualTo(PmConstants.PM_MEETING_STATUS_PASS);
                break;
            default:
                break;
        }
        example.setOrderByClause("id desc");

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
           // branchId=pmMeeting.getBranchId();

            PmMeetingFileExample example = new PmMeetingFileExample();
            example.createCriteria().andMeetingIdEqualTo(id);
            List<PmMeetingFile> pmMeetingFiles=pmMeetingFileMapper.selectByExample(example);
            modelMap.put("pmMeetingFiles",pmMeetingFiles);

        } else {
            boolean odAdmin = ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
            if (!odAdmin) {
                Integer loginUserId = ShiroHelper.getCurrentUserId();
                Member member = memberService.get(loginUserId);
                if (branchMemberService.isPresentAdmin(loginUserId, member.getPartyId(), member.getBranchId())) {
                    pmMeeting.setPartyId(member.getPartyId());
                    pmMeeting.setBranchId(member.getBranchId());
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
                                     @RequestParam(value = "_files[]", required = false) MultipartFile[] _files, // 附件
                                     @RequestParam(value = "attendIds[]", required = false) String attendIds,//参会人员
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
            if(StringUtils.isNotBlank(attendIds)){
                record.setAttends(attendIds);
            }
            record.setType(type);

            pmMeetingService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PM, "添加三会一课：%s",record.getId()));

        } else {
            if (reedit == 1) {

                record.setStatus(PM_MEETING_STATUS_INIT);
                record.setIsBack(false);
            }
            if(StringUtils.isNotBlank(attendIds)){
            record.setAttends(attendIds);
            }
            pmMeetingService.updateByPrimaryKeySelective(record);
           logger.info(addLog(LogConstants.LOG_PM, "更新三会一课：%s", record.getId()));
        }
        for (PmMeetingFile pmMeetingFile : pmMeetingFiles) {
            pmMeetingFile.setMeetingId(record.getId());
            pmMeetingFileMapper.insertSelective(pmMeetingFile);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmMeeting:edit")
    @RequestMapping(value = "/pmMeeting_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyBranchMeeting_del(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {
        if (null != ids && ids.length>0){
            pmMeetingService.del(ids);
            logger.info(addLog(LogConstants.LOG_PM, "批量删除三会一课：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmMeeting:edit")
    @RequestMapping("/pmMeeting_member")
    public String pmMeeting_member(Integer partyId,Integer branchId,HttpServletRequest request, ModelMap modelMap) {

        MemberViewExample example = new MemberViewExample();
        MemberViewExample.Criteria criteria = example.createCriteria();
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        List<MemberView> membersViews=memberViewMapper.selectByExample(example);
        modelMap.put("membersViews",membersViews);
        return "pm/pmMeeting/pmMeeting_member";
    }
/*    @RequestMapping("/pmMeeting_memberData")
    public void pmMeeting_memberData(Integer partyId,
                                  Integer branchId,
                                  HttpServletResponse response, Integer pageSize, Integer pageNo, ModelMap modelMap)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        MemberViewExample example = new MemberViewExample();
        MemberViewExample.Criteria criteria = example.createCriteria();

        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        List<MemberView> records= memberViewMapper.selectByExample(example);
        modelMap.put("rows", records);
        long count = memberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberView> records= memberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
    }*/

    @RequiresPermissions("pmMeeting:approve")
    @RequestMapping( "/pmMeeting_check")
    public String pmMeeting_check(Integer id,Boolean check,HttpServletRequest request, ModelMap modelMap) {
        PmMeeting pmMeeting=pmMeetingMapper.selectByPrimaryKey(id);
        modelMap.put("pmMeeting",pmMeeting);
        return "pm/pmMeeting/pmMeeting_check";
    }

    @RequiresPermissions("pmMeeting:approve")
    @RequestMapping(value = "/pmMeeting_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmMeeting_check(PmMeeting record,Boolean check,Boolean hasPass,HttpServletRequest request, ModelMap modelMap) {
       if(check){
           if(hasPass==null)
               record.setStatus(PM_MEETING_STATUS_DENY);
           else
               record.setStatus(PM_MEETING_STATUS_PASS);
       }else{
        record.setStatus(PM_MEETING_STATUS_DENY);
        record.setIsBack(true);
       }
        pmMeetingMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_PM, "审核三会一课：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }
}
