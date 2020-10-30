package controller.oa;

import domain.oa.OaGrid;
import domain.oa.OaGridExample;
import domain.oa.OaGridExample.Criteria;
import domain.oa.OaGridParty;
import domain.oa.OaGridPartyExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.OaConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/oa")
public class OaGridController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("oaGrid:list")
    @RequestMapping("/oaGrid")
    public String oaGrid(@RequestParam(required = false, defaultValue = "1") Byte cls,//1使用中 2已完成 3删除
                         ModelMap modelMap) {
        modelMap.put("cls", cls);

        return "oa/oaGrid/oaGrid_page";
    }

    @RequiresPermissions("oaGrid:list")
    @RequestMapping("/oaGrid_data")
    @ResponseBody
    public void oaGrid_data(HttpServletResponse response,
                            String name,
                            Byte status,
                            @RequestParam(required = false, defaultValue = "1") Byte cls, //1使用中 2已完成 3删除
                            @RequestParam(required = false, defaultValue = "0") int export,
                            Integer[] ids, // 导出的记录
                            Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaGridExample example = new OaGridExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.trimLike(name));
        }

        if (cls == 3){
            criteria.andStatusEqualTo(OaConstants.OA_GRID_HASDELETED);
        }else{
            criteria.andStatusNotEqualTo(OaConstants.OA_GRID_HASDELETED);
        }

        if (status != null){
            criteria.andStatusEqualTo(status);
        }

        long count = oaGridMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OaGrid> records= oaGridMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(oaGrid.class, oaGridMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions(value={"oaGrid:edit", "oaGrid:release"}, logical = Logical.OR)
    @RequestMapping(value = "/oaGrid_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaGrid_au(OaGrid record,
                            MultipartFile _templateFilePath,
                            HttpServletRequest request) throws IOException {

        Integer id = record.getId();

        record.setCol(StringUtils.upperCase(record.getCol()));
        if (_templateFilePath != null){
            record.setTemplateFilePath(upload(_templateFilePath, "oa_attach_file"));
        }
        if (id == null) {
            record.setStatus(OaConstants.OA_GRID_INIT);
            oaGridService.insertSelective(record);
            logger.info(log( LogConstants.LOG_OA, "添加数据表格报送模板：{0}", record.getId()));
        } else {

            oaGridService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_OA, "更新数据表格报送模板：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions(value={"oaGrid:edit", "oaGrid:release"}, logical = Logical.OR)
    @RequestMapping("/oaGrid_au")
    public String oaGrid_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            OaGrid oaGrid = oaGridMapper.selectByPrimaryKey(id);
            modelMap.put("oaGrid", oaGrid);
        }

        if(!ShiroHelper.isPermitted("oaGrid:edit"))
            return "oa/oaGrid/oaGrid_release_au";

        return "oa/oaGrid/oaGrid_au";
    }

    @RequiresPermissions("oaGrid:del")
    @RequestMapping(value = "/oaGrid_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map oaGrid_batchDel(HttpServletRequest request, Integer[] ids, Byte delete, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            oaGridService.batchDel(ids, delete);
            logger.info(log( LogConstants.LOG_OA, "批量删除数据表格报送模板：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaGrid:del")
    @RequestMapping(value = "/oaGrid_realDel", method = RequestMethod.POST)
    @ResponseBody
    public Map oaGrid_realDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            oaGridService.realDel(ids);
            logger.info(log( LogConstants.LOG_OA, "批量完全删除数据表格报送模板：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaGrid:release")
    @RequestMapping(value = "/oaGrid_release", method = RequestMethod.POST)
    @ResponseBody
    public Map oaGrid_release(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            oaGridService.batchRelease(ids);
            logger.info(log( LogConstants.LOG_OA, "批量发布数据表格报送模板：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaGrid:list")
    @RequestMapping("/oaGrid_preview")
    public String oaGrid_preview(Integer id, Byte summary, ModelMap modelMap) throws IOException, InvalidFormatException {

        if (id != null) {

            OaGrid oaGrid = oaGridMapper.selectByPrimaryKey(id);
            int row = oaGrid.getRow();
            int col = ExcelUtils.toColIndex(oaGrid.getCol());
            String path = springProps.uploadPath + oaGrid.getTemplateFilePath();

            OaGridPartyExample example = new OaGridPartyExample();
            example.createCriteria().andGridIdEqualTo(id).andStatusEqualTo(OaConstants.OA_GRID_PARTY_REPORT);
            List<OaGridParty> oaGridPartyList = oaGridPartyMapper.selectByExample(example);
            String table = null;
            if (summary==0 || (summary == 1 && oaGridPartyList.size() == 0)) {
                table = ExcelToHtmlUtils.toHtml(path, true, row, col);
            }else if (summary == 1){
                List<Map> dataList = iOaTaskMapper.getOaGridPartyData(id);

                File file = new File(path);
                InputStream is = new FileInputStream(file);
                Workbook wb = WorkbookFactory.create(is);

                Sheet sheet = wb.getSheetAt(0);
                String cellLabel = null;
                Row dataRow = null;
                Cell dataCell = null;
                for (Map data : dataList) {
                    cellLabel = data.get("cell_label").toString();
                    dataRow = sheet.getRow(ExcelUtils.getRowIndex(cellLabel) - 1);
                    dataCell = dataRow.getCell(ExcelUtils.getColIndex(cellLabel) - 1);
                    dataCell.setCellValue(data.get("sum").toString());
                }

                sheet.setForceFormulaRecalculation(true);//强制执行sheet的函数
                table = ExcelToHtmlUtils.toHtml(wb, true, row, col);
            }
            modelMap.put("table", table);
        }

        return "oa/oaGrid/oaGrid_preview";
    }

    @RequiresPermissions("oaGrid:list")
    @RequestMapping("/oaGrid_summaryExport")
    public void oaGrid_summaryExport(int id, HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidFormatException {

        OaGrid oaGrid = oaGridMapper.selectByPrimaryKey(id);
        List<Map> dataList = iOaTaskMapper.getOaGridPartyData(id);

        String path = springProps.uploadPath + oaGrid.getTemplateFilePath();
        File file = new File(path);
        InputStream is = new FileInputStream(file);
        Workbook wb = WorkbookFactory.create(is);

        OaGridPartyExample example = new OaGridPartyExample();
        example.createCriteria().andGridIdEqualTo(id).andStatusEqualTo(OaConstants.OA_GRID_PARTY_REPORT);
        List<OaGridParty> oaGridPartyList = oaGridPartyMapper.selectByExample(example);

        if (oaGridPartyList.size() > 0) {

            Sheet sheet = wb.getSheetAt(0);
            String cellLabel = null;
            Row row = null;
            Cell cell = null;
            for (Map data : dataList) {
                cellLabel = data.get("cell_label").toString();
                row = sheet.getRow(ExcelUtils.getRowIndex(cellLabel) - 1);
                cell = row.getCell(ExcelUtils.getColIndex(cellLabel) - 1);
                cell.setCellValue(StringUtils.defaultIfBlank(data.get("sum").toString(), "0"));
            }

            sheet.setForceFormulaRecalculation(true);//强制执行sheet的函数
        }

        ExportHelper.output(wb, oaGrid.getName() + FileUtils.getExtention(path), response);

        logger.info(log( LogConstants.LOG_OA, "下载汇总数据文件：{0}", id));


    }

    @RequiresPermissions("oaGrid:list")
    @RequestMapping("/oaGrid_zipExport")
    public void oaGrid_zipExport(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        OaGrid oaGrid = oaGridMapper.selectByPrimaryKey(id);

        OaGridPartyExample example = new OaGridPartyExample();
        example.createCriteria().andGridIdEqualTo(id).andStatusEqualTo(OaConstants.OA_GRID_PARTY_REPORT);
        List<OaGridParty> oaGridPartyList = oaGridPartyMapper.selectByExample(example);

        Map<String, File> fileMap = new LinkedHashMap<>();
        for (OaGridParty oaGridParty : oaGridPartyList) {

            String excelFilePath = oaGridParty.getExcelFilePath();

            fileMap.put(oaGridParty.getPartyName() + "_" + oaGrid.getName() + FileUtils.getExtention(excelFilePath),
                    new File(springProps.uploadPath + excelFilePath));
        }
        DownloadUtils.addFileDownloadCookieHeader(response);

        DownloadUtils.zip(fileMap, oaGrid.getName(), request, response);
        logger.info(log( LogConstants.LOG_OA, "打包下载党统报送数据文件：{0}", id));

    }

    @RequestMapping("/oaGrid_selects")
    @ResponseBody
    public Map oaGrid_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaGridExample example = new OaGridExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.trimLike(searchStr));
        }

        long count = oaGridMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<OaGrid> records = oaGridMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(OaGrid record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
