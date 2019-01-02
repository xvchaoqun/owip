package controller.cadre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import controller.BaseController;
import domain.cadre.CadreCompany;
import domain.cadre.CadreCompanyView;
import domain.cadre.CadreCompanyViewExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import persistence.cadre.common.CadreCompanyStatBean;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadreCompanyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreCompanyList:list")
    @RequestMapping("/cadreCompanyList")
    public String cadreCompanyList(@RequestParam(defaultValue = "1") Byte cls,
                                   @RequestParam(defaultValue = "1") Byte module,
                                   ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("module", module);

        if(cls==3){
            String cadreStatus = "-1";
            if(module==1){
                cadreStatus = StringUtils.join(Arrays.asList(CadreConstants.CADRE_STATUS_LEADER,
                        CadreConstants.CADRE_STATUS_LEADER_LEAVE), ",");
            }else if(module==2){
                cadreStatus = CadreConstants.CADRE_STATUS_MIDDLE+"";
            }
            Map<Integer, CadreCompanyStatBean> statMap = cadreCompanyService.listCadreCompanyStatBeans(cadreStatus);
            modelMap.put("statMap", statMap);
            return "cadre/cadreCompany/cadreCompanyList_stat";
        }else if(cls==4){

            List<Map> statByTypeData = cadreCompanyService.statByTypeData();
            modelMap.put("rowDataMap", statByTypeData);

            return "cadre/cadreCompany/cadreCompanyList_statByType";
        }

        return "cadre/cadreCompany/cadreCompanyList_page";
    }

    // 导出
    @RequiresPermissions("cadreCompanyList:list")
    @RequestMapping("/cadreCompanyList_statExport")
    public void cadreCompanyList_statExport(Byte cadreStatus, HttpServletResponse response, ModelMap modelMap) throws IOException {

        cadreCompanyService.exportStat(cadreStatus, response);

    }
    // 导出
    @RequiresPermissions("cadreCompanyList:list")
    @RequestMapping("/cadreCompanyList_statByTypeExport")
    public void cadreCompanyList_statByTypeExport(HttpServletResponse response, ModelMap modelMap) throws IOException {

        cadreCompanyService.exportStatByType(response);

    }

    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompanyList_setting")
    public String cadreCompanyList_setting() {

        return "cadre/cadreCompany/cadreCompanyList_setting";
    }

    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompany")
    public String cadreCompany(Integer cadreId, @RequestParam(defaultValue = "1") Byte cls,
                               ModelMap modelMap) {

        modelMap.put("cls", cls);

        String name = "company";
        modelMap.put("canUpdateInfoCheck", cadreInfoCheckService.canUpdateInfoCheck(cadreId, name));
        modelMap.put("canUpdate", cadreInfoCheckService.canUpdate(cadreId, name));

        return "cadre/cadreCompany/cadreCompany_page";
    }

    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompany_data")
    public void cadreCompany_data(HttpServletResponse response,
                                  Integer cadreId,
                                  Byte module, // 1 校领导 2干部
                                  Byte cadreStatus,
                                  @RequestParam(defaultValue = "1") Byte cls,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreCompanyViewExample example = new CadreCompanyViewExample();
        CadreCompanyViewExample.Criteria criteria = example.createCriteria()
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("field(cadre_status, 2,5,3,1,4,6) desc, cadre_sort_order desc, start_time desc");

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if(cadreStatus!=null){ // 用于导出 校领导/离任校领导
            criteria.andCadreStatusEqualTo(cadreStatus);
        }
        if(module!=null) {
            if (module == 1) {
                criteria.andCadreStatusIn(Arrays.asList(CadreConstants.CADRE_STATUS_LEADER,
                        CadreConstants.CADRE_STATUS_LEADER_LEAVE));
            }else{
                cadreStatus = CadreConstants.CADRE_STATUS_MIDDLE;
                criteria.andCadreStatusEqualTo(CadreConstants.CADRE_STATUS_MIDDLE);
            }
        }

        if(cls==1 || cls==0){ // 信息修改里可以看到 当前兼职
            criteria.andIsFinishedEqualTo(false);
        }else if(cls==2){
            criteria.andIsFinishedEqualTo(true);
        }else {
            criteria.andIdIsNull();
        }

        if (export == 1) {
            cadreCompanyService.export(cadreStatus, example, response);
            return;
        }

        long count = cadreCompanyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreCompanyView> CadreCompanys = cadreCompanyViewMapper
                .selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", CadreCompanys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(CadreView.class, CadreMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
    @JsonIgnoreProperties(value = {"user", "cadreEdus", "unit", "postType"})
    private class CadreMixin {

/*        @JsonProperty
        public String code;
        @JsonProperty
        public String realname;
        @JsonProperty
        public String title;
        @JsonProperty
        public Boolean isDouble;*/
    }

    @RequiresPermissions("cadreCompany:edit")
    @RequestMapping(value = "/cadreCompany_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCompany_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入
            CadreCompany record, MultipartFile _approvalFile, HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if (_approvalFile != null) {
            String ext = FileUtils.getExtention(_approvalFile.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".pdf")) {
                return failed("文件格式错误，请上传pdf文档");
            }

            record.setApprovalFilename(_approvalFile.getOriginalFilename());
            record.setApprovalFile(uploadPdf(_approvalFile, "cadreCompany"));
        }

        record.setHasPay(BooleanUtils.isTrue(record.getHasPay()));
        record.setHasHand(BooleanUtils.isTrue(record.getHasHand()));

        if (id == null) {

            if (!toApply) {
                cadreCompanyService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部企业兼职情况：%s", record.getId()));
            } else {
                cadreCompanyService.modifyApply(record, null, false);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-干部企业兼职情况：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreCompany _record = cadreCompanyMapper.selectByPrimaryKey(id);
            if (_record.getCadreId().intValue() != record.getCadreId()) {
                throw new IllegalArgumentException("数据异常");
            }

            if (!toApply) {
                cadreCompanyService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部企业兼职情况：%s", record.getId()));
            } else {
                if (_isUpdate == false) {
                    cadreCompanyService.modifyApply(record, id, false);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-干部企业兼职情况：%s", record.getId()));
                } else {
                    // 更新修改申请的内容
                    cadreCompanyService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-干部企业兼职情况：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCompany:edit")
    @RequestMapping("/cadreCompany_au")
    public String cadreCompany_au(Integer id, int cadreId,
                                  Boolean isFinished,
                                  ModelMap modelMap) {

        if (id != null) {
            CadreCompany cadreCompany = cadreCompanyMapper.selectByPrimaryKey(id);
            modelMap.put("cadreCompany", cadreCompany);
            isFinished = cadreCompany.getIsFinished();
        }
        modelMap.put("isFinished", isFinished);
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadreCompany/cadreCompany_au";
    }

    @RequiresPermissions("cadreCompany:del")
    @RequestMapping(value = "/cadreCompany_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadreCompanyService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部企业兼职情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCompany:finish")
    @RequestMapping("/cadreCompany_finish")
    public String cadreCompany_finish(int id, ModelMap modelMap) {

        CadreCompany cadreCompany = cadreCompanyMapper.selectByPrimaryKey(id);
        modelMap.put("cadreCompany", cadreCompany);

        return "cadre/cadreCompany/cadreCompany_finish";
    }

    @RequiresPermissions("cadreCompany:finish")
    @RequestMapping(value = "/cadreCompany_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreCompany_finish(HttpServletRequest request,
                                   int id,
                                   @DateTimeFormat(pattern = DateUtils.YYYYMM) Date finishTime,
                                   Boolean isFinished,
                                   ModelMap modelMap) {

        cadreCompanyService.finish(id, finishTime, BooleanUtils.isTrue(isFinished));
        logger.info(addLog(LogConstants.LOG_ADMIN, "兼职结束：%s", id));

        return success(FormUtils.SUCCESS);
    }


    /*public void cadreCompany_export(byte cadreStatus, CadreCompanyViewExample example, HttpServletResponse response) {

        List<CadreCompanyView> cadreCompanys = cadreCompanyViewMapper.selectByExample(example);
        long rownum = cadreCompanyViewMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"兼职起始时间", "兼职单位及职务"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreCompanyView cadreCompany = cadreCompanys.get(i);
            String[] values = {
                    DateUtils.formatDate(cadreCompany.getStartTime(), DateUtils.YYYY_MM_DD),
                    cadreCompany.getUnit()
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "干部企业兼职情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }*/
}
