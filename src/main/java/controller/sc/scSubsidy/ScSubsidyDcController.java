package controller.sc.scSubsidy;

import controller.sc.ScBaseController;
import domain.sc.scSubsidy.ScSubsidyDc;
import domain.sc.scSubsidy.ScSubsidyDcExample;
import domain.sc.scSubsidy.ScSubsidyDcExample.Criteria;
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
@RequestMapping("/sc")
public class ScSubsidyDcController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidyDc")
    public String scSubsidyDc(@RequestParam(required = false, defaultValue = "1") Byte cls,
                              ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "sc/scSubsidy/scSubsidyDc/scSubsidyDc_page";
    }

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidyDc_data")
    @ResponseBody
    public void scSubsidyDc_data(HttpServletResponse response,
                                    Integer subsidyId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScSubsidyDcExample example = new ScSubsidyDcExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (subsidyId!=null) {
            criteria.andSubsidyIdEqualTo(subsidyId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scSubsidyDc_export(example, response);
            return;
        }

        long count = scSubsidyDcMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScSubsidyDc> records= scSubsidyDcMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scSubsidyDc.class, scSubsidyDcMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping(value = "/scSubsidyDc_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scSubsidyDc_au(ScSubsidyDc record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            
            scSubsidyDcService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "添加干部任免记录：%s", record.getId()));
        } else {

            scSubsidyDcService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "更新干部任免记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping("/scSubsidyDc_au")
    public String scSubsidyDc_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScSubsidyDc scSubsidyDc = scSubsidyDcMapper.selectByPrimaryKey(id);
            modelMap.put("scSubsidyDc", scSubsidyDc);
        }
        return "sc/scSubsidy/scSubsidyDc/scSubsidyDc_au";
    }

    @RequiresPermissions("scSubsidy:del")
    @RequestMapping(value = "/scSubsidyDc_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scSubsidyDc_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scSubsidyDcService.del(id);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "删除干部任免记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scSubsidy:del")
    @RequestMapping(value = "/scSubsidyDc_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scSubsidyDc_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scSubsidyDcService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "批量删除干部任免记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scSubsidyDc_export(ScSubsidyDcExample example, HttpServletResponse response) {

        List<ScSubsidyDc> records = scSubsidyDcMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"subsidy_id|100","cadre_id|100","任免日期|100","类别|100","职务|100","行政级别|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScSubsidyDc record = records.get(i);
            String[] values = {
                record.getSubsidyId()+"",
                            record.getCadreId()+"",
                            DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYY_MM_DD),
                            record.getType()+"",
                            record.getPost(),
                            record.getAdminLevel()+""
            };
            valuesList.add(values);
        }
        String fileName = "干部任免记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
