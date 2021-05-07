package controller.parttime;

import domain.parttime.ParttimeApply;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.utils.DownloadUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ParttimeExportController extends ParttimeBaseController {
    // 导出
    @RequiresPermissions("parttimeApply:view")
    @RequestMapping(value = "/parttime/parttimeApply_export")
    public void parttimeApply_export(int applyId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        //输出文件
        String filename = String.format("兼职申报申请");
        DownloadUtils.addFileDownloadCookieHeader(response);
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");
        parttimeExportService.getExportWord(applyId,response.getWriter());

    }
}
