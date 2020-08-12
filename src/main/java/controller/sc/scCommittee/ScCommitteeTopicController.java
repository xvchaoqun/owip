package controller.sc.scCommittee;

import controller.sc.ScBaseController;
import domain.sc.scCommittee.*;
import domain.unit.Unit;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScCommitteeTopicController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scCommitteeTopic:list")
    @RequestMapping("/scCommitteeTopic")
    public String scCommitteeTopic(@RequestParam(defaultValue = "1") Integer cls,
                                   Integer[] unitIds,
                                   ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("scCommittees", scCommitteeService.findAll());

        if (unitIds != null) {
            List<Integer> _unitIds = Arrays.asList(unitIds);
            modelMap.put("selectedUnitIds", _unitIds);
        }
        List<Unit> runUnits = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_RUN);
        modelMap.put("runUnits", runUnits);
        List<Unit> historyUnits = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_HISTORY);
        modelMap.put("historyUnits", historyUnits);


        return "sc/scCommittee/scCommitteeTopic/scCommitteeTopic_page";
    }

    @RequiresPermissions("scCommitteeTopic:list")
    @RequestMapping("/scCommitteeTopic_data")
    public void scCommitteeTopic_data(HttpServletResponse response,
                                      Integer year,
                                      Integer committeeId,
                                      @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date holdDate,
                                      String name,
                                      Integer[] unitIds,
                                      @RequestParam(required = false, defaultValue = "0") int export,
                                      Integer[] ids, // 导出的记录
                                      Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScCommitteeTopicViewExample example = new ScCommitteeTopicViewExample();
        ScCommitteeTopicViewExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("hold_date desc, committee_id desc, seq asc, id asc");

        if (committeeId != null) {
            criteria.andCommitteeIdEqualTo(committeeId);
        }
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (name != null) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (unitIds != null && unitIds.length > 0) {

            criteria.andUnitIdsContain(unitIds);
        }

        long count = scCommitteeTopicViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScCommitteeTopicView> records = scCommitteeTopicViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scCommitteeTopic.class, scCommitteeTopicMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scCommitteeTopic:edit")
    @RequestMapping(value = "/scCommitteeTopic_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scCommitteeTopic_au(ScCommitteeTopic record,
                                      Integer[] selectedUnitIds,
                                      MultipartFile _voteFilePath,
                                      HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if (record.getSeq() != null && scCommitteeTopicService.idDuplicate
                (id, record.getCommitteeId(), record.getSeq())) {
            return failed("议题序号重复");
        }

        record.setUnitIds(StringUtils.join(selectedUnitIds, ","));

        record.setHasVote(BooleanUtils.isTrue(record.getHasVote()));
        record.setHasOtherVote(BooleanUtils.isTrue(record.getHasOtherVote()));

        record.setContent(record.getContent());
        record.setVoteFilePath(uploadPdf(_voteFilePath, "scCommitteeTopic-vote"));
        if (id == null) {
            if (record.getSeq() == null)
                record.setSeq(scCommitteeTopicService.genSeq(record.getCommitteeId()));

            record.setRecordUserId(ShiroHelper.getCurrentUserId());
            scCommitteeTopicService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "添加党委常委会议题：%s", record.getId()));
        } else {

            scCommitteeTopicService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "更新党委常委会议题：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scCommitteeTopic:edit")
    @RequestMapping("/scCommitteeTopic_au")
    public String scCommitteeTopic_au(Integer id, Integer committeeId, ModelMap modelMap) {

        Set<Integer> selectUnitIds = new HashSet<>();
        if (id != null) {
            ScCommitteeTopic scCommitteeTopic = scCommitteeTopicMapper.selectByPrimaryKey(id);
            modelMap.put("scCommitteeTopic", scCommitteeTopic);
            if (scCommitteeTopic != null) {
                committeeId = scCommitteeTopic.getCommitteeId();
            }

            selectUnitIds = NumberUtils.toIntSet(scCommitteeTopic.getUnitIds(), ",");
        }
        modelMap.put("selectUnitIds", new ArrayList<>(selectUnitIds));

        modelMap.put("committeeId", committeeId);

        modelMap.put("scCommittees", scCommitteeService.findAll());

        // MAP<unitTypeId, List<unitId>>
        Map<Integer, List<Integer>> unitListMap = new LinkedHashMap<>();
        Map<Integer, List<Integer>> historyUnitListMap = new LinkedHashMap<>();
        Map<Integer, Unit> unitMap = unitService.findAll();
        for (Unit unit : unitMap.values()) {

            Integer unitTypeId = unit.getTypeId();
            if (unit.getStatus() == SystemConstants.UNIT_STATUS_HISTORY) {
                List<Integer> units = historyUnitListMap.get(unitTypeId);
                if (units == null) {
                    units = new ArrayList<>();
                    historyUnitListMap.put(unitTypeId, units);
                }
                units.add(unit.getId());
            } else {
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

        return "sc/scCommittee/scCommitteeTopic/scCommitteeTopic_au";
    }


    @RequiresPermissions("scCommitteeTopic:list")
    @RequestMapping("/scCommitteeTopic_cadre")
    @ResponseBody
    public ScCommitteeTopicCadre scCommitteeTopic_cadre(int topicId, int cadreId) {

        return scCommitteeTopicService.getTopicCadre(topicId, cadreId);
    }

    @RequiresPermissions("scCommitteeTopic:edit")
    @RequestMapping("/scCommitteeTopic_content")
    public String scCommitteeTopic_content(Integer topicId, ModelMap modelMap) {

        if (topicId != null) {
            ScCommitteeTopic scCommitteeTopic = scCommitteeTopicMapper.selectByPrimaryKey(topicId);
            modelMap.put("scCommitteeTopic", scCommitteeTopic);

            ScCommittee scCommittee = scCommitteeMapper.selectByPrimaryKey(scCommitteeTopic.getCommitteeId());
            modelMap.put("scCommittee", scCommittee);
        }

        return "sc/scCommittee/scCommitteeTopic/scCommitteeTopic_content";
    }

    @RequiresPermissions("scCommitteeTopic:del")
    @RequestMapping(value = "/scCommitteeTopic_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            scCommitteeTopicService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_COMMITTEE, "批量删除党委常委会议题：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/scCommitteeTopic_selects")
    @ResponseBody
    public Map scCommitteeTopic_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScCommitteeTopicViewExample example = new ScCommitteeTopicViewExample();
        ScCommitteeTopicViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("hold_date desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = scCommitteeTopicViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScCommitteeTopicView> records = scCommitteeTopicViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(ScCommitteeTopicView record:records){

                String holdDate = DateUtils.formatDate(record.getHoldDate(), DateUtils.YYYYMMDD_DOT);
                Map<String, Object> option = new HashMap<>();
                option.put("text", String.format("[%s]", holdDate) + record.getName());
                option.put("holdDate", holdDate);
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
