package controller.cg;

import domain.cg.CgUnit;
import domain.cg.CgUnitExample;
import domain.cg.CgUnitExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.unit.UnitMapper;
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
@RequestMapping("/cg")
public class CgUnitController extends CgBaseController {

    @Autowired
    private UnitMapper unitMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cgUnit:list")
    @RequestMapping("/cgUnit")
    public String cgUnit() {

        return "cg/cgUnit/cgUnit_page";
    }

    @RequiresPermissions("cgUnit:list")
    @RequestMapping("/cgUnit_data")
    @ResponseBody
    public void cgUnit_data(HttpServletResponse response,
                                    Integer teamId,
                                    Integer unitId,
                                    Date confirmDate,
                                
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

        CgUnitExample example = new CgUnitExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_current desc,confirm_date desc");

        if (teamId!=null) {
            criteria.andTeamIdEqualTo(teamId);
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (confirmDate!=null) {
        criteria.andConfirmDateGreaterThan(confirmDate);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cgUnit_export(example, response);
            return;
        }

        long count = cgUnitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CgUnit> records= cgUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cgUnit.class, cgUnitMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cgUnit:edit")
    @RequestMapping(value = "/cgUnit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgUnit_au(CgUnit record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));

        if (cgUnitService.idDuplicate(id, record.getTeamId(), record.getIsCurrent())) {
            return failed("添加重复");
        }
        if (id == null) {

            cgUnitService.insertSelective(record);
            logger.info(log( LogConstants.LOG_CG, "添加挂靠单位：{0}", record.getId()));
        } else {

            cgUnitService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_CG, "更新挂靠单位：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgUnit:edit")
    @RequestMapping("/cgUnit_au")
    public String cgUnit_au(Integer id,Integer teamId, ModelMap modelMap) {

        Boolean isCurrent = true;
        if (id != null) {

            CgUnit cgUnit = cgUnitMapper.selectByPrimaryKey(id);
            isCurrent = cgUnit.getIsCurrent();
            teamId = cgUnit.getTeamId();
            modelMap.put("unit",unitMapper.selectByPrimaryKey(cgUnit.getUnitId()));
            modelMap.put("cgUnit", cgUnit);
        }

        modelMap.put("teamId",teamId);
        modelMap.put("isCurrent",isCurrent);
        return "cg/cgUnit/cgUnit_au";
    }

    @RequiresPermissions("cgUnit:del")
    @RequestMapping(value = "/cgUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgUnit_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cgUnitService.del(id);
            logger.info(log( LogConstants.LOG_CG, "删除挂靠单位：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgUnit:del")
    @RequestMapping(value = "/cgUnit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cgUnit_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cgUnitService.batchDel(ids);
            logger.info(log( LogConstants.LOG_CG, "批量删除挂靠单位：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void cgUnit_export(CgUnitExample example, HttpServletResponse response) {

        List<CgUnit> records = cgUnitMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属委员会或领导小组|100","单位|100","确定时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CgUnit record = records.get(i);
            String[] values = {
                record.getTeamId()+"",
                            record.getUnitId()+"",
                            DateUtils.formatDate(record.getConfirmDate(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = String.format("挂靠单位(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    //批量撤销挂靠单位
    @RequiresPermissions("cgUnit:plan")
    @RequestMapping(value = "/cgUnit_plan", method = RequestMethod.POST)
    @ResponseBody
    public Map cgUnit_plan(Integer[] ids, Boolean isCurrent) {

        if (null != ids && ids.length>0){
            cgUnitService.updateCgRuleStatus(ids, isCurrent);
            logger.info(addLog(LogConstants.LOG_PS, "批量撤销委员会和小组挂靠单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
