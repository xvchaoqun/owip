package controller.unit;

import controller.BaseController;
import domain.unit.UnitPost;
import domain.unit.UnitTeam;
import domain.unit.UnitTeamPlan;
import domain.unit.UnitTeamPlanExample;
import domain.unit.UnitTeamPlanExample.Criteria;
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

public class UnitTeamPlanController extends BaseController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequiresPermissions("unitTeam:list")
    @RequestMapping("/unitTeamPlan")
    public String unitTeamPlan(@RequestParam(required = false, defaultValue = "1") int cls,
                               Integer unitTeamId, ModelMap modelMap) {
        
        modelMap.put("cls", cls);

        UnitTeam unitTeam = unitTeamMapper.selectByPrimaryKey(unitTeamId);
        modelMap.put("unitTeam", unitTeam);

        if(cls==2){

            return "unit/unitTeamPlan/unitTeamPlan_dispatchCadres";
        }
        
        return "unit/unitTeamPlan/unitTeamPlan_page";
    }
    
    @RequiresPermissions("unitTeam:list")
    @RequestMapping("/unitTeamPlan_data")
    @ResponseBody
    public void unitTeamPlan_data(HttpServletResponse response,
                                  Integer unitTeamId,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  Integer[] ids, // 导出的记录
                                  Integer pageSize, Integer pageNo) throws IOException {
        
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        
        UnitTeamPlanExample example = new UnitTeamPlanExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("start_date desc");
        
        if (unitTeamId != null) {
            criteria.andUnitTeamIdEqualTo(unitTeamId);
        }
        
        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            unitTeamPlan_export(example, response);
            return;
        }
        
        long count = unitTeamPlanMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            
            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitTeamPlan> records = unitTeamPlanMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(unitTeamPlan.class, unitTeamPlanMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
    
    @RequiresPermissions("unitTeam:edit")
    @RequestMapping(value = "/unitTeamPlan_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTeamPlan_au(UnitTeamPlan record, HttpServletRequest request) {
        
        Integer id = record.getId();
        
        if (id == null) {
            
            unitTeamPlanService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加班子下的干部配置方案：%s", record.getId()));
        } else {
            
            unitTeamPlanService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新班子下的干部配置方案：%s", record.getId()));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("unitTeam:list")
    @RequestMapping("/unitTeamPlan_posts")
    public String unitTeamPlan_posts(int id, int type, ModelMap modelMap) {
        
        UnitTeamPlan unitTeamPlan = unitTeamPlanMapper.selectByPrimaryKey(id);
        modelMap.put("unitTeamPlan", unitTeamPlan);
        
        if(type==0) {
            String mainPosts = unitTeamPlan.getMainPosts();
            if (StringUtils.isNotBlank(mainPosts)) {
                modelMap.put("unitPosts", iUnitMapper.getUnitPosts(mainPosts));
            }
        }else {
            String vicePosts = unitTeamPlan.getVicePosts();
            if (StringUtils.isNotBlank(vicePosts)) {
                modelMap.put("unitPosts", iUnitMapper.getUnitPosts(vicePosts));
            }
        }
        return "unit/unitTeamPlan/unitTeamPlan_posts";
    }
    
    @RequiresPermissions("unitTeam:edit")
    @RequestMapping("/unitTeamPlan_au")
    public String unitTeamPlan_au(Integer id, Integer unitTeamId, ModelMap modelMap) {
        
        if (id != null) {
            UnitTeamPlan unitTeamPlan = unitTeamPlanMapper.selectByPrimaryKey(id);
            modelMap.put("unitTeamPlan", unitTeamPlan);
            unitTeamId = unitTeamPlan.getUnitTeamId();
            
            String mainPosts = unitTeamPlan.getMainPosts();
            if(StringUtils.isNotBlank(mainPosts)) {
                Set<Integer> mainPostIds = new HashSet<>();
                for (String _id : mainPosts.split(",")) {
                    mainPostIds.add(Integer.parseInt(_id));
                }
                modelMap.put("mainPostIds", mainPostIds);
            }
    
            String vicePosts = unitTeamPlan.getVicePosts();
            if(StringUtils.isNotBlank(vicePosts)) {
                Set<Integer> vicePostIds = new HashSet<>();
                for (String _id : vicePosts.split(",")) {
                    vicePostIds.add(Integer.parseInt(_id));
                }
                modelMap.put("vicePostIds", vicePostIds);
            }
        }
        
        UnitTeam unitTeam = unitTeamMapper.selectByPrimaryKey(unitTeamId);
        List<UnitPost> unitPosts = unitPostService.list(unitTeam.getUnitId());
        modelMap.put("unitPosts", unitPosts);
    
        return "unit/unitTeamPlan/unitTeamPlan_au";
    }
    
    @RequiresPermissions("unitTeam:edit")
    @RequestMapping("/unitTeamPlan_abolish")
    public String unitTeamPlan_abolish(int id, ModelMap modelMap) {

        UnitTeamPlan unitTeamPlan = unitTeamPlanMapper.selectByPrimaryKey(id);
        modelMap.put("unitTeamPlan", unitTeamPlan);

        return "unit/unitTeamPlan/unitTeamPlan_abolish";
    }
    
    @RequiresPermissions("unitTeam:del")
    @RequestMapping(value = "/unitTeamPlan_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTeamPlan_del(HttpServletRequest request, Integer id) {
        
        if (id != null) {
            
            unitTeamPlanService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除班子下的干部配置方案：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("unitTeam:del")
    @RequestMapping(value = "/unitTeamPlan_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map unitTeamPlan_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {
        
        
        if (null != ids && ids.length > 0) {
            unitTeamPlanService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除班子下的干部配置方案：%s", StringUtils.join(ids, ",")));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    
    public void unitTeamPlan_export(UnitTeamPlanExample example, HttpServletResponse response) {
        
        List<UnitTeamPlan> records = unitTeamPlanMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属班子|100", "起始时间|100", "废止时间|100", "备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            UnitTeamPlan record = records.get(i);
            String[] values = {
                    record.getUnitTeamId() + "",
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "班子下的干部配置方案_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
