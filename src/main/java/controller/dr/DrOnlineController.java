package controller.dr;

import domain.base.MetaType;
import domain.dr.DrMember;
import domain.dr.DrOnline;
import domain.dr.DrOnlineExample;
import domain.dr.DrOnlineExample.Criteria;
import domain.dr.DrOnlineNotice;
import domain.sc.scRecord.ScRecordView;
import domain.sc.scRecord.ScRecordViewExample;
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
import org.springframework.web.util.HtmlUtils;
import sys.constants.DrConstants;
import sys.constants.LogConstants;
import sys.constants.ScConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
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
                           ModelMap modelMap,
                           @RequestParam(required = false, defaultValue = "1") Byte cls) {

        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(id);
        modelMap.put("drOnline", drOnline);
        modelMap.put("cls", cls);
        if (cls == 1) {
            return "dr/drOnline/drOnline_page";
        }
        return "";
    }

    @RequiresPermissions("drOnline:list")
    @RequestMapping("/drOnline_data")
    @ResponseBody
    public void drOnline_data(HttpServletResponse response,
                                    Integer recordId,
                                    Short year,
                                    @RequestDateRange DateRange _recommendDate,
                                    Integer seq,
                                    Byte status,
                                    Integer type,
                                    @RequestDateRange DateRange _startTime,
                                    @RequestDateRange DateRange _endTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
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


        if (status != null){
            criteria.andStatusEqualTo(status);
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
                              Integer noticeId,
                              String _recommendDate,
                              String _startTime,
                              String _endTime,
                              @RequestParam(value = "memberIds", required = false) Integer[] memberIds,
                              HttpServletRequest request) {

        Integer id = record.getId();
        Integer seq = record.getSeq();
        record.setMembers(StringUtils.trimToEmpty(StringUtils.join(memberIds, ",")));
        record.setRecommendDate(DateUtils.parseDate(_recommendDate, DateUtils.YYYY_MM_DD));
        record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD));
        DrOnlineNotice drOnlineNotice = drOnlineNoticeMapper.selectByPrimaryKey(noticeId);
        record.setNotice(HtmlUtils.htmlUnescape(drOnlineNotice.getContent()));

        if (id == null) {
            drOnlineService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DR, "添加按批次管理线上民主推荐：{0}", record.getId()));
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

        Map<Integer,DrOnlineNotice> drOnlineNotices = drOnlineNoticeService.findAll();

        modelMap.put("drOnlineNotices", drOnlineNotices);

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
    public Map do_drOnline_noticeEdit(DrOnline record, HttpServletRequest request) {

        Integer id = record.getId();
        record.setNotice(HtmlUtils.htmlUnescape(record.getNotice()));

        if (id == null) {

            drOnlineService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DR, "添加线上民主推荐说明模板：{0}", record.getCode()));
        } else {

            drOnlineService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DR, "更新线上民主推荐说明模板：{0}", record.getCode()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping("/drOnline_noticeEdit")
    public String drOnline_noticeEdit(Integer id, ModelMap modelMap){

        if (id != null) {
            DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(id);
            modelMap.put("drOnline", drOnline);
        }

        return "dr/drOnline/drOnline_noticeEdit";
    }

    @RequiresPermissions("drOnline:del")
    @RequestMapping(value = "/drOnline_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnline_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            drOnlineService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DR, "批量删除按批次管理线上民主推荐：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping(value = "/drOnline_changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnline_changeStatus(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids,
                                 Byte status,
                                 ModelMap modelMap) {


        if (null != ids && ids.length>0){

            drOnlineService.changeStatus(ids, status);
            logger.info(log( LogConstants.LOG_DR, "批量修改按批次管理线上民主推荐的状态：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping("/drOnline_selectPost")
    public String drOnline_selectPost(Integer id,
                                      Integer pageSize,
                                      Integer pageNo,
                                      ModelMap modelMap) {

        if (id != null) {
            DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(id);
            modelMap.put("drOnline", drOnline);
        }

        if (null == pageSize) {
            pageSize = 5;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        // 从干部选任纪实-单个岗位调整（正在进行、选任暂停，选拔方式为“民主推荐”）中选择岗位
        MetaType scTypeDr = CmTag.getMetaTypeByCode("mt_sctype_dr");
        List<Byte> statusList = new ArrayList<>();
        statusList.add(ScConstants.SC_RECORD_STATUS_INIT);
        statusList.add(ScConstants.SC_RECORD_STATUS_SUSPEND);
        ScRecordViewExample example = new ScRecordViewExample();
        example.createCriteria().andStatusIn(statusList)
                .andScTypeEqualTo(scTypeDr.getId());

        long count = scRecordViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScRecordView> records = scRecordViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
