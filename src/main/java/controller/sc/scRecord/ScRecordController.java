package controller.sc.scRecord;

import controller.sc.ScBaseController;
import domain.sc.scMotion.ScMotionView;
import domain.sc.scMotion.ScMotionViewExample;
import domain.sc.scRecord.ScRecord;
import domain.sc.scRecord.ScRecordView;
import domain.sc.scRecord.ScRecordViewExample;
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
import sys.constants.LogConstants;
import sys.constants.ScConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScRecordController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scRecord:list")
    @RequestMapping("/scRecord")
    public String scRecord(@RequestParam(defaultValue = "1") Integer cls,  ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (cls == 10) {

            return "sc/scRecord/scRecord/scRecordList";
        }
        return "sc/scRecord/scRecord/scRecord_page";
    }

    @RequiresPermissions("scRecord:list")
    @RequestMapping("/scRecord_data")
    @ResponseBody
    public void scRecord_data(HttpServletResponse response,
                                    Short year,
                                    String postName,
                                    Integer motionId,
                                    Integer scType,
                                    Byte status,
                                 @DateTimeFormat(pattern = DateUtils.YYYYMMDD)Date holdDate,
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

        ScRecordViewExample example = new ScRecordViewExample();
        ScRecordViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc, hold_date desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if(scType!=null){
            criteria.andScTypeEqualTo(scType);
        }
        if(postName!=null){
            criteria.andPostNameLike(SqlUtils.like(postName));
        }
        if (motionId!=null) {
            criteria.andMotionIdEqualTo(motionId);
        }
        if(holdDate!=null){
            criteria.andHoldDateGreaterThanOrEqualTo(holdDate);
        }
        if (status!=null) {
            criteria.andStatusEqualTo(status);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scRecord_export(example, response);
            return;
        }

        long count = scRecordViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScRecordView> records= scRecordViewMapper.selectByExampleWithRowbounds(example,
         new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scRecord.class, scRecordMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scRecord:edit")
    @RequestMapping(value = "/scRecord_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scRecord_au(ScRecord record, HttpServletRequest request) {

        Integer id = record.getId();

       /* if (scRecordService.idDuplicate(id, code)) {
            return failed("添加重复");
        }*/
        if (id == null) {
            scRecordService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_SC_RECORD, "添加纪实"));
        } else {

            scRecordService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_SC_RECORD, "更新纪实：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scRecord:edit")
    @RequestMapping("/scRecord_au")
    public String scRecord_au(Integer id, ModelMap modelMap) {

        ScMotionViewExample example = new ScMotionViewExample();
        example.setOrderByClause("hold_date desc");
        List<ScMotionView> scMotions = scMotionViewMapper.selectByExample(example);
        modelMap.put("scMotions", scMotions);

        if (id != null) {
            ScRecord scRecord = scRecordMapper.selectByPrimaryKey(id);
            modelMap.put("scRecord", scRecord);
        }
        return "sc/scRecord/scRecord/scRecord_au";
    }

    @RequiresPermissions("scRecord:del")
    @RequestMapping(value = "/scRecord_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scRecord_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            scRecordService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_RECORD, "批量删除纪实：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/scRecord_selects")
    @ResponseBody
    public Map scRecord_selects(Integer pageSize, Integer pageNo,
                                Byte status,
                                Integer scType,
                                Integer unitId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScRecordViewExample example = new ScRecordViewExample();
        ScRecordViewExample.Criteria criteria = example.createCriteria();
        if(status!=null){
            criteria.andStatusEqualTo(status);
        }

        example.setOrderByClause("status asc, hold_date desc");

        if(scType!=null){
            criteria.andScTypeEqualTo(scType);
        }

        if(unitId!=null){
            criteria.andUnitIdEqualTo(unitId);
        }
        if(StringUtils.isNotBlank(searchStr)){
            criteria.search(searchStr.trim());
        }

        long count = scRecordViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScRecordView> records = scRecordViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(ScRecordView record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getCode() + "-" + record.getPostName() + "-" + record.getJob());
                option.put("id", record.getId() + "");
                option.put("del", record.getStatus() != ScConstants.SC_RECORD_STATUS_INIT);
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    // 根据年份、考察对象、选任启动日期读取纪实列表
    @RequestMapping("/scRecords")
    @ResponseBody
    public void scRecords(Short year, Integer userId,
                          @DateTimeFormat(pattern = DateUtils.YYYYMMDD) Date holdDate) throws IOException {

        List<Integer> recordIdList = iScMapper.getRecordIdList(year, userId, holdDate);

        if(recordIdList.size()==0) return;

        ScRecordViewExample example = new ScRecordViewExample();
        ScRecordViewExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(recordIdList);
        example.setOrderByClause("status asc, hold_date desc");

        List<ScRecordView> records = scRecordViewMapper.selectByExample(example);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", records.size());

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scRecord.class, scRecordMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    public void scRecord_export(ScRecordViewExample example, HttpServletResponse response) {

        List<ScRecordView> records = scRecordViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份|100","纪实编号|100","所属动议|100","状态|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScRecordView record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getSeq(),
                            record.getMotionId()+"",
                            record.getStatus()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "纪实_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
