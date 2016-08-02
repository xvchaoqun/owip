package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreInfo;
import domain.cadre.CadreInfoExample;
import domain.sys.SysUser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CadreInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreInfo:edit")
    @RequestMapping(value = "/cadreInfo_updateContent", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInfo_updateWork(int cadreId, String content, byte type, HttpServletRequest request) {

        cadreInfoService.insertOrUpdate(cadreId, content, type);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加/更新干部信息采集：%s, %s,, %s", cadreId, content,
                SystemConstants.CADRE_INFO_TYPE_MAP.get(type)));
        return success(FormUtils.SUCCESS);
    }
}
