package controller.parttime;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.parttime.ParttimeApply;
import domain.parttime.ParttimeApplyFile;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.parttime.common.ParttimeApprovalResult;
import persistence.parttime.common.ParttimeApproverTypeBean;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m/parttime")
public class MobileParttimeApplyController extends ParttimeBaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("parttimeApply:list")
    //@RequiresPermissions(RoleConstants.PERMISSION_CLAADMIN)
    @RequestMapping("/parttimeApply")
    public String parttimeApply(ModelMap modelMap) {

        return "mobile/index";
    }

    @RequiresPermissions("parttimeApply:list")
    //@RequiresPermissions(RoleConstants.PERMISSION_CLAADMIN)
    @RequestMapping("/parttimeApply_page")
    public String parttimeApply_page(HttpServletResponse response,
                                @SortParam(required = false, defaultValue = "create_time", tableName = "parttime_apply") String sort,
                                @OrderParam(required = false, defaultValue = "desc") String order,
                                Integer cadreId,
                                @RequestDateRange DateRange _applyDate,
                                Byte type, // 出行时间范围
                                // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(通过或不通过)或0：未审批）
                                @RequestParam(required = false, defaultValue = "0") int status,
                                @RequestParam(required = false, defaultValue = "0") int export,
                                Integer pageNo, HttpServletRequest request, ModelMap modelMap) throws IOException {

        modelMap.put("status", status);

        Map map = parttimeApplyService.findApplyList(response, cadreId, _applyDate,
                type, null, null, status, sort, order, pageNo, springProps.mPageSize, export);
        if(map == null) return null; // 导出

        //request.setAttribute("isView", false);

        modelMap.put("parttimeApplys", map.get("applys"));

        String searchStr = "&status=" + status;
        CommonList commonList = (CommonList) map.get("commonList");
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        return "parttime/mobile/parttimeApply_page";
    }

    @RequiresPermissions("parttimeApply:approvalList")
    @RequestMapping("/parttimeApplyList")
    public String parttimeApplyList(ModelMap modelMap) {

        return "mobile/index";
    }

    @RequiresPermissions("parttimeApply:approvalList")
    @RequestMapping("/parttime_ApplyList_page")
    public String parttimeApplyList_page(@CurrentUser SysUserView loginUser, HttpServletResponse response,
                                    Integer cadreId,
                                    @RequestDateRange DateRange _applyDate,
                                    Byte type, // 出行时间范围
                                    // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
                                    @RequestParam(required = false, defaultValue = "0") int status,
                                    Integer pageNo, ModelMap modelMap) {

        modelMap.put("status", status);

        Map map = parttimeApplyService.findApplyList(loginUser.getId(), cadreId, _applyDate, type, status, pageNo, null);
        modelMap.put("parttimeApplys", map.get("applys"));
        String searchStr = "&status=" + status;
        CommonList commonList = (CommonList) map.get("commonList");
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        return "parttime/mobile/parttimeApplyList_page";
    }

    @RequiresPermissions("parttimeApply:view")
    @RequestMapping("/parttimeApply_view")
    public String parttimeApply_view(Integer id, ModelMap modelMap) {

        ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(id);
        Integer cadreId = parttimeApply.getCadreId();

        // 判断一下查看权限++++++++++++++++++++???
        if(!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTTIMEAPPLY_ADMIN)) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            if(cadre.getId().intValue()!=cadreId) {
                //ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ParttimeApproverTypeBean approverTypeBean = parttimeApplyService.getApproverTypeBean(ShiroHelper.getCurrentUserId());
                if (approverTypeBean==null || !approverTypeBean.getApprovalCadreIdSet().contains(parttimeApply.getCadreId()))
                    throw new OpException("您没有权限");
            }
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());

        modelMap.put("sysUser", sysUser);
        modelMap.put("cadre", cadre);
        modelMap.put("parttimeApply", parttimeApply);

        List<ParttimeApplyFile> files = parttimeApplyService.getFiles(parttimeApply.getId());
        modelMap.put("files", files);

        Map<Integer, ParttimeApprovalResult> approvalResultMap = parttimeApplyService.getApprovalResultMap(id);
        modelMap.put("approvalResultMap", approvalResultMap);

        int currentYear = DateUtils.getYear(parttimeApply.getApplyTime());
        modelMap.put("parttimeApplys", parttimeApplyService.getAnnualApplyList(cadreId, currentYear));

        return "parttime/mobile/parttimeApply_view";
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApply_approval")
    public String parttimeApply_approval(Integer id, ModelMap modelMap) {

        ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(id);
        modelMap.put("parttimeApply", parttimeApply);

        return "parttime/mobile/parttimeApply_approval";
    }

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApply_detail")
    public String parttimeApply_detail(Integer id, ModelMap modelMap, HttpServletRequest request) {

        request.setAttribute("isView", true);

        ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(id);
        modelMap.put("parttimeApply", parttimeApply);

        return "parttime/mobile/parttimeApply_detail";
    }


}
