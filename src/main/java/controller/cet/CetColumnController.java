package controller.cet;

import domain.cet.CetColumn;
import domain.cet.CetColumnExample;
import domain.cet.CetColumnExample.Criteria;
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
public class CetColumnController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetColumn:list")
    @RequestMapping("/cetColumn")
    public String cetColumn() {
        return "cet/cetColumn/cetColumn_page";
    }

    @RequiresPermissions("cetColumn:list")
    @RequestMapping("/cetColumn_data")
    public void cetColumn_data(HttpServletResponse response,
                                    byte type,
                                     boolean isOnline,
                                    Integer fid,
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

        CetColumnExample example = new CetColumnExample();
        Criteria criteria = example.createCriteria().andIsOnlineEqualTo(isOnline).andTypeEqualTo(type);
        example.setOrderByClause("sort_order desc");

        if(fid!=null){
            criteria.andFidEqualTo(fid);
        }else{
            criteria.andFidIsNull();
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetColumn_export(example, response);
            return;
        }

        long count = cetColumnMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetColumn> records= cetColumnMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetColumn.class, cetColumnMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetColumn:edit")
    @RequestMapping(value = "/cetColumn_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetColumn_au(CetColumn record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetColumnService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加课程栏目：%s", record.getId()));
        } else {

            cetColumnService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新课程栏目：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetColumn:edit")
    @RequestMapping("/cetColumn_au")
    public String cetColumn_au(Integer id,
                               Boolean isOnline,
                               Integer fid,
                               Byte type,
                               ModelMap modelMap) {

        if (id != null) {
            CetColumn cetColumn = cetColumnMapper.selectByPrimaryKey(id);
            modelMap.put("cetColumn", cetColumn);
            if(cetColumn!=null){
                isOnline = cetColumn.getIsOnline();
                fid = cetColumn.getFid();
                type = cetColumn.getType();
            }
        }
        modelMap.put("isOnline", isOnline);
        modelMap.put("fid", fid);
        modelMap.put("type", type);

        return "cet/cetColumn/cetColumn_au";
    }

    @RequiresPermissions("cetColumn:del")
    @RequestMapping(value = "/cetColumn_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetColumn_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetColumnService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除课程栏目：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetColumn:changeOrder")
    @RequestMapping(value = "/cetColumn_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetColumn_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetColumnService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_CET, "课程栏目调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetColumn_export(CetColumnExample example, HttpServletResponse response) {

        List<CetColumn> records = cetColumnMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"是否线上课程栏目|100","所属栏目|100","子栏目数量|100","栏目名称|100","类型|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetColumn record = records.get(i);
            String[] values = {
                record.getIsOnline() + "",
                            record.getFid()+"",
                            record.getChildNum()+"",
                            record.getName(),
                            record.getType() + "",
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "课程栏目_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetColumn_selects")
    @ResponseBody
    public Map cetColumn_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetColumnExample example = new CetColumnExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = cetColumnMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetColumn> cetColumns = cetColumnMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cetColumns && cetColumns.size()>0){

            for(CetColumn cetColumn:cetColumns){

                Select2Option option = new Select2Option();
                option.setText(cetColumn.getName());
                option.setId(cetColumn.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
