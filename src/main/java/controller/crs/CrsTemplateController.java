package controller.crs;

import domain.crs.CrsTemplate;
import domain.crs.CrsTemplateExample;
import domain.crs.CrsTemplateExample.Criteria;
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
import org.springframework.web.util.HtmlUtils;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CrsTemplateController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsTemplate:list")
    @RequestMapping("/crsTemplate")
    public String crsTemplate() {

        return "crs/crsTemplate/crsTemplate_page";
    }

    @RequiresPermissions("crsTemplate:list")
    @RequestMapping("/crsTemplate_data")
    public void crsTemplate_data(HttpServletResponse response,
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

        CrsTemplateExample example = new CrsTemplateExample();
        Criteria criteria = example.createCriteria();

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        long count = crsTemplateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsTemplate> records = crsTemplateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsTemplate.class, crsTemplateMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsTemplate:edit")
    @RequestMapping(value = "/crsTemplate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsTemplate_au(CrsTemplate record, HttpServletRequest request) {

        Integer id = record.getId();
        record.setContent(record.getContent());
        if (id == null) {
            crsTemplateService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加招聘条件通用模板：%s", record.getId()));
        } else {

            crsTemplateService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新招聘条件通用模板：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsTemplate:edit")
    @RequestMapping("/crsTemplate_au")
    public String crsTemplate_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsTemplate crsTemplate = crsTemplateMapper.selectByPrimaryKey(id);
            modelMap.put("crsTemplate", crsTemplate);
        }
        return "crs/crsTemplate/crsTemplate_au";
    }

    @RequiresPermissions("crsTemplate:del")
    @RequestMapping(value = "/crsTemplate_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crsTemplateService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除招聘条件通用模板：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
