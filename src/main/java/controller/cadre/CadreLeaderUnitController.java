package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreLeaderUnit;
import domain.cadre.CadreLeaderUnitExample;
import domain.cadre.CadreLeaderUnitView;
import domain.cadre.CadreLeaderUnitViewExample;
import domain.cadre.CadreView;
import domain.unit.Unit;
import mixin.CadreMixin;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreLeaderUnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreLeaderUnit:list")
    @RequestMapping("/cadreLeaderUnit")
    public String cadreLeaderUnit(HttpServletResponse response,
                              @RequestParam(required = false, defaultValue = "1")Byte cls,
                              Integer cadreId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (cadreId!=null) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
        }

        List<Unit> units = iUnitMapper.findLeaderUnitEscape();
        modelMap.put("units", units);

        return "cadre/cadreLeaderUnit/cadreLeaderUnit_page";
    }
    @RequiresPermissions("cadreLeaderUnit:list")
    @RequestMapping("/cadreLeaderUnit_data")
    @ResponseBody
    public void cadreLeaderUnit_data(HttpServletResponse response,
                                 Integer cadreId,
                                 Integer typeId,
                                 String job,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreLeaderUnitViewExample example = new CadreLeaderUnitViewExample();
        CadreLeaderUnitViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("leader_sort_order desc, unit_sort_order asc, sort_order asc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        long count = cadreLeaderUnitViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreLeaderUnitView> records = cadreLeaderUnitViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(Cadre.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreLeaderUnit:edit")
    @RequestMapping(value = "/cadreLeaderUnit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreLeaderUnit_au(CadreLeaderUnit record, HttpServletRequest request) {

        Integer id = record.getId();

        if (cadreLeaderUnitService.idDuplicate(record.getId(), record.getLeaderId(), record.getUnitId())) {
            return failed("添加重复");
        }

        if (id == null) {
            cadreLeaderUnitService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加校领导单位：%s", record.getId()));
        } else {

            cadreLeaderUnitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新校领导单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    // 未分配校领导的单位
    @RequiresPermissions("cadreLeaderUnit:edit")
    @RequestMapping("/cadreLeaderUnit_escape")
    public String cadreLeaderUnit_escape(ModelMap modelMap) {

        List<Unit> units = iUnitMapper.findLeaderUnitEscape();
        modelMap.put("units", units);

        return "cadre/cadreLeaderUnit/cadreLeaderUnit_escape";
    }

    @RequiresPermissions("cadreLeaderUnit:edit")
    @RequestMapping("/cadreLeaderUnit_au")
    public String cadreLeaderUnit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreLeaderUnit cadreLeaderUnit = cadreLeaderUnitMapper.selectByPrimaryKey(id);
            modelMap.put("cadreLeaderUnit", cadreLeaderUnit);
        }
        return "cadre/cadreLeaderUnit/cadreLeaderUnit_au";
    }

    @RequiresPermissions("cadreLeaderUnit:edit")
    @RequestMapping(value = "/cadreLeaderUnit_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreLeaderUnit_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreLeaderUnitService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "校领导分管工作调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreLeaderUnit:del")
    @RequestMapping(value = "/cadreLeaderUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreLeaderUnit_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreLeaderUnitService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除校领导单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreLeaderUnit:del")
    @RequestMapping(value = "/cadreLeaderUnit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreLeaderUnitService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除校领导单位：%s", new Object[]{ids}));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cadreLeaderUnit_export(CadreLeaderUnitExample example, HttpServletResponse response) {

        List<CadreLeaderUnit> cadreLeaderUnits = cadreLeaderUnitMapper.selectByExample(example);
        long rownum = cadreLeaderUnitMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"校领导","所属单位","类别"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreLeaderUnit cadreLeaderUnit = cadreLeaderUnits.get(i);
            String[] values = {
                        cadreLeaderUnit.getLeaderId()+"",
                                            cadreLeaderUnit.getUnitId()+"",
                                            cadreLeaderUnit.getTypeId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "校领导单位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
