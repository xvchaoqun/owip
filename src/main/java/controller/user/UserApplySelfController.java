package controller.user;

import controller.BaseController;
import domain.*;
import domain.ApplySelfExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserApplySelfController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("cadre")
    @RequestMapping("/applySelf")
    public String applySelf() {

        return "index";
    }
    @RequiresRoles("cadre")
    @RequestMapping("/applySelf_page")
    public String applySelf_page(@CurrentUser SysUser loginUser,
                                 @RequestParam(required = false, defaultValue = "create_time") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySelfExample example = new ApplySelfExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());

        int count = applySelfMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelf> applySelfs = applySelfMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("applySelfs", applySelfs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "user/applySelf/applySelf_page";
    }

    @RequiresRoles("cadre")
    @RequestMapping(value = "/applySelf_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_applySelf_au(@CurrentUser SysUser loginUser, ApplySelf record, String _applyDate, String _startDate, String _endDate,  HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_applyDate)){
            record.setApplyDate(DateUtils.parseDate(_applyDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_startDate)){
            record.setStartDate(DateUtils.parseDate(_startDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endDate)){
            record.setEndDate(DateUtils.parseDate(_endDate, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {

            int userId= loginUser.getId();
            Cadre cadre = cadreService.findByUserId(userId);
            record.setCadreId(cadre.getId());

            record.setCreateTime(new Date());
            applySelfService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加因私出国申请：%s", record.getId()));
        } else {
            record.setCadreId(null);

            applySelfService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新因私出国申请：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("cadre")
    @RequestMapping("/applySelf_au")
    public String applySelf_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            modelMap.put("applySelf", applySelf);
        }

        List<String> countryList = new ArrayList<>();
        Map<Integer, Country> countryMap = countryService.findAll();
        for (Country country : countryMap.values()) {
            countryList.add(country.getCninfo());
        }
        modelMap.put("countryList", JSONUtils.toString(countryList));

        return "user/applySelf/applySelf_au";
    }
}
