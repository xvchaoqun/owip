package controller.oa;

import domain.oa.*;
import domain.oa.OaGridPartyExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
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
import sys.constants.LogConstants;
import sys.constants.OaConstants;
import sys.helper.PartyHelper;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/oa")
public class OaGridPartyController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("oaGridParty:list")
    @RequestMapping("/oaGridParty")
    public String oaGridParty(Integer partyId,
                              @RequestParam(required = false, defaultValue = "0") Byte cls,//状态，0未填报 1暂存 2已报送 3已退回
                              ModelMap modelMap) {
        modelMap.put("cls", cls);
        if (partyId != null){
            modelMap.put("party", partyMapper.selectByPrimaryKey(partyId));
        }
        List<OaGrid> oaGridList = oaGridMapper.selectByExample(new OaGridExample());
        modelMap.put("oaGridList", oaGridList);

        OaGridPartyExample example = new OaGridPartyExample();
        Criteria criteria = example.createCriteria();
        criteria.addPermits(loginUserService.adminPartyIdList());

        int totalNum = (int) oaGridPartyMapper.countByExample(example);

        criteria.andStatusEqualTo(OaConstants.OA_GRID_PARTY_REPORT);
        int reportNum = (int) oaGridPartyMapper.countByExample(example);

        modelMap.put("notReportNum", totalNum - reportNum);
        modelMap.put("reportNum", reportNum);

        return "oa/oaGridParty/oaGridParty_page";
    }

    @RequiresPermissions("oaGridParty:list")
    @RequestMapping("/oaGridParty_data")
    @ResponseBody
    public void oaGridParty_data(HttpServletResponse response,
                                 String gridName,
                                 Integer year,
                                 Integer partyId,
                                 Byte status,
                                 @RequestParam(required = false, defaultValue = "0") Byte cls,//状态，0未填报 1暂存 2已报送 3已退回
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaGridPartyExample example = new OaGridPartyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        criteria.addPermits(loginUserService.adminPartyIdList());

        if (StringUtils.isNotBlank(gridName)) {
            criteria.andGridNameLike(SqlUtils.trimLike(gridName));
        }
        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (cls != null){
            if (cls == OaConstants.OA_GRID_PARTY_REPORT){
                criteria.andStatusEqualTo(OaConstants.OA_GRID_PARTY_REPORT);
            }else {
                List<Byte> _status = new ArrayList<>();
                _status.add(OaConstants.OA_GRID_PARTY_INI);
                _status.add(OaConstants.OA_GRID_PARTY_SAVE);
                _status.add(OaConstants.OA_GRID_PARTY_BACK);
                criteria.andStatusIn(_status);
            }
        }
        if (status != null){
            criteria.andStatusEqualTo(status);
        }

        long count = oaGridPartyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OaGridParty> records= oaGridPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(oaGridParty.class, oaGridPartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("oaGridParty:edit")
    @RequestMapping(value = "/oaGridParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaGridParty_au(Integer id,
                                 MultipartFile _excelFilePath,
                                 MultipartFile[] _filePath,
                                 HttpServletRequest request) throws IOException, InvalidFormatException {

        OaGridParty record = oaGridPartyMapper.selectByPrimaryKey(id);

        // 权限校验
        PartyHelper.checkAuth(record.getPartyId());

        //党统数据
        if (_excelFilePath != null){
            record.setExcelFilePath(upload(_excelFilePath, "oa_grid_party_file"));
        }
        //签字文件
        if (_filePath != null){
            List<String> fileNameList = new ArrayList<>();
            List<String> filePathList = new ArrayList<>();
            for (MultipartFile file : _filePath) {
                String originaFileName = file.getOriginalFilename();
                String path = upload(file, "oa_grid_party_file");
                fileNameList.add(originaFileName);
                filePathList.add(path);
            }
            String fileName = StringUtils.trimToNull(record.getFileName());
            String filePath = StringUtils.trimToNull(record.getFilePath());
            if (StringUtils.isBlank(fileName)){
                record.setFileName(StringUtils.join(fileNameList, ";"));
                record.setFilePath(StringUtils.join(filePathList, ";"));
            }else{
                record.setFileName(fileName + ";" + StringUtils.join(fileNameList, ";"));
                record.setFilePath(filePath + ";" + StringUtils.join(filePathList, ";"));
            }
        }

        oaGridPartyService.update(record, _excelFilePath, request);
        logger.info(log( LogConstants.LOG_OA, "上传党统报送文件：{0}", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaGridParty:edit")
    @RequestMapping("/oaGridParty_au")
    public String oaGridParty_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            OaGridParty oaGridParty = oaGridPartyMapper.selectByPrimaryKey(id);
            modelMap.put("party", partyMapper.selectByPrimaryKey(oaGridParty.getPartyId()));
            modelMap.put("oaGrid", oaGridMapper.selectByPrimaryKey(oaGridParty.getGridId()));
            modelMap.put("oaGridParty", oaGridParty);
        }
        return "oa/oaGridParty/oaGridParty_au";
    }

    // 删除，权限使用oaGrid:release
    @RequiresPermissions("oaGrid:release")
    @RequestMapping(value = "/oaGridParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map oaGridParty_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            oaGridPartyService.batchDel(ids);
            logger.info(log( LogConstants.LOG_OA, "批量删除二级党委数据表格报送结果：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaGridParty:edit")
    @RequestMapping(value = "/oaGridParty_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaGridParty_report(HttpServletRequest request, Integer[] ids, Byte report, String backReason, ModelMap modelMap) {


        if (null != ids && ids.length>0 && report != null){
            oaGridPartyService.batchReport(ids, report, backReason);
            logger.info(log( LogConstants.LOG_OA, "批量{1}二级党委数据表格报送结果：{0}", StringUtils.join(ids, ","), report==1?"报送":"退回"));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaGridParty:edit")
    @RequestMapping("/oaGridParty_report")
    public String oaGridParty_report(Integer[] ids, ModelMap modelMap) {

        if (ids.length == 1) {
            modelMap.put("oaGridParty", oaGridPartyMapper.selectByPrimaryKey(ids[0]));
        }

        return "oa/oaGridParty/oaGridParty_report";
    }

    @RequiresPermissions("oaGridParty:edit")
    @RequestMapping("/oaGridParty_backReason")
    public String oaGridParty_backReason(Integer id, ModelMap modelMap) {

        if (id != null) {
            modelMap.put("oaGridParty", oaGridPartyMapper.selectByPrimaryKey(id));
        }

        return "oa/oaGridParty/oaGridParty_backReason";
    }

    @RequiresPermissions("oaGridParty:edit")
    @RequestMapping("/oaGridParty_preview")
    public String oaGridParty_preview(int id, boolean tpl, boolean isSave, ModelMap modelMap) throws IOException, InvalidFormatException {

        OaGridParty oaGridParty = oaGridPartyMapper.selectByPrimaryKey(id);

        // 权限校验
        PartyHelper.checkAuth(oaGridParty.getPartyId());

        OaGrid oaGrid = oaGridParty.getOaGrid();
        int row = oaGrid.getRow();
        int col = ExcelUtils.toColIndex(oaGrid.getCol());
        String path = springProps.uploadPath + oaGrid.getTemplateFilePath();

        if (isSave){
            path = springProps.uploadPath + oaGridParty.getExcelFilePath();

        }
        String table = ExcelToHtmlUtils.toHtml(path, true, oaGrid.getRow(),
                ExcelUtils.toColIndex(oaGrid.getCol()));

        if(!tpl && !isSave) {
            OaGridPartyDataExample example = new OaGridPartyDataExample();
            example.createCriteria().andGridPartyIdEqualTo(id);
            List<OaGridPartyData> dataList = oaGridPartyDataMapper.selectByExample(example);

            File file = new File(path);
            InputStream is = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(is);

            Sheet sheet = wb.getSheetAt(0);
            String cellLabel = null;
            Row dataRow = null;
            Cell dataCell = null;
            for (OaGridPartyData data : dataList) {
                cellLabel = data.getCellLabel();
                dataRow = sheet.getRow(ExcelUtils.getRowIndex(cellLabel) - 1);
                dataCell = dataRow.getCell(ExcelUtils.getColIndex(cellLabel) - 1);
                dataCell.setCellValue(data.getNum());
            }

            sheet.setForceFormulaRecalculation(true);//强制执行sheet的函数
            table = ExcelToHtmlUtils.toHtml(wb, true, row, col);
        }


        modelMap.put("table", table);

        return "oa/oaGridParty/oaGridParty_preview";
    }

    @RequiresPermissions("oaGridParty:edit")
    @RequestMapping("/oaGridParty_files")
    public String oaGridParty_files(int id,
                                    ModelMap modelMap) {

        OaGridParty oaGridParty = oaGridPartyMapper.selectByPrimaryKey(id);
        // 权限校验
        PartyHelper.checkAuth(oaGridParty.getPartyId());

        String fileName = StringUtils.trimToNull(oaGridParty.getFileName());
        if (StringUtils.isNotBlank(fileName)){
            String[] fileNames = oaGridParty.getFileName().split(";");
            String[] filePaths = oaGridParty.getFilePath().split(";");
            modelMap.put("fileNames", Arrays.asList(fileNames));
            modelMap.put("filePaths", Arrays.asList(filePaths));
        }
        modelMap.put("oaGridParty", oaGridParty);

        return "oa/oaGridParty/oaGridParty_files";
    }

    @RequiresPermissions("oaGridParty:del")
    @RequestMapping(value = "/oaGridPartyFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaGridPartyFile_del(HttpServletRequest request, Integer id, String filePath, String fileName, ModelMap modelMap) {


        if (null != id){

            oaGridPartyService.delReportFile(id, filePath);
            logger.info(log(LogConstants.LOG_OA, "删除签字文件：{0},{1},{2}", id,fileName,filePath));
        }

        return success(FormUtils.SUCCESS);
    }

}
