package controller.parttime;

import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ParttimeExportController extends ParttimeBaseController {
    // 导出
    @RequiresPermissions("parttimeApply:view")
    @RequestMapping("/parttimeApply_export")
    public void claApply_export(int applyId, HttpServletResponse response, HttpServletRequest request) throws IOException, TemplateException {


    }
}
