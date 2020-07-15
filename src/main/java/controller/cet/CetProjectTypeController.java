package controller.cet;

import domain.cet.CetProjectType;
import domain.cet.CetProjectTypeExample;
import domain.cet.CetProjectTypeExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetProjectTypeController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProjectType:list")
    @RequestMapping("/cetProjectType")
    public String cetProjectType(@RequestParam(required = false, defaultValue = "1") Byte type,
                                 ModelMap modelMap) {

        modelMap.put("type", type);

        return "cet/cetProjectType/cetProjectType_page";
    }

    @RequiresPermissions("cetProjectType:list")
    @RequestMapping("/cetProjectType_data")
    public void cetProjectType_data(HttpServletResponse response,
                                    @RequestParam(defaultValue = "1") Byte type,
                                    String name,
                                    String code,
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

        CetProjectTypeExample example = new CetProjectTypeExample();
        CetProjectTypeExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("sort_order asc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (StringUtils.isNotBlank(code)){
            criteria.andCodeLike(SqlUtils.trimLike(code));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetProjectType_export(example, response);
            return;
        }

        long count = cetProjectTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetProjectType> records= cetProjectTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetProjectType.class, cetProjectTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetProjectType:edit")
    @RequestMapping(value = "/cetProjectType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectType_au(CetProjectType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetProjectTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加培训类别：%s", record.getName()));
        } else {

            cetProjectTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新培训类别：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectType:edit")
    @RequestMapping("/cetProjectType_au")
    public String cetProjectType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetProjectType cetProjectType = cetProjectTypeMapper.selectByPrimaryKey(id);
            modelMap.put("cetProjectType", cetProjectType);
        }
        return "cet/cetProjectType/cetProjectType_au";
    }

    @RequiresPermissions("cetProjectType:del")
    @RequestMapping(value = "/cetProjectType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetProjectType_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetProjectTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除培训类别：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectType:changeOrder")
    @RequestMapping(value = "/cetProjectType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetProjectTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "培训类别调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetProjectType_export(CetProjectTypeExample example, HttpServletResponse response) {

        List<CetProjectType> records = cetProjectTypeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetProjectType record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "培训类别_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetProjectType_selects")
    @ResponseBody
    public Map cetProjectType_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetProjectTypeExample example = new CetProjectTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = cetProjectTypeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetProjectType> cetProjectTypes = cetProjectTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cetProjectTypes && cetProjectTypes.size()>0){

            for(CetProjectType cetProjectType:cetProjectTypes){

                Select2Option option = new Select2Option();
                option.setText(cetProjectType.getName());
                option.setId(cetProjectType.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
