package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreCompany;
import domain.cadre.CadreCompanyExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreCompanyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompany")
    public String cadreCompany() {

        return "index";
    }
    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompany_page")
    public String cadreCompany_page() {

        return "cadre/cadreCompany/cadreCompany_page";
    }

    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompany_data")
    public void cadreCompany_data(HttpServletResponse response,
                                 Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreCompanyExample example = new CadreCompanyExample();
        CadreCompanyExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("start_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreCompany_export(example, response);
            return;
        }

        int count = cadreCompanyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreCompany> CadreCompanys = cadreCompanyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", CadreCompanys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, sourceMixins);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
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

            CadreCompany record, String _startTime,MultipartFile _paper, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }

      /*  if(_paper!=null){
            //String ext = FileUtils.getExtention(_proof.getOriginalFilename());
            String originalFilename = _paper.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "cadre" + FILE_SEPARATOR
                    + "file" + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_paper, new File(springProps.uploadPath + savePath));

            record.setPaperFilename(originalFilename);
            record.setPaper(savePath);
        }*/

        if(_paper!=null){
            String ext = FileUtils.getExtention(_paper.getOriginalFilename());
            if(!StringUtils.equalsIgnoreCase(ext, ".pdf")){
                throw new RuntimeException("文件格式错误，请上传pdf文档");
            }

            String originalFilename = _paper.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "cadre" + FILE_SEPARATOR
                    + "file" + FILE_SEPARATOR
                    + fileName;
            String savePath =  realPath + FileUtils.getExtention(originalFilename);
            //String pdfPath = realPath + ".pdf";
            FileUtils.copyFile(_paper, new File(springProps.uploadPath + savePath));
            //FileUtils.word2pdf(springProps.uploadPath + savePath, springProps.uploadPath +pdfPath);

            try {
                String swfPath = realPath + ".swf";
                FileUtils.pdf2Swf(springProps.swfToolsCommand, springProps.uploadPath + savePath, springProps.uploadPath + swfPath);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            record.setPaperFilename(originalFilename);
            record.setPaper(savePath);
        }

        record.setHasPay(BooleanUtils.isTrue(record.getHasPay()));

        if (id == null) {

            if(!toApply) {
                cadreCompanyService.insertSelective(record);
                logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部企业兼职情况：%s", record.getId()));
            }else{
                cadreCompanyService.modifyApply(record, null, false);
                logger.info(addLog(SystemConstants.LOG_USER, "提交添加申请-干部企业兼职情况：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreCompany _record = cadreCompanyMapper.selectByPrimaryKey(id);
            if(_record.getCadreId().intValue() != record.getCadreId()){
                throw new IllegalArgumentException("数据异常");
            }

            if(!toApply) {
                cadreCompanyService.updateByPrimaryKeySelective(record);
                logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部企业兼职情况：%s", record.getId()));
            }else{
                if(_isUpdate==false) {
                    cadreCompanyService.modifyApply(record, id, false);
                    logger.info(addLog(SystemConstants.LOG_USER, "提交修改申请-干部企业兼职情况：%s", record.getId()));
                }else{
                    // 更新修改申请的内容
                    cadreCompanyService.updateModify(record, applyId);
                    logger.info(addLog(SystemConstants.LOG_USER, "修改申请内容-干部企业兼职情况：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCompany:edit")
    @RequestMapping("/cadreCompany_au")
    public String cadreCompany_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreCompany cadreCompany = cadreCompanyMapper.selectByPrimaryKey(id);
            modelMap.put("cadreCompany", cadreCompany);
        }
        CadreView cadre = cadreService.findAll().get(cadreId);
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


        if (null != ids && ids.length>0){
            cadreCompanyService.batchDel(ids, cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部企业兼职情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreCompany_export(CadreCompanyExample example, HttpServletResponse response) {

        List<CadreCompany> cadreCompanys = cadreCompanyMapper.selectByExample(example);
        int rownum = cadreCompanyMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"兼职起始时间","兼职单位及职务"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreCompany cadreCompany = cadreCompanys.get(i);
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
        try {
            String fileName = "干部企业兼职情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
