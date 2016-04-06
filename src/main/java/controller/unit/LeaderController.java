package controller.unit;

import controller.BaseController;
import domain.*;
import domain.LeaderExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.CadreMixin;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LeaderController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("leader:list")
    @RequestMapping("/leader")
    public String leader() {

        return "index";
    }
    @RequiresPermissions("leader:list")
    @RequestMapping("/leader_page")
    public String leader_page(HttpServletResponse response,
                              @SortParam(required = false, defaultValue = "sort_order", tableName = "base_leader") String sort,
                              @OrderParam(required = false, defaultValue = "desc") String order,
                              Integer cadreId,
                              Integer typeId,
                              String job,
                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer pageSize, Integer pageNo, ModelMap modelMap) {
        if (cadreId!=null) {
            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            if (cadre != null) {
                SysUser sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }

        return "unit/leader/leader_page";
    }
    @RequiresPermissions("leader:list")
    @RequestMapping("/leader_data")
    @ResponseBody
    public void leader_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "base_leader") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
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

        LeaderExample example = new LeaderExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (StringUtils.isNotBlank(job)) {
            criteria.andJobLike("%" + job + "%");
        }

        if (export == 1) {
            leader_export(example, response);
            return ;
        }

        int count = leaderMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Leader> Leaders = leaderMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", Leaders);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(Cadre.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("leader:edit")
    @RequestMapping(value = "/leader_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leader_au(Leader record, ModelMap modelMap, HttpServletRequest request) {

        Integer id = record.getId();

        if (leaderService.idDuplicate(id, record.getCadreId(), record.getTypeId())) {
            return failed("添加重复");
        }

        if (id == null) {
            leaderService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加校领导：%s", record.getId()));
        } else {

            leaderService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新校领导：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("leader:edit")
    @RequestMapping("/leader_au")
    public String leader_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Leader leader = leaderMapper.selectByPrimaryKey(id);
            modelMap.put("leader", leader);
            Cadre cadre = cadreService.findAll().get(leader.getCadreId());
            modelMap.put("cadre", cadre);
            SysUser sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }
        return "unit/leader/leader_au";
    }

    @RequiresPermissions("leader:del")
    @RequestMapping(value = "/leader_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leader_del(Integer id, HttpServletRequest request) {

        if (id != null) {

            leaderService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除校领导：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("leader:del")
    @RequestMapping(value = "/leader_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            leaderService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除校领导：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("leader:changeOrder")
    @RequestMapping(value = "/leader_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leader_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        leaderService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "校领导调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void leader_export(LeaderExample example, HttpServletResponse response) {

        List<Leader> leaders = leaderMapper.selectByExample(example);
        int rownum = leaderMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"校领导","类别","分管工作"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            Leader leader = leaders.get(i);
            String[] values = {
                        leader.getCadreId()+"",
                                            leader.getTypeId()+"",
                                            leader.getJob()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "校领导_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequiresPermissions("leader:unit")
    @RequestMapping("/leader_unit")
    public String unit_history(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            LeaderUnitExample example = new LeaderUnitExample();
            LeaderUnitExample.Criteria criteria = example.createCriteria().andLeaderIdEqualTo(id);
            example.setOrderByClause(String.format("%s %s", "id", "desc"));

            int count = leaderUnitMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<LeaderUnit> leaderUnits = leaderUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("leaderUnits", leaderUnits);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (id!=null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);

            Leader leader = leaderMapper.selectByPrimaryKey(id);
            modelMap.put("leader", leader);
        }

        return "unit/leader/leader_unit";
    }
}
