package controller.sc.scGroup;

import controller.sc.ScBaseController;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sc.scGroup.*;
import domain.sys.SysUserView;
import domain.unit.Unit;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.gson.GsonUtils;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/sc")
public class ScGroupTopicController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scGroupTopic:list")
    @RequestMapping("/scGroupTopic")
    public String scGroupTopic(@RequestParam(defaultValue = "1") Integer cls,
                               Integer[] unitIds,
                               ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (unitIds!=null) {
            List<Integer> _unitIds = Arrays.asList(unitIds);
            modelMap.put("selectedUnitIds", _unitIds);
        }
        List<Unit> runUnits = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_RUN);
        modelMap.put("runUnits", runUnits);
        List<Unit> historyUnits = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_HISTORY);
        modelMap.put("historyUnits", historyUnits);

        {
            ScGroupExample example = new ScGroupExample();
            example.createCriteria().andIsDeletedEqualTo(false);
            example.setOrderByClause("hold_date desc");
            List<ScGroup> scGroups = scGroupMapper.selectByExample(example);
            modelMap.put("scGroups", scGroups);
        }

        return "sc/scGroup/scGroupTopic/scGroupTopic_page";
    }

    @RequiresPermissions("scGroupTopic:list")
    @RequestMapping("/scGroupTopic_data")
    public void scGroupTopic_data(HttpServletResponse response,
                                    Integer groupId,
                                  Integer year,
                                  @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date holdDate,
                                  String name,
                                  Integer[] unitIds,
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

        ScGroupTopicViewExample example = new ScGroupTopicViewExample();
        ScGroupTopicViewExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("id desc");

        if (groupId!=null) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (name!=null) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (holdDate!=null) {
            criteria.andHoldDateEqualTo(holdDate);
        }
        if (unitIds != null && unitIds.length>0) {
            List<Integer> selectedUnitIds = Arrays.asList(unitIds);
            ScGroupTopicUnitExample example2 = new ScGroupTopicUnitExample();
            example2.createCriteria().andUnitIdIn(selectedUnitIds);
            List<ScGroupTopicUnit> scGroupTopicUnits = scGroupTopicUnitMapper.selectByExample(example2);
            List<Integer> topicIds = new ArrayList<>();
            for (ScGroupTopicUnit scGroupTopicUnit : scGroupTopicUnits) {
                topicIds.add(scGroupTopicUnit.getTopicId());
            }
            if(topicIds.size()>0){
                criteria.andIdIn(topicIds);
            }else{
                criteria.andIdIsNull();
            }
        }

        long count = scGroupTopicViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScGroupTopicView> records= scGroupTopicViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scGroupTopic.class, scGroupTopicMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scGroupTopic:edit")
    @RequestMapping(value = "/scGroupTopic_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupTopic_au(ScGroupTopic record,
                                  MultipartFile[] files,
                                  Integer[] unitIds,
                                  String users, // 确定的考察对象
                                  HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();
        record.setContent(record.getContent());
        record.setMemo(record.getMemo());

        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String _filePath = upload(file, "scGroupTopic");
            filePaths.add(_filePath);
        }
        if(filePaths.size()>0) {
            record.setFilePath(StringUtils.join(filePaths, ","));
        }

        List<TopicUser> topicUsers = null;
         MetaType metaType = CmTag.getMetaTypeByCode("mt_sgt_candidate");
        if(metaType!=null && metaType.getId().intValue()==record.getType()){

            topicUsers = GsonUtils.toBeans(users, TopicUser.class);
            if(topicUsers.size()==0) return failed("请选择考察对象");
        }

        if (id == null) {

            scGroupTopicService.insertSelective(record, unitIds, topicUsers);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "添加干部小组会议题：%s", record.getId()));
        } else {

            scGroupTopicService.updateByPrimaryKeySelective(record, unitIds, topicUsers);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "更新干部小组会议题：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupTopic:edit")
    @RequestMapping("/scGroupTopic_content")
    public String scGroupTopic_content(Integer topicId, ModelMap modelMap) {

        if (topicId != null) {
            ScGroupTopic scGroupTopic = scGroupTopicMapper.selectByPrimaryKey(topicId);
            modelMap.put("scGroupTopic", scGroupTopic);

            Integer recordId = scGroupTopic.getRecordId();
            if(recordId!=null){
                modelMap.put("scRecord", iScMapper.getScRecordView(recordId));
            }
            Integer unitPostId = scGroupTopic.getUnitPostId();
            if(unitPostId!=null){
                modelMap.put("unitPost", unitPostMapper.selectByPrimaryKey(unitPostId));
            }

            modelMap.put("selectUsers", scGroupTopicService.getTopicUsers(scGroupTopic.getId()));
            modelMap.put("candidateUser", CmTag.getUserById(scGroupTopic.getCandidateUserId()));
        }

        return "sc/scGroup/scGroupTopic/scGroupTopic_content";
    }

    @RequiresPermissions("scGroupTopic:edit")
    @RequestMapping("/scGroupTopic_au")
    public String scGroupTopic_au(Integer id, Integer groupId, ModelMap modelMap) {

        Set<Integer> selectUnitIds = new HashSet<>();
        if (id != null) {
            ScGroupTopic scGroupTopic = scGroupTopicMapper.selectByPrimaryKey(id);
            modelMap.put("scGroupTopic", scGroupTopic);
            if(scGroupTopic!=null){
                groupId = scGroupTopic.getGroupId();

                Integer recordId = scGroupTopic.getRecordId();
                if(recordId!=null){
                    modelMap.put("scRecord", iScMapper.getScRecordView(recordId));
                }
                Integer unitPostId = scGroupTopic.getUnitPostId();
                if(unitPostId!=null){
                    modelMap.put("unitPost", unitPostMapper.selectByPrimaryKey(unitPostId));
                }

                modelMap.put("selectUsers", scGroupTopicService.getTopicUsers(scGroupTopic.getId()));
                modelMap.put("candidateUser", CmTag.getUserById(scGroupTopic.getCandidateUserId()));
            }
            List<Unit> units = scGroupTopicService.getUnits(id);
            for (Unit unit : units) {
                selectUnitIds.add(unit.getId());
            }
        }

        modelMap.put("groupId", groupId);

        modelMap.put("selectUnitIds", new ArrayList<>(selectUnitIds));

        {
            ScGroupExample example = new ScGroupExample();
            example.createCriteria().andIsDeletedEqualTo(false);
            example.setOrderByClause("hold_date desc");
            List<ScGroup> scGroups = scGroupMapper.selectByExample(example);
            modelMap.put("scGroups", scGroups);
        }

        // MAP<unitTypeId, List<unitId>>
        Map<Integer, List<Integer>> unitListMap = new LinkedHashMap<>();
        Map<Integer, List<Integer>> historyUnitListMap = new LinkedHashMap<>();
        Map<Integer, Unit> unitMap = unitService.findAll();
        for (Unit unit : unitMap.values()) {

            Integer unitTypeId = unit.getTypeId();
            if (unit.getStatus() == SystemConstants.UNIT_STATUS_HISTORY){
                List<Integer> units = historyUnitListMap.get(unitTypeId);
                if (units == null) {
                    units = new ArrayList<>();
                    historyUnitListMap.put(unitTypeId, units);
                }
                units.add(unit.getId());
            }else {
                List<Integer> units = unitListMap.get(unitTypeId);
                if (units == null) {
                    units = new ArrayList<>();
                    unitListMap.put(unitTypeId, units);
                }
                units.add(unit.getId());
            }
        }
        modelMap.put("unitListMap", unitListMap);
        modelMap.put("historyUnitListMap", historyUnitListMap);

        return "sc/scGroup/scGroupTopic/scGroupTopic_au";
    }

    @RequiresPermissions("scGroupTopic:edit")
    @RequestMapping(value = "/scGroupTopic_selectUser", method = RequestMethod.POST)
    public void do_scGroupTopic_selectUser(
            Integer userId,
            HttpServletResponse response) throws IOException {

        TopicUser user = new TopicUser();
        if (userId != null) {
            CadreView cv = cadreService.dbFindByUserId(userId);
            if (cv != null) {
                user.setUserId(cv.getUserId());
                user.setRealname(cv.getRealname());
                user.setCode(cv.getCode());
                user.setTitle(cv.getTitle());
            } else {

                SysUserView uv = sysUserService.findById(userId);
                user.setUserId(uv.getId());
                user.setRealname(uv.getRealname());
                user.setCode(uv.getCode());
                user.setTitle(uv.getUnit());
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("user", user);
        JSONUtils.write(response, resultMap);
    }

    // 确定的考察对象
    @RequestMapping("/scGroupTopic_users")
    @ResponseBody
    public Map scGroupTopic_users(Integer recordId){

        List<TopicUser> topicUsers = new ArrayList<>();

        if(recordId!=null) {
            MetaType metaType = CmTag.getMetaTypeByCode("mt_sgt_candidate");
            ScGroupTopic scGroupTopic = scGroupTopicService.getTopic(recordId, metaType.getId());
            if (scGroupTopic != null) {
                topicUsers = scGroupTopicService.getTopicUsers(scGroupTopic.getId());
            }
        }
        int totalCount = topicUsers.size();
        List options = new ArrayList<>();
        if(totalCount>0){
            for(TopicUser record:topicUsers){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getCode() + "-" + record.getRealname() + "-" + record.getTitle());
                option.put("id", record.getUserId() + "");
                option.put("title", record.getTitle());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", totalCount);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("scGroupTopic:del")
    @RequestMapping(value = "/scGroupTopic_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scGroupTopicService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "批量删除干部小组会议题：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/scGroupTopic_selects")
    @ResponseBody
    public Map scGroupTopic_selects(Integer pageSize, Integer pageNo, Integer type, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScGroupTopicViewExample example = new ScGroupTopicViewExample();
        ScGroupTopicViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("hold_date desc");

        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = scGroupTopicViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScGroupTopicView> records = scGroupTopicViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(ScGroupTopicView record:records){

                String holdDate = DateUtils.formatDate(record.getHoldDate(), DateUtils.YYYYMMDD_DOT);
                Map<String, Object> option = new HashMap<>();
                option.put("text", String.format("[%s]", holdDate) + record.getName());
                option.put("holdDate", holdDate);
                if(record.getUnitPostId()!=null) {
                    option.put("unitPost", unitPostMapper.selectByPrimaryKey(record.getUnitPostId()));
                }
                option.put("scType", record.getScType());
                option.put("content", record.getContent());

                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
