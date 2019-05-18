package controller.partySchool;

import controller.BaseController;
import domain.partySchool.PartySchool;
import domain.partySchool.PartySchoolExample;
import domain.partySchool.PartySchoolExample.Criteria;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class PartySchoolController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partySchool:list")
    @RequestMapping("/partySchool")
    public String partySchool( @RequestParam(required = false, defaultValue = "0") boolean isHistory, ModelMap modelMap) {

        modelMap.put("isHistory", isHistory);

        return "partySchool/partySchool/partySchool_page";
    }

    @RequiresPermissions("partySchool:list")
    @RequestMapping("/partySchool_data")
    public void partySchool_data(HttpServletResponse response,
                                 String name,
                                 @RequestParam(required = false, defaultValue = "0") boolean isHistory,
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

        PartySchoolExample example = new PartySchoolExample();
        Criteria criteria = example.createCriteria().andIsHistoryEqualTo(isHistory);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            partySchool_export(example, response);
            return;
        }

        long count = partySchoolMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartySchool> records= partySchoolMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partySchool.class, partySchoolMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("partySchool:edit")
    @RequestMapping(value = "/partySchool_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partySchool_au(PartySchool record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setIsHistory(false);
            partySchoolService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加二级党校：%s", record.getId()));
        } else {

            partySchoolService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新二级党校：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partySchool:edit")
    @RequestMapping("/partySchool_au")
    public String partySchool_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PartySchool partySchool = partySchoolMapper.selectByPrimaryKey(id);
            modelMap.put("partySchool", partySchool);
        }
        return "partySchool/partySchool/partySchool_au";
    }

    @RequiresPermissions("partySchool:history")
    @RequestMapping(value = "/partySchool_history", method = RequestMethod.POST)
    @ResponseBody
    public Map partySchool_history(HttpServletRequest request,
                                   boolean isHistory,
                                   @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partySchoolService.history(ids, isHistory);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量转移二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("partySchool:del")
    @RequestMapping(value = "/partySchool_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partySchool_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partySchoolService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partySchool:changeOrder")
    @RequestMapping(value = "/partySchool_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partySchool_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        partySchoolService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "二级党校调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void partySchool_export(PartySchoolExample example, HttpServletResponse response) {

        List<PartySchool> records = partySchoolMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"二级党校名称|100","设立日期|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartySchool record = records.get(i);
            String[] values = {
                record.getName(),
                            DateUtils.formatDate(record.getFoundDate(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "二级党校_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/partySchool_selects")
    @ResponseBody
    public Map partySchool_selects(Integer pageSize,
                                   Boolean isHistory,
                                   Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartySchoolExample example = new PartySchoolExample();
        Criteria criteria = example.createCriteria().andIsHistoryEqualTo(false);
        example.setOrderByClause("is_history asc, sort_order desc");

        if(isHistory!=null){
            criteria.andIsHistoryEqualTo(isHistory);
        }

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = partySchoolMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PartySchool> partySchools = partySchoolMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != partySchools && partySchools.size()>0){

            for(PartySchool partySchool:partySchools){

                Map<String, Object> option = new HashMap<>();
                option.put("text", partySchool.getName());
                option.put("id", partySchool.getId() + "");
                option.put("del", partySchool.getIsHistory());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
