package controller.unit;

import controller.BaseController;
import domain.sys.SysConfig;
import domain.unit.UnitTeam;
import domain.unit.UnitTeamExample;
import domain.unit.UnitTeamExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class UnitTeamController extends BaseController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
/*    @RequiresPermissions("unitTeam:list")
    @RequestMapping("/unitTeam_view")
    public String unitTeam_view(int id, HttpServletResponse response, ModelMap modelMap) {
        
        UnitTeam unitTeam = unitTeamMapper.selectByPrimaryKey(id);
        modelMap.put("unitTeam", unitTeam);
        
        return "unit/unitTeam/unitTeam_view";
    }
    */
    @RequiresPermissions("unitTeam:list")
    @RequestMapping("/unitTeam")
    public String unitTeam(HttpServletResponse response,
                           @RequestParam(required = false, defaultValue = "0") int list,
                           ModelMap modelMap) {
        
        if(list==1){
            return "unit/unitTeam/unitTeamList_page";
        }
        
        return "unit/unitTeam/unitTeam_page";
    }
    
    @RequiresPermissions("unitTeam:list")
    @RequestMapping("/unitTeam_data")
    @ResponseBody
    public void unitTeam_data(HttpServletResponse response,
                               String name,
                               Integer unitId,
                               Integer timeLevel,
                               @RequestDateRange DateRange _deposeTime,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {
        
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        
        UnitTeamExample example = new UnitTeamExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");
        
        if(unitId!=null){
            criteria.andUnitIdEqualTo(unitId);
        }
        if(timeLevel!=null){
            if(timeLevel==2){ // 本学期
                _deposeTime = new DateRange();
                SysConfig sysConfig = sysConfigService.get();
                _deposeTime.setStart(sysConfig.getTermStartDate());
                _deposeTime.setEnd(sysConfig.getTermEndDate());
            }
            criteria.andTimeLevelEqualTo(timeLevel, _deposeTime);
        }
        
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        
        if (export == 1) {
            unitTeam_export(example, response);
            return;
        }
        
        long count = unitTeamMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            
            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitTeam> records = unitTeamMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
    
    @RequiresPermissions("unitTeam:edit")
    @RequestMapping(value = "/unitTeam_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTeam_au(UnitTeam record, HttpServletRequest request) {
        
        Integer id = record.getId();
        
        if (id == null) {
            unitTeamService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加单位行政班子：%s", record.getId()));
        } else {
            
            unitTeamService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新单位行政班子：%s", record.getId()));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    // 设定本学期起止时间
    @RequiresPermissions("unitTeam:edit")
    @RequestMapping("/unitTeam_term")
    public String unitTeam_term(ModelMap modelMap) {
    
        return "unit/unitTeam/unitTeam_term";
    }
    
    @RequiresPermissions("unitTeam:edit")
    @RequestMapping(value = "/unitTeam_term", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTeam_term(
                                @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date termStartDate,
                                @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date termEndDate,
                                HttpServletRequest request) {
        
        SysConfig sysConfig = sysConfigService.get();
        SysConfig record = new SysConfig();
        record.setId(sysConfig.getId());
        record.setTermStartDate(termStartDate);
        record.setTermEndDate(termEndDate);
        
        sysConfigService.updateByPrimaryKeySelective(record);
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("unitTeam:edit")
    @RequestMapping("/unitTeam_au")
    public String unitTeam_au(Integer id, Integer unitId,
                               @RequestParam(required = false, defaultValue = "0") int auType,
                               ModelMap modelMap) {
        
        if (id != null) {
            UnitTeam unitTeam = unitTeamMapper.selectByPrimaryKey(id);
            unitId = unitTeam.getUnitId();
            modelMap.put("unitTeam", unitTeam);
        }
        
        modelMap.put("unit", unitService.findAll().get(unitId));
        
        if (auType == 1) {
            return "unit/unitTeam/unitTeam_expectDepose";
        } else if (auType == 2 || auType == 3) {
            return "unit/unitTeam/unitTeam_dispatchCadre";
        }
        
        return "unit/unitTeam/unitTeam_au";
    }
    
    @RequiresPermissions("unitTeam:edit")
    @RequestMapping(value = "/unitTeam_dispatchCadre", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTeam_dispatchCadre(int unitTeamId, Integer dispatchCadreId,
                                          @RequestParam(required = false, defaultValue = "2") int auType,
                                          @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date dispatchCadreDate,
                                          HttpServletRequest request) {
        
        UnitTeam unitTeam = unitTeamMapper.selectByPrimaryKey(unitTeamId);
        
        UnitTeam record = new UnitTeam();
        record.setId(unitTeamId);
        if (auType == 2) {
            
            if (unitTeam.getDeposeDate() != null && dispatchCadreDate != null
                    && dispatchCadreDate.after(unitTeam.getDeposeDate())) {
                return failed("任职时间或免职时间有误。");
            }
            
            if (dispatchCadreId == null) {
                commonMapper.excuteSql("update unit_team set appoint_dispatch_cadre_id=null where id=" + unitTeamId);
            }
            record.setAppointDispatchCadreId(dispatchCadreId);
            record.setAppointDate(dispatchCadreDate);
        } else if (auType == 3) {
            
            if (unitTeam.getAppointDate() != null && dispatchCadreDate != null
                    && dispatchCadreDate.before(unitTeam.getAppointDate())) {
                return failed("任职时间或免职时间有误。");
            }
            
            if (dispatchCadreId == null) {
                commonMapper.excuteSql("update unit_team set depose_dispatch_cadre_id=null where id=" + unitTeamId);
            }
            record.setDeposeDispatchCadreId(dispatchCadreId);
            record.setDeposeDate(dispatchCadreDate);
        }
        
        
        unitTeamService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新单位行政班子任职/免职信息：%s", unitTeamId));
        
        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("unitTeam:del")
    @RequestMapping(value = "/unitTeam_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTeam_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            unitTeamService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除单位行政班子：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/
    
    @RequiresPermissions("unitTeam:del")
    @RequestMapping(value = "/unitTeam_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {
        
        
        if (null != ids && ids.length > 0) {
            unitTeamService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除单位行政班子：%s", StringUtils.join(ids, ",")));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("unitTeam:changeOrder")
    @RequestMapping(value = "/unitTeam_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitTeam_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {
        
        unitTeamService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "单位行政班子调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    
    public void unitTeam_export(UnitTeamExample example, HttpServletResponse response) {
        
        List<UnitTeam> unitTeams = unitTeamMapper.selectByExample(example);
        long rownum = unitTeamMapper.countByExample(example);
        
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);
        
        String[] titles = {"届数", "名称", "应换届时间", "实际换届时间", "任命时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }
        
        for (int i = 0; i < rownum; i++) {
            
            UnitTeam unitTeam = unitTeams.get(i);
            String[] values = {
                    unitTeam.getSeq() + "",
                    unitTeam.getName(),
                    DateUtils.formatDate(unitTeam.getExpectDeposeDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(unitTeam.getDeposeDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(unitTeam.getAppointDate(), DateUtils.YYYY_MM_DD)
            };
            
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {
                
                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        
        String fileName = "单位行政班子_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
    
    @RequestMapping("/unitTeam_selects")
    @ResponseBody
    public Map unitTeam_selects(Integer pageSize, Integer pageNo, int unitId, String searchStr) throws IOException {
        
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        
        UnitTeamExample example = new UnitTeamExample();
        Criteria criteria = example.createCriteria().andUnitIdEqualTo(unitId);
        example.setOrderByClause("sort_order desc");
        
        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike("%" + searchStr + "%");
        }
        
        long count = unitTeamMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            
            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitTeam> unitTeams = unitTeamMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        
        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != unitTeams && unitTeams.size() > 0) {
            
            for (UnitTeam unitTeam : unitTeams) {
                
                Select2Option option = new Select2Option();
                option.setText(unitTeam.getName());
                option.setId(unitTeam.getId() + "");
                
                options.add(option);
            }
        }
        
        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
