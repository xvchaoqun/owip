package controller.recruit;

import controller.BaseController;
import domain.recruit.RecruitTemplate;
import domain.recruit.RecruitTemplateExample;
import domain.recruit.RecruitTemplateExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RecruitTemplateController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("recruitTemplate:list")
    @RequestMapping("/recruitTemplate")
    public String recruitTemplate() {

        return "index";
    }

    @RequiresPermissions("recruitTemplate:list")
    @RequestMapping("/recruitTemplate_page")
    public String recruitTemplate_page() {

        return "recruit/recruitTemplate/recruitTemplate_page";
    }

    @RequiresPermissions("recruitTemplate:list")
    @RequestMapping("/recruitTemplate_data")
    public void recruitTemplate_data(HttpServletResponse response,
                                     Byte type,
                                     String name,
                                     Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        RecruitTemplateExample example = new RecruitTemplateExample();
        Criteria criteria = example.createCriteria();

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        int count = recruitTemplateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<RecruitTemplate> records = recruitTemplateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(recruitTemplate.class, recruitTemplateMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("recruitTemplate:edit")
    @RequestMapping(value = "/recruitTemplate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_recruitTemplate_au(RecruitTemplate record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            recruitTemplateService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加招聘条件通用模板：%s", record.getId()));
        } else {

            recruitTemplateService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新招聘条件通用模板：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("recruitTemplate:edit")
    @RequestMapping("/recruitTemplate_au")
    public String recruitTemplate_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            RecruitTemplate recruitTemplate = recruitTemplateMapper.selectByPrimaryKey(id);
            modelMap.put("recruitTemplate", recruitTemplate);
        }
        return "recruit/recruitTemplate/recruitTemplate_au";
    }

    @RequiresPermissions("recruitTemplate:del")
    @RequestMapping(value = "/recruitTemplate_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            recruitTemplateService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除招聘条件通用模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
