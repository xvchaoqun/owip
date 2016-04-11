package controller.cadre;

import controller.BaseController;
import domain.*;
import domain.CadreFamliyExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreFamliyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreFamliy:list")
    @RequestMapping("/cadreFamliy")
    public String cadreFamliy() {

        return "index";
    }
    @RequiresPermissions("cadreFamliy:list")
    @RequestMapping("/cadreFamliy_page")
    public String cadreFamliy_page(HttpServletResponse response,
                                    int cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export, ModelMap modelMap) {

        List<CadreFamliy> cadreFamliys = new ArrayList<>();
        {
            CadreFamliyExample example = new CadreFamliyExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadreFamliys = cadreFamliyMapper.selectByExample(example);
        }
        modelMap.put("cadreFamliys", cadreFamliys);


        Map<Integer, CadreFamliy> cadreFamliyMap = new HashMap<>();
        for (CadreFamliy cadreFamliy : cadreFamliys) {
            cadreFamliyMap.put(cadreFamliy.getId(), cadreFamliy);
        }
        modelMap.put("cadreFamliyMap", cadreFamliyMap);

        List<CadreFamliyAbroad> cadreFamliyAbroads = new ArrayList<>();
        {
            CadreFamliyAbroadExample example = new CadreFamliyAbroadExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadreFamliyAbroads = cadreFamliyAbroadMapper.selectByExample(example);
        }
        modelMap.put("cadreFamliyAbroads", cadreFamliyAbroads);

        return "cadre/cadreFamliy/cadreFamliy_page";
    }

    @RequiresPermissions("cadreFamliy:edit")
    @RequestMapping(value = "/cadreFamliy_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamliy_au(CadreFamliy record, String _birthday, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_birthday)){
            record.setBirthday(DateUtils.parseDate(_birthday, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            cadreFamliyService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加家庭成员信息：%s", record.getId()));
        } else {

            cadreFamliyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新家庭成员信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreFamliy:edit")
    @RequestMapping("/cadreFamliy_au")
    public String cadreFamliy_au(Integer id, int cadreId, ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        if (id != null) {
            CadreFamliy cadreFamliy = cadreFamliyMapper.selectByPrimaryKey(id);
            modelMap.put("cadreFamliy", cadreFamliy);
        }
        return "cadre/cadreFamliy/cadreFamliy_au";
    }

    @RequiresPermissions("cadreFamliy:del")
    @RequestMapping(value = "/cadreFamliy_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreFamliy_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreFamliyService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除家庭成员信息：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreFamliy:del")
    @RequestMapping(value = "/cadreFamliy_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreFamliyService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除家庭成员信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cadreFamliy_export(CadreFamliyExample example, HttpServletResponse response) {

        List<CadreFamliy> cadreFamliys = cadreFamliyMapper.selectByExample(example);
        int rownum = cadreFamliyMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"称谓","姓名","政治面貌","工作单位及职务"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreFamliy cadreFamliy = cadreFamliys.get(i);
            String[] values = {
                        cadreFamliy.getTitle()+"",
                                            cadreFamliy.getRealname(),
                                            cadreFamliy.getPoliticalStatus()+"",
                                            cadreFamliy.getUnit()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "家庭成员信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/cadreFamliy_selects")
    @ResponseBody
    public Map cadreFamliy_selects(Integer pageSize, Integer pageNo, int cadreId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreFamliyExample example = new CadreFamliyExample();
        Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andRealnameLike("%" + searchStr + "%");
        }

        int count = cadreFamliyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CadreFamliy> cadreFamliys = cadreFamliyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cadreFamliys && cadreFamliys.size()>0){

            for(CadreFamliy cadreFamliy:cadreFamliys){

                Select2Option option = new Select2Option();
                option.setText(cadreFamliy.getRealname());
                option.setId(cadreFamliy.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
