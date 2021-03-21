package controller.ces;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.CadreView;
import domain.ces.CesResult;
import domain.ces.CesResultExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CesResultController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cesResult:list")
    @RequestMapping("/cesResult")
    public String cesResult() {

        return "ces/cesResult/cesResult_page";
    }

    @RequiresPermissions("cesResult:list")
    @RequestMapping("/cesResult_data")
    @ResponseBody
    public void cesResult_data(HttpServletResponse response,
                                    Integer unitId,
                                    Integer cadreId,
                                    byte type,
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

        CesResultExample example = new CesResultExample();
        CesResultExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc");

        //单位年度测评结果
        if (type == SystemConstants.CES_RESULT_TYPE_UNIT) {
            criteria.andUnitIdEqualTo(unitId);
        } else if(type == SystemConstants.CES_RESULT_TYPE_CADRE){
            criteria.andCadreIdEqualTo(cadreId);
        }else{
            criteria.andIdIsNull();
        }

        if (export == 1) {
            cesResult_export(ids, response);
            return;
        }

        long count = cesResultMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CesResult> records= cesResultMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cesResult.class, cesResultMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cesResult:*")
    @RequestMapping("/cesResults")
    public String cesResults(byte type, ModelMap modelMap) {

        return "ces/cesResult/cesResult_list";
    }

    @RequiresPermissions("cesResult:list")
    @RequestMapping("/cesResults_data")
    @ResponseBody
    public void cesResult_data(HttpServletResponse response,
                                    byte type,
                                    Integer year,
                                    String name,
                                    String title,
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

        CesResultExample example = new CesResultExample();
        CesResultExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc");
        criteria.andTypeEqualTo(type);
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameEqualTo(name);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleEqualTo(title);
        }

        if (export == 1) {
            cesResult_export(ids, response);
            return;
        }

        long count = cesResultMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CesResult> records= cesResultMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cesResult.class, cesResultMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cesResult:edit")
    @RequestMapping(value = "/cesResult_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cesResult_au(CesResult record, HttpServletRequest request) {
        Integer id = record.getId();

        CesResult cesResult = cesResultService.get(record.getType(), record.getUnitId(),
                record.getCadreId(), record.getYear(), record.getName());
        if(cesResult!=null){
            if(id==null || id.intValue() != cesResult.getId()){
                return failed("添加重复");
            }
        }

        if(record.getNum()< record.getRank()){
             return failed("排名有误");
        }

        if (id == null) {

            cesResultService.insertSelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "添加年终测评结果：{0}", record.getId()));
        } else {

            cesResultService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "更新年终测评结果：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cesResult:edit")
    @RequestMapping("/cesResult_au")
    public String cesResult_au(Integer id, Integer unitId, ModelMap modelMap) {

        if (id != null) {

            CesResult cesResult = cesResultMapper.selectByPrimaryKey(id);
            modelMap.put("cesResult", cesResult);
            unitId = cesResult.getUnitId();
        }

        if (unitId != null) {

            modelMap.put("unit", CmTag.getUnit(unitId));
        }

        return "ces/cesResult/cesResult_au";
    }

    @RequiresPermissions("cesResult:del")
    @RequestMapping(value = "/cesResult_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cesResult_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cesResultService.batchDel(ids);
            logger.info(log( LogConstants.LOG_ADMIN, "批量删除年终测评结果：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cesResult:import")
    @RequestMapping("/cesResult_import")
    public String cesResult_import() {

        return "ces/cesResult/cesResult_import";
    }

    @RequiresPermissions("cesResult:import")
    @RequestMapping(value = "/cesResult_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cesResult_import(byte type, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);

        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CesResult> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            int col = 0;
            CesResult record = new CesResult();
            String year = StringUtils.trim(xlsRow.get(col++));
            if(StringUtils.isBlank(year) || !NumberUtils.isDigits(year)){
                continue;
            }
            record.setYear(Integer.valueOf(year));

            if(type==SystemConstants.CES_RESULT_TYPE_CADRE) {

                String userCode = StringUtils.trim(xlsRow.get(col++));
                if (StringUtils.isBlank(userCode)) {
                    continue;
                }
                SysUserView uv = sysUserService.findByCode(userCode);
                if (uv == null) {
                    throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
                }
                CadreView cv = cadreService.dbFindByUserId(uv.getUserId());
                if (cv == null) {
                    throw new OpException("第{0}行不是干部[{1}]", row, userCode);
                }
                int cadreId = cv.getId();
                record.setCadreId(cadreId);

                String unitName = StringUtils.trimToNull(xlsRow.get(col++));
                if(StringUtils.isNotBlank(unitName)) {
                    Unit unit = unitService.findRunUnitByName(unitName);
                    if(unit!=null){
                        record.setUnitId(unit.getId());
                    }
                }

                String title = StringUtils.trimToNull(xlsRow.get(col++));
                record.setTitle(title);
            }else{

                String unitName = StringUtils.trimToNull(xlsRow.get(col++));
                if(StringUtils.isNotBlank(unitName)) {
                    Unit unit = unitService.findRunUnitByName(unitName);
                    if(unit!=null){
                        record.setUnitId(unit.getId());
                    }
                }

                String title = StringUtils.trimToNull(xlsRow.get(col++));
                if(StringUtils.isBlank(title)){
                    title = unitName;
                }
                record.setTitle(title);
            }

            String name = StringUtils.trimToNull(xlsRow.get(col++));
            record.setName(name);

            String num = StringUtils.trimToNull(xlsRow.get(col++));
            if(StringUtils.isBlank(num) || !NumberUtils.isDigits(num)){
                continue;
            }
            record.setNum(Integer.valueOf(num));

            String rank = StringUtils.trimToNull(xlsRow.get(col++));
            if(StringUtils.isBlank(rank) || !NumberUtils.isDigits(rank)){
                continue;
            }
            record.setRank(Integer.valueOf(rank));

            String remark = StringUtils.trimToNull(xlsRow.get(col++));
            record.setRemark(remark);

            records.add(record);
        }

        int addCount = cesResultService.batchImport(type, records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入干部年终考核测评数据成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    public void cesResult_export(Integer[] ids,  HttpServletResponse response) {
       List<Integer> yearList = iCesResultMapper.getCesResultYear(ids);
        CesResultExample example =new CesResultExample();
        CesResultExample.Criteria criteria =example.createCriteria();
        if(ids != null && ids.length > 0){
            criteria.andCadreIdIn(Arrays.asList(ids));
        }

        List<CesResult> records = cesResultMapper.selectByExample(example);
        Map<Integer, Map<Integer, CesResult>> resultMap = new LinkedHashMap<>();

        for (CesResult cesResult : records) {

            int cadreId = cesResult.getCadreId();
            Map<Integer, CesResult> cesResultMap = resultMap.get(cadreId);
            if(cesResultMap==null){
                cesResultMap = new HashMap<>();
                resultMap.put(cadreId, cesResultMap);
            }

            cesResultMap.put(cesResult.getYear(), cesResult);
        }
        List<String> titles = new ArrayList(Arrays.asList("工作证号|100", "姓名|80", "所在单位及职务|250|left"));
        for (Integer year : yearList) {
            titles.add(year + "年|80");
        }

        List<List<String>> valuesList = new ArrayList<>();

        for (Map.Entry<Integer, Map<Integer, CesResult>> entry : resultMap.entrySet()) {

            int cadreId = entry.getKey();
            Map<Integer, CesResult> cesResultMap = entry.getValue();

            CadreView cadre = CmTag.getCadreById(cadreId);
            List<String> values = new ArrayList(Arrays.asList(cadre.getCode(), cadre.getRealname(), cadre.getTitle()));
            for (Integer year : yearList) {

                String result = null;
                CesResult cesResult = cesResultMap.get(year);
                if(cesResult !=null){
                String name = cesResult.getName() == null?"":cesResult.getName();
                String rank = cesResult.getRank() == null?"":cesResult.getRank()+"";
                String num = cesResult.getNum() == null?"":cesResult.getNum()+"";
                if(cesResult!=null){
                    result = name+","+rank+"/"+num;
                }
                }
                values.add(StringUtils.trimToEmpty(result));
            }

            valuesList.add(values);
        }

        String fileName = "年终考核测评数据";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

}
