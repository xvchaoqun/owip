package controller.pm;

import domain.base.MetaType;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.party.*;
import domain.pm.Pm3Meeting;
import domain.pm.Pm3MeetingExample;
import domain.pm.Pm3MeetingExample.Criteria;
import freemarker.template.TemplateException;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import persistence.pm.common.PmMeetingStat;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PmConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
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
import static sys.constants.MemberConstants.MEMBER_STATUS_TRANSFER;
import static sys.helper.PartyHelper.isDirectBranch;

@Controller
@RequestMapping("/pm")
public class Pm3MeetingController extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pm3Meeting:list")
    @RequestMapping("/pm3Meeting")
    public String pm3Meeting(@RequestParam(required = false, defaultValue = "0") Byte cls,
                             Integer partyId,
                             Integer branchId,
                             ModelMap modelMap) {

        boolean addPermits = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
        boolean isPa = ShiroHelper.hasRole(RoleConstants.ROLE_PARTYADMIN);
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        modelMap.put("isOw", addPermits);
        modelMap.put("isPa", isPa);

        Map pmInitCount = iPmMapper.selectPmInitCount3(!addPermits,adminPartyIdList, adminBranchIdList);
        if (pmInitCount != null) {
            modelMap.putAll(pmInitCount);
        }

        for (Integer directId : adminPartyIdList) {
            if (PartyHelper.isDirectBranch(directId)){
                modelMap.put("isDirectBranch", 1);
            }
        }

        if (partyId!=null){
            modelMap.put("party", partyMapper.selectByPrimaryKey(partyId));
        }
        if (branchId!=null){
            modelMap.put("branch", branchMapper.selectByPrimaryKey(branchId));
        }
        modelMap.put("cls", cls);

        return "pm/pm3Meeting/pm3Meeting_page";
    }

    @RequiresPermissions("pm3Meeting:list")
    @RequestMapping("/pm3Meeting_data")
    @ResponseBody
    public void pm3Meeting_data(HttpServletResponse response,
                                Integer partyId,
                                Integer branchId,
                                Integer year,
                                String name,
                                Byte type,
                                @RequestDateRange DateRange _startTime,
                                @RequestDateRange DateRange _endTime,
                                Byte isSum,
                                 @RequestParam(required = false, defaultValue = "0") Byte cls,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        Pm3MeetingExample example = new Pm3MeetingExample();
        Criteria criteria = example.createCriteria().andIsDeleteEqualTo(false);
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        example.setOrderByClause("id desc");

        if (cls!=null) {
            criteria.andStatusEqualTo(cls);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.trimLike(name));
        }
        if (isSum!=null){
            criteria.andTypeNotEqualTo(PmConstants.PM_3_BRANCH_COMMITTEE);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (_startTime.getStart()!=null){
            criteria.andStartTimeGreaterThanOrEqualTo(_startTime.getStart());
        }
        if (_startTime.getEnd()!=null){
            criteria.andStartTimeLessThanOrEqualTo(_startTime.getEnd());
        }
        if (_endTime.getStart()!=null){
            criteria.andEndTimeGreaterThanOrEqualTo(_endTime.getStart());
        }
        if (_endTime.getEnd() != null) {
            criteria.andEndTimeLessThanOrEqualTo(_endTime.getEnd());
        }

        long count = pm3MeetingMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Pm3Meeting> records= pm3MeetingMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pm3Meeting.class, pm3MeetingMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pm3Meeting:edit")
    @RequestMapping(value = "/pm3Meeting_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pm3Meeting_au(Pm3Meeting record,
                                Byte type1,
                                Byte type2,
                                String _startTime,
                                String _endTime,
                                String absentIds,//请假人员
                                HttpServletRequest request) throws InterruptedException, IOException {

        Integer id = record.getId();
        record.setType(type1==1?type1:type2);

        if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }

        if (StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseStringToDate(_startTime));
        }
        if (StringUtils.isNotBlank(_endTime)){
            record.setEndTime(DateUtils.parseStringToDate(_endTime));
        }
        if(StringUtils.isNotBlank(_startTime)){
            record.setYear(DateUtils.getYear(record.getStartTime()));
            record.setQuarter(DateUtils.getQuarter(record.getStartTime()));
            record.setMonth(DateUtils.getMonth(record.getStartTime()));
        }
        if (id == null) {
            record.setAbsents(absentIds);

            pm3MeetingService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PM, "添加组织生活：%s",record.getId()));

        } else {
            record.setAbsents(absentIds);
            pm3MeetingService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PM, "更新组织生活：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pm3Meeting:edit")
    @RequestMapping("/pm3Meeting_au")
    public String pm3Meeting_au(Integer id,
                                Boolean edit,
                                ModelMap modelMap) {
        Pm3Meeting pm3Meeting=new Pm3Meeting();
        if(id != null){

            pm3Meeting =pm3MeetingMapper.selectByPrimaryKey(id);

        } else {
            boolean odAdmin = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
            if (!odAdmin) {

                List<Integer> adminPartyIds = loginUserService.adminPartyIdList();
                List<Integer> adminBranchIds = loginUserService.adminBranchIdList();
                if(adminPartyIds.size()==0&&adminBranchIds.size()==1){
                    pm3Meeting.setPartyId(CmTag.getBranch(adminBranchIds.get(0)).getPartyId());
                    pm3Meeting.setBranchId(adminBranchIds.get(0));
                    modelMap.put("adminOnePartyOrBranch",true);

                    BranchViewExample example = new BranchViewExample();
                    example.createCriteria().andIdEqualTo(pm3Meeting.getBranchId());
                    List<BranchView> branchViews= branchViewMapper.selectByExample(example);
                    modelMap.put("memberCount",branchViews.get(0).getMemberCount());

                }else if(adminPartyIds.size()==1&&adminBranchIds.size()==0&&isDirectBranch(adminPartyIds.get(0))){
                    pm3Meeting.setPartyId(adminPartyIds.get(0));
                    modelMap.put("adminOnePartyOrBranch",true);

                    PartyViewExample example = new PartyViewExample();
                    example.createCriteria().andIdEqualTo(pm3Meeting.getPartyId());
                    List<PartyView> partyViews= partyViewMapper.selectByExample(example);
                    modelMap.put("memberCount",partyViews.get(0).getMemberCount());
                }else{
                    modelMap.put("adminOnePartyOrBranch",false);
                }
            }
        }

        modelMap.put("pm3Meeting",pm3Meeting);
        modelMap.put("edit",edit);
        return "pm/pm3Meeting/pm3Meeting_au";
    }

    @RequiresPermissions("pm3Meeting:del")
    @RequestMapping(value = "/pm3Meeting_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pm3Meeting_batchDel(HttpServletRequest request, Integer[] ids) {

        if (null != ids && ids.length>0){
            for (Integer id : ids) {
                Pm3Meeting record = pm3MeetingMapper.selectByPrimaryKey(id);
                if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
                    throw new UnauthorizedException();
                }
            }
            pm3MeetingService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PM, "批量删除组织生活月报：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pm3Meeting:edit")
    @RequestMapping(value = "/pm3Meeting_back", method = RequestMethod.POST)
    @ResponseBody
    public Map pm3Meeting_back(HttpServletRequest request, Integer[] ids) {

        if (null != ids && ids.length>0){
            for (Integer id : ids) {
                Pm3Meeting record = pm3MeetingMapper.selectByPrimaryKey(id);
                if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
                    throw new UnauthorizedException();
                }
            }
            pm3MeetingService.batchBack(ids);
            logger.info(log( LogConstants.LOG_PM, "批量退回组织生活月报：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("pm3Meeting:edit")
    @RequestMapping(value = "/pm3Meeting_submit", method = RequestMethod.POST)
    @ResponseBody
    public Map pm3Meeting_submit(HttpServletRequest request, Integer id, ModelMap modelMap) {

        if (null != id){
            pm3MeetingService.submit(id);
            logger.info(log( LogConstants.LOG_PM, "提交组织生活月报：{0}", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pm3Meeting:check")
    @RequestMapping(value = "/pm3Meeting_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pm3Meeting_check(HttpServletRequest request, Integer[] ids, boolean check, String checkOpinion, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            for (Integer id : ids) {
                Pm3Meeting record = pm3MeetingMapper.selectByPrimaryKey(id);
                if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),record.getPartyId(), record.getBranchId())){
                    throw new UnauthorizedException();
                }
            }
            pm3MeetingService.check(ids, check, checkOpinion);
            logger.info(log( LogConstants.LOG_OA, "批量审核组织生活月报：{0}", StringUtils.join(ids, ","), check?"报送":"退回"));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pm3Meeting:check")
    @RequestMapping("/pm3Meeting_check")
    public String pm3Meeting_check(Integer[] ids, ModelMap modelMap) {

        if (ids.length == 1) {
            modelMap.put("pm3Meeting", pm3MeetingMapper.selectByPrimaryKey(ids[0]));
        }

        return "pm/pm3Meeting/pm3Meeting_check";
    }

    @RequiresPermissions("pm3Meeting:edit")
    @RequestMapping("/pm3Meeting_download")
    public void pm3Meeting_download(int id, HttpServletRequest request,
                                             HttpServletResponse response) throws IOException, TemplateException {

        pm3MeetingService.download(id, request, response);
    }

    @RequiresPermissions("pm3MeetingStat:list")
    @RequestMapping("/pm3MeetingStat")
    public String pm3MeetingStat(@RequestParam(required = false, defaultValue = "1") Byte cls,
                                 Integer partyId,
                                 Integer branchId,
                                 ModelMap modelMap) {

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }
        modelMap.put("cls", cls);
        return "pm/pm3Meeting/pm3Meeting_stat";
    }

    @RequiresPermissions("pm3MeetingStat:list")
    @RequestMapping("/pm3MeetingStat_data")
    @ResponseBody
    public void pm3MeetingStat_data(HttpServletResponse response,
                                    Integer year,
                                    Byte quarter,
                                    Integer month,
                                    Integer partyId,
                                    Integer branchId,
                                    @RequestParam(required = false, defaultValue = "1") Byte cls,
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

        int count = iPmMapper.countPm3MeetingStat(cls,year,quarter,month,partyId,branchId,PmConstants.PM_3_STATUS_PASS, addPermits, adminPartyIdList, adminBranchIdList);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmMeetingStat> records= iPmMapper.selectPm3MeetingStat(cls,year,quarter,month,partyId,branchId,PmConstants.PM_3_STATUS_PASS,addPermits, adminPartyIdList, adminBranchIdList,new RowBounds((pageNo - 1) * pageSize, pageSize));
        if (export==1){
            pm3MeetingStat_export(records, cls, response);
        }

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

    @RequiresPermissions("pm3Meeting:list")
    @RequestMapping("/pm3Meeting_count")
    public String pmMeeting2_count(Integer cls,
                                   Integer year,
                                   Integer quarter,
                                   Integer month,
                                   Integer partyId,
                                   Integer branchId,
                                   Byte type,
                                   Byte isSum,
                                   ModelMap modelMap) {

        return "pm/pm3Meeting/pm3Meeting_count";
    }

    @RequiresPermissions("pm3Meeting:edit")
    @RequestMapping("/pm3Meeting_member")
    public String pm3Meeting_member(Integer partyId,Integer branchId,HttpServletRequest request, ModelMap modelMap) {

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
        List<Byte> statusList = new ArrayList<>();
        statusList.add(MEMBER_STATUS_NORMAL);
        statusList.add(MEMBER_STATUS_TRANSFER);
        criteria.andStatusIn(statusList);
        List<MemberView> membersViews=memberViewMapper.selectByExample(example);
        modelMap.put("membersViews",membersViews);
        return "pm/pm3Meeting/pm3Meeting_member";
    }

    public void pm3MeetingStat_export(List<PmMeetingStat> records, Byte cls, HttpServletResponse response) {

        int rownum = records.size();
        String[] titles1 = {"年份|100","月份|100","分党委|252","党支部|252","支部类型|100","支部委员会|100","党员集体活动总数|100","党员大会|100","党小组会|100","党课|100","组织生活会民主评议党员|100","主题党日|100"};
        String[] titles2 = {"年份|100","分党委|252","党支部|252","支部类型|100","支部委员会|100","党员集体活动总数|100","党员大会|100","党小组会|100","党课|100","组织生活会民主评议党员|100","主题党日|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PmMeetingStat record = records.get(i);
            Integer branchId = record.getBranchId();
            String type = "";
            if (branchId != null) {
                String types = CmTag.getBranch(branchId).getTypes();
                String[] _types = types.split(",");
                for (String s : _types) {
                    MetaType metaType = CmTag.getMetaType(Integer.valueOf(s));
                    if (StringUtils.isBlank(type)){
                        type = metaType.getName();
                    }else {
                        type += "," + metaType.getName();
                    }
                }
            }else {
                type = "直属党支部";
            }
            int sum = record.getCount2()+record.getCount3()+record.getCount4()+record.getCount5()+record.getCount6();
            if (cls==1) {
                String[] values = {
                        record.getYear() + "",
                        record.getMonth() + "",
                        CmTag.getParty(record.getPartyId()).getName(),
                        record.getBranchId() != null ? CmTag.getBranch(record.getBranchId()).getName() : "",
                        type,
                        record.getCount1() + "",
                        sum + "",
                        record.getCount2() + "",
                        record.getCount3() + "",
                        record.getCount4() + "",
                        record.getCount5() + "",
                        record.getCount6() + ""
                };
                valuesList.add(values);
            }else {
                String[] values = {
                        record.getYear() + "",
                        CmTag.getParty(record.getPartyId()).getName(),
                        record.getBranchId() != null ? CmTag.getBranch(record.getBranchId()).getName() : "",
                        type,
                        record.getCount1() + "",
                        sum + "",
                        record.getCount2() + "",
                        record.getCount3() + "",
                        record.getCount4() + "",
                        record.getCount5() + "",
                        record.getCount6() + ""
                };
                valuesList.add(values);
            }
        }
        String fileName = String.format("组织生活汇总(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(cls==1?titles1:titles2, valuesList, fileName, response);
    }
}
