package controller.user;

import controller.BaseController;
import domain.Cadre;
import domain.PassportApply;
import domain.PassportApplyExample;
import domain.SysUser;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.utils.FormUtils;
import sys.utils.IpUtils;

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
    public Map do_passportApply_au(int classId, @CurrentUser SysUser loginUser,  HttpServletRequest request) {

        PassportApply record = new PassportApply();

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);

        record.setCadreId(cadre.getId());
        record.setClassId(classId);
        Date date = new Date();
        record.setApplyDate(date);
        record.setCreateTime(date);
        record.setIp(IpUtils.getRealIp(request));
        record.setStatus(SystemConstants.PASSPORT_APPLY_STATUS_INIT);

        passportApplyService.apply(record);
        logger.info(addLog(SystemConstants.LOG_ABROAD, "申请办理因私出国证件：%s", record.getId()));

        Map<String, Object> success = success(FormUtils.SUCCESS);
        success.put("applyId", record.getId());
        return success;
    }

    @RequiresRoles("cadre")
    @RequestMapping(value = "/passportApply_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_del(@CurrentUser SysUser loginUser, HttpServletRequest request, Integer id) {

        int userId= loginUser.getId();
        Cadre cadre = cadreService.findByUserId(userId);
        if (id != null) {
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            if(passportApply.getStatus()==SystemConstants.PASSPORT_APPLY_STATUS_INIT
                    && passportApply.getCadreId().intValue() == cadre.getId().intValue()) {
                passportApplyService.del(id);
                logger.info(addLog(SystemConstants.LOG_ABROAD, "删除申请办理因私出国证件：%s", id));
            }
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

    /*@RequiresRoles("cadre")
    @RequestMapping("/passportApply")
    public String passportApply() {

        return "index";
    }*/

    @RequiresRoles("cadre")
    @RequestMapping("/passportApply_page")
    public String passportApply_page(@CurrentUser SysUser loginUser,
                                     @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_passport_apply") String sort,
                                     @OrderParam(required = false, defaultValue = "desc") String order,
                                     // 1证件列表 2申请证件列表
                                     @RequestParam(defaultValue = "1")Integer type,
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

        modelMap.put("type", type);
        searchStr += "&type=" + type;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "user/passportApply/passportApply_page";
    }
}
