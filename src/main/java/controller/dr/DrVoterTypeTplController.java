package controller.dr;

import domain.dr.DrVoterType;
import domain.dr.DrVoterTypeExample;
import domain.dr.DrVoterTypeTpl;
import domain.dr.DrVoterTypeTplExample;
import domain.dr.DrVoterTypeTplExample.Criteria;
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
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class DrVoterTypeTplController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drVoterTypeTpl:list")
    @RequestMapping("/drVoterTypeTpl")
    public String drVoterTypeTpl() {

        return "dr/drVoterTypeTpl/drVoterTypeTpl_page";
    }

    @RequiresPermissions("drVoterTypeTpl:list")
    @RequestMapping("/drVoterTypeTpl_data")
    @ResponseBody
    public void drVoterTypeTpl_data(HttpServletResponse response,
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

        DrVoterTypeTplExample example = new DrVoterTypeTplExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            drVoterTypeTpl_export(example, response);
            return;
        }

        long count = drVoterTypeTplMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrVoterTypeTpl> records= drVoterTypeTplMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drVoterTypeTpl.class, drVoterTypeTplMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drVoterTypeTpl:edit")
    @RequestMapping(value = "/drVoterTypeTpl_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drVoterTypeTpl_au(DrVoterTypeTpl record, HttpServletRequest request) {

        Integer id = record.getId();

        /*if (drVoterTypeTplService.idDuplicate(id, code)) {
            return failed("添加重复");
        }*/
        if (id == null) {
            
            drVoterTypeTplService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_DR, "添加填表人类别模板：%s", record.getId()));
        } else {

            drVoterTypeTplService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_DR, "更新填表人类别模板：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drVoterTypeTpl:edit")
    @RequestMapping("/drVoterTypeTpl_au")
    public String drVoterTypeTpl_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DrVoterTypeTpl drVoterTypeTpl = drVoterTypeTplMapper.selectByPrimaryKey(id);
            modelMap.put("drVoterTypeTpl", drVoterTypeTpl);
        }
        return "dr/drVoterTypeTpl/drVoterTypeTpl_au";
    }

    @RequiresPermissions("drVoterTypeTpl:del")
    @RequestMapping(value = "/drVoterTypeTpl_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drVoterTypeTpl_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            drVoterTypeTplService.del(id);
            logger.info(addLog( LogConstants.LOG_DR, "删除填表人类别模板：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drVoterTypeTpl:del")
    @RequestMapping(value = "/drVoterTypeTpl_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drVoterTypeTpl_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            drVoterTypeTplService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_DR, "批量删除填表人类别模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drVoterTypeTpl:changeOrder")
    @RequestMapping(value = "/drVoterTypeTpl_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drVoterTypeTpl_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        drVoterTypeTplService.changeOrder(id, addNum);
        logger.info(addLog( LogConstants.LOG_DR, "填表人类别模板调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drVoterTypeTpl:list")
    @RequestMapping("/drVoterTypeTpl_type")
    public String drVoterTypeTpl_type(Integer id, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            if (null == pageSize) {
                pageSize = 8;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            DrVoterTypeExample example = new DrVoterTypeExample();
            DrVoterTypeExample.Criteria criteria = example.createCriteria().andTplIdEqualTo(id);
            example.setOrderByClause("sort_order asc");

            int count = (int) drVoterTypeMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<DrVoterType> drVoterTypes = drVoterTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("drVoterTypes", drVoterTypes);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id != null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            DrVoterTypeTpl drVoterTypeTpl = drVoterTypeTplMapper.selectByPrimaryKey(id);
            modelMap.put("drVoterTypeTpl", drVoterTypeTpl);
            modelMap.put("drVoterTypeTplMap", drVoterTypeTplService.findAll());
        }

        return "dr/drVoterTypeTpl/drVoterTypeTpl_type";
    }

    public void drVoterTypeTpl_export(DrVoterTypeTplExample example, HttpServletResponse response) {

        List<DrVoterTypeTpl> records = drVoterTypeTplMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"模板名称|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DrVoterTypeTpl record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "填表人类别模板_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/drVoterTypeTpl_selects")
    @ResponseBody
    public Map drVoterTypeTpl_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DrVoterTypeTplExample example = new DrVoterTypeTplExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = drVoterTypeTplMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DrVoterTypeTpl> records = drVoterTypeTplMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DrVoterTypeTpl record:records){

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
