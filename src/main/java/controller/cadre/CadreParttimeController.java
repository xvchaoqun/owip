package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.CadreInfo;
import domain.cadre.CadreParttime;
import domain.cadre.CadreParttimeExample;
import domain.cadre.CadreParttimeExample.Criteria;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreParttimeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/cadreParttime_import")
    public String cadreCompany_import(ModelMap modelMap) {

        return "cadre/cadreParttime/cadreParttime_import";
    }

    @RequestMapping(value = "/cadreParttime_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCompany_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CadreParttime> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            CadreParttime record = new CadreParttime();
            row++;

            String userCode = StringUtils.trim(xlsRow.get(0));
            if(StringUtils.isBlank(userCode)){
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            int userId = uv.getId();
            CadreView cv = cadreService.dbFindByUserId(userId);
            if(cv == null){
                throw new OpException("第{0}行工作证号[{1}]不是干部", row, userCode);
            }
            record.setCadreId(cv.getId());

            Date startTime = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(2)));
            Date endTime = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(3)));

            if(endTime!=null && startTime != null && endTime.before(startTime)){
                throw new OpException("第{0}行结束时间在起始时间之前", row);
            }
            if(endTime!=null && endTime.after(new Date())){
                throw new OpException("第{0}行结束时间超出了今天", row);
            }

            record.setStartTime(startTime);
            record.setEndTime(startTime);

            record.setUnit(StringUtils.trimToNull(xlsRow.get(4)));
            if(StringUtils.isBlank(record.getUnit())){
                throw new OpException("第{0}行兼职单位为空", row);
            }

            record.setPost(StringUtils.trimToNull(xlsRow.get(5)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(6)));
            record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
            records.add(record);
        }

        int addCount = cadreParttimeService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,"导入干部社会或学术兼职成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("cadreParttime:list")
    @RequestMapping("/cadreParttime_page")
    public String cadreParttime_page(
            @RequestParam(defaultValue = "1") Byte type, // 1 列表 2 预览
            Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 2) {

            modelMap.put("cadreParttimes", cadreParttimeService.list(cadreId));

            CadreInfo cadreInfo = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_PARTTIME);
            modelMap.put("cadreInfo", cadreInfo);
        }else{
            String name = "parttime";
            modelMap.put("canUpdateInfoCheck", cadreInfoCheckService.canUpdateInfoCheck(cadreId, name));
            modelMap.put("canUpdate", cadreInfoCheckService.canUpdate(cadreId, name));
        }
        return "cadre/cadreParttime/cadreParttime_page";
    }

    @RequiresPermissions("cadreParttime:list")
    @RequestMapping("/cadreParttime_data")
    public void cadreParttime_data(HttpServletResponse response,
                                   @SortParam(required = false, defaultValue = "sort_order", tableName = "cadre_parttime") String sort,
                                   @OrderParam(required = false, defaultValue = "desc") String order,
                                   Integer cadreId,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer[] ids, // 导出的记录（干部id)
                                   @RequestParam(required = false, defaultValue = "0") int exportType,// 0: 现任干部 1：年轻干部
                                   Integer reserveType, // 年轻干部类别
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreParttimeExample example = new CadreParttimeExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            if (ids!=null && ids.length>0)
                criteria.andCadreIdIn(Arrays.asList(ids));

            cadreParttime_export(ids,CadreConstants.CADRE_STATUS_CJ, exportType, reserveType, response);
            return;
        }

        long count = cadreParttimeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreParttime> CadreParttimes = cadreParttimeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", CadreParttimes);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;

    }

    @RequiresPermissions("cadreParttime:edit")
    @RequestMapping(value = "/cadreParttime_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreParttime_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入

            CadreParttime record, String _startTime, String _endTime,MultipartFile _file, HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_startTime)) {
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYYMM));
        }
        if (StringUtils.isNotBlank(_endTime)) {
            record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYYMM));
        }

        if (_file != null) {
            String ext = FileUtils.getExtention(_file.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".pdf")) {
                return failed("文件格式错误，请上传pdf文档");
            }
            String originalFilename = _file.getOriginalFilename();
            String savePath = uploadPdf(_file, "cadre_parttime");
            record.setFileName(FileUtils.getFileName(originalFilename));
            record.setFilePath(savePath);
        }

        if (id == null) {

            if (!toApply) {
                cadreParttimeService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部社会或学术兼职：%s", record.getId()));
            } else {
                cadreParttimeService.modifyApply(record, null, false, null);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-干部社会或学术兼职：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreParttime _record = cadreParttimeMapper.selectByPrimaryKey(id);
            if (_record.getCadreId().intValue() != record.getCadreId()) {
                throw new OpException("数据请求错误，没有操作权限");
            }

            if (!toApply) {
                cadreParttimeService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部社会或学术兼职：%s", record.getId()));
            } else {
                if (_isUpdate == false) {
                    cadreParttimeService.modifyApply(record, id, false, null);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-干部社会或学术兼职：%s", record.getId()));
                } else {
                    // 更新修改申请的内容
                    cadreParttimeService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-干部社会或学术兼职：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreParttime:edit")
    @RequestMapping("/cadreParttime_au")
    public String cadreParttime_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreParttime cadreParttime = cadreParttimeMapper.selectByPrimaryKey(id);
            modelMap.put("cadreParttime", cadreParttime);
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreParttime/cadreParttime_au";
    }

    @RequiresPermissions("cadreParttime:del")
    @RequestMapping(value = "/cadreParttime_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            cadreParttimeService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部社会或学术兼职：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreParttime:changeOrder")
    @RequestMapping(value = "/cadreParttime_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreParttime_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        CadreParttime cadreParttime = cadreParttimeMapper.selectByPrimaryKey(id);
        cadreParttimeService.changeOrder(id, cadreParttime.getCadreId(), addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "干部社会或学术兼职调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreParttime_export(Integer[] ids, Byte status, int exportType, Integer reserveType, HttpServletResponse response) {

        List<CadreParttime> cadreParttimes = new ArrayList<>();
        String preStr = "";
        if (exportType == 0){
            cadreParttimes = iCadreMapper.getCadreParttimes(ids,status);
        }else {
            preStr = metaTypeService.getName(reserveType);
            cadreParttimes = iCadreMapper.getCadreReserveParttimes(ids, status, reserveType);
        }
        int rownum = cadreParttimes.size();

        String[] titles = {"工号|100","姓名|80","起始时间|100", "结束时间|100", "兼职单位|400",
        "兼任职务|200","备注|200"};

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            CadreParttime cadreParttime = cadreParttimes.get(i);
            CadreView cadre = CmTag.getCadreById(cadreParttime.getCadreId());

            if (cadre == null ){
                continue;
            }
            String[] values = {
                    cadre.getCode(),
                    cadre.getRealname(),
                    DateUtils.formatDate(cadreParttime.getStartTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(cadreParttime.getEndTime(), DateUtils.YYYYMM),
                    cadreParttime.getUnit(),
                    cadreParttime.getPost(),
                    cadreParttime.getRemark()
            };
            valuesList.add(values);
        }

        String fileName = preStr + "社会或学术兼职";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

}
