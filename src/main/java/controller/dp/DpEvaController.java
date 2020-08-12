package controller.dp;

import domain.dp.DpEva;
import domain.dp.DpEvaExample;
import domain.dp.DpEvaExample.Criteria;
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
@RequestMapping("/dp")
public class DpEvaController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpEva:list")
    @RequestMapping("/dpEva")
    public String dpEva(Integer userId,
                        ModelMap modelMap) {
        modelMap.put("userId", userId);

        return "dp/dpEva/dpEva_page";
    }

    @RequiresPermissions("dpEva:list")
    @RequestMapping("/dpEva_data")
    @ResponseBody
    public void dpEva_data(HttpServletResponse response,
                                    Integer userId,
                                    Integer year,
                                
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

        DpEvaExample example = new DpEvaExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (year!=null) {
            criteria.andYearEqualTo(year);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpEva_export(example, response);
            return;
        }

        long count = dpEvaMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpEva> records= dpEvaMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpEva.class, dpEvaMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpEva:edit")
    @RequestMapping(value = "/dpEva_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpEva_au(DpEva record, HttpServletRequest request) {

        Integer id = record.getId();

        /*if (dpEvaService.idDuplicate(id, code)) {
            return failed("添加重复");
        }*/
        if (id == null) {
            
            dpEvaService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加统战人员年度考核记录：{0}", record.getId()));
        } else {

            dpEvaService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新统战人员年度考核记录：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpEva:edit")
    @RequestMapping("/dpEva_au")
    public String dpEva_au(Integer id, Integer userId, ModelMap modelMap) {

        modelMap.put("userId", userId);
        if (id != null) {
            DpEva dpEva = dpEvaMapper.selectByPrimaryKey(id);
            modelMap.put("dpEva", dpEva);
        }
        return "dp/dpEva/dpEva_au";
    }

    @RequiresPermissions("dpEva:del")
    @RequestMapping(value = "/dpEva_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpEva_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpEvaService.del(id);
            logger.info(log( LogConstants.LOG_DPPARTY, "删除统战人员年度考核记录：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpEva:del")
    @RequestMapping(value = "/dpEva_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpEva_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpEvaService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DPPARTY, "批量删除统战人员年度考核记录：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void dpEva_export(DpEvaExample example, HttpServletResponse response) {

        List<DpEva> records = dpEvaMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"用户id|100","年份|100","时任职务|100","考核情况|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpEva record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getYear()+"",
                            record.getTitle(),
                            record.getType()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("统战人员年度考核记录(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dpEva_selects")
    @ResponseBody
    public Map dpEva_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpEvaExample example = new DpEvaExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            //criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = dpEvaMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpEva> records = dpEvaMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpEva record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getUserId());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
