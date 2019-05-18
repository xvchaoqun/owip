package controller.crs;

import domain.crs.CrsPostRequire;
import domain.crs.CrsPostRequireExample;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CrsPostRequireController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("crsPostRequire:list")
    @RequestMapping("/crsPostRequire")
    public String crsPostRequire(HttpServletResponse response,
        String name,
    @RequestParam(required = false, defaultValue = "0") int export,
    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        return "crs/crsPostRequire/crsPostRequire_page";
    }

    @RequiresPermissions("crsPostRequire:list")
    @RequestMapping("/crsPostRequire_data")
    public void crsPostRequire_data(HttpServletResponse response,
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

        CrsPostRequireExample example = new CrsPostRequireExample();
        CrsPostRequireExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            crsPostRequire_export(example, response);
            return;
        }

        long count = crsPostRequireMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsPostRequire> records= crsPostRequireMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(CrsPostRequire.class, CrsPostRequireMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsPostRequire:edit")
    @RequestMapping(value = "/crsPostRequire_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPostRequire_au(CrsPostRequire record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(SystemConstants.AVAILABLE);
            crsPostRequireService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加招聘岗位资格模板：%s", record.getId()));
        } else {

            crsPostRequireService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新招聘岗位资格模板：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPostRequire:edit")
    @RequestMapping("/crsPostRequire_au")
    public String crsPostRequire_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPostRequire crsPostRequire = crsPostRequireMapper.selectByPrimaryKey(id);
            modelMap.put("crsPostRequire", crsPostRequire);
        }
        return "crs/crsPostRequire/crsPostRequire_au";
    }

    @RequiresPermissions("crsPostRequire:edit")
    @RequestMapping("/crsPostRequire_preview")
    public String crsPostRequire_preview(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPostRequire crsPostRequire = crsPostRequireMapper.selectByPrimaryKey(id);
            modelMap.put("crsPostRequire", crsPostRequire);
        }
        return "crs/crsPostRequire/crsPostRequire_preview";
    }

    @RequiresPermissions("crsPostRequire:del")
    @RequestMapping(value = "/crsPostRequire_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPostRequire_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //crsPostRequireService.del(id);
            logger.info(addLog(LogConstants.LOG_CRS, "删除招聘岗位资格模板：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPostRequire:del")
    @RequestMapping(value = "/crsPostRequire_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            crsPostRequireService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除招聘岗位资格模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPostRequire:changeOrder")
    @RequestMapping(value = "/crsPostRequire_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPostRequire_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crsPostRequireService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CRS, "招聘岗位资格模板调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void crsPostRequire_export(CrsPostRequireExample example, HttpServletResponse response) {

        List<CrsPostRequire> records = crsPostRequireMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"模板名称","备注","排序","状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrsPostRequire record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getRemark(),
                            record.getSortOrder()+"",
                            record.getStatus()+""
            };
            valuesList.add(values);
        }
        String fileName = "招聘岗位资格模板_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/crsPostRequire_selects")
    @ResponseBody
    public Map crsPostRequire_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsPostRequireExample example = new CrsPostRequireExample();
        CrsPostRequireExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = crsPostRequireMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CrsPostRequire> crsPostRequires = crsPostRequireMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != crsPostRequires && crsPostRequires.size()>0){

            for(CrsPostRequire crsPostRequire:crsPostRequires){

                Select2Option option = new Select2Option();
                option.setText(crsPostRequire.getName());
                option.setId(crsPostRequire.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
