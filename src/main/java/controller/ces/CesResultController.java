package controller.ces;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.*;
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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CesResultController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cesResult:edit")
    @RequestMapping(value = "/cesResult_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cesResult_au(CesResult record, HttpServletRequest request) {
        Integer id = record.getId();

        if(record.getNum()< record.getRank()){
             return failed("排名有误");
        }

        if (StringUtils.isBlank(record.getTitle())) {
            CadreView cadre = cadreService.get(record.getCadreId());
            if (cadre != null) {
                record.setTitle(cadre.getTitle());
            } else {
                Unit unit = CmTag.getUnit(record.getUnitId());
                if (unit != null) {
                    record.setTitle(unit.getName());
                }
            }
        }

        CesResult cesResult = cesResultService.get(record.getType(), record.getUnitId(),
                    record.getCadreId(), record.getYear(), record.getName());
        if(cesResult!=null){
            if(id==null || id.intValue() != cesResult.getId()){
                return failed("添加重复");
            }
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
    public String cesResult_au(Integer id, Integer unitId, Integer cadreId, ModelMap modelMap) {

        if (id != null) {
            CesResult cesResult = cesResultMapper.selectByPrimaryKey(id);
            modelMap.put("cesResult", cesResult);
            unitId = cesResult.getUnitId();
            cadreId = cesResult.getCadreId();
        }
        if (cadreId != null) {
            modelMap.put("cadre", CmTag.getCadreById(cadreId));
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
    @RequestMapping("/cesResult_import_page")
    public String cesResult_import() {
        return "ces/cesResult/cesResult_import";
    }

    @RequiresPermissions("cesResult:import")
    @RequestMapping(value = "/cesResult_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cesResult_import(byte type,Integer _cadreId, Integer _unitId, HttpServletRequest request) throws InvalidFormatException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);

        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CesResult> records = new ArrayList<>();
        int row = 1;
        String _title = null;
        for (Map<Integer, String> xlsRow : xlsRows) {
            row++;
            int col = 0;
            CesResult record = new CesResult();
            String year = StringUtils.trim(xlsRow.get(col++));
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

            if(type==SystemConstants.CES_RESULT_TYPE_CADRE) {
                record.setType(SystemConstants.CES_RESULT_TYPE_CADRE);

                String userCode = StringUtils.trim(xlsRow.get(col++));
                if (StringUtils.isBlank(userCode)) {
                    throw new OpException("第{0}行工作证号不能为空", row, userCode);
                }
                SysUserView uv = sysUserService.findByCode(userCode);
                if (uv == null) {
                    throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
                }
                CadreView cv = cadreService.dbFindByUserId(uv.getUserId());
                if (cv == null) {
                    throw new OpException("第{0}行不是干部[{1}]", row, userCode);
                }
                if (_cadreId != null) {
                    Cadre cadre = CmTag.getCadre(uv.getUserId());
                    if (!_cadreId.equals(cadre.getId())) {
                        throw new OpException("第{0}行导入的非干部本人数据", row);
                    }
                }
                int cadreId = cv.getId();
                record.setCadreId(cadreId);
                _title = cv.getTitle();

                String userName = StringUtils.trim(xlsRow.get(col++));
                if (StringUtils.isBlank(userName)) {
                    throw new OpException("第{0}行姓名不能为空", row);
                }
                if (!StringUtils.equals(uv.getRealname(), userName)) {
                    throw new OpException("第{0}行姓名[{1}]与工作证号[{2}]不匹配", row, userName, userCode);
                }
            } else {
                record.setType(SystemConstants.CES_RESULT_TYPE_UNIT);
            }

            String unitName = StringUtils.trimToNull(xlsRow.get(col++));
            if(StringUtils.isBlank(unitName)) {
                throw new OpException("第{0}行时任单位不能为空", row, unitName);
            }
            Unit unit = unitService.findRunUnitByName(unitName);
            if(unit==null){
                throw new OpException("第{0}行时任单位[{1}]不存在", row, unitName);
            }
            if (!StringUtils.equals(unitName, unit.getName())) {
                throw new OpException("第{0}行时任单位与姓名[{1}]不匹配", row, unitName);
            }
            if (_unitId != null) {
                if (!unit.getId().equals(_unitId)) {
                    throw new OpException("第{0}行导入的非本单位记录", row, unitName);
                }
            }
            record.setUnitId(unit.getId());

            String title = StringUtils.trimToNull(xlsRow.get(col++));
            if(StringUtils.isBlank(title)){
                title = _title;
            }
            record.setTitle(title);

            String name = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(name)) {
                throw new OpException("第{0}行测评类别不能为空", row, name);
            }
            record.setName(name);

            String num = StringUtils.trimToNull(xlsRow.get(col++));
            if(StringUtils.isBlank(num) || !NumberUtils.isDigits(num)){
                throw new OpException("第{0}行总人数格式不正确", row, num);
            }
            record.setNum(Integer.valueOf(num));

            String rank = StringUtils.trimToNull(xlsRow.get(col++));
            if(StringUtils.isBlank(rank) || !NumberUtils.isDigits(rank)){
                throw new OpException("第{0}行排名格式不正确", row, rank);
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
                type == SystemConstants.CES_RESULT_TYPE_CADRE ? "干部" : "班子" + "导入干部年终考核结果成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }


    @RequiresPermissions("cesResult:import")
    @RequestMapping("/cesResult_import")
    public String cesResultImport() {
        return "ces/cesResult/cesResult_import";
    }

    @RequiresPermissions("cesResult:list")
    @RequestMapping("/cesResult")
    public String cesResults(byte type, Integer cadreId, Integer unitId, ModelMap modelMap) {
        modelMap.put("type", type);
        if (cadreId != null) {
            CadreView cadreView = cadreService.get(cadreId);
            modelMap.put("cesResult", cadreView);
            modelMap.put("unit", cadreView.getUnit());
        }
        if (unitId != null) {
            modelMap.put("unit", CmTag.getUnit(unitId));
        }
        return "ces/cesResult/cesResult_page";
    }

    @RequiresPermissions("cesResult:list")
    @RequestMapping("/cesResult_data")
    @ResponseBody
    public void cesResult_data(HttpServletRequest request,HttpServletResponse response,
                                Integer cadreId,
                                Integer unitId,
                                byte type,
                                Integer year,
                                String name,
                                String title,
                                @RequestParam(required = false, defaultValue = "0") byte export,
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

        // 权限控制
        if(!ShiroHelper.isPermitted(RoleConstants.PERMISSION_CADREADMIN)){

            Cadre cadre = CmTag.getCadre(ShiroHelper.getCurrentUserId());
            if (cadre != null) {
                if (ShiroHelper.isPermitted("cesResult:unit")) {
                    List<Integer> adminUnitIds = unitPostService.getAdminUnitIds(cadre.getId());
                    if(adminUnitIds.size()>0) {
                        criteria.andUnitIdIn(adminUnitIds);
                    }else{
                        criteria.andIdIsNull();
                    }
                } else {
                    // 不是管理员，只能查看本人的信息
                    criteria.andCadreIdEqualTo(cadre.getId());
                }
            } else {
                criteria.andIdIsNull();
            }
        }

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.trimLike(name));
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(SqlUtils.trimLike(title));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0){
                criteria.andIdIn(Arrays.asList(ids));
            }
            cesResultService.cesResult_export(type, example, response);
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
}
