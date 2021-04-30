package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.CadreInfo;
import domain.cadre.CadreTrain;
import domain.cadre.CadreTrainExample;
import domain.cadre.CadreTrainExample.Criteria;
import domain.cadre.CadreView;
import domain.cet.CetRecord;
import domain.cet.CetRecordExample;
import domain.sys.SysUserView;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import persistence.cet.CetRecordMapper;
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
public class CadreTrainController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    protected CetRecordMapper cetRecordMapper;

    @RequestMapping("/cadreTrain_import")
    public String cadreCompany_import(ModelMap modelMap) {

        return "cadre/cadreTrain/cadreTrain_import";
    }

    @RequestMapping(value = "/cadreTrain_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCompany_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CadreTrain> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            CadreTrain record = new CadreTrain();
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

            record.setContent(StringUtils.trimToNull(xlsRow.get(4)));

            record.setUnit(StringUtils.trimToNull(xlsRow.get(5)));
            if(StringUtils.isBlank(record.getUnit())){
                throw new OpException("第{0}行主办单位为空", row);
            }

            record.setRemark(StringUtils.trimToNull(xlsRow.get(6)));
            records.add(record);
        }

        int addCount = cadreTrainService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,"导入干部培训情况成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions("cadreTrain:list")
    @RequestMapping("/cadreTrain_page")
    public String cadreTrain_page(
            @RequestParam(defaultValue = "1") Byte type, // 1 列表 2 预览
            Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 2) {

            modelMap.put("cadreTrains", cadreTrainService.list(cadreId));

            CadreInfo cadreInfo = cadreInfoService.get(cadreId, CadreConstants.CADRE_INFO_TYPE_TRAIN);
            modelMap.put("cadreInfo", cadreInfo);
        }else{
            String name = "train";
            modelMap.put("canUpdateInfoCheck", cadreInfoCheckService.canUpdateInfoCheck(cadreId, name));
            modelMap.put("canUpdate", cadreInfoCheckService.canUpdate(cadreId, name));
        }
        return "cadre/cadreTrain/cadreTrain_page";
    }
    @RequiresPermissions("cadreTrain:list")
    @RequestMapping("/cadreTrain_data")
    public void cadreTrain_data(HttpServletResponse response,
                                Integer cadreId,
                                @RequestParam(required = false, defaultValue = "0") int export,
                                Integer[] ids, // 导出的记录（干部id)
                                @RequestParam(required = false, defaultValue = "0") int exportType,// 0: 现任干部 1：年轻干部 2: 现任校领导 3：离任校领导
                                Integer reserveType, // 年轻干部类别
                                @RequestParam(required = false, defaultValue = "0") Byte status, //默认0：干部领导信息 >0 ：校领导status(现任，离任)
                                Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreTrainExample example = new CadreTrainExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("start_time asc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            if (ids!=null && ids.length>0)
                criteria.andCadreIdIn(Arrays.asList(ids));
            if (status == 0) {
                status = CadreConstants.CADRE_STATUS_CJ;
            }
            cadreTrain_export(ids,status, exportType, reserveType, response);
            return;
        }

        long count = cadreTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreTrain> CadreTrains = cadreTrainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", CadreTrains);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreTrain:edit")
    @RequestMapping(value = "/cadreTrain_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTrain_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入

            CadreTrain record, String _startTime, String _endTime,MultipartFile _file, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseDate(_startTime, "yyyy.MM.dd"));
        }
        if(StringUtils.isNotBlank(_endTime)){
            record.setEndTime(DateUtils.parseDate(_endTime, "yyyy.MM.dd"));
        }

        if (_file != null) {
            String ext = FileUtils.getExtention(_file.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".pdf")) {
                return failed("文件格式错误，请上传pdf文档");
            }
            String originalFilename = _file.getOriginalFilename();
            String savePath = uploadPdf(_file, "cadre_train");
            record.setFileName(FileUtils.getFileName(originalFilename));
            record.setFilePath(savePath);
        }

        if (id == null) {

            if(!toApply) {
                cadreTrainService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部培训情况：%s", record.getId()));
            }else{
                cadreTrainService.modifyApply(record, null, false, null);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-干部培训情况：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreTrain _record = cadreTrainMapper.selectByPrimaryKey(id);
            if(_record.getCadreId().intValue() != record.getCadreId()){
                throw new OpException("数据请求错误，没有操作权限");
            }

            if(!toApply) {
                cadreTrainService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部培训情况：%s", record.getId()));
            }else{
                if(_isUpdate==false) {
                    cadreTrainService.modifyApply(record, id, false, null);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-干部培训情况：%s", record.getId()));
                }else{
                    // 更新修改申请的内容
                    cadreTrainService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-干部培训情况：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTrain:edit")
    @RequestMapping("/cadreTrain_au")
    public String cadreTrain_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreTrain cadreTrain = cadreTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cadreTrain", cadreTrain);
        }

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadreTrain/cadreTrain_au";
    }

    // 提取培训记录（培训综合管理-培训记录汇总）
    @RequiresPermissions("cadreTrain:edit")
    @RequestMapping(value = "/cadreTrain_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreTrain_collect(HttpServletRequest request,
                                     int cadreId,
                                     Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreTrainService.cadreTrain_draw(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "提取%s所有培训记录：%s", cadreId, StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreTrain:edit")
    @RequestMapping("/cadreTrain_draw")
    public String cadreTrain_draw(int cadreId,
                                     String name,
                                     String organizer,
                                     ModelMap modelMap){

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        Integer userId = cadre.getUserId();
        modelMap.put("cadre", cadre);
        CetRecordExample example = new CetRecordExample();
        CetRecordExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId).andIsDeletedEqualTo(false);
        example.setOrderByClause("start_date asc");
        if (StringUtils.isNotBlank(name)){
            criteria.andNameLike(SqlUtils.trimLike(name));
        }
        if (StringUtils.isNotBlank(organizer)){
            criteria.andOrganizerLike(SqlUtils.trimLike(organizer));
        }

        List<CetRecord> cetRecords = cetRecordMapper.selectByExample(example);
        modelMap.put("cetRecords", cetRecords);

        return "cadre/cadreTrain/cadreTrain_draw";
    }

    @RequiresPermissions("cadreTrain:del")
    @RequestMapping(value = "/cadreTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreTrainService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部培训情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreTrain_export(Integer[] ids, Byte status, int exportType, Integer reserveType, HttpServletResponse response) {

        List<CadreTrain> cadreTrains = new ArrayList<>();
        String preStr = "";
        if (exportType == 0){
            cadreTrains = iCadreMapper.getCadreTrains(ids,status);
        } else if (exportType == 2 || exportType == 3) {
            if (ids == null || ids.length == 0) {
                List<CadreView> list = cadreService.getLeaderCadreView(ids, status);
                if (list.size() > 0) {
                    ids = new Integer[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        ids[i] = list.get(i).getId();
                    }
                }
            }
            cadreTrains = iCadreMapper.getCadreTrains(ids,status);
        } else {
            preStr = metaTypeService.getName(reserveType);
            cadreTrains = iCadreMapper.getCadreReserveTrains(ids, reserveType, CadreConstants.CADRE_RESERVE_STATUS_NORMAL);
        }
        int rownum = cadreTrains.size();

        List<String[]> valuesList = new ArrayList<>();
        String[] titles = {"工号|100","姓名|100","起始时间|100","结束时间|100","培训内容|400","主办单位|200","备注|200"};

        for (int i = 0; i < rownum; i++) {

            CadreTrain cadreTrain = cadreTrains.get(i);
            CadreView cadre = CmTag.getCadreById(cadreTrain.getCadreId());

            if (cadre == null) {
                continue;
            }
            String[] values = {
                    cadre.getCode(),
                    cadre.getRealname(),
                    DateUtils.formatDate(cadreTrain.getStartTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(cadreTrain.getEndTime(), DateUtils.YYYYMM),
                    cadreTrain.getContent(),
                    cadreTrain.getUnit(),
                    cadreTrain.getRemark()
            };
            valuesList.add(values);
        }

        String fileName = preStr + "干部培训情况";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
