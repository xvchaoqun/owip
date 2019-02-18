package controller.leader;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.leader.LeaderUnit;
import domain.leader.LeaderUnitExample;
import domain.leader.LeaderUnitView;
import domain.leader.LeaderUnitViewExample;
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
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LeaderUnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("leaderUnit:list")
    @RequestMapping("/leaderUnit")
    public String leaderUnit(HttpServletResponse response,
                              @RequestParam(required = false, defaultValue = "1")Byte cls,
                              Integer cadreId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (cadreId!=null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
        }

        List<Unit> units = iUnitMapper.findLeaderUnitEscape();
        modelMap.put("units", units);

        return "leader/leaderUnit/leaderUnit_page";
    }
    @RequiresPermissions("leaderUnit:list")
    @RequestMapping("/leaderUnit_data")
    @ResponseBody
    public void leaderUnit_data(HttpServletResponse response,
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

        LeaderUnitViewExample example = new LeaderUnitViewExample();
        LeaderUnitViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        long count = leaderUnitViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<LeaderUnitView> records = leaderUnitViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

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

    @RequiresPermissions("leaderUnit:edit")
    @RequestMapping(value = "/leaderUnit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leaderUnit_au(LeaderUnit record, HttpServletRequest request) {

        Integer id = record.getId();

        if (leaderUnitService.idDuplicate(record.getId(), record.getUserId(), record.getUnitId())) {
            return failed("添加重复");
        }

        if (id == null) {
            leaderUnitService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加校级领导单位：%s", record.getId()));
        } else {

            leaderUnitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新校级领导单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    // 未分配校级领导的单位
    @RequiresPermissions("leaderUnit:edit")
    @RequestMapping("/leaderUnit_escape")
    public String leaderUnit_escape(ModelMap modelMap) {

        List<Unit> units = iUnitMapper.findLeaderUnitEscape();
        modelMap.put("units", units);

        return "leader/leaderUnit/leaderUnit_escape";
    }

    @RequiresPermissions("leaderUnit:edit")
    @RequestMapping("/leaderUnit_au")
    public String leaderUnit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            LeaderUnit leaderUnit = leaderUnitMapper.selectByPrimaryKey(id);
            modelMap.put("leaderUnit", leaderUnit);
        }
        return "leader/leaderUnit/leaderUnit_au";
    }

    @RequiresPermissions("leaderUnit:edit")
    @RequestMapping(value = "/leaderUnit_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leaderUnit_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        leaderUnitService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "校级领导分管工作调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("leaderUnit:del")
    @RequestMapping(value = "/leaderUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leaderUnit_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            leaderUnitService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除校级领导单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("leaderUnit:del")
    @RequestMapping(value = "/leaderUnit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            leaderUnitService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除校级领导单位：%s", new Object[]{ids}));
        }

        return success(FormUtils.SUCCESS);
    }

    public void leaderUnit_export(LeaderUnitExample example, HttpServletResponse response) {

        List<LeaderUnit> leaderUnits = leaderUnitMapper.selectByExample(example);
        long rownum = leaderUnitMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"校级领导","所属单位","类别"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            LeaderUnit leaderUnit = leaderUnits.get(i);
            String[] values = {
                        leaderUnit.getUserId()+"",
                                            leaderUnit.getUnitId()+"",
                                            leaderUnit.getTypeId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "校级领导单位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
