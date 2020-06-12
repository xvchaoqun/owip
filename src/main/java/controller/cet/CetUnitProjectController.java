package controller.cet;

import domain.cet.CetUnitProject;
import domain.cet.CetUnitProjectExample;
import domain.cet.CetUnitProjectExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import sys.tool.paging.CommonList;
import sys.utils.*;

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
    public String cetUnitProject(Byte cls, ModelMap modelMap) {

        if (cls == null) {
            cls = ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN) ? (byte) 1 : 2;
        }

        modelMap.put("cls", cls);

        boolean addPermits = ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN);
        List<Integer> adminPartyIdList = new ArrayList<>();
        if(addPermits) {
            adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            if (adminPartyIdList.size() == 0) {
                throw new UnauthorizedException();
            }
        }
        List<Map> mapList = iCetMapper.unitProjectGroupByStatus(addPermits, adminPartyIdList);
        Map<Byte, Integer> statusCountMap = new HashMap<>();
        for (Map resultMap : mapList) {
            byte status = ((Integer) resultMap.get("status")).byteValue();
            int num = ((Long) resultMap.get("num")).intValue();
            statusCountMap.put(status, num);
        }

        modelMap.put("statusCountMap", statusCountMap);

        return "cet/cetUnitProject/cetUnitProject_page";
    }

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/cetUnitProject_data")
    @ResponseBody
    public void cetUnitProject_data(HttpServletResponse response,
                                    Byte cls,
                                    Integer year,
                                    String projectName,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (cls == null) {
            cls = ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN) ? (byte) 1 : 2;
        }

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

        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        /*if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }*/
        if(StringUtils.isNotBlank(projectName)){
            criteria.andProjectNameLike(SqlUtils.like(projectName));
        }

        if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            if (adminPartyIdList.size() == 0) {
                throw new UnauthorizedException();
            }
            criteria.andCetPartyIdIn(adminPartyIdList);
        }

        if (cls == 1) {
            criteria.andStatusEqualTo(CetConstants.CET_UNIT_PROJECT_STATUS_PASS);
        } else if (cls == 2) {
            criteria.andStatusEqualTo(CetConstants.CET_UNIT_PROJECT_STATUS_UNREPORT);
        } else if (cls == 3) {
            criteria.andStatusEqualTo(CetConstants.CET_UNIT_PROJECT_STATUS_REPORT);
        } else if (cls == 4) {
            criteria.andStatusEqualTo(CetConstants.CET_UNIT_PROJECT_STATUS_UNPASS);
        } else if (cls == 5) {
            criteria.andStatusEqualTo(CetConstants.CET_UNIT_PROJECT_STATUS_DELETE);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUnitProject_export(example, response);
            return;
        }

        long count = cetUnitProjectMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUnitProject> records = cetUnitProjectMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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

    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping(value = "/cetUnitProject_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitProject_au(CetUnitProject record, HttpServletRequest request) {

        CetUnitProject oldRecord = null;
        Integer id = record.getId();
        if (id != null) {
            oldRecord = cetUnitProjectMapper.selectByPrimaryKey(id);
        }

        if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            if (adminPartyIdList.size() == 0) {
                throw new UnauthorizedException();
            }
            if (record.getCetPartyId() != null && !adminPartyIdList.contains(record.getCetPartyId())) {
                throw new UnauthorizedException();
            }
            if (oldRecord != null && oldRecord.getCetPartyId() != null
                    && !adminPartyIdList.contains(oldRecord.getCetPartyId())) {
                throw new UnauthorizedException();
            }

            record.setStatus(CetConstants.CET_UNIT_PROJECT_STATUS_UNREPORT);
            record.setIsValid(false);
        } else {
            record.setStatus(CetConstants.CET_UNIT_PROJECT_STATUS_PASS);
        }

        record.setIsValid(BooleanUtils.isTrue(record.getIsValid()));
        if (id == null) {

            record.setAddTime(new Date());
            record.setAddUserId(ShiroHelper.getCurrentUserId());
            cetUnitProjectService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加二级党委培训班：%s", record.getId()));
        } else {

            record.setStatus(null);
            cetUnitProjectService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新二级党委培训班：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping("/cetUnitProject_au")
    public String cetUnitProject_au(Integer id,
                                    ModelMap modelMap) {

        if (id != null) {
            CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(id);
            modelMap.put("cetUnitProject", cetUnitProject);

            modelMap.put("cetParty", cetPartyMapper.selectByPrimaryKey(cetUnitProject.getCetPartyId()));
            modelMap.put("unit", unitService.findAll().get(cetUnitProject.getUnitId()));
        }

        return "cet/cetUnitProject/cetUnitProject_au";
    }

    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping(value = "/cetUnitProject_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitProject_report(int id, ModelMap modelMap) {

        cetUnitProjectService.report(id);
        logger.info(addLog(LogConstants.LOG_CET, "二级党委培训报送：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping("/cetUnitProject_check")
    public String cetUnitProject_check(@RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (ids != null && ids.length == 1)
            modelMap.put("cetUnitProject", cetUnitProjectMapper.selectByPrimaryKey(ids[0]));

        return "cet/cetUnitProject/cetUnitProject_check";
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping(value = "/cetUnitProject_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitProject_check(HttpServletRequest request,
                                       @RequestParam(value = "ids[]") Integer[] ids,
                                       Boolean pass, Boolean isValid, String backReason, ModelMap modelMap) {

        if (ids != null && ids.length > 0) {

            CetUnitProject record = new CetUnitProject();
            record.setStatus(BooleanUtils.isTrue(pass) ? CetConstants.CET_UNIT_PROJECT_STATUS_PASS
                    : CetConstants.CET_UNIT_PROJECT_STATUS_UNPASS);
            record.setBackReason(backReason);
            record.setIsValid(BooleanUtils.isTrue(isValid));

            CetUnitProjectExample example = new CetUnitProjectExample();
            example.createCriteria().andIdIn(Arrays.asList(ids))
                    .andStatusEqualTo(CetConstants.CET_UNIT_PROJECT_STATUS_REPORT);
            cetUnitProjectMapper.updateByExampleSelective(record, example);

            if (BooleanUtils.isTrue(pass)) {
                commonMapper.excuteSql("update cet_unit_project set back_reason=null where id in (" + StringUtils.join(ids, ",") + ")");
            }
        }

        return success(FormUtils.SUCCESS);
    }

    // 二级党委管理员删除
    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping(value = "/cetUnitProject_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitProject_del(HttpServletRequest request, int id, ModelMap modelMap) {

        cetUnitProjectService.del(id);
        logger.info(addLog(LogConstants.LOG_CET, "删除二级党委培训班：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping(value = "/cetUnitProject_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitProject_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN);

        if (null != ids && ids.length > 0) {
            cetUnitProjectService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除二级党委培训班：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetUnitProject:edit")
    @RequestMapping(value = "/cetUnitProject_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitProject_back(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            cetUnitProjectService.back(ids);
            logger.info(addLog(LogConstants.LOG_CET, "返回报送二级党委培训班：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cetUnitProject_export(CetUnitProjectExample example, HttpServletResponse response) {

        List<CetUnitProject> records = cetUnitProjectMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100", "培训班主办方|100", "培训结束时间|100", "培训开始时间|100", "培训班名称|100", "培训班类型|100", "培训学时|100", "参训人数|100", "培训地点|100", "是否计入年度学习任务|100", "备注|100", "操作人|100", "添加时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUnitProject record = records.get(i);
            String[] values = {
                    record.getYear() + "",
                    record.getUnitId() + "",
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    record.getProjectName(),
                    record.getProjectType() + "",
                    record.getPeriod() + "",
                    record.getTotalCount() + "",
                    record.getAddress(),
                    record.getIsValid() + "",
                    record.getRemark(),
                    record.getAddUserId() + "",
                    DateUtils.formatDate(record.getAddTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "二级党委培训班_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetUnitProject_selects")
    @ResponseBody
    public Map cetUnitProject_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

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

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andProjectNameLike("%" + searchStr.trim() + "%");
        }

        long count = cetUnitProjectMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUnitProject> records = cetUnitProjectMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List options = new ArrayList<>();
        if (null != records && records.size() > 0) {

            for (CetUnitProject record : records) {

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
