package controller.pm;

import controller.BaseController;
import domain.pm.PmQuarterParty;
import domain.pm.PmQuarterPartyExample;
import domain.pm.PmQuarterPartyExample.Criteria;
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

public class PmQuarterPartyController extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmQuarterParty:list")
    @RequestMapping("/pmQuarterParty")
    public String pmQuarterParty() {

        return "pm/pmQuarterParty/pmQuarterParty_page";
    }

    @RequiresPermissions("pmQuarterParty:list")
    @RequestMapping("/pmQuarterParty_data")
    @ResponseBody
    public void pmQuarterParty_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "pm_quarter_party") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer partyId,
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

        PmQuarterPartyExample example = new PmQuarterPartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pmQuarterParty_export(example, response);
            return;
        }

        long count = pmQuarterPartyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmQuarterParty> records= pmQuarterPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmQuarterParty.class, pmQuarterPartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmQuarterParty:edit")
    @RequestMapping(value = "/pmQuarterParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmQuarterParty_au(PmQuarterParty record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            
            pmQuarterPartyService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PM, "添加三会一课分党委季度汇总详情：{0}", record.getId()));
        } else {

            pmQuarterPartyService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PM, "更新三会一课分党委季度汇总详情：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmQuarterParty:edit")
    @RequestMapping("/pmQuarterParty_au")
    public String pmQuarterParty_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PmQuarterParty pmQuarterParty = pmQuarterPartyMapper.selectByPrimaryKey(id);
            modelMap.put("pmQuarterParty", pmQuarterParty);
        }
        return "pm/pmQuarterParty/pmQuarterParty_au";
    }

    @RequiresPermissions("pmQuarterParty:del")
    @RequestMapping(value = "/pmQuarterParty_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmQuarterParty_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmQuarterPartyService.del(id);
            logger.info(log( LogConstants.LOG_PM, "删除三会一课分党委季度汇总详情：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmQuarterParty:del")
    @RequestMapping(value = "/pmQuarterParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmQuarterParty_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pmQuarterPartyService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PM, "批量删除三会一课分党委季度汇总详情：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmQuarterParty:changeOrder")
    @RequestMapping(value = "/pmQuarterParty_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmQuarterParty_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        pmQuarterPartyService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_PM, "三会一课分党委季度汇总详情调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void pmQuarterParty_export(PmQuarterPartyExample example, HttpServletResponse response) {

        List<PmQuarterParty> records = pmQuarterPartyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"分党委id|100","召开会议支部数量|100","不召开会议支部数量|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PmQuarterParty record = records.get(i);
            String[] values = {
                record.getPartyId()+"",
                            record.getBranchNum()+"",
                            record.getExculdeBranchNum()+""
            };
            valuesList.add(values);
        }
        String fileName = String.format("三会一课分党委季度汇总详情(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/pmQuarterParty_selects")
    @ResponseBody
    public Map pmQuarterParty_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmQuarterPartyExample example = new PmQuarterPartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");


        long count = pmQuarterPartyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PmQuarterParty> records = pmQuarterPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PmQuarterParty record:records){

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
