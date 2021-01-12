package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.CadreEvaResult;
import domain.cadre.CadreEvaResultExample;
import domain.cadre.CadreEvaResultExample.Criteria;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
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

public class CadreEvaResultController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreEvaResult:list")
    @RequestMapping("/cadreEvaResult")
    public String cadreEvaResult() {

        return "cadre/cadreEvaResult/cadreEvaResult_page";
    }

    @RequiresPermissions("cadreEvaResult:list")
    @RequestMapping("/cadreEvaResult_data")
    @ResponseBody
    public void cadreEvaResult_data(HttpServletResponse response,
                                    Integer cadreId,
                                
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

        CadreEvaResultExample example = new CadreEvaResultExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreEvaResult_export(ids, response);
            return;
        }

        long count = cadreEvaResultMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreEvaResult> records= cadreEvaResultMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cadreEvaResult.class, cadreEvaResultMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreEvaResult:edit")
    @RequestMapping(value = "/cadreEvaResult_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEvaResult_au(CadreEvaResult record, HttpServletRequest request) {

        Integer id = record.getId();

        if (cadreEvaResultService.idDuplicate(id,
                record.getCadreId(),record.getYear(),record.getGroupName())) {
            return failed("添加重复");
        }
        
        if (id == null) {

            cadreEvaResultService.insertSelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "添加干部年度测评结果：{0}", record.getId()));
        } else {

            cadreEvaResultService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "更新干部年度测评结果：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEvaResult:edit")
    @RequestMapping("/cadreEvaResult_au")
    public String cadreEvaResult_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreEvaResult cadreEvaResult = cadreEvaResultMapper.selectByPrimaryKey(id);
            modelMap.put("cadreEvaResult", cadreEvaResult);
        }
        return "cadre/cadreEvaResult/cadreEvaResult_au";
    }

    @RequiresPermissions("cadreEvaResult:del")
    @RequestMapping(value = "/cadreEvaResult_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEvaResult_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreEvaResultService.del(id);
            logger.info(log( LogConstants.LOG_ADMIN, "删除干部年度测评结果：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEvaResult:del")
    @RequestMapping(value = "/cadreEvaResult_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreEvaResult_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreEvaResultService.batchDel(ids);
            logger.info(log( LogConstants.LOG_ADMIN, "批量删除干部年度测评结果：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cadreEvaResult:import")
    @RequestMapping("/cadreEvaResult_import")
    public String cadreEvaResult_import() {

        return "cadre/cadreEvaResult/cadreEvaResult_import";
    }

    @RequiresPermissions("cadreEvaResult:import")
    @RequestMapping(value = "/cadreEvaResult_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEvaResult_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);

        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CadreEvaResult> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            CadreEvaResult record = new CadreEvaResult();
            String year = StringUtils.trim(xlsRow.get(0));
            if(StringUtils.isBlank(year) || !NumberUtils.isDigits(year)){
                continue;
            }
            record.setYear(Integer.valueOf(year));

            String userCode = StringUtils.trim(xlsRow.get(1));
            if(StringUtils.isBlank(userCode)){
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            CadreView cv = cadreService.dbFindByUserId(uv.getUserId());
            if (cv == null){
                throw new OpException("第{0}行不是干部[{1}]", row, userCode);
            }
            int cadreId = cv.getId();
            record.setCadreId(cadreId);

            String groupName = StringUtils.trimToNull(xlsRow.get(3));
            record.setGroupName(groupName);

            String num = StringUtils.trimToNull(xlsRow.get(4));
            if(StringUtils.isBlank(num) || !NumberUtils.isDigits(num)){
                continue;
            }
            record.setNum(Integer.valueOf(num));

            String sortOrder = StringUtils.trimToNull(xlsRow.get(5));
            if(StringUtils.isBlank(sortOrder) || !NumberUtils.isDigits(sortOrder)){
                continue;
            }
            record.setSortOrder(Integer.valueOf(sortOrder));

            String remark = StringUtils.trimToNull(xlsRow.get(6));
            record.setRemark(remark);

            records.add(record);

        }

        int addCount = cadreEvaResultService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入干部年终考核测评数据成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
    public void cadreEvaResult_export(Integer[] ids,  HttpServletResponse response) {
       List<Integer> yearList = iCadreMapper.getCadreEvaResultYear(ids);
        CadreEvaResultExample example =new CadreEvaResultExample();
        Criteria criteria =example.createCriteria();
        if(ids != null && ids.length > 0){
            criteria.andCadreIdIn(Arrays.asList(ids));
        }

        List<CadreEvaResult> records = cadreEvaResultMapper.selectByExample(example);
        Map<Integer, Map<Integer, CadreEvaResult>> resultMap = new LinkedHashMap<>();

        for (CadreEvaResult cadreEvaResult : records) {

            int cadreId = cadreEvaResult.getCadreId();
            Map<Integer, CadreEvaResult> cadreEvaResultMap = resultMap.get(cadreId);
            if(cadreEvaResultMap==null){
                cadreEvaResultMap = new HashMap<>();
                resultMap.put(cadreId, cadreEvaResultMap);
            }

            cadreEvaResultMap.put(cadreEvaResult.getYear(), cadreEvaResult);
        }
        List<String> titles = new ArrayList(Arrays.asList("工作证号|100", "姓名|80", "所在单位及职务|250|left"));
        for (Integer year : yearList) {
            titles.add(year + "年|80");
        }

        List<List<String>> valuesList = new ArrayList<>();

        for (Map.Entry<Integer, Map<Integer, CadreEvaResult>> entry : resultMap.entrySet()) {

            int cadreId = entry.getKey();
            Map<Integer, CadreEvaResult> cadreEvaResultMap = entry.getValue();

            CadreView cadre = CmTag.getCadreById(cadreId);
            List<String> values = new ArrayList(Arrays.asList(cadre.getCode(), cadre.getRealname(), cadre.getTitle()));
            for (Integer year : yearList) {

                String result = null;
                CadreEvaResult cadreEvaResult = cadreEvaResultMap.get(year);
                if(cadreEvaResult !=null){
                String groupName = cadreEvaResult.getGroupName() == null?"":cadreEvaResult.getGroupName();
                String sortOrder = cadreEvaResult.getSortOrder() == null?"":cadreEvaResult.getSortOrder()+"";
                String num = cadreEvaResult.getNum() == null?"":cadreEvaResult.getNum()+"";
                if(cadreEvaResult!=null){
                    result = groupName+","+sortOrder+"/"+num;
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
