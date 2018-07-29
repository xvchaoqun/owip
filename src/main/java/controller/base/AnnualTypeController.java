package controller.base;

import controller.BaseController;
import domain.base.AnnualType;
import domain.base.AnnualTypeExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AnnualTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private void checkPermission(byte module){

        SecurityUtils.getSubject().checkPermission("annual_type_module:"+module);
    }

    @RequestMapping("/annualTypeList")
    public String annualTypeList(String[] cls, ModelMap modelMap) {

        return "base/annualType/annualTypeList";
    }

    @RequestMapping("/annualTypeList_item")
    public String annualTypeList_item(byte module, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        checkPermission(module);

        if (null == pageSize) {
            pageSize = 8;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        AnnualTypeExample example = new AnnualTypeExample();
        example.createCriteria().andModuleEqualTo(module);
        example.setOrderByClause("sort_order desc");

        long count = annualTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<AnnualType> annualTypes = annualTypeMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("annualTypes", annualTypes);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;
        searchStr += "&module=" + module;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "base/annualType/annualTypeList_item";
    }

    @RequestMapping("/annualType")
    public String annualType() {

        return "base/annualType/annualType_page";
    }

    @RequestMapping(value = "/annualType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_annualType_au(AnnualType record, byte module, HttpServletRequest request) {

        checkPermission(module);

        record.setModule(module);
        Integer id = record.getId();

        if (id == null) {
            
            annualTypeService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "添加年度类型：%s", record.getId()));
        } else {

            annualTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "更新年度类型：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/annualType_au")
    public String annualType_au(Integer id, byte module, ModelMap modelMap) {

        if (id != null) {
            AnnualType annualType = annualTypeMapper.selectByPrimaryKey(id);
            modelMap.put("annualType", annualType);
            module = annualType.getModule();
        }

        checkPermission(module);

        modelMap.put("module", module);

        return "base/annualType/annualType_au";
    }

    @RequestMapping(value = "/annualType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_annualType_del(HttpServletRequest request, int id) {

        AnnualType annualType = annualTypeMapper.selectByPrimaryKey(id);
        byte module = annualType.getModule();
        checkPermission(module);

        annualTypeService.del(id);
        logger.info(addLog( LogConstants.LOG_ADMIN, "删除年度类型：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/annualType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_annualType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        AnnualType annualType = annualTypeMapper.selectByPrimaryKey(id);
        byte module = annualType.getModule();
        checkPermission(module);

        annualTypeService.changeOrder(id, addNum);
        logger.info(addLog( LogConstants.LOG_ADMIN, "年度类型调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/annualType_selects")
    @ResponseBody
    public Map annualType_selects(Integer pageSize,Integer pageNo,
                                  byte module,
                                  Short year, String searchStr) throws IOException {

        checkPermission(module);

        Map resultMap = success();
        resultMap.put("totalCount", 0);
        if(year==null) return resultMap;

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        AnnualTypeExample example = new AnnualTypeExample();
        AnnualTypeExample.Criteria criteria = example.createCriteria().andYearEqualTo(year);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = annualTypeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<AnnualType> annualTypes = annualTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != annualTypes && annualTypes.size()>0){

            for(AnnualType annualType:annualTypes){

                Select2Option option = new Select2Option();
                option.setText(annualType.getName());
                option.setId(annualType.getId() + "");

                options.add(option);
            }
        }

        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
