package controller.cet;

import domain.cet.CetUnit;
import domain.cet.CetUnitExample;
import domain.cet.CetUnitExample.Criteria;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetUnitController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetUnit:list")
    @RequestMapping("/cetUnit")
    public String cetUnit() {

        return "cet/cetUnit/cetUnit_page";
    }

    @RequiresPermissions("cetUnit:list")
    @RequestMapping("/cetUnit_data")
    public void cetUnit_data(HttpServletResponse response,

                                    Integer unitId,
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

        CetUnitExample example = new CetUnitExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUnit_export(example, response);
            return;
        }

        long count = cetUnitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUnit> records= cetUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetUnit.class, cetUnitMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetUnit:edit")
    @RequestMapping(value = "/cetUnit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnit_au(CetUnit record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetUnitService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加内设机构：%s", record.getId()));
        } else {

            cetUnitService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新内设机构：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetUnit:edit")
    @RequestMapping("/cetUnit_au")
    public String cetUnit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetUnit cetUnit = cetUnitMapper.selectByPrimaryKey(id);
            modelMap.put("cetUnit", cetUnit);
        }
        return "cet/cetUnit/cetUnit_au";
    }

    @RequiresPermissions("cetUnit:del")
    @RequestMapping(value = "/cetUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnit_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetUnitService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除内设机构：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetUnit:del")
    @RequestMapping(value = "/cetUnit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetUnit_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetUnitService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除内设机构：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetUnit_export(CetUnitExample example, HttpServletResponse response) {

        List<CetUnit> records = cetUnitMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属内设机构|100","管理员|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUnit record = records.get(i);
            String[] values = {
                record.getUnitId()+"",
                            record.getUserId()+""
            };
            valuesList.add(values);
        }
        String fileName = "内设机构_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
