package controller.sp;

import domain.sp.SpRetire;
import domain.sp.SpRetireExample;
import domain.sp.SpRetireExample.Criteria;
import domain.sys.TeacherInfo;
import mixin.MixinUtils;
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
import service.sys.TeacherInfoService;
import sys.constants.LogConstants;
import sys.tags.CmTag;
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
@RequestMapping("/sp")
public class SpRetireController extends SpBaseController {

    @Autowired
    private TeacherInfoService teacherInfoService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sp:list")
    @RequestMapping("/spRetire")
    public String spRetire(Integer userId,Integer unitId, ModelMap modelMap) {

        modelMap.put("unit",CmTag.getUnitById(unitId));
        modelMap.put("sysUser",CmTag.getUserById(userId));
        return "sp/spRetire/spRetire_page";
    }

    @RequiresPermissions("sp:list")
    @RequestMapping("/spRetire_data")
    @ResponseBody
    public void spRetire_data(HttpServletResponse response,
                                    Integer unitId,
                                    Integer userId,
                                    Boolean isCadre,
                                
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

        SpRetireExample example = new SpRetireExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (isCadre!=null) {
            criteria.andIsCadreEqualTo(isCadre);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            spRetire_export(example, response);
            return;
        }

        long count = spRetireMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SpRetire> records= spRetireMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(spRetire.class, spRetireMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spRetire_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spRetire_au(SpRetire record, HttpServletRequest request) {

        Integer id = record.getId();

        if (spRetireService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        spRetireService.updateRecord(record);

        if (id == null) {

                spRetireService.insertSelective(record);
            logger.info(log( LogConstants.LOG_SP, "添加离退休教师代表：{0}", record.getId()));
        } else {

            spRetireService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_SP, "更新离退休教师代表：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping("/spRetire_au")
    public String spRetire_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SpRetire spRetire = spRetireMapper.selectByPrimaryKey(id);
            modelMap.put("spRetire", spRetire);
            modelMap.put("sysUser", CmTag.getUserById(spRetire.getUserId()));
            modelMap.put("unit",CmTag.getUnitById(spRetire.getUnitId()));
        }
        return "sp/spRetire/spRetire_au";
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spRetire_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spRetire_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            spRetireService.del(id);
            logger.info(log( LogConstants.LOG_SP, "删除离退休教师代表：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spRetire_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map spRetire_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            spRetireService.batchDel(ids);
            logger.info(log( LogConstants.LOG_SP, "批量删除离退休教师代表：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("sp:edit")
    @RequestMapping(value = "/spRetire_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spRetire_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        spRetireService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_SP, "离退休教师代表调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void spRetire_export(SpRetireExample example, HttpServletResponse response) {

        List<SpRetire> records = spRetireMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名|100","所在单位|100","政治面貌|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            SpRetire record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getUnitId()+"",
                            record.getPoliticsStatus()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("离退休教师代表(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/spRetire_selects")
    @ResponseBody
    public Map spRetire_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SpRetireExample example = new SpRetireExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = spRetireMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SpRetire> records = spRetireMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(SpRetire record:records){

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

    @RequestMapping(value = "/spRetire_details", method = RequestMethod.POST)
    @ResponseBody
    public Map do_spRetire_details(Integer userId){

        Map dateilsMap = new HashMap();

        TeacherInfo teacherInfo = teacherInfoService.get(userId);

        if (teacherInfo == null) return null;
        dateilsMap.put("teacherInfo",teacherInfo);

        return dateilsMap;
    }
}
