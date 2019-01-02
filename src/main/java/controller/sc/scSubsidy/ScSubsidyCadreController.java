package controller.sc.scSubsidy;

import controller.sc.ScBaseController;
import domain.sc.scSubsidy.ScSubsidyCadre;
import domain.sc.scSubsidy.ScSubsidyCadreView;
import domain.sc.scSubsidy.ScSubsidyCadreViewExample;
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
@RequestMapping("/sc")
public class ScSubsidyCadreController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidyCadre")
    public String scSubsidyCadre(@RequestParam(required = false, defaultValue = "1") Byte cls,
                                 Integer cadreId,
                                 Integer hrType,
                                 Integer feType,
                                 ModelMap modelMap) {
        modelMap.put("cls", cls);

        if(cadreId!=null){
            modelMap.put("cadre", cadreService.findAll().get(cadreId));
        }
        if(hrType!=null){
            modelMap.put("hrAnnualType", annualTypeService.findAll(SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY).get(hrType));
        }
        if(feType!=null){
            modelMap.put("feAnnualType", annualTypeService.findAll(SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY).get(feType));
        }

        return "sc/scSubsidy/scSubsidyCadre/scSubsidyCadre_page";
    }

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidyCadre_data")
    @ResponseBody
    public void scSubsidyCadre_data(HttpServletResponse response,
                                    Short year,
                                    Integer hrType,
                                    Integer hrNum,
                                    Integer feType,
                                    Integer feNum,
                                    Integer cadreId,
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

        ScSubsidyCadreViewExample example = new ScSubsidyCadreViewExample();
        ScSubsidyCadreViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (hrType!=null) {
            criteria.andHrTypeEqualTo(hrType);
        }
        if (hrNum!=null) {
            criteria.andHrNumEqualTo(hrNum);
        }
        if (feType!=null) {
            criteria.andFeTypeEqualTo(feType);
        }
        if (feNum!=null) {
            criteria.andFeNumEqualTo(feNum);
        }

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scSubsidyCadre_export(example, response);
            return;
        }

        long count = scSubsidyCadreViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScSubsidyCadreView> records= scSubsidyCadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scSubsidyCadre.class, scSubsidyCadreMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping(value = "/scSubsidyCadre_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scSubsidyCadre_au(ScSubsidyCadre record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            
            scSubsidyCadreService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "添加干部津贴变动文件包含的干部：%s", record.getId()));
        } else {

            scSubsidyCadreService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "更新干部津贴变动文件包含的干部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping("/scSubsidyCadre_au")
    public String scSubsidyCadre_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScSubsidyCadre scSubsidyCadre = scSubsidyCadreMapper.selectByPrimaryKey(id);
            modelMap.put("scSubsidyCadre", scSubsidyCadre);
        }
        return "sc/scSubsidy/scSubsidyCadre/scSubsidyCadre_au";
    }

    @RequiresPermissions("scSubsidy:del")
    @RequestMapping(value = "/scSubsidyCadre_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scSubsidyCadre_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scSubsidyCadreService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "批量删除干部津贴变动文件包含的干部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scSubsidyCadre_export(ScSubsidyCadreViewExample example, HttpServletResponse response) {

        List<ScSubsidyCadreView> records = scSubsidyCadreViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"subsidy_id|100","干部|100","所在单位|100","现任职务|100","行政级别|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScSubsidyCadreView record = records.get(i);
            String[] values = {
                record.getSubsidyId()+"",
                            record.getCadreId()+"",
                            record.getUnitId()+"",
                            record.getPost(),
                            record.getAdminLevel()+""
            };
            valuesList.add(values);
        }
        String fileName = "干部津贴变动文件包含的干部_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
