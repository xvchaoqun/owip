package controller.op;

import domain.op.*;
import domain.op.OpReportExample.Criteria;
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
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/op")
public class OpReportController extends OpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("opReport:list")
    @RequestMapping("/opReport")
    public String opReport(HttpServletResponse response,
                           Integer userId,
                           ModelMap modelMap) {

        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        return "op/opReport/opReport_page";
    }

    @RequiresPermissions("opReport:list")
    @RequestMapping("/opReport_data")
    @ResponseBody
    public void opReport_data(HttpServletResponse response,
                              Integer id,
                              @RequestDateRange DateRange reportDate,
                              @RequestDateRange DateRange startDate,
                              @RequestDateRange DateRange endDate,
                              String unit,

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

        OpReportExample example = new OpReportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (id!=null) {
            criteria.andIdEqualTo(id);
        }
        if (reportDate.getStart()!=null) {
            criteria.andReportDateGreaterThanOrEqualTo(reportDate.getStart());
        }
        if (reportDate.getEnd()!=null) {
            criteria.andReportDateLessThanOrEqualTo(reportDate.getEnd());
        }
        if (startDate.getStart() != null){
            criteria.andStartDateGreaterThanOrEqualTo(startDate.getStart());
        }
        if (startDate.getEnd() != null){
            criteria.andStartDateLessThanOrEqualTo(startDate.getEnd());
        }
        if (endDate.getStart() != null){
            criteria.andEndDateGreaterThanOrEqualTo(endDate.getStart());
        }
        if (endDate.getEnd() != null){
            criteria.andEndDateLessThanOrEqualTo(endDate.getEnd());
        }
        if (StringUtils.isNotBlank(unit)) {
            criteria.andUnitLike(SqlUtils.like(unit));
        }

        /*if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            opReport_export(example, response);
            return;
        }*/

        long count = opReportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OpReport> records= opReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(opReport.class, opReportMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("opReport:edit")
    @RequestMapping(value = "/opReport_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_opReport_au(OpReport record,
                              String reportDate,
                              String startDate,
                              String endDate,
                              HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(reportDate)){
            record.setReportDate(DateUtils.parseDate(reportDate, DateUtils.YYYYMMDD_DOT));

        }
        if (StringUtils.isNotBlank(startDate)){
            record.setStartDate(DateUtils.parseDate(startDate, DateUtils.YYYYMMDD_DOT));
        }
        if (StringUtils.isNotBlank(endDate)){
            record.setEndDate(DateUtils.parseDate(endDate, DateUtils.YYYYMMDD_DOT));
        }

        if (id == null) {
            
            opReportService.insertSelective(record);
            logger.info(log( LogConstants.LOG_OP, "添加报送上级部门：{0}", record.getUnit()));
        } else {

            opReportService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_OP, "更新报送上级部门：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("opReport:edit")
    @RequestMapping("/opReport_au")
    public String opReport_au(Integer id,
                              ModelMap modelMap) {

        if (id != null) {
            OpReport opReport = opReportMapper.selectByPrimaryKey(id);
            modelMap.put("opReport", opReport);
        }
        return "op/opReport/opReport_au";
    }

    //附件
    @RequiresPermissions("opReport:edit")
    @RequestMapping("/opReportFiles")
    public String opReportFiles(Integer id, ModelMap modelMap) {

        OpReport opReport = opReportMapper.selectByPrimaryKey(id);
        Date startDate = opReport.getStartDate();
        Date endDate = opReport.getEndDate();
        List<OpRecord> opRecords = opRecordMapper.selectByExample(new OpRecordExample());
        List<Integer> recordIds = new ArrayList<>();
        Date recordDate = null;
        for (OpRecord opRecord : opRecords){
            recordDate = opRecord.getStartDate();
            if (recordDate.after(startDate) && recordDate.before(endDate)){
                recordIds.add(opRecord.getId());
            }
        }

        if (recordIds.size() > 0) {
            OpAttatchExample example = new OpAttatchExample();
            example.createCriteria().andRecordIdIn(recordIds);
            List<OpAttatch> opAttatchs = opAttatchMapper.selectByExample(example);
            modelMap.put("opAttatchs", opAttatchs);
        }

        modelMap.put("opReport", opReport);


        return "op/opAttatch/opReportFiles";
    }

    @RequiresPermissions("opReport:edit")
    @RequestMapping(value = "/opReport_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_opReport_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            opReportService.del(id);
            logger.info(log( LogConstants.LOG_OP, "删除报送上级部门：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("opReport:edit")
    @RequestMapping(value = "/opReport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map opReport_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            opReportService.batchDel(ids);
            logger.info(log( LogConstants.LOG_OP, "批量删除报送上级部门：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/opReport_selects")
    @ResponseBody
    public Map opReport_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OpReportExample example = new OpReportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            //criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = opReportMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<OpReport> records = opReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(OpReport record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getId());
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
