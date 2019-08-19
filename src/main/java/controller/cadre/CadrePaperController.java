package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.cadre.CadrePaper;
import domain.cadre.CadrePaperExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadrePaperController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePaper:list")
    @RequestMapping("/cadrePaper_page")
    public String cadrePaper_page(Integer cadreId, ModelMap modelMap) {

        return "cadre/cadrePaper/cadrePaper_page";
    }

    @RequiresPermissions("cadrePaper:list")
    @RequestMapping("/cadrePaper_data")
    public void cadrePaper_data(HttpServletResponse response,
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

        CadrePaperExample example = new CadrePaperExample();
        CadrePaperExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("pub_time desc");

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadrePaper_export(example, response);
            return;
        }

        long count = cadrePaperMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePaper> cadrePapers = cadrePaperMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadrePapers);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadrePaper:edit")
    @RequestMapping(value = "/cadrePaper_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePaper_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入

            CadrePaper record, MultipartFile _file, String _pubTime, HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_pubTime)) {
            record.setPubTime(DateUtils.parseDate(_pubTime, DateUtils.YYYY_MM_DD));
        }

        if (_file != null) {
            String ext = FileUtils.getExtention(_file.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".pdf")) {
               return failed("[发表论文情况]文件格式错误，请上传pdf文档");
            }

            String originalFilename = _file.getOriginalFilename();
            String savePath = uploadPdf(_file, "cadre_paper");

            record.setFileName(FileUtils.getFileName(originalFilename));
            record.setFilePath(savePath);
        }

        if (id == null) {

            if (!toApply) {
                cadrePaperService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "添加发表论文情况：%s", record.getId()));
            } else {
                cadrePaperService.modifyApply(record, null, false, null);
                logger.info(addLog(LogConstants.LOG_CADRE, "提交添加申请-发表论文情况：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadrePaper _record = cadrePaperMapper.selectByPrimaryKey(id);
            if (_record.getCadreId().intValue() != record.getCadreId()) {
                throw new OpException("数据请求错误，没有操作权限");
            }

            if (!toApply) {
                cadrePaperService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_ADMIN, "更新发表论文情况：%s", record.getId()));
            } else {
                if (_isUpdate == false) {
                    cadrePaperService.modifyApply(record, id, false, null);
                    logger.info(addLog(LogConstants.LOG_CADRE, "提交修改申请-发表论文情况：%s", record.getId()));
                } else {
                    // 更新修改申请的内容
                    cadrePaperService.updateModify(record, applyId);
                    logger.info(addLog(LogConstants.LOG_CADRE, "修改申请内容-发表论文情况：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePaper:edit")
    @RequestMapping("/cadrePaper_au")
    public String cadrePaper_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadrePaper cadrePaper = cadrePaperMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePaper", cadrePaper);
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadrePaper/cadrePaper_au";
    }

    @RequiresPermissions("cadrePaper:del")
    @RequestMapping(value = "/cadrePaper_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadrePaperService.batchDel(ids, cadreId);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除发表论文情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadrePaper_export(CadrePaperExample example, HttpServletResponse response) {

        List<CadrePaper> cadrePapers = cadrePaperMapper.selectByExample(example);
        long rownum = cadrePaperMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部", "发表论文情况"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadrePaper cadrePaper = cadrePapers.get(i);
            String[] values = {
                    cadrePaper.getCadreId() + "",
                    cadrePaper.getFileName()
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }

        String fileName = "发表论文情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }

}
