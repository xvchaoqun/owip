package controller.cadre;

import controller.BaseController;
import domain.CadreTutor;
import domain.CadreTutorExample;
import domain.CadreTutorExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CadreTutorController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreTutor:list")
    @RequestMapping("/cadreTutor")
    public String cadreTutor() {

        return "index";
    }
    @RequiresPermissions("cadreTutor:list")
    @RequestMapping("/cadreTutor_page")
    public String cadreTutor_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreTutorExample example = new CadreTutorExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreTutor_export(example, response);
            return null;
        }

        int count = cadreTutorMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreTutor> cadreTutors = cadreTutorMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadreTutors", cadreTutors);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "cadre/cadreTutor/cadreTutor_page";
    }

    @RequiresPermissions("cadreTutor:edit")
    @RequestMapping(value = "/cadreTutor_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTutor_au(CadreTutor record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cadreTutorService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加导师信息：%s", record.getId()));
        } else {

            cadreTutorService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新导师信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTutor:edit")
    @RequestMapping("/cadreTutor_au")
    public String cadreTutor_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreTutor cadreTutor = cadreTutorMapper.selectByPrimaryKey(id);
            modelMap.put("cadreTutor", cadreTutor);
        }
        return "cadre/cadreTutor/cadreTutor_au";
    }

    @RequiresPermissions("cadreTutor:del")
    @RequestMapping(value = "/cadreTutor_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTutor_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreTutorService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除导师信息：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTutor:del")
    @RequestMapping(value = "/cadreTutor_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreTutorService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除导师信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTutor:changeOrder")
    @RequestMapping(value = "/cadreTutor_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTutor_changeOrder(Integer id, int cadreId,  Integer addNum, HttpServletRequest request) {

        cadreTutorService.changeOrder(id, cadreId, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "导师信息调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreTutor_export(CadreTutorExample example, HttpServletResponse response) {

        List<CadreTutor> cadreTutors = cadreTutorMapper.selectByExample(example);
        int rownum = cadreTutorMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"导师姓名","所在单位及职务（职称）","类型"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreTutor cadreTutor = cadreTutors.get(i);
            String[] values = {
                        cadreTutor.getName(),
                                            cadreTutor.getTitle(),
                                            cadreTutor.getType()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "导师信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/cadreTutor_selects")
    @ResponseBody
    public Map cadreTutor_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreTutorExample example = new CadreTutorExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = cadreTutorMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CadreTutor> cadreTutors = cadreTutorMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cadreTutors && cadreTutors.size()>0){

            for(CadreTutor cadreTutor:cadreTutors){

                Select2Option option = new Select2Option();
                option.setText(cadreTutor.getName());
                option.setId(cadreTutor.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
