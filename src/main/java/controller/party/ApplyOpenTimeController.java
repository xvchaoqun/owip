package controller.party;

import controller.BaseController;
import domain.ApplyOpenTime;
import domain.ApplyOpenTimeExample;
import domain.ApplyOpenTimeExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class ApplyOpenTimeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applyOpenTime:list")
    @RequestMapping("/applyOpenTime")
    public String applyOpenTime() {

        return "index";
    }
    @RequiresPermissions("applyOpenTime:list")
    @RequestMapping("/applyOpenTime_page")
    public String applyOpenTime_page(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_apply_open_time") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Byte type,
                                    Integer branchId,
                                    String _startTime,
                                    String _endTime,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplyOpenTimeExample example = new ApplyOpenTimeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        int count = applyOpenTimeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplyOpenTime> ApplyOpenTimes = applyOpenTimeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("applyOpenTimes", ApplyOpenTimes);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (type!=null) {
            searchStr += "&type=" + type;
        }
        if (branchId!=null) {
            searchStr += "&branchId=" + branchId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("APPLY_STAGE_MAP", SystemConstants.APPLY_STAGE_MAP);

        return "party/applyOpenTime/applyOpenTime_page";
    }

    @RequiresPermissions("applyOpenTime:edit")
    @RequestMapping(value = "/applyOpenTime_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applyOpenTime_au(ApplyOpenTime record, String _startTime, String _endTime,
                                   ModelMap modelMap, HttpServletRequest request) {

        Integer id = record.getId();

        record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD));
        record.setIsGlobal((record.getIsGlobal() == null) ? false : record.getIsGlobal());

        if (id == null) {
            applyOpenTimeService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "添加党员申请开放时间段：%s", record.getId()));
        } else {

            applyOpenTimeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "更新党员申请开放时间段：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applyOpenTime:edit")
    @RequestMapping("/applyOpenTime_au")
    public String applyOpenTime_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplyOpenTime applyOpenTime = applyOpenTimeMapper.selectByPrimaryKey(id);
            modelMap.put("applyOpenTime", applyOpenTime);
        }
        modelMap.put("APPLY_STAGE_MAP", SystemConstants.APPLY_STAGE_MAP);
        modelMap.put("partyMap", partyService.findAll());

        return "party/applyOpenTime/applyOpenTime_au";
    }

    @RequiresPermissions("applyOpenTime:del")
    @RequestMapping(value = "/applyOpenTime_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applyOpenTime_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            applyOpenTimeService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "删除党员申请开放时间段：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("applyOpenTime:del")
    @RequestMapping(value = "/applyOpenTime_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            applyOpenTimeService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_MEMBER_APPLY, "批量删除党员申请开放时间段：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
