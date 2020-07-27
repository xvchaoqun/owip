package controller.sc.scMotion;

import controller.sc.ScBaseController;
import domain.sc.scMotion.ScMotion;
import domain.sc.scMotion.ScMotionView;
import domain.sc.scMotion.ScMotionViewExample;
import domain.unit.UnitPost;
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
public class ScMotionController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*@RequiresPermissions("scMotion:list")
    @RequestMapping("/scMotion_posts")
    public String scMotion_posts(int motionId, ModelMap modelMap) {

        ScMotion scMotion = scMotionMapper.selectByPrimaryKey(motionId);
        modelMap.put("scMotion", scMotion);

        ScMotionPostExample example = new ScMotionPostExample();
        example.createCriteria().andMotionIdEqualTo(motionId);
        List<ScMotionPost> scMotionPosts = scMotionPostMapper.selectByExample(example);

        modelMap.put("scMotionPosts", scMotionPosts);

        return "sc/scMotion/scMotion/scMotion_posts";
    }*/

    @RequiresPermissions("scMotion:list")
    @RequestMapping("/scMotion")
    public String scMotion(Integer unitId, Integer unitPostId,
    @RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(unitId!=null){
            modelMap.put("unit", unitService.findAll().get(unitId));
        }
        if(unitPostId!=null){

            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(unitPostId);
            modelMap.put("unitPost", unitPost);
        }

        return "sc/scMotion/scMotion/scMotion_page";
    }

    @RequiresPermissions("scMotion:list")
    @RequestMapping("/scMotion_data")
    @ResponseBody
    public void scMotion_data(HttpServletResponse response,
                                    Short year,
                                    Integer unitId,
                                    Integer unitPostId,
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

        ScMotionViewExample example = new ScMotionViewExample();
        ScMotionViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc, hold_date desc");

        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (unitPostId!=null) {
            criteria.andUnitPostIdEqualTo(unitPostId);
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scMotion_export(example, response);
            return;
        }

        long count = scMotionViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMotionView> records= scMotionViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMotion.class, scMotionMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMotion:edit")
    @RequestMapping(value = "/scMotion_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMotion_au(ScMotion record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setContent(record.getContent());
        if (id == null) {
            record.setRecordUserId(ShiroHelper.getCurrentUserId());
            scMotionService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_SC_MOTION, "添加动议：%s", record.getSeq()));
        } else {

            scMotionService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_SC_MOTION, "更新动议：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMotion:edit")
    @RequestMapping("/scMotion_au")
    public String scMotion_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMotion scMotion = scMotionMapper.selectByPrimaryKey(id);
            modelMap.put("scMotion", scMotion);

            int unitPostId = scMotion.getUnitPostId();
            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(unitPostId);
            modelMap.put("unitPost", unitPost);

            if(scMotion.getCommitteeTopicId()!=null){
                modelMap.put("scCommitteeTopic", iScMapper.getScCommitteeTopicView(scMotion.getCommitteeTopicId()));
            }

            if(scMotion.getGroupTopicId()!=null){
                modelMap.put("scGroupTopic", iScMapper.getScGroupTopicView(scMotion.getGroupTopicId()));
            }
        }

        return "sc/scMotion/scMotion/scMotion_au";
    }

    @RequiresPermissions("scMotion:list")
    @RequestMapping("/scMotion_content")
    public String scMotion_content(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMotion scMotion = scMotionMapper.selectByPrimaryKey(id);
            modelMap.put("scMotion", scMotion);
        }

        return "sc/scMotion/scMotion/scMotion_content";
    }

    @RequiresPermissions("scMotion:del")
    @RequestMapping(value = "/scMotion_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMotion_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMotionService.del(id);
            logger.info(addLog( LogConstants.LOG_SC_MOTION, "删除动议：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMotion:del")
    @RequestMapping(value = "/scMotion_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scMotion_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMotionService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_MOTION, "批量删除动议：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scMotion_export(ScMotionViewExample example, HttpServletResponse response) {

        List<ScMotionView> records = scMotionViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份|100","动议日期|100","动议编号|100","所属单位|100","动议形式|100","干部选任方式|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScMotionView record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            DateUtils.formatDate(record.getHoldDate(), DateUtils.YYYY_MM_DD),
                            record.getCode()+"",
                            record.getUnitId()+"",
                            record.getWay()+"",
                            record.getScType()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "动议_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequestMapping("/scMotion_selects")
    @ResponseBody
    public Map scMotion_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMotionExample example = new ScMotionExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = scMotionMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMotion> records = scMotionMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(ScMotion record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
