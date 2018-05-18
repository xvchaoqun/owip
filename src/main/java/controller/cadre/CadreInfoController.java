package controller.cadre;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CadreInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreInfo:edit")
    @RequestMapping(value = "/cadreInfo_updateContent", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInfo_updateContent(int cadreId, String content, byte type, HttpServletRequest request) {

        cadreInfoService.insertOrUpdate(cadreId, content, type);
        logger.info(addLog(LogConstants.LOG_ADMIN, "添加/更新干部信息采集：%s, %s, %s", cadreId, content,
                CadreConstants.CADRE_INFO_TYPE_MAP.get(type)));
        return success(FormUtils.SUCCESS);
    }
}
