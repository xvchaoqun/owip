package controller.pm;

import controller.BaseController;
import domain.pm.PmQuarterBranch;
import domain.pm.PmQuarterBranchExample;
import domain.pm.PmQuarterBranchExample.Criteria;
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

public class PmQuarterBranchController extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @RequiresPermissions("pmQuarterBranch:list")
    @RequestMapping("/pmQuarterBranch")
    public String pmQuarterBranch() {

        return "pm/pmQuarterBranch/pmQuarterBranch_page";
    }

//    @RequiresPermissions("pmQuarterBranch:list")
    @RequestMapping("/pmQuarterBranch_data")
    @ResponseBody
    public void pmQuarterBranch_data(HttpServletResponse response,
                                    Integer partyId,
                                    Integer branchId,
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

        PmQuarterBranchExample example = new PmQuarterBranchExample();
        Criteria criteria = example.createCriteria();

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pmQuarterBranch_export(example, response);
            return;
        }

        long count = pmQuarterBranchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmQuarterBranch> records= pmQuarterBranchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmQuarterBranch.class, pmQuarterBranchMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

//    @RequiresPermissions("pmQuarterBranch:edit")
  //  @RequestMapping(value = "/pmQuarterBranch_au", method = RequestMethod.POST)
@RequestMapping("/pmQuarterBranch_au")
    @ResponseBody
    public Map do_pmQuarterBranch_au(Integer quarterId,Integer partyId,PmQuarterBranch record, HttpServletRequest request) {

        Integer id = record.getId();


        if (id == null) {
            
            pmQuarterBranchService.insertSelective(quarterId,partyId);
            logger.info(log( LogConstants.LOG_PM, "添加三会一课支部季度汇总详情：{0}", record.getId()));
        } else {

            pmQuarterBranchService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PM, "更新三会一课支部季度汇总详情：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

//    @RequiresPermissions("pmQuarterBranch:edit")
//    @RequestMapping("/pmQuarterBranch_au")
//    public String pmQuarterBranch_au(Integer id, ModelMap modelMap) {
//
//        if (id != null) {
//            PmQuarterBranch pmQuarterBranch = pmQuarterBranchMapper.selectByPrimaryKey(id);
//            modelMap.put("pmQuarterBranch", pmQuarterBranch);
//        }
//        return "pm/pmQuarterBranch/pmQuarterBranch_au";
//    }

//    @RequiresPermissions("pmQuarterBranch:del")
    @RequestMapping(value = "/pmQuarterBranch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmQuarterBranch_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmQuarterBranchService.del(id);
            logger.info(log( LogConstants.LOG_PM, "删除三会一课支部季度汇总详情：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

//    @RequiresPermissions("pmQuarterBranch:del")
    @RequestMapping(value = "/pmQuarterBranch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmQuarterBranch_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pmQuarterBranchService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PM, "批量删除三会一课支部季度汇总详情：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

//    @RequiresPermissions("pmQuarterBranch:changeOrder")
    @RequestMapping(value = "/pmQuarterBranch_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmQuarterBranch_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        pmQuarterBranchService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_PM, "三会一课支部季度汇总详情调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void pmQuarterBranch_export(PmQuarterBranchExample example, HttpServletResponse response) {

        List<PmQuarterBranch> records = pmQuarterBranchMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"支部名称|100","是否在排除召开会议的列表|100","会议次数|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PmQuarterBranch record = records.get(i);
            String[] values = {
                record.getBranchName(),
                            record.getIsExclude()+"",
                            record.getMeetingNum()+""
            };
            valuesList.add(values);
        }
        String fileName = String.format("三会一课支部季度汇总详情(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

//    @RequestMapping("/pmQuarterBranch_selects")
    @ResponseBody
    public Map pmQuarterBranch_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmQuarterBranchExample example = new PmQuarterBranchExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andBranchNameLike(SqlUtils.like(searchStr));
        }

        long count = pmQuarterBranchMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PmQuarterBranch> records = pmQuarterBranchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PmQuarterBranch record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getBranchName());
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
