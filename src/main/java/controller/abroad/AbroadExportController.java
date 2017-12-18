package controller.abroad;

import bean.ApproverTypeBean;
import controller.AbroadBaseController;
import controller.global.OpException;
import domain.abroad.ApplySelf;
import domain.cadre.CadreView;
import freemarker.template.TemplateException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;

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
    public void applySelf_export(int applySelfId, HttpServletResponse response) throws IOException, TemplateException {

        ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(applySelfId);
        Integer cadreId = applySelf.getCadreId();

        // 判断一下查看权限++++++++++++++++++++???
        if (ShiroHelper.lackRole(SystemConstants.ROLE_CADREADMIN)) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
                ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();
                if (approverTypeBean == null || !approverTypeBean.getApprovalCadreIdSet().contains(applySelf.getCadreId()))
                    throw new OpException("您没有权限");
            }
        }

        //输出文件
        String filename = "处级干部因私出国（境）证件领取申请表";
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((filename + ".doc").getBytes(), "iso-8859-1"));
        response.setContentType("application/msword;charset=UTF-8");

        abroadExportService.process(applySelfId, response.getWriter());
    }
}
