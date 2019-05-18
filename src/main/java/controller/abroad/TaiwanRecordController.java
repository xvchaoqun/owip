package controller.abroad;

import domain.abroad.TaiwanRecord;
import domain.abroad.TaiwanRecordExample;
import domain.abroad.TaiwanRecordExample.Criteria;
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
import sys.constants.AbroadConstants;
import sys.constants.LogConstants;
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

@Controller
@RequestMapping("/abroad")
public class TaiwanRecordController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("taiwanRecord:list")
    @RequestMapping("/taiwanRecord")
    public String taiwanRecord() {

        return "abroad/taiwanRecord/taiwanRecord_page";
    }

    @RequiresPermissions("taiwanRecord:list")
    @RequestMapping("/taiwanRecord_data")
    public void taiwanRecord_data(HttpServletResponse response,
                                  Integer cadreId,
                                  @RequestDateRange DateRange _recordDate,
                                  @RequestDateRange DateRange _startDate,
                                  @RequestDateRange DateRange _endDate,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        TaiwanRecordExample example = new TaiwanRecordExample();
        Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("create_time desc");

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (_recordDate != null) {
            if (_recordDate.getStart() != null) {
                criteria.andRecordDateGreaterThanOrEqualTo(_recordDate.getStart());
            }
            if (_recordDate.getEnd() != null) {
                criteria.andRecordDateLessThanOrEqualTo(_recordDate.getEnd());
            }
        }

        if (_startDate != null) {
            if (_startDate.getStart() != null) {
                criteria.andStartDateGreaterThanOrEqualTo(_startDate.getStart());
            }
            if (_startDate.getEnd() != null) {
                criteria.andStartDateLessThanOrEqualTo(_startDate.getEnd());
            }
        }

        if (_endDate != null) {
            if (_endDate.getStart() != null) {
                criteria.andEndDateGreaterThanOrEqualTo(_endDate.getStart());
            }
            if (_endDate.getEnd() != null) {
                criteria.andEndDateLessThanOrEqualTo(_endDate.getEnd());
            }
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            taiwanRecord_export(example, response);
            return;
        }

        long count = taiwanRecordMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<TaiwanRecord> records = taiwanRecordMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(taiwanRecord.class, taiwanRecordMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("taiwanRecord:edit")
    @RequestMapping(value = "/taiwanRecord_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_taiwanRecord_au(TaiwanRecord record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setIsDeleted(false);
            record.setCreateTime(new Date());
            taiwanRecordService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "添加因公赴台备案：%s", record.getId()));
        } else {

            taiwanRecordService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ABROAD, "更新因公赴台备案：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("taiwanRecord:edit")
    @RequestMapping("/taiwanRecord_au")
    public String taiwanRecord_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            TaiwanRecord taiwanRecord = taiwanRecordMapper.selectByPrimaryKey(id);
            modelMap.put("taiwanRecord", taiwanRecord);
        }
        return "abroad/taiwanRecord/taiwanRecord_au";
    }

    // 修改新证件应交组织部日期
    @RequiresPermissions("taiwanRecord:edit")
    @RequestMapping("/taiwanRecord_expectDate")
    public String taiwanRecord_expectDate( Integer id, ModelMap modelMap) {

        TaiwanRecord taiwanRecord = taiwanRecordMapper.selectByPrimaryKey(id);
        modelMap.put("taiwanRecord", taiwanRecord);

        return "abroad/taiwanRecord/taiwanRecord_expectDate";
    }

    @RequiresPermissions("taiwanRecord:edit")
    @RequestMapping(value = "/taiwanRecord_expectDate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_taiwanRecord_expectDate(Integer id, @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date expectDate) throws IOException {

        TaiwanRecord record = new TaiwanRecord();
        record.setExpectDate(expectDate);

        TaiwanRecordExample example = new TaiwanRecordExample();
        example.createCriteria().andIdEqualTo(id).andIsDeletedEqualTo(false)
                .andHandleDateIsNull(); // 交证件后不可修改

        taiwanRecordMapper.updateByExampleSelective(record, example);

        logger.info(addLog(LogConstants.LOG_ABROAD, "[因公赴台备案]修改新证件应交组织部日期：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("taiwanRecord:del")
    @RequestMapping(value = "/taiwanRecord_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            taiwanRecordService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ABROAD, "批量删除因公赴台备案：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void taiwanRecord_export(TaiwanRecordExample example, HttpServletResponse response) {

        List<TaiwanRecord> records = taiwanRecordMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"干部|100", "备案时间|100", "离境时间|100", "回国时间|100", "出访活动类别|150",
                "现持有台湾通行证号码|180", "办理新证件方式|150",
                "新证件应交组织部日期|180", "新证件实交组织部日期|180", "备注|300|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            TaiwanRecord record = records.get(i);
            String[] values = {
                    record.getCadreId() + "",
                    DateUtils.formatDate(record.getRecordDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                    record.getReason(),
                    record.getPassportCode(),
                    AbroadConstants.ABROAD_TAIWAN_RECORD_HANDLE_TYPE_MAP.get(record.getHandleType()),
                    DateUtils.formatDate(record.getExpectDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getHandleDate(), DateUtils.YYYY_MM_DD),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "因公赴台备案_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
