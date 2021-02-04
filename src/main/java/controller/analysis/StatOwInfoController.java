package controller.analysis;

import bean.StatByteBean;
import controller.BaseController;
import domain.cet.CetTraineeType;
import domain.party.Party;
import domain.party.PartyExample;
import domain.sys.StudentInfoExample;
import domain.sys.SysUser;
import domain.sys.SysUserExample;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.analysis.StatOwInfoMapper;
import service.analysis.StatOwInfoService;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Controller
public class StatOwInfoController extends BaseController {

    @Autowired
    private StatOwInfoService statOwInfoService;

    @RequiresPermissions("statOwInfo:list")
    @RequestMapping("/statOwInfo")
    public String statOwInfo(@RequestParam(required = false, defaultValue = "1") Byte cls,
                             @RequestParam(required = false, defaultValue = "1") Byte export,
                             ModelMap modelMap, HttpServletResponse response) {
        modelMap.put("cls", cls);
        Date now = new Date();
        int year = DateUtils.getYear(now);
        int month = DateUtils.getMonth(now);
        modelMap.put("year", year);
        modelMap.put("month", month);
        DecimalFormat df = new DecimalFormat("0.00");

        String schoolName = CmTag.getSysConfig().getSchoolName();
        modelMap.put("schoolName", schoolName);

        if (cls == 1) {
            //Map<缓存时间字符串, 数据>
            Map cacheMap= statOwInfoService.getYjsSchool(cls, df);
            modelMap.putAll(cacheMap);
            if (export == 2) {
                XSSFWorkbook wb = statOwInfoService.statOnInfoExport(modelMap);
                String filename = "全校研究生队伍党员信息统计.xlsx";
                ExportHelper.output(wb, filename, response);
                return null;
            }
        } else if (cls == 2) {
            List<String> columns = new ArrayList<>();
            columns.add("入党申请人");
            columns.add("入党积极分子");
            columns.add("发展对象");
            columns.add("正式党员");
            columns.add("预备党员");
            columns.add("普通学生");
            columns.add("合计");
            columns.add("培养情况占比");
            columns.add("党员占比");
            modelMap.put("columns", columns);

            List<Map<String, String>> data = statOwInfoService.getYjsPartyInfo(df);
            modelMap.put("data", data);
            if (export == 2) {
                XSSFWorkbook wb = statOwInfoService.statOnPartyInfoExport(modelMap);
                String filename = String.format("各二级党组织研究生队伍党员信息统计.xlsx");
                ExportHelper.output(wb, filename, response);
                return null;
            }
            return "analysis/statOwInfo/ow_yjs_party_page";
        }
        return "analysis/statOwInfo/ow_yjs_school_page";
    }

    @RequiresPermissions("statOwInfo:list")
    @RequestMapping("/flushStatOwInfoCache")
    @ResponseBody
    public Map flushStatOwInfoCache(){

        CmTag.clearCache("statOwInfo", null);

        return success();
    }
}
