package controller.pm;

import controller.BaseController;
import domain.pm.PmQuarter;
import domain.pm.PmQuarterExample;
import domain.pm.PmQuarterExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

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

public class PmQuarterController extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

   // @RequiresPermissions("pmQuarter:list")
    @RequestMapping("/pmQuarter")
    public String pmQuarter() {

        return "pm/pmQuarter/pmQuarter_page";
    }

   // @RequiresPermissions("pmQuarter:list")
    @RequestMapping("/pmQuarter_data")
    @ResponseBody
    public void pmQuarter_data(HttpServletResponse response,
                                    Byte quarter,
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

        PmQuarterExample example = new PmQuarterExample();
        Criteria criteria = example.createCriteria();

        if (quarter!=null) {
            criteria.andQuarterEqualTo(quarter);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pmQuarter_export(example, response);
            return;
        }

        long count = pmQuarterMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmQuarter> records= pmQuarterMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmQuarter.class, pmQuarterMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

  //  @RequiresPermissions("pmQuarter:edit")
//    @RequestMapping(value = "/pmQuarter_au", method = RequestMethod.POST)
    @RequestMapping("/pmQuarter_au")
    @ResponseBody
    public void do_pmQuarter_au(Integer partyId,Byte type,PmQuarter record, HttpServletRequest request) {

        Integer id = record.getId();


        if (id == null) {
            
            pmQuarterService.insertSelective(partyId,type);
            logger.info(log( LogConstants.LOG_PM, "添加三会一课季度汇总：{0}", record.getId()));
        } else {

            pmQuarterService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PM, "更新三会一课季度汇总：{0}", record.getId()));
        }

        return;
    }

   // @RequiresPermissions("pmQuarter:edit")
//    @RequestMapping("/pmQuarter_au")
//    public String pmQuarter_au(Integer id, ModelMap modelMap) {
//
//        if (id != null) {
//            PmQuarter pmQuarter = pmQuarterMapper.selectByPrimaryKey(id);
//            modelMap.put("pmQuarter", pmQuarter);
//        }
//        return "pm/pmQuarter/pmQuarter_au";
//    }

 //   @RequiresPermissions("pmQuarter:del")
    @RequestMapping(value = "/pmQuarter_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmQuarter_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmQuarterService.del(id);
            logger.info(log( LogConstants.LOG_PM, "删除三会一课季度汇总：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

   // @RequiresPermissions("pmQuarter:del")
    @RequestMapping(value = "/pmQuarter_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmQuarter_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pmQuarterService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PM, "批量删除三会一课季度汇总：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("pmQuarter:changeOrder")
    @RequestMapping(value = "/pmQuarter_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmQuarter_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        pmQuarterService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_PM, "三会一课季度汇总调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void pmQuarter_export(PmQuarterExample example, HttpServletResponse response) {

        List<PmQuarter> records = pmQuarterMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","季度|100","数量(分党委或支部数量)|100","应召开党员大会支部数量|100","已召开党员大会数量|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PmQuarter record = records.get(i);
            String[] values = {
                    String.valueOf(record.getYear()),
                            record.getQuarter()+"",
                            record.getNum()+"",
                            record.getDueNum()+"",
                            record.getFinishNum()+""
            };
            valuesList.add(values);
        }
        String fileName = String.format("三会一课季度汇总(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/pmQuarter_selects")
    @ResponseBody
    public Map pmQuarter_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmQuarterExample example = new PmQuarterExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");



        long count = pmQuarterMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PmQuarter> records = pmQuarterMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PmQuarter record:records){

                Map<String, Object> option = new HashMap<>();

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
