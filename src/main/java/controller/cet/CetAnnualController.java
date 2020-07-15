package controller.cet;

import domain.cet.CetAnnual;
import domain.cet.CetAnnualExample;
import domain.cet.CetAnnualExample.Criteria;
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
@RequestMapping("/cet")
public class CetAnnualController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetAnnual:list")
    @RequestMapping("/cetAnnual_detail")
    public String cetAnnual_detail(int annualId, ModelMap modelMap) {

        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        modelMap.put("cetAnnual", cetAnnual);
        
        return "cet/cetAnnual/cetAnnual_detail";
    }
    @RequiresPermissions("cetAnnual:list")
    @RequestMapping("/cetAnnual")
    public String cetAnnual(ModelMap modelMap) {

        return "cet/cetAnnual/cetAnnual_page";
    }

    @RequiresPermissions("cetAnnual:list")
    @RequestMapping("/cetAnnual_data")
    @ResponseBody
    public void cetAnnual_data(HttpServletResponse response,
                                    Integer year,
                                    Integer traineeTypeId,
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

        CetAnnualExample example = new CetAnnualExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc, id desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (traineeTypeId!=null) {
            criteria.andTraineeTypeIdEqualTo(traineeTypeId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetAnnual_export(example, response);
            return;
        }

        long count = cetAnnualMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetAnnual> records= cetAnnualMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetAnnual.class, cetAnnualMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetAnnual:edit")
    @RequestMapping(value = "/cetAnnual_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnual_au(CetAnnual record, HttpServletRequest request) {

        Integer id = record.getId();
      
        if (id == null) {
            
            cetAnnualService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_CET, "添加年度学习档案"));
        } else {

            cetAnnualService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_CET, "更新年度学习档案：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetAnnual:edit")
    @RequestMapping("/cetAnnual_au")
    public String cetAnnual_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(id);
            modelMap.put("cetAnnual", cetAnnual);
        }
        
        return "cet/cetAnnual/cetAnnual_au";
    }

    @RequiresPermissions("cetAnnual:del")
    @RequestMapping(value = "/cetAnnual_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnual_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetAnnualService.del(id);
            logger.info(addLog( LogConstants.LOG_CET, "删除年度学习档案：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetAnnual:del")
    @RequestMapping(value = "/cetAnnual_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetAnnual_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetAnnualService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_CET, "批量删除年度学习档案：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 导出学时情况统计表
    @RequiresPermissions("cetAnnual:list")
    @RequestMapping("/cetAnnual_exportObjs")
    public void cetAnnual_exportObjs(int annualId, @RequestParam(value = "ids[]") Integer[] ids, HttpServletResponse response) throws IOException {
    
        cetExportService.cetAnnual_exportObjs(annualId, ids, response);
        return;
    }
    
    public void cetAnnual_export(CetAnnualExample example, HttpServletResponse response) {

        List<CetAnnual> records = cetAnnualMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","培训对象类型|100","培训对象人数|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetAnnual record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getTraineeTypeId()+"",
                            record.getObjCount()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "年度学习档案_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
