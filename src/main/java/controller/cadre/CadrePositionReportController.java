package controller.cadre;

import controller.BaseController;
import domain.cadre.*;
import domain.cadre.CadrePositionReportExample.Criteria;
import domain.sys.SysUserView;
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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller

public class CadrePositionReportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePositionReport:list")
    @RequestMapping("/cadrePositionReport")
    public String cadrePositionReport(Integer admin,Integer year,
                                      Integer cadreId, ModelMap modelMap) {
        if (cadreId!=null) {
            CadreView cadre=CmTag.getCadreById(cadreId);
            modelMap.put("cadre",cadre);
        }
        return "cadre/cadrePositionReport/cadrePositionReport_page";
    }

    @RequiresPermissions("cadrePositionReport:list")
    @RequestMapping("/cadrePositionReport_data")
    @ResponseBody
    public void cadrePositionReport_data(@CurrentUser SysUserView loginUser, HttpServletResponse response, Integer admin, Integer year,
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

        CadrePositionReportExample example = new CadrePositionReportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (admin==1&&ShiroHelper.isPermitted("cadrePositionReport:adminMenu")) {
            if (cadreId!=null) {
                criteria.andCadreIdEqualTo(cadreId);
            }
        } else { // 干部只能看到自己的
            CadreView cadre = cadreService.dbFindByUserId(loginUser.getUserId());
            criteria.andCadreIdEqualTo(cadre.getId());
        }

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cadrePositionReport_export(example, response);
            return;
        }

        long count = cadrePositionReportMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePositionReport> records= cadrePositionReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cadrePositionReport.class, cadrePositionReportMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadrePositionReport:edit")
    @RequestMapping(value = "/cadrePositionReport_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePositionReport_au(CadrePositionReport record, HttpServletRequest request) {

        Integer id = record.getId();
        Integer cadreId = record.getCadreId();
        Integer year = record.getYear();
        if (cadrePositionReportService.idDuplicate(id,cadreId,year)) {
            return failed("添加重复");
        }
        if (id == null) {
            
            cadrePositionReportService.insertSelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "添加干部述职报告：{0}", record.getId()));
        } else {

            cadrePositionReportService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "更新干部述职报告：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePositionReport:edit")
    @RequestMapping("/cadrePositionReport_au")
    public String cadrePositionReport_au(@CurrentUser SysUserView loginUser,Integer id,Boolean edit, Integer admin,ModelMap modelMap) {

        if (id != null) {
            CadrePositionReport cadrePositionReport = cadrePositionReportMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePositionReport", cadrePositionReport);
        }else if(admin==0&&ShiroHelper.isPermitted("cadrePositionReport:menu")){

            CadrePositionReport cadrePositionReport=new CadrePositionReport();
            CadreView cadre = cadreService.dbFindByUserId(loginUser.getUserId());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            Integer formatDate = Integer.valueOf(sdf.format(date));
            cadrePositionReport.setYear(formatDate);
            cadrePositionReport.setCadreId(cadre.getId());
            modelMap.put("cadrePositionReport", cadrePositionReport);
        }
        modelMap.put("edit",edit);
        modelMap.put("admin",admin);
        return "cadre/cadrePositionReport/cadrePositionReport_au";
    }

    @RequiresPermissions("cadrePositionReport:edit")
    @RequestMapping(value = "/cadrePositionReport_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePositionReport_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadrePositionReportService.del(id);
            logger.info(log( LogConstants.LOG_ADMIN, "删除干部述职报告：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePositionReport:edit")
    @RequestMapping(value = "/cadrePositionReport_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadrePositionReport_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadrePositionReportService.batchDel(ids);
            logger.info(log( LogConstants.LOG_ADMIN, "批量删除干部述职报告：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePositionReport:edit")
    @RequestMapping(value = "/cadrePositionReport_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadrePositionReportService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "干部述职报告调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadrePositionReport_export(CadrePositionReportExample example, HttpServletResponse response) {

        List<CadrePositionReport> records = cadrePositionReportMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"干部id|100","年度|100","创建时间|100","述职报告内容|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadrePositionReport record = records.get(i);
            String[] values = {
                record.getCadreId()+"",
                    String.valueOf(record.getYear()),
                            DateUtils.formatDate(record.getCreateDate(), DateUtils.YYYY_MM_DD),
                            record.getContent()
            };
            valuesList.add(values);
        }
        String fileName = String.format("干部述职报告(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cadrePositionReport_selects")
    @ResponseBody
    public Map cadrePositionReport_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadrePositionReportExample example = new CadrePositionReportExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

       /* if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = cadrePositionReportMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CadrePositionReport> records = cadrePositionReportMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CadrePositionReport record:records){

                Map<String, Object> option = new HashMap<>();
              /*  option.put("text", record.getName());*/
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
