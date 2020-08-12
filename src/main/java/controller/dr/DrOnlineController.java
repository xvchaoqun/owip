package controller.dr;

import domain.dr.DrMember;
import domain.dr.DrOnline;
import domain.dr.DrOnlineExample;
import domain.dr.DrOnlineExample.Criteria;
import domain.dr.DrOnlineNotice;
import domain.unit.Unit;
import domain.unit.UnitPostView;
import domain.unit.UnitPostViewExample;
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
import sys.constants.DrConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequestMapping("/dr")
@Controller
public class DrOnlineController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drOnline:list")
    @RequestMapping("/drOnline")
    public String drOnline(Integer id,
                           Byte status,
                           ModelMap modelMap,
                           Byte isDeleted,
                           Integer unitId,
                           Integer[] unitTypes,
                           @RequestParam(required = false, defaultValue = "1") Byte cls) {

        if (null != isDeleted)
            modelMap.put("isDeleted", isDeleted);

        if (status != null){
            modelMap.put("status", status);
        }

        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(id);
        modelMap.put("drOnline", drOnline);
        modelMap.put("cls", cls);

        if (cls == 2) {
            if (unitTypes != null) {
                modelMap.put("selectUnitTypes", Arrays.asList(unitTypes));
            }
            if(unitId!=null){
                Unit unit = unitService.findAll().get(unitId);
                modelMap.put("unit", unit);
            }
            return "dr/drOnline/drOnlinePost";
        }

        return  "dr/drOnline/drOnline_page";
    }

    @RequiresPermissions("drOnline:list")
    @RequestMapping("/drOnline_data")
    @ResponseBody
    public void drOnline_data(HttpServletResponse response,
                                    @RequestParam(required = false, defaultValue = "1") Byte cls,
                                    Integer recordId,
                                    Short year,
                                    @RequestDateRange DateRange _recommendDate,
                                    Integer seq,
                                    Integer type,
                                    @RequestDateRange DateRange _startTime,
                                    @RequestDateRange DateRange _endTime,
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

        DrOnlineExample example = new DrOnlineExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        criteria.andIsDeletedEqualTo(cls==4);

        if(cls==1){
            criteria.andStatusIn(Arrays.asList(DrConstants.DR_ONLINE_INIT, DrConstants.DR_ONLINE_PUBLISH));
        }else if(cls==3){
            criteria.andStatusEqualTo(DrConstants.DR_ONLINE_FINISH);
        }

        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (_recommendDate.getStart() != null) {
            criteria.andRecommendDateGreaterThanOrEqualTo(_recommendDate.getStart());
        }
        if (_recommendDate.getEnd() != null){
            criteria.andRecommendDateLessThanOrEqualTo(_recommendDate.getEnd());
        }
        if (seq!=null) {
            criteria.andSeqEqualTo(seq);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (_startTime.getStart() != null){
            criteria.andStartTimeGreaterThanOrEqualTo(_startTime.getStart());
        }
        if (_startTime.getEnd() != null){
            criteria.andStartTimeLessThanOrEqualTo(_startTime.getEnd());
        }
        if (_endTime.getStart() != null){
            criteria.andEndTimeGreaterThanOrEqualTo(_endTime.getStart());
        }
        if (_endTime.getEnd() != null){
            criteria.andEndTimeLessThanOrEqualTo(_endTime.getEnd());
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            drOnline_export(example, response);
            return;
        }

        long count = drOnlineMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrOnline> records= drOnlineMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOnline.class, drOnlineMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping(value = "/drOnline_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnline_au(DrOnline record,
                              String _recommendDate,
                              Integer[] memberIds,
                              HttpServletRequest request) {

        Integer id = record.getId();
        record.setRecommendDate(DateUtils.parseDate(_recommendDate, DateUtils.YYYYMMDD_DOT));
        record.setMembers(StringUtils.trimToEmpty(StringUtils.join(memberIds, ",")));
        record.setIsDeleted(false);

        if (id == null) {
            drOnlineService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DR, "添加按批次管理线上民主推荐"));
        } else {

            drOnlineService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DR, "更新按批次管理线上民主推荐：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping("/drOnline_au")
    public String drOnline_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(id);
            modelMap.put("drOnline", drOnline);

            /*Integer recordId = drOnline.getRecordId();
            ScRecordView scRecordView = iScMapper.getScRecordView(recordId);
            modelMap.put("scRecord", scRecordView);*/
            modelMap.put("chiefMember", drMemberMapper.selectByPrimaryKey(drOnline.getChiefMemberId()));

            Set<Integer> selectMemberIds = new HashSet<>();
            String members = drOnline.getMembers();
            if (StringUtils.isNotBlank(members)){
                for (String memberIdStr : members.split(",")){
                    selectMemberIds.add(Integer.valueOf(memberIdStr));
                }
            }
            modelMap.put("selectMemberIds", new ArrayList<>(selectMemberIds));
        }

        Map<Byte, List<DrMember>> drMemberListMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry: DrConstants.DR_MEMBER_STATUS_MAP.entrySet()){
            List<DrMember> drMembers = drMemberService.getMembers(entry.getKey());
            if (drMembers.size() > 0){
                drMemberListMap.put(entry.getKey(), drMembers);
            }
        }

        modelMap.put("drMemberListMap", drMemberListMap);

        return "dr/drOnline/drOnline_au";
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping(value = "/drOnline_noticeEdit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnline_noticeEdit(Integer id, String notice, Integer isMobile, HttpServletRequest request) {

        DrOnline record = new DrOnline();
        record.setId(id);
        if (isMobile == 1){
            record.setMobileNotice(notice);
        }else {
            record.setNotice(notice);
        }

        drOnlineService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_DR, "更新线上民主推荐说明模板：{0}", record.getCode()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping("/drOnline_noticeEdit")
    public String drOnline_noticeEdit(Integer id, Integer isMobile, ModelMap modelMap){

        Map<Integer,DrOnlineNotice> noticeMap = drOnlineNoticeService.findAll();
        modelMap.put("noticeMap", noticeMap);

        if (isMobile != null){
            modelMap.put("isMobile", isMobile);
        }
        if (id != null) {
            DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(id);
            modelMap.put("drOnline", drOnline);
        }

        return "dr/drOnline/drOnline_noticeEdit";
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping(value = "/drOnline_inspectorNotice", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnline_inspectorNotice(Integer id, String inspectorNotice, HttpServletRequest request) {

        DrOnline record = new DrOnline();
        record.setId(id);
        if (null != inspectorNotice){
            record.setInspectorNotice(inspectorNotice);
        }

        drOnlineService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_DR, "更新线上民主推荐说明（纸质票）模板：{0}", record.getCode()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping("/drOnline_inspectorNotice")
    public String drOnline_inspectorNotice(Integer id, ModelMap modelMap){

        Map<Integer,DrOnlineNotice> noticeMap = drOnlineNoticeService.findAll();
        modelMap.put("noticeMap", noticeMap);

        if (id != null) {
            DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(id);
            modelMap.put("drOnline", drOnline);
        }

        return "dr/drOnline/inspector_notice";
    }

    @RequiresPermissions("drOnline:del")
    @RequestMapping(value = "/drOnline_fakeDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnline_fakeDel(HttpServletRequest request,
                                Integer[] ids,
                                boolean isDeleted,
                                ModelMap modelMap) {

        if (null != ids && ids.length>0){
            drOnlineService.fakeDel(ids, isDeleted);
            logger.info(log( LogConstants.LOG_DR, "假删除按批次管理线上民主推荐：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnline:del")
    @RequestMapping(value = "/drOnline_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnline_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            drOnlineService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DR, "批量删除按批次管理线上民主推荐：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping(value = "/drOnline_changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnline_changeStatus(HttpServletRequest request, Integer[] ids,
                                 Byte status,
                                 ModelMap modelMap) {


        if (null != ids && ids.length>0){

            drOnlineService.changeStatus(ids, status);
            logger.info(log( LogConstants.LOG_DR, "批量修改线上民主推荐的状态：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping("/drOnline_selectPost")
    public String drOnline_selectPost(Integer pageSize,
                                      Integer pageNo,
                                      ModelMap modelMap) {


        if (null == pageSize) {
            pageSize = 5;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitPostViewExample example = new UnitPostViewExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL);

        long count = unitPostViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitPostView> records = unitPostViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        modelMap.put("records", records);
        modelMap.put("commonList", commonList);

        return "dr/drOnline/drOnline_selectPost";
    }

    public void drOnline_export(DrOnlineExample example, HttpServletResponse response) {

        List<DrOnline> records = drOnlineMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属纪实|100","年份|100","推荐日期|100","编号|100","状态 0未发布 1已发布 2已撤回 3已完成 |100","推荐类型 会议推荐和谈话推荐|100","推荐组负责人|100","推荐组成员|100","推荐起始时间|100","推荐截止时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DrOnline record = records.get(i);
            String[] values = {
                record.getRecordId()+"",
                            //DateUtils.formatDate(record.getYear(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getRecommendDate(), DateUtils.YYYY_MM_DD),
                            record.getSeq()+"",
                            record.getStatus()+"",
                            record.getType()+"",
                            record.getChiefMemberId()+"",
                            record.getMembers(),
                            DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = String.format("按批次管理线上民主推荐(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
