package controller.analysis;

import controller.BaseController;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.analysis.StatOwInfoService;
import service.global.CacheHelper;
import sys.tags.CmTag;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@Controller
public class StatOwInfoController extends BaseController {

    @Autowired
    private StatOwInfoService statOwInfoService;
    @Autowired
    private CacheHelper cacheHelper;

    @RequiresPermissions("statSummary:menu")
    @RequestMapping("/statOwInfo")
    public String statOwInfo(@RequestParam(required = false, defaultValue = "1") Byte cls,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             ModelMap modelMap, HttpServletResponse response) throws IOException {
        modelMap.put("cls", cls);
        DecimalFormat df = new DecimalFormat("0.00");

        String schoolName = CmTag.getSysConfig().getSchoolName();
        modelMap.put("schoolName", schoolName);

        if (cls == 1) {
            Map cacheMap= statOwInfoService.getOwYjsInfo(cls, df);
            modelMap.putAll(cacheMap);
            if (export == 1) {
                XSSFWorkbook wb = statOwInfoService.statOwYjsInfoExport(modelMap);
                String filename = "全校研究生队伍党员信息统计.xlsx";
                ExportHelper.output(wb, filename, response);
                return null;
            }
        } else if (cls == 2) {//各二级单位本科生党员信息统计
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

            Map cacheMap = statOwInfoService.getPartyYjsInfo(cls, df);
            modelMap.putAll(cacheMap);
            if (export == 1) {
                XSSFWorkbook wb = statOwInfoService.statPartyYjsInfoExport(modelMap);
                String filename = String.format("各二级党组织研究生队伍党员信息统计.xlsx");
                ExportHelper.output(wb, filename, response);
                return null;
            }
            return "analysis/statOwInfo/stat_party_yjs_page";
        }else if (cls==3){
            Map cacheMap = statOwInfoService.getOwBksInfo(cls,modelMap);
            modelMap.putAll(cacheMap);
            if(export == 1){
                XSSFWorkbook wb = statOwInfoService.statOwBksInfoExport(modelMap);
                String fileName = sysConfigService.getSchoolName()
                        + "本科生党员信息统计表";
                ExportHelper.output(wb, fileName + ".xlsx",response);
                return null;
            }
            modelMap.put("cls",cls);
            return "analysis/statOwInfo/stat_ow_Bks_page";
        }else if(cls==4){
            Map cacheMap = statOwInfoService.getPartyBksInfo(cls,modelMap);
            modelMap.putAll(cacheMap);
            if(export == 1){
                XSSFWorkbook wb = statOwInfoService.statPartyBksInfoExport(modelMap);
                String fileName = sysConfigService.getSchoolName()
                        + "二级党委本科生队伍党员信息分析";
                ExportHelper.output(wb, fileName + ".xlsx",response);
                return null;
            }

            return "analysis/statOwInfo/stat_party_Bks_page";
        }
        return "analysis/statOwInfo/stat_ow_yjs_page";
    }

    @RequiresPermissions("statSummary:menu")
    @RequestMapping("/flushStatOwInfoCache")
    @ResponseBody
    public Map flushStatOwInfoCache(Byte cls){

        cacheHelper.clearCache("statOwInfo", cls);
        return success();
    }
}
