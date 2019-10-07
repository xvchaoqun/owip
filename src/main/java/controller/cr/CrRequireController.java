package controller.cr;

import domain.cr.CrRequire;
import domain.cr.CrRequireExample;
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
public class CrRequireController extends CrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("crRequire:list")
    @RequestMapping("/crRequire")
    public String crRequire(HttpServletResponse response,
        String name,
    @RequestParam(required = false, defaultValue = "0") int export,
    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        return "cr/crRequire/crRequire_page";
    }

    @RequiresPermissions("crRequire:list")
    @RequestMapping("/crRequire_data")
    public void crRequire_data(HttpServletResponse response,
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

        CrRequireExample example = new CrRequireExample();
        CrRequireExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            crRequire_export(example, response);
            return;
        }

        long count = crRequireMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrRequire> records= crRequireMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(CrRequire.class, CrRequireMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRequire_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crRequire_au(CrRequire record,
                               HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(SystemConstants.AVAILABLE);
            crRequireService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加招聘资格模板：%s", record.getId()));
        } else {

            crRequireService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新招聘资格模板：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping("/crRequire_au")
    public String crRequire_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrRequire crRequire = crRequireMapper.selectByPrimaryKey(id);
            modelMap.put("crRequire", crRequire);
        }
        return "cr/crRequire/crRequire_au";
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping("/crRequire_preview")
    public String crRequire_preview(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrRequire crRequire = crRequireMapper.selectByPrimaryKey(id);
            modelMap.put("crRequire", crRequire);
        }
        return "cr/crRequire/crRequire_preview";
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRequire_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crRequire_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //crRequireService.del(id);
            logger.info(addLog(LogConstants.LOG_CRS, "删除招聘资格模板：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRequire_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            crRequireService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除招聘资格模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRequire_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crRequire_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crRequireService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CRS, "招聘资格模板调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void crRequire_export(CrRequireExample example, HttpServletResponse response) {

        List<CrRequire> records = crRequireMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"模板名称","备注","排序","状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrRequire record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getRemark(),
                            record.getSortOrder()+"",
                            record.getStatus()+""
            };
            valuesList.add(values);
        }
        String fileName = "招聘资格模板_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/crRequire_selects")
    @ResponseBody
    public Map crRequire_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrRequireExample example = new CrRequireExample();
        CrRequireExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.AVAILABLE);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = crRequireMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CrRequire> crRequires = crRequireMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != crRequires && crRequires.size()>0){

            for(CrRequire crRequire:crRequires){

                Select2Option option = new Select2Option();
                option.setText(crRequire.getName());
                option.setId(crRequire.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
