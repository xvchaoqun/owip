package controller.crs;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.SystemConstants;

/**
 * Created by lm on 2017/10/26.
 */
@Controller
public class CrsLogController {

    @RequiresPermissions("crsLog:list")
    @RequestMapping("/crsLog")
    public String crsLog(){

        return "forward:/sysApprovalLog?type=" + SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT +"&displayType=2";
    }
}
