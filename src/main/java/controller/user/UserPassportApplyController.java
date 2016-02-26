package controller.user;

import controller.BaseController;
import domain.Cadre;
import domain.PassportApply;
import domain.PassportApplyExample;
import domain.SysUser;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/2/25.
 */
@Controller
@RequestMapping("/user")
public class UserPassportApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("cadre")
    @RequestMapping(value = "/passportApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_au(@CurrentUser SysUser loginUser, PassportApply record,String _applyDate, String _expectDate, String _handleDate, HttpServletRequest request) {

        Integer id = record.getId();
        if(StringUtils.isNotBlank(_applyDate)){
            record.setApplyDate(DateUtils.parseDate(_applyDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_expectDate)){
            record.setExpectDate(DateUtils.parseDate(_expectDate, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleDate)) {
            record.setHandleDate(DateUtils.parseDate(_handleDate, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {

            int userId= loginUser.getId();
            Cadre cadre = cadreService.findByUserId(userId);
            record.setCadreId(cadre.getId());

            record.setCreateTime(new Date());
            passportApplyService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "申请办理因私出国证件：%s", record.getId()));
        } else {

            record.setCadreId(null);

            passportApplyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新申请办理因私出国证件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportApply_begin")
    public String passportApply_begin() {

        return "user/passportApply/passportApply_begin";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportApply_select")
    public String passportApply_select() {

        return "user/passportApply/passportApply_select";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportApply_confirm")
    public String passportApply_confirm() {

        return "user/passportApply/passportApply_confirm";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportApply")
    public String passportApply() {

        return "index";
    }

    @RequiresRoles("cadre")
    @RequestMapping("/passportApply_page")
    public String passportApply_page(@CurrentUser SysUser loginUser,
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

        PassportApplyExample example = new PassportApplyExample();
        PassportApplyExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());

        int count = passportApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PassportApply> passportApplys = passportApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("passportApplys", passportApplys);

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

        return "user/passportApply/passportApply_page";
    }
}
