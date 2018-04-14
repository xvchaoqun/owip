package controller.cet;

import domain.cet.CetTrainEvaTable;
import domain.cet.CetTrainEvaTableExample;
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
import sys.constants.SystemConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetTrainEvaTableController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("cetTrainEvaTable:list")
    @RequestMapping("/cetTrainEvaTable")
    public String cetTrainEvaTable(HttpServletResponse response,
        String name,
    @RequestParam(required = false, defaultValue = "0") int export,
    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        return "cet/cetTrainEvaTable/cetTrainEvaTable_page";
    }

    @RequiresPermissions("cetTrainEvaTable:list")
    @RequestMapping("/cetTrainEvaTable_data")
    public void cetTrainEvaTable_data(HttpServletResponse response,
                                    String name,
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

        CetTrainEvaTableExample example = new CetTrainEvaTableExample();
        CetTrainEvaTableExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrainEvaTable_export(example, response);
            return;
        }

        int count = (int) cetTrainEvaTableMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainEvaTable> records= cetTrainEvaTableMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(TrainEvaTable.class, TrainEvaTableMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainEvaTable:edit")
    @RequestMapping(value = "/cetTrainEvaTable_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainEvaTable_au(CetTrainEvaTable record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(SystemConstants.AVAILABLE);
            cetTrainEvaTableService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加评估表：%s", record.getId()));
        } else {

            cetTrainEvaTableService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新评估表：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainEvaTable:edit")
    @RequestMapping("/cetTrainEvaTable_au")
    public String cetTrainEvaTable_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrainEvaTable cetTrainEvaTable = cetTrainEvaTableMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainEvaTable", cetTrainEvaTable);
        }
        return "cet/cetTrainEvaTable/cetTrainEvaTable_au";
    }

    @RequiresPermissions("cetTrainEvaTable:edit")
    @RequestMapping("/cetTrainEvaTable_preview")
    public String cetTrainEvaTable_preview(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrainEvaTable cetTrainEvaTable = cetTrainEvaTableMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainEvaTable", cetTrainEvaTable);
        }
        return "cet/cetTrainEvaTable/cetTrainEvaTable_preview";
    }

    @RequiresPermissions("cetTrainEvaTable:del")
    @RequestMapping(value = "/cetTrainEvaTable_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainEvaTable_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //cetTrainEvaTableService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除评估表：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainEvaTable:del")
    @RequestMapping(value = "/cetTrainEvaTable_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTrainEvaTableService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除评估表：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainEvaTable:changeOrder")
    @RequestMapping(value = "/cetTrainEvaTable_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainEvaTable_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetTrainEvaTableService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "评估表调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetTrainEvaTable_export(CetTrainEvaTableExample example, HttpServletResponse response) {

        List<CetTrainEvaTable> records = cetTrainEvaTableMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"评估表名称","备注","排序","状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainEvaTable record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getRemark(),
                            record.getSortOrder()+"",
                            record.getStatus()+""
            };
            valuesList.add(values);
        }
        String fileName = "评估表_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetTrainEvaTable_selects")
    @ResponseBody
    public Map cetTrainEvaTable_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainEvaTableExample example = new CetTrainEvaTableExample();
        CetTrainEvaTableExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = (int) cetTrainEvaTableMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetTrainEvaTable> cetTrainEvaTables = cetTrainEvaTableMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cetTrainEvaTables && cetTrainEvaTables.size()>0){

            for(CetTrainEvaTable cetTrainEvaTable:cetTrainEvaTables){

                Select2Option option = new Select2Option();
                option.setText(cetTrainEvaTable.getName());
                option.setId(cetTrainEvaTable.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
