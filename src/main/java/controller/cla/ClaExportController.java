package controller.cla;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cla.ClaApply;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.cla.common.ClaApproverTypeBean;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lm on 2017/12/18.
 */
@Controller
@RequestMapping("/cla")
public class ClaExportController extends ClaBaseController {

    // 导出
    @RequiresPermissions("claApply:view")
    @RequestMapping("/claApply_export")
    public void claApply_export(int applyId, HttpServletResponse response) throws IOException, TemplateException {

        ClaApply claApply = claApplyMapper.selectByPrimaryKey(applyId);
        Integer cadreId = claApply.getCadreId();

        // 判断一下查看权限++++++++++++++++++++???
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CLAADMIN)) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            if (cadre.getId().intValue() != cadreId) {
                //ShiroUser shiroUser = ShiroHelper.getShiroUser();
                ClaApproverTypeBean approverTypeBean = claApplyService.getApproverTypeBean(ShiroHelper.getCurrentUserId());
                if (approverTypeBean == null || !approverTypeBean.getApprovalCadreIdSet().contains(claApply.getCadreId()))
                    throw new OpException("您没有权限");
            }
        }

        //输出文件
        /*String filename = "处级干部因私出国（境）证件领取申请表";
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");

        claExportService.process(applyId, response.getWriter());*/
    }
}
