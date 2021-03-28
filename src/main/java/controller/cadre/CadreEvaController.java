package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreEva;
import domain.cadre.CadreEvaExample;
import domain.cadre.CadreEvaExample.Criteria;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import domain.unit.Unit;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
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
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class CadreEvaController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreEva:list")
    @RequestMapping("/cadreEva")
    public String cadreEva(Integer cadreId, ModelMap modelMap) {
        if (cadreId != null) {
            CadreView cadreView = cadreService.get(cadreId);
            modelMap.put("cadre", cadreView);
        }
        return "cadre/cadreEva/cadreEva_page";
    }

    @RequiresPermissions("cadreEva:list")
    @RequestMapping("/cadreEva_data")
    @ResponseBody
    public void cadreEva_data(HttpServletRequest request, HttpServletResponse response,
                              Integer cadreId,
                              Integer year,
                              Integer type,
                              String title,
                              @RequestParam(required = false, defaultValue = "0") int export, // 导出近五年考核结果
                              Integer[] ids, // 导出的记录（干部id)
                              Integer pageSize, Integer pageNo,
                              @RequestParam(required = false, defaultValue = "0") int exportType,// 0: 现任干部 1：年轻干部
                              Integer reserveType // 年轻干部类别
                                )  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreEvaExample example = new CadreEvaExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleEqualTo(title);
        }

        if (export == 1) {
            cadreEvaService.cadreEva_export(ids, cadreId, request, response);
            return;
        }

        long count = cadreEvaMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreEva> records= cadreEvaMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cadreEva.class, cadreEvaMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreEva:edit")
    @RequestMapping(value = "/cadreEva_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEva_au(CadreEva record, HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isBlank(record.getTitle())) {
            if (record.getCadreId() != null) {
                CadreView cadre = cadreService.get(record.getCadreId());
                if (cadre != null) {
                    record.setTitle(cadre.getTitle());
                } else {
                    if (record.getCadre() != null) {
                        Integer unitId = record.getCadre().getUnitId();
                        Unit unit = CmTag.getUnit(unitId);
                        if (unit != null) {
                            record.setTitle(unit.getName());
                        }
                    }
                }
            }
        }
        if (id == null) {
            if (cadreEvaService.idDuplicate(id, record.getCadreId(), record.getYear())) {
                return failed("添加重复");
            }
            cadreEvaService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "添加年度考核记录：%s", record.getId()));
        } else {

            cadreEvaService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "更新年度考核记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEva:edit")
    @RequestMapping("/cadreEva_au")
    public String cadreEva_au(Integer id, Integer cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreEva cadreEva = cadreEvaMapper.selectByPrimaryKey(id);
            modelMap.put("cadreEva", cadreEva);
            cadreId = cadreEva.getCadreId();
        }
        if (cadreId != null) {
            modelMap.put("cadre", cadreService.get(cadreId));
        }
        return "cadre/cadreEva/cadreEva_au";
    }

    @RequiresPermissions("cadreEva:import")
    @RequestMapping("/cadreEva_import")
    public String cadreEva_import() {

        return "cadre/cadreEva/cadreEva_import";
    }

    @RequiresPermissions("cadreEva:import")
    @RequestMapping(value = "/cadreEva_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEva_import(HttpServletRequest request, Integer _cadreId) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);

        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CadreEva> records = new ArrayList<>();
        int row = 1;
        int col = 0;
        for (Map<Integer, String> xlsRow : xlsRows) {
            CadreEva record = new CadreEva();
            row++;
            String year = xlsRow.get(col++);
            if (StringUtils.isBlank(year)) {
                throw new OpException("第{0}行年份[{1}]不能为空", row, year);
            }
            if(year.trim().length() < 4){
                throw new OpException("第{0}行年份[{1}]格式不正确", row, year);
            }
            try {
                Integer _year = Integer.parseInt(year.substring(0, 4));
                record.setYear(_year);
            }catch (Exception e){
                throw new OpException("第{0}行年份[{1}]格式不正确", row, year);
            }

            String userCode = StringUtils.trim(xlsRow.get(col++));
            if(StringUtils.isBlank(userCode)){
                throw new OpException("第{0}行工作证号不能为空", row, userCode);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            CadreView cv = cadreService.dbFindByUserId(uv.getUserId());
            if (cv == null){
                throw new OpException("第{0}行不是干部[{1}]", row, userCode);
            }
            if (_cadreId != null) {
                Cadre cadre = CmTag.getCadre(uv.getUserId());
                if (!_cadreId.equals(cadre.getId())) {
                    throw new OpException("第{0}行导入的工作证号为[{1}]的记录非干部本人数据", row, userCode);
                }
            }
            int cadreId = cv.getId();
            record.setCadreId(cadreId);

            String userName = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(userName)) {
                throw new OpException("第{0}行姓名[{1}]不能为空", row, userName);
            } else if (!StringUtils.equals(uv.getRealname(), userName)) {
                throw new OpException("第{0}行姓名[{1}]与工作证号[{2}]不匹配", row, userName, userCode);
            }

            String result = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(result)) {
                throw new OpException("第{0}行考核情况[{1}]不能为空", row, result);
            }
            MetaType metaType = metaTypeService.findByName("mc_cadre_eva", result);
            if (metaType == null) {
                throw new OpException("第{0}行第{1}列考核情况[{2}]不存在", row, col + 1, result);
            }
            record.setType(metaType.getId());

            String title = StringUtils.trimToNull(xlsRow.get(col++));
            if(StringUtils.isBlank(title)){
                title = cv.getTitle();
            }
            record.setTitle(title);

            String remark = StringUtils.trimToNull(xlsRow.get(col++));
            record.setRemark(remark);
            records.add(record);
            col = 0;
        }

        int addCount = cadreEvaService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入干部年度考核记录成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("cadreEva:del")
    @RequestMapping(value = "/cadreEva_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEva_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreEvaService.del(id);
            logger.info(addLog( LogConstants.LOG_ADMIN, "删除年度考核记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEva:del")
    @RequestMapping(value = "/cadreEva_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreEva_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreEvaService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_ADMIN, "批量删除年度考核记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
