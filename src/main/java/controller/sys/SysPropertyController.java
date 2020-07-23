package controller.sys;

import controller.BaseController;
import domain.sys.SysProperty;
import domain.sys.SysPropertyExample;
import domain.sys.SysPropertyExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class SysPropertyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sysProperty:list")
    @RequestMapping("/sysProperty")
    public String sysProperty(@RequestParam(required = false, defaultValue = "1")Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        return "sys/sysProperty/sysProperty_page";
    }

    @RequiresPermissions("sysProperty:list")
    @RequestMapping("/sysProperty_data")
    @ResponseBody
    public void sysProperty_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "sys_property") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    String code,
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

        SysPropertyExample example = new SysPropertyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            sysProperty_export(example, response);
            return;
        }

        long count = sysPropertyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysProperty> records= sysPropertyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(sysProperty.class, sysPropertyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("sysProperty:edit")
    @RequestMapping(value = "/sysProperty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysProperty_au(SysProperty record, MultipartFile _file, HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if (sysPropertyService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }

        record.setContent(HtmlUtils.htmlUnescape(record.getContent()));

        if(record.getType()== SystemConstants.SYS_PROPERTY_TYPE_BOOL){
            record.setContent(BooleanUtils.toBoolean(record.getContent())?"true":"false");
        }else if(record.getType()==SystemConstants.SYS_PROPERTY_TYPE_PIC){
            record.setContent(upload(_file, "sysProperty"));

            if(StringUtils.equals(record.getCode(), "dr_site_bg")) {
                FileUtils.copyFile(springProps.uploadPath + record.getContent(),
                        CmTag.getImgFolder() + "dr_login_bg.png");
            }
        }

        if (id == null) {
            
            sysPropertyService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "添加系统属性：%s", record.getName()));
        } else {

            sysPropertyService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "更新系统属性：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysProperty:edit")
    @RequestMapping("/sysProperty_au")
    public String sysProperty_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SysProperty sysProperty = sysPropertyMapper.selectByPrimaryKey(id);
            modelMap.put("sysProperty", sysProperty);
        }
        return "sys/sysProperty/sysProperty_au";
    }

    @RequiresPermissions("sysProperty:del")
    @RequestMapping(value = "/sysProperty_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysProperty_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            sysPropertyService.del(id);
            logger.info(addLog( LogConstants.LOG_ADMIN, "删除系统属性：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysProperty:del")
    @RequestMapping(value = "/sysProperty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map sysProperty_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            sysPropertyService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_ADMIN, "批量删除系统属性：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysProperty:changeOrder")
    @RequestMapping(value = "/sysProperty_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysProperty_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        sysPropertyService.changeOrder(id, addNum);
        logger.info(addLog( LogConstants.LOG_ADMIN, "系统属性调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void sysProperty_export(SysPropertyExample example, HttpServletResponse response) {

        List<SysProperty> records = sysPropertyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"代码|100","名称|100","内容|100","类型|100","排序|100","说明|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            SysProperty record = records.get(i);
            String[] values = {
                record.getCode(),
                            record.getName(),
                            record.getContent(),
                            record.getType()+"",
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "系统属性_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequestMapping("/sysProperty_selects")
    @ResponseBody
    public Map sysProperty_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysPropertyExample example = new SysPropertyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = sysPropertyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SysProperty> records = sysPropertyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(SysProperty record:records){

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
    }*/
}
