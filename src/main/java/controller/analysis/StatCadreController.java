package controller.analysis;

import controller.BaseController;
import domain.cadre.CadreEdu;
import domain.dispatch.Dispatch;
import mixin.DispatchMixin;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class StatCadreController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("statCadre:list")
    @RequestMapping("/stat_cadre")
    public String stat_cadre(String type,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             ModelMap modelMap, HttpServletResponse response) throws IOException {

        if (export == 1) {
            XSSFWorkbook wb = statCadreService.toXlsx();

            String fileName = "北京师范大学中层领导干部情况统计表（" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "）";
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return null;
        }

        Map<String, List> rs = statCadreService.stat(type);
        modelMap.put("rs", rs);

        return "analysis/cadre/stat_cadre_page";
    }

    // 分类信息统计
    @RequiresPermissions("statCadreCategory:list")
    @RequestMapping("/stat_cadre_category")
    public String stat_cadre_category(int type, Integer cadreId, ModelMap modelMap) throws IOException {

        if (cadreId != null) {
            modelMap.put("cadre", cadreService.findAll().get(cadreId));
        }

        /*switch (type){
            case 1:
                List<CadreEdu> cadreEdus = iCadreMapper.findCadreEdus(SystemConstants.CADRE_SCHOOL_TYPE_ABROAD);
                break;
        }
        */
        return "analysis/cadre/stat_cadre_category";
    }

    @RequiresPermissions("statCadreCategory:list")
    @RequestMapping("/stat_cadre_category_data")
    public void stat_cadre_category_data(int type, HttpServletResponse response,
                                         Integer cadreId,
                                         Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }

        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        RowBounds rowBounds = new RowBounds((pageNo - 1) * pageSize, pageSize);

        int count = 0;
        List records = new ArrayList<>();

        switch (type) {
            case 1:
                count = iCadreMapper.countCadreEdus(SystemConstants.CADRE_SCHOOL_TYPE_ABROAD);
                records = iCadreMapper.findCadreEdus(SystemConstants.CADRE_SCHOOL_TYPE_ABROAD, rowBounds);
                break;
        }

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
}
