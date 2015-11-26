package controller;

import domain.Cadre;
import domain.CadreResearch;
import domain.CadreResearchExample;
import domain.CadreResearchExample.Criteria;
import domain.SysUser;
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
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CadreResearchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreResearch:list")
    @RequestMapping("/cadreResearch")
    public String cadreResearch() {

        return "index";
    }
    @RequiresPermissions("cadreResearch:list")
    @RequestMapping("/cadreResearch_page")
    public String cadreResearch_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "id") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreResearchExample example = new CadreResearchExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreResearch_export(example, response);
            return null;
        }

        int count = cadreResearchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreResearch> CadreResearchs = cadreResearchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadreResearchs", CadreResearchs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (cadreId!=null) {
            searchStr += "&cadreId=" + cadreId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "cadreResearch/cadreResearch_page";
    }

    @RequiresPermissions("cadreResearch:edit")
    @RequestMapping(value = "/cadreResearch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreResearch_au(CadreResearch record,
                                   MultipartFile _chairFile,
                                   MultipartFile _joinFile,
                                   MultipartFile _publishFile,HttpServletRequest request) {

        Integer id = record.getId();

        if(_chairFile!=null){
            String ext = FileUtils.getExtention(_chairFile.getOriginalFilename());
            if(!StringUtils.equalsIgnoreCase(ext, ".doc") && !StringUtils.equalsIgnoreCase(ext, ".docx")){
                throw new RuntimeException("[主持科研项目情况]文件格式错误，请上传word文档");
            }

            String originalFilename = _chairFile.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = File.separator
                    + "research" + File.separator
                    + "chair" + File.separator
                    + fileName;
            String savePath =  realPath + FileUtils.getExtention(originalFilename);
            String pdfPath = realPath + ".pdf";
            FileUtils.copyFile(_chairFile, new File(springProps.uploadPath + savePath));
            FileUtils.word2pdf(springProps.uploadPath + savePath, springProps.uploadPath +pdfPath);

            try {
                String swfPath = realPath + ".swf";
                FileUtils.pdf2Swf(springProps.swfToolsCommand, springProps.uploadPath + pdfPath, springProps.uploadPath + swfPath);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            record.setChairFileName(originalFilename);
            record.setChairFile(savePath);
        }

        if(_joinFile!=null){
            String ext = FileUtils.getExtention(_joinFile.getOriginalFilename());
            if(!StringUtils.equalsIgnoreCase(ext, ".doc") && !StringUtils.equalsIgnoreCase(ext, ".docx")){
                throw new RuntimeException("[参与科研项目情况]文件格式错误，请上传word文档");
            }

            String originalFilename = _joinFile.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = File.separator
                    + "research" + File.separator
                    + "join" + File.separator
                    + fileName;
            String savePath =  realPath + FileUtils.getExtention(originalFilename);
            String pdfPath = realPath + ".pdf";
            FileUtils.copyFile(_joinFile, new File(springProps.uploadPath + savePath));
            FileUtils.word2pdf(springProps.uploadPath + savePath, springProps.uploadPath +pdfPath);

            try {
                String swfPath = realPath + ".swf";
                FileUtils.pdf2Swf(springProps.swfToolsCommand, springProps.uploadPath + pdfPath, springProps.uploadPath + swfPath);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            record.setJoinFileName(originalFilename);
            record.setJoinFile(savePath);
        }

        if(_publishFile!=null){
            String ext = FileUtils.getExtention(_publishFile.getOriginalFilename());
            if(!StringUtils.equalsIgnoreCase(ext, ".doc") && !StringUtils.equalsIgnoreCase(ext, ".docx")){
                throw new RuntimeException("[出版著作及发表论文等情况]文件格式错误，请上传word文档");
            }

            String originalFilename = _publishFile.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = File.separator
                    + "research" + File.separator
                    + "publish" + File.separator
                    + fileName;
            String savePath =  realPath + FileUtils.getExtention(originalFilename);
            String pdfPath = realPath + ".pdf";
            FileUtils.copyFile(_publishFile, new File(springProps.uploadPath + savePath));
            FileUtils.word2pdf(springProps.uploadPath + savePath, springProps.uploadPath +pdfPath);

            try {
                String swfPath = realPath + ".swf";
                FileUtils.pdf2Swf(springProps.swfToolsCommand, springProps.uploadPath + pdfPath, springProps.uploadPath + swfPath);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            record.setPublishFileName(originalFilename);
            record.setPublishFile(savePath);
        }

        if (id == null) {
            cadreResearchService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加干部科研情况：%s", record.getId()));
        } else {

            cadreResearchService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新干部科研情况：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/cadreResearch_swf")
    public void cadreResearch_swf(Integer id, @RequestParam(required = false,defaultValue = "chair")String type
            , HttpServletResponse response) throws IOException{

        CadreResearch cadreResearch = cadreResearchMapper.selectByPrimaryKey(id);
        String filePath = StringUtils.equalsIgnoreCase(type, "chair")?cadreResearch.getChairFile()
                :StringUtils.equalsIgnoreCase(type, "join")?cadreResearch.getJoinFile():cadreResearch.getPublishFile();
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

    @RequestMapping("/cadreResearch_swf_preview")
    public String cadreResearch_swf_preview(Integer id, @RequestParam(required = false,defaultValue = "chair")String type,
                              ModelMap modelMap) {

        CadreResearch cadreResearch = cadreResearchMapper.selectByPrimaryKey(id);
        String filePath = StringUtils.equalsIgnoreCase(type, "chair")?cadreResearch.getChairFile()
                :StringUtils.equalsIgnoreCase(type, "join")?cadreResearch.getJoinFile():cadreResearch.getPublishFile();
        modelMap.put("cadreResearch", cadreResearch);
        modelMap.put("filePath", filePath);
        return "cadreResearch/cadreResearch_swf_preview";
    }

    @RequiresPermissions("cadreResearch:download")
    @RequestMapping("/cadreResearch_download")
    public void cadreResearch_download(Integer id,
                                  @RequestParam(required = false,defaultValue = "chair")String type,
                                  HttpServletResponse response) throws IOException{

        CadreResearch cadreResearch = cadreResearchMapper.selectByPrimaryKey(id);
        String filePath = StringUtils.equalsIgnoreCase(type, "chair")?cadreResearch.getChairFile()
                :StringUtils.equalsIgnoreCase(type, "join")?cadreResearch.getJoinFile():cadreResearch.getPublishFile();
        byte[] bytes = FileUtils.getBytes(filePath);

        String fileName = StringUtils.equalsIgnoreCase(type, "chair")?cadreResearch.getChairFileName()
                :StringUtils.equalsIgnoreCase(type, "join")?cadreResearch.getJoinFileName():cadreResearch.getPublishFileName();
        fileName = URLEncoder.encode(fileName, "UTF-8");

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @RequiresPermissions("cadreResearch:edit")
    @RequestMapping("/cadreResearch_au")
    public String cadreResearch_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreResearch cadreResearch = cadreResearchMapper.selectByPrimaryKey(id);
            modelMap.put("cadreResearch", cadreResearch);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadreResearch/cadreResearch_au";
    }

    @RequiresPermissions("cadreResearch:del")
    @RequestMapping(value = "/cadreResearch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreResearch_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreResearchService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除干部科研情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreResearch:del")
    @RequestMapping(value = "/cadreResearch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreResearchService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除干部科研情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreResearch_export(CadreResearchExample example, HttpServletResponse response) {

        List<CadreResearch> cadreResearchs = cadreResearchMapper.selectByExample(example);
        int rownum = cadreResearchMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部","主持科研项目情况","参与科研项目情况","出版著作及发表论文等情况"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreResearch cadreResearch = cadreResearchs.get(i);
            String[] values = {
                        cadreResearch.getCadreId()+"",
                                            cadreResearch.getChairFile(),
                                            cadreResearch.getJoinFile(),
                                            cadreResearch.getPublishFile()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部科研情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
