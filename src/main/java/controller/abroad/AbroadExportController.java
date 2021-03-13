package controller.abroad;

import controller.global.OpException;
import domain.abroad.ApplySelf;
import domain.cadre.CadreView;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.abroad.common.ApproverTypeBean;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.utils.DownloadUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lm on 2017/12/18.
 */
@Controller
@RequestMapping("/abroad")
public class AbroadExportController extends AbroadBaseController {

    // 导出
    @RequiresPermissions("applySelf:view")
    @RequestMapping("/applySelf_export")
    public void applySelf_export(int applySelfId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        Integer cadreId = applySelf.getCadreId();

        // 判断一下查看权限++++++++++++++++++++???
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_ABROADADMIN)) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                //ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ApproverTypeBean approverTypeBean = applySelfService.getApproverTypeBean(ShiroHelper.getCurrentUserId());
                if (approverTypeBean == null || !approverTypeBean.getApprovalCadreIdSet().contains(applySelf.getCadreId()))
                    throw new OpException("您没有权限");
            }
        }

        //输出文件
        String filename = "处级干部因私出国（境）证件领取申请表";
        response.reset();
        DownloadUtils.addFileDownloadCookieHeader(response);

        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");

        abroadExportService.process(applySelfId, response.getWriter());
    }
}
