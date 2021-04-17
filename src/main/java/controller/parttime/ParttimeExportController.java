package controller.parttime;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.parttime.ParttimeApply;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.parttime.common.ParttimeApproverTypeBean;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.utils.DownloadUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ParttimeExportController extends ParttimeBaseController {
    // 导出
    @RequiresPermissions("parttimeApply:view")
    @RequestMapping("/parttimeApply_export")
    public void claApply_export(int applyId, HttpServletResponse response, HttpServletRequest request) throws IOException, TemplateException {

        ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(applyId);
        Integer cadreId = parttimeApply.getCadreId();

        // 判断一下查看权限++++++++++++++++++++???
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_CLAADMIN)) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                //ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ParttimeApproverTypeBean approverTypeBean = parttimeApplyService.getApproverTypeBean(ShiroHelper.getCurrentUserId());
                if (approverTypeBean == null || !approverTypeBean.getApprovalCadreIdSet().contains(parttimeApply.getCadreId()))
                    throw new OpException("您没有权限");
            }
        }

        //输出文件
        String filename = "处级干部因私出国（境）证件领取申请表";
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");
        parttimeExportService.process(applyId, response.getWriter());
    }
}
