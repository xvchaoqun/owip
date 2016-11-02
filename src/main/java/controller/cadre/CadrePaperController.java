package controller.cadre;

import controller.BaseController;
import domain.cadre.*;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
public class CadrePaperController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePaper:list")
    @RequestMapping("/cadrePaper")
    public String cadrePaper() {

        return "index";
    }
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
       CadrePaperExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("pub_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadrePaper_export(example, response);
            return;
        }

        int count = cadrePaperMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, sourceMixins);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
    @RequiresPermissions("cadrePaper:edit")
    @RequestMapping(value = "/cadrePaper_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePaper_au(CadrePaper record,
                                   MultipartFile _file, String _pubTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_pubTime)){
            record.setPubTime(DateUtils.parseDate(_pubTime, DateUtils.YYYY_MM_DD));
        }

        if(_file!=null){
            String ext = FileUtils.getExtention(_file.getOriginalFilename());
            if(!StringUtils.equalsIgnoreCase(ext, ".doc") && !StringUtils.equalsIgnoreCase(ext, ".docx")){
                throw new RuntimeException("[发表论文情况]文件格式错误，请上传word文档");
            }

            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = File.separator
                    + "cadre" + File.separator
                    + "paper" + File.separator
                    + fileName;
            String savePath =  realPath + FileUtils.getExtention(originalFilename);
            String pdfPath = realPath + ".pdf";
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));
            FileUtils.word2pdf(springProps.uploadPath + savePath, springProps.uploadPath +pdfPath);

            try {
                String swfPath = realPath + ".swf";
                FileUtils.pdf2Swf(springProps.swfToolsCommand, springProps.uploadPath + pdfPath, springProps.uploadPath + swfPath);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            record.setFileName(originalFilename);
            record.setFilePath(savePath);
        }

        if (id == null) {
            cadrePaperService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加发表论文情况：%s", record.getId()));
        } else {

            cadrePaperService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新发表论文情况：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/cadrePaper_swf")
    public void cadrePaper_swf(Integer id, @RequestParam(required = false,defaultValue = "chair")String type
            , HttpServletResponse response) throws IOException{

        CadrePaper cadrePaper = cadrePaperMapper.selectByPrimaryKey(id);
        String filePath = cadrePaper.getFilePath();
        filePath = springProps.uploadPath + FileUtils.getFileName(filePath) + ".swf";

        byte[] bytes = FileUtils.getBytes(filePath);
        if(bytes==null) return ;

        response.reset();
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping("/cadrePaper_swf_preview")
    public String cadrePaper_swf_preview(Integer id, ModelMap modelMap) {

        CadrePaper cadrePaper = cadrePaperMapper.selectByPrimaryKey(id);
        String filePath = cadrePaper.getFilePath();
        modelMap.put("cadrePaper", cadrePaper);
        modelMap.put("filePath", filePath);
        return "cadre/cadrePaper/cadrePaper_swf_preview";
    }

    @RequiresPermissions("cadrePaper:edit")
    @RequestMapping("/cadrePaper_au")
    public String cadrePaper_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadrePaper cadrePaper = cadrePaperMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePaper", cadrePaper);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadrePaper/cadrePaper_au";
    }

    @RequiresPermissions("cadrePaper:del")
    @RequestMapping(value = "/cadrePaper_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePaper_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadrePaperService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除发表论文情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePaper:del")
    @RequestMapping(value = "/cadrePaper_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadrePaperService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除发表论文情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadrePaper_export(CadrePaperExample example, HttpServletResponse response) {

        List<CadrePaper> cadrePapers = cadrePaperMapper.selectByExample(example);
        int rownum = cadrePaperMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部","发表论文情况"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadrePaper cadrePaper = cadrePapers.get(i);
            String[] values = {
                        cadrePaper.getCadreId()+"",
                                            cadrePaper.getFileName()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "发表论文情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
