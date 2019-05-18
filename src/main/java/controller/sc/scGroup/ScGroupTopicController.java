package controller.sc.scGroup;

import controller.sc.ScBaseController;
import domain.sc.scGroup.*;
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
import org.springframework.web.util.HtmlUtils;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

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
                               @RequestParam(required = false, value = "unitIds") Integer[] unitIds,
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
                                  @RequestParam(required = false, value = "unitIds") Integer[] unitIds,
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
    /*@RequiresPermissions("scGroupTopic:edit")
    @RequestMapping(value = "/scGroupTopic_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupTopic_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(file, "pdf")) {
            throw new OpException("文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "scGroupTopic");

        Map<String, Object> resultMap = success();
        //resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("filePath", savePath);

        return resultMap;
    }*/
    @RequiresPermissions("scGroupTopic:edit")
    @RequestMapping(value = "/scGroupTopic_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupTopic_au(ScGroupTopic record,
                                  MultipartFile[] files,
                                  @RequestParam(value = "unitIds[]", required = false) Integer[] unitIds,
                                  HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();
        record.setContent(HtmlUtils.htmlUnescape(record.getContent()));
        record.setMemo(HtmlUtils.htmlUnescape(record.getMemo()));

        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String _filePath = upload(file, "scGroupTopic");
            filePaths.add(_filePath);
        }
        if(filePaths.size()>0) {
            record.setFilePath(StringUtils.join(filePaths, ","));
        }

        if (id == null) {

            scGroupTopicService.insertSelective(record, unitIds);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "添加干部小组会议题：%s", record.getId()));
        } else {

            scGroupTopicService.updateByPrimaryKeySelective(record, unitIds);
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

        /*List<Unit> runUnits = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_RUN);
        modelMap.put("runUnits", runUnits);
        List<Unit> historyUnits = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_HISTORY);
        modelMap.put("historyUnits", historyUnits);*/

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

    @RequiresPermissions("scGroupTopic:del")
    @RequestMapping(value = "/scGroupTopic_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scGroupTopicService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "批量删除干部小组会议题：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
