package controller.op;

import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.op.OpAttatchExample;
import domain.op.OpRecord;
import domain.op.OpRecordExample;
import domain.op.OpRecordExample.Criteria;
import domain.sys.SysUserView;
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
import service.cadre.CadrePostService;
import sys.constants.LogConstants;
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

@Controller
@RequestMapping("/op")
public class OpRecordController extends OpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private CadrePostService cadrePostService;

    @RequiresPermissions("opRecord:list")
    @RequestMapping("/opRecord")
    public String opRecord(Integer userId,
                           Integer talkUserId,
                           ModelMap modelMap) {

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (talkUserId != null){
            modelMap.put("talkUser", sysUserService.findById(talkUserId));
        }

        return "op/opRecord/opRecord_page";
    }

    @RequiresPermissions("opRecord:list")
    @RequestMapping("/opRecord_data")
    @ResponseBody
    public void opRecord_data(HttpServletResponse response,
                                    Integer id,
                                    @RequestDateRange DateRange startDate,
                                    Integer userId,
                                    Integer adminLevel,
                                    Integer type,
                                    Integer way,
                                    Integer talkUserId,
                                    Integer[] issue,
                                
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

        OpRecordExample example = new OpRecordExample();
        OpRecordExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("start_date asc");

        if (id!=null) {
            criteria.andIdEqualTo(id);
        }
        if (startDate.getStart()!=null) {
            criteria.andStartDateGreaterThanOrEqualTo(startDate.getStart());
        }
        if (startDate.getEnd()!=null) {
            criteria.andStartDateLessThanOrEqualTo(startDate.getEnd());
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (adminLevel!=null) {
            criteria.andAdminLevelEqualTo(adminLevel);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
            if (issue!=null) {
                for (Integer iss : issue){
                    if (iss != null) {
                        criteria.andIssueEqualTo(iss);
                    }
                }
            }
        }

        if (way!=null) {
            criteria.andWayEqualTo(way);
        }
        if (talkUserId!=null) {
            criteria.andTalkUserIdEqualTo(talkUserId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            opRecord_export(example, response);
            return;
        }

        long count = opRecordMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OpRecord> records= opRecordMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(opRecord.class, opRecordMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("opRecord:edit")
    @RequestMapping(value = "/opRecord_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_opRecord_au(OpRecord record,
                              String startDate,
                              Integer[] issue,
                              HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(startDate)){
            record.setStartDate(DateUtils.parseDate(startDate, DateUtils.YYYYMMDD_DOT));
        }else {
            record.setStartDate(DateUtils.parseDate(String.valueOf(new Date()),DateUtils.YYYYMMDD_DOT));
        }
        for (Integer iss : issue){
            if (iss != null) {
                record.setIssue(iss);
            }
        }
        if (id == null) {
            Integer userId = record.getUserId();
            CadreView cadreView = new CadreView();
            CadreViewExample example = new CadreViewExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<CadreView> cadreViews = cadreViewMapper.selectByExample(example);
            if (cadreViews.size() == 1){
                cadreView = cadreViews.get(0);
            }
            if (cadreView == null){
                record.setPost("--");
                record.setAdminLevel(22);
            }else {
                record.setPost(cadreView.getTitle());
                record.setAdminLevel(cadreView.getAdminLevel());
            }
            opRecordService.insertSelective(record);
            logger.info(log( LogConstants.LOG_OP, "添加组织处理：处理对象{0}", record.getUserId()));
        } else {

            opRecordService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_OP, "更新组织处理：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("opRecord:edit")
    @RequestMapping("/opRecord_au")
    public String opRecord_au(Integer id,
                              Integer userId,
                              Integer talkUserId,
                              ModelMap modelMap) {

        /*if (userId != null){
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }
        if (talkUserId != null){
            modelMap.put("talkSysUser", CmTag.getUserById(talkUserId));
        }*/
        if (id != null) {
            OpRecord opRecord = opRecordMapper.selectByPrimaryKey(id);
            modelMap.put("sysUser", CmTag.getUserById(opRecord.getUserId()));
            modelMap.put("talkSysUser", CmTag.getUserById(opRecord.getTalkUserId()));
            modelMap.put("opRecord", opRecord);
        }else {
            if (userId != null){
                modelMap.put("sysUser", CmTag.getUserById(userId));
            }
            if (talkUserId != null){
                modelMap.put("talkSysUser", CmTag.getUserById(talkUserId));
            }
        }
        return "op/opRecord/opRecord_au";
    }

    @RequiresPermissions("opRecord:edit")
    @RequestMapping(value = "/opRecord_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map opRecord_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            //删除附件
            List<Integer> opAttatchIds = opAttatchService.getIdByRecordId(ids);
            if (opAttatchIds.size() > 0) {
                OpAttatchExample example = new OpAttatchExample();
                example.createCriteria().andIdIn(opAttatchIds);
                opAttatchMapper.deleteByExample(example);
            }

            //删除组织处理
            opRecordService.batchDel(ids);
            logger.info(log( LogConstants.LOG_OP, "批量删除组织处理：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void opRecord_export(OpRecordExample example, HttpServletResponse response) {

        List<OpRecord> records = opRecordMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|100","处理对象|100","执行日期|100","时任职务|350","行政级别|100","组织处理方式|100",
                "开展方式|200","谈话人类型|200","具体谈话人|100","针对问题|350","其他针对问题|200","备注|200"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            OpRecord record = records.get(i);
            SysUserView uv = sysUserService.findById(record.getUserId());
            SysUserView talkUv = sysUserService.findById(record.getTalkUserId());
            String[] values = {
                    uv.getCode(),
                    uv.getRealname(),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYYMMDD_DOT),
                    record.getPost() == null ? "--" : record.getPost(),
                    metaTypeService.getName(record.getAdminLevel()),
                    metaTypeService.getName(record.getType()),
                    metaTypeService.getName(record.getWay()),
                    metaTypeService.getName(record.getTalkType()),
                    talkUv.getRealname(),
                    metaTypeService.getName(record.getIssue()),
                    record.getIssueOther() == null ? "--" : record.getIssueOther(),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("组织处理(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/opRecord_selects")
    @ResponseBody
    public Map opRecord_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OpRecordExample example = new OpRecordExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            //criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = opRecordMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<OpRecord> records = opRecordMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            List<Integer> userIds = new ArrayList<>();

            for(OpRecord record:records){

                Integer userId = record.getUserId();
                if (!userIds.contains(userId)) {
                    userIds.add(userId);
                    SysUserView uv = sysUserService.findById(userId);
                    Map<String, Object> option = new HashMap<>();
                    option.put("text", uv.getRealname());
                    option.put("id", uv.getUserId());
                    option.put("username", uv.getUsername());
                    option.put("code", uv.getCode());

                    options.add(option);
                }
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequestMapping("/opRecord_talkUser_selects")
    @ResponseBody
    public Map opRecord_talkUser_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OpRecordExample example = new OpRecordExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            //criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = opRecordMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<OpRecord> records = opRecordMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            List<Integer> talkUserIds = new ArrayList<>();

            for(OpRecord record:records){

                Integer talkUserId = record.getTalkUserId();
                if (!talkUserIds.contains(talkUserId)){
                    talkUserIds.add(talkUserId);
                    SysUserView uv = sysUserService.findById(talkUserId);
                    Map<String, Object> option = new HashMap<>();
                    option.put("text", uv.getRealname());
                    option.put("id", uv.getUserId());
                    option.put("username", uv.getUsername());
                    option.put("code", uv.getCode());

                    options.add(option);
                }
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    /*//待考虑组织处理方式二级联动的方法
    @RequestMapping("/op/metaTypes")
    public String metaTypes(String __code, String extraAttr, ModelMap modelMap) {

        if (StringUtils.isNotBlank(extraAttr)) {
            List<MetaType> metaTypes = new ArrayList<>();
            for (MetaType metaType : metaTypeService.metaTypes(__code).values()) {
                if (StringUtils.equals(extraAttr, metaType.getExtraAttr())) {
                    metaTypes.add(metaType);
                }
            }
            modelMap.put("metaTypes", metaTypes);
        } else {
            modelMap.put("metaTypes", metaTypeService.metaTypes(__code).values());
        }

        return "op/opRecord/metaTypes";
    }*/
}
