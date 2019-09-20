package controller.cet;

import domain.cet.CetUnitProject;
import domain.cet.CetUnitProjectExample;
import domain.cet.CetUnitProjectExample.Criteria;
import domain.unit.Unit;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
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
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
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
@RequestMapping("/cet")
public class CetUnitProjectController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/cetUnitProject")
    public String cetUnitProject(byte addType, ModelMap modelMap) {

        modelMap.put("addType", addType);
        
        return "cet/cetUnitProject/cetUnitProject_page";
    }

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/cetUnitProject_data")
    @ResponseBody
    public void cetUnitProject_data(HttpServletResponse response,
                                byte addType,
                                    Integer year,
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

        CetUnitProjectExample example = new CetUnitProjectExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc, start_date desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        
        // 查看权限（单位管理员和组织部管理员）
        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");

        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            byte upperType = CetConstants.CET_UPPER_TRAIN_UNIT;
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);
            Set<Integer> adminLeaderUserIdSet = cetUpperTrainAdminService.adminLeaderUserIdSet(upperType, currentUserId);
            if (adminUnitIdSet.size() == 0 && adminLeaderUserIdSet.size() == 0) {
                throw new UnauthorizedException();
            }

            SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UNIT);
            criteria.andUnitAdmin(adminUnitIdSet, adminLeaderUserIdSet);
        }else {
            criteria.andIdIsNull();
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUnitProject_export(example, response);
            return;
        }

        long count = cetUnitProjectMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUnitProject> records= cetUnitProjectMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetUnitProject.class, cetUnitProjectMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    //@RequiresPermissions("cetUnitProject:edit")
    @RequestMapping(value = "/cetUnitProject_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitProject_au(CetUnitProject record, HttpServletRequest request) {

        CetUnitProject oldRecord = null;
        Integer id = record.getId();
        byte addType = record.getAddType();
        if (id != null) {
            oldRecord = cetUnitProjectMapper.selectByPrimaryKey(id);
            record.setAddType(null);
        }
        
        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");
            
        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {

            byte upperType = CetConstants.CET_UPPER_TRAIN_UNIT;
            if(!ShiroHelper.isPermitted("cetUpperTrain:edit")) {
                Integer currentUserId = ShiroHelper.getCurrentUserId();
                Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);

                if (id != null) {
                    Integer oldUnitId = oldRecord.getUnitId();
                    if (oldUnitId==null || !adminUnitIdSet.contains(oldUnitId)) {
                        throw new UnauthorizedException(); // 非单位管理员
                    }
                }else{
                    Integer unitId = record.getUnitId();
                    if (unitId == null || !adminUnitIdSet.contains(unitId)) {
                        throw new UnauthorizedException(); // 非单位管理员
                    }
                }

                SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UNIT);
            }
        } else{
            throw new UnauthorizedException();
        }
        
        record.setIsValid(BooleanUtils.isTrue(record.getIsValid()));
        if (id == null) {
            record.setAddTime(new Date());
            record.setAddUserId(ShiroHelper.getCurrentUserId());
            cetUnitProjectService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_CET, "添加二级单位培训班：%s", record.getId()));
        } else {

            cetUnitProjectService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_CET, "更新二级单位培训班：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("cetUnitProject:edit")
    @RequestMapping("/cetUnitProject_au")
    public String cetUnitProject_au(Integer id,
                                    byte addType,  // 2：单位填写  3： 组织部填写
                                    ModelMap modelMap) {

        if (id != null) {
            CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(id);
            modelMap.put("cetUnitProject", cetUnitProject);

            modelMap.put("party", partyService.findAll().get(cetUnitProject.getPartyId()));
            modelMap.put("unit", unitService.findAll().get(cetUnitProject.getUnitId()));

            addType = cetUnitProject.getAddType();
        }
        
        modelMap.put("addType", addType);
        
        byte upperType = CetConstants.CET_UPPER_TRAIN_UNIT;
        
        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) {
            int currentUserId = ShiroHelper.getCurrentUserId();
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(upperType, currentUserId);
            Set<Integer> adminLeaderUserIdSet = cetUpperTrainAdminService.adminLeaderUserIdSet(upperType, currentUserId);
            if(adminLeaderUserIdSet.size()>0){
                // 如果是校领导管理员，从所有的单位中选
                List<Unit> upperUnits = iCetMapper.findUpperUnits(upperType);
                modelMap.put("upperUnits", upperUnits);
            }else if(adminUnitIdSet.size()>0){
                List<Unit> upperUnits = new ArrayList<>();
                for (Integer adminUintId : adminUnitIdSet) {
                    Unit unit = CmTag.getUnit(adminUintId);
                    if(unit!=null){
                        upperUnits.add(unit);
                    }
                }
                modelMap.put("upperUnits", upperUnits);
            }else{
                throw new UnauthorizedException();
            }

        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {
            List<Unit> upperUnits = iCetMapper.findUpperUnits(upperType);
            modelMap.put("upperUnits", upperUnits);
        }else {
            throw new UnauthorizedException();
        }
       
                
        return "cet/cetUnitProject/cetUnitProject_au";
    }

    //@RequiresPermissions("cetUnitProject:del")
    @RequestMapping(value = "/cetUnitProject_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetUnitProject_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetUnitProjectService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_CET, "批量删除二级单位培训班：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

   
    public void cetUnitProject_export(CetUnitProjectExample example, HttpServletResponse response) {

        List<CetUnitProject> records = cetUnitProjectMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","培训班主办方|100","培训结束时间|100","培训开始时间|100","培训班名称|100","培训班类型|100","培训学时|100","参训人数|100","培训地点|100","是否计入年度学习任务|100","备注|100","操作人|100","添加时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUnitProject record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getUnitId()+"",
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            record.getProjectName(),
                            record.getProjectType()+"",
                            record.getPeriod()+"",
                            record.getTotalCount()+"",
                            record.getAddress(),
                            record.getIsValid()+"",
                            record.getRemark(),
                            record.getAddUserId()+"",
                            DateUtils.formatDate(record.getAddTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "二级单位培训班_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetUnitProject_selects")
    @ResponseBody
    public Map cetUnitProject_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetUnitProjectExample example = new CetUnitProjectExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andProjectNameLike("%"+searchStr.trim()+"%");
        }

        long count = cetUnitProjectMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetUnitProject> records = cetUnitProjectMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CetUnitProject record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getProjectName());
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
