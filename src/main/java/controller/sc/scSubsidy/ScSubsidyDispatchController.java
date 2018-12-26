package controller.sc.scSubsidy;

import controller.sc.ScBaseController;
import domain.sc.scSubsidy.ScSubsidyDispatch;
import domain.sc.scSubsidy.ScSubsidyDispatchView;
import domain.sc.scSubsidy.ScSubsidyDispatchViewExample;
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
public class ScSubsidyDispatchController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidyDispatch")
    public String scSubsidyDispatch(@RequestParam(required = false, defaultValue = "1") Byte cls,
                                    Integer hrType,
                                    ModelMap modelMap) {

        modelMap.put("cls", cls);
        if(hrType!=null){
            modelMap.put("hrAnnualType", annualTypeService.findAll(SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY).get(hrType));
        }
        return "sc/scSubsidy/scSubsidyDispatch/scSubsidyDispatch_page";
    }

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidyDispatch_data")
    @ResponseBody
    public void scSubsidyDispatch_data(HttpServletResponse response,
                                       Short year,
                                       Integer hrType,
                                       Integer hrNum,
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

        ScSubsidyDispatchViewExample example = new ScSubsidyDispatchViewExample();
        ScSubsidyDispatchViewExample.Criteria criteria = example.createCriteria();
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

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scSubsidyDispatch_export(example, response);
            return;
        }

        long count = scSubsidyDispatchViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScSubsidyDispatchView> records= scSubsidyDispatchViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scSubsidyDispatch.class, scSubsidyDispatchMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping(value = "/scSubsidyDispatch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scSubsidyDispatch_au(ScSubsidyDispatch record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            
            scSubsidyDispatchService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "添加干部津贴变动包含的干部任免文件：%s", record.getId()));
        } else {

            scSubsidyDispatchService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "更新干部津贴变动包含的干部任免文件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping("/scSubsidyDispatch_au")
    public String scSubsidyDispatch_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScSubsidyDispatch scSubsidyDispatch = scSubsidyDispatchMapper.selectByPrimaryKey(id);
            modelMap.put("scSubsidyDispatch", scSubsidyDispatch);
        }
        return "sc/scSubsidy/scSubsidyDispatch/scSubsidyDispatch_au";
    }

    @RequiresPermissions("scSubsidy:del")
    @RequestMapping(value = "/scSubsidyDispatch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scSubsidyDispatch_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scSubsidyDispatchService.del(id);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "删除干部津贴变动包含的干部任免文件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scSubsidy:del")
    @RequestMapping(value = "/scSubsidyDispatch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scSubsidyDispatch_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scSubsidyDispatchService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "批量删除干部津贴变动包含的干部任免文件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void scSubsidyDispatch_export(ScSubsidyDispatchViewExample example, HttpServletResponse response) {

        List<ScSubsidyDispatchView> records = scSubsidyDispatchViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"subsidy_id|100","dispatch_id|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScSubsidyDispatchView record = records.get(i);
            String[] values = {
                record.getSubsidyId()+"",
                            record.getDispatchId()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部津贴变动包含的干部任免文件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
