package controller.abroad.user;

import controller.abroad.AbroadBaseController;
import domain.abroad.PassportApply;
import domain.abroad.PassportApplyExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.Logical;
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
import sys.constants.AbroadConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.ContextHelper;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/2/25.
 */
@Controller
@RequestMapping("/user/abroad")
public class UserPassportApplyController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(value = {RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADREINSPECT, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/passportApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_au(int classId, Integer cadreId,  HttpServletRequest request) {

        String ip = ContextHelper.getRealIp();
        PassportApply record = new PassportApply();

        if(cadreId==null || ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
        }

        record.setCadreId(cadreId);
        record.setClassId(classId);
        Date date = new Date();
        record.setApplyDate(date);
        record.setCreateTime(date);
        record.setIp(ip);
        record.setStatus(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_INIT);
        record.setIsDeleted(false);

        passportApplyService.apply(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "申请办理因私出国证件：%s", record.getId()));

        // 短信通知干部本人
        abroadShortMsgService.sendPassportApplySubmitMsgToCadre(record.getId(), request);
        // 短信通知干部管理员
        abroadShortMsgService.sendPassportApplySubmitMsgToCadreAdmin(record.getId(), ip);

        Map<String, Object> success = success(FormUtils.SUCCESS);
        success.put("applyId", record.getId());
        return success;
    }

    @RequiresRoles(value = {RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADREINSPECT}, logical = Logical.OR)
    @RequestMapping(value = "/passportApply_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
        if (id != null) {
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            if(passportApply.getStatus()==AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_INIT
                    && passportApply.getCadreId().intValue() == cadre.getId().intValue()) {
                passportApplyService.del(id);
                logger.info(addLog(LogConstants.LOG_ABROAD, "删除申请办理因私出国证件：%s", id));
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADREINSPECT, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping("/passportApply_begin")
    public String passportApply_begin() {

        return "abroad/user/passportApply/passportApply_begin";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADREINSPECT, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping("/passportApply_select")
    public String passportApply_select() {

        return "abroad/user/passportApply/passportApply_select";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADREINSPECT, RoleConstants.ROLE_CADREADMIN}, logical = Logical.OR)
    @RequestMapping("/passportApply_confirm")
    public String passportApply_confirm(Integer cadreId, ModelMap modelMap) {

        CadreView cadre = null;
        if(cadreId==null || ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)){
            // 确认干部只能提交自己的申请
            cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
        }else{
            cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
        }
        modelMap.put("cadre", cadre);

        return "abroad/user/passportApply/passportApply_confirm";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADREINSPECT}, logical = Logical.OR)
    @RequestMapping("/passportApply")
    public String passportApply(@CurrentUser SysUserView loginUser,
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

        criteria.andIsDeletedEqualTo(false);

        int userId= loginUser.getId();
        CadreView cadre = cadreService.dbFindByUserId(userId);
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

        return "abroad/user/passportApply/passportApply_page";
    }
}
