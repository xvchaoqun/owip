package controller.ps;

import domain.ps.PsTask;
import domain.ps.PsTaskExample;
import domain.ps.PsTaskExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/ps")
public class PsTaskController extends PsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("psTask:list")
    @RequestMapping("/psTask")
    public String psTask() {

        return "ps/psTask/psTask_page";
    }

    @RequiresPermissions("psTask:list")
    @RequestMapping("/psTask_data")
    @ResponseBody
    public void psTask_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "ps_task") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    String name,
                                    Integer year,
                                    String psIds,
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

        PsTaskExample example = new PsTaskExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(psIds)) {
            criteria.andPsIdsLike(SqlUtils.like(psIds));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            psTask_export(example, response);
            return;
        }

        long count = psTaskMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PsTask> records= psTaskMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(psTask.class, psTaskMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("psTask:edit")
    @RequestMapping(value = "/psTask_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psTask_au(PsTask record, HttpServletRequest request) {

        Integer id = record.getId();

        /*if (psTaskService.idDuplicate(id, code)) {
            return failed("添加重复");
        }*/
        if (id == null) {
            
            psTaskService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PS, "添加年度工作任务：{0}", record.getId()));
        } else {

            psTaskService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PS, "更新年度工作任务：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psTask:edit")
    @RequestMapping("/psTask_au")
    public String psTask_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PsTask psTask = psTaskMapper.selectByPrimaryKey(id);
            modelMap.put("psTask", psTask);
        }
        return "ps/psTask/psTask_au";
    }

    @RequiresPermissions("psTask:del")
    @RequestMapping(value = "/psTask_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psTask_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            psTaskService.del(id);
            logger.info(log( LogConstants.LOG_PS, "删除年度工作任务：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psTask:del")
    @RequestMapping(value = "/psTask_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map psTask_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            psTaskService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PS, "批量删除年度工作任务：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("psTask:changeOrder")
    @RequestMapping(value = "/psTask_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psTask_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        psTaskService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_PS, "年度工作任务调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }*/

    public void psTask_export(PsTaskExample example, HttpServletResponse response) {

        List<PsTask> records = psTaskMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称|100","年度|100","发布范围|100","附件|100","是否发布|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PsTask record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getYear()+"",
                            record.getPsIds(),
                            record.getFiles(),
                            record.getIsPublish()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "年度工作任务_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/psTask_selects")
    @ResponseBody
    public Map psTask_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PsTaskExample example = new PsTaskExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = psTaskMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PsTask> records = psTaskMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PsTask record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
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
