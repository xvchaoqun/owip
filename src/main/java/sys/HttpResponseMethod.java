package sys;

import controller.global.OpException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import service.SpringProps;
import service.sys.LogService;
import shiro.ShiroHelper;
import sys.tags.CmTag;
import sys.tool.graphicsmagick.GmTool;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface HttpResponseMethod {

    Logger logger = LoggerFactory.getLogger(HttpResponseMethod.class);

    default Map<String, Object> success() {

        return success(null);
    }

    default Map<String, Object> success(String msg) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        resultMap.put("msg", StringUtils.defaultIfBlank(msg, "success"));
        return resultMap;
    }

    default Map<String, Object> formValidError(String fieldName, String msg) {

        Map<String, Object> resultMap = success(msg);
        resultMap.put("success", true);
        resultMap.put("error", true);
        resultMap.put("field", fieldName);

        return resultMap;
    }

    default Map<String, Object> failed(String msg) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        resultMap.put("msg", StringUtils.defaultIfBlank(msg, "failed"));
        return resultMap;
    }

    default Map<String, Object> failed(String msg, Object... params) {

        msg = StringUtils.defaultIfBlank(msg, "failed");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        resultMap.put("msg", MessageFormat.format(msg, params));
        return resultMap;
    }

    default Map<String, Object> ret(int ret, String msg) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("ret", ret);
        resultMap.put("msg", StringUtils.defaultIfBlank(msg, "failed"));
        return resultMap;
    }

    default String toPdfImage(String path, Integer pageNo){

        return toPdfImage(path, true, null, pageNo);
    }

    default String toPdfImage(String path, boolean flush, Integer resolution, Integer pageNo){

        String ext = FileUtils.getExtention(path); // linux区分文件名大小写
        path = FileUtils.getFileName(path) + (StringUtils.equalsIgnoreCase(ext, ".pdf") ? ext : ".pdf");

        SpringProps springProps = CmTag.getBean(SpringProps.class);
        String pdfFilePath = springProps.uploadPath + path;

        if (!FileUtils.exists(pdfFilePath)) return null;

        String imgPath = pdfFilePath + (pageNo==null?".jpg":String.format("-%03d.jpg", pageNo));

        if (flush || !FileUtils.exists(imgPath)) {

            resolution = resolution == null ? CmTag.getIntProperty("pdfResolution", 100) : resolution;
            try {
                String cmd = null;
                if(pageNo==null) {
                     cmd = PdfUtils.pdf2jpg(pdfFilePath, resolution, PropertiesUtils.getString("gs.command"));
                }else{
                    cmd = PdfUtils.pdf2jpg(pdfFilePath, resolution, PropertiesUtils.getString("gs.command"), pageNo);
                }
                logger.info(cmd);
            } catch (Exception e) {
                logger.error("gs {}, {}", pdfFilePath, e.getMessage());
            }
        }

        return imgPath;
    }

    default void displayPdfImage(String path, boolean flush,
                                 Integer resolution,
                                 Integer pageNo, HttpServletResponse response) throws IOException {

        String imgPath = toPdfImage(path, flush, resolution, pageNo);
        ImageUtils.displayImage(FileUtils.getBytes(imgPath), response);
    }

    // 异步Pdf转图片（没生效？）
    /*@Async
    default void asyncPdf2jpg(String pdfFilePath, Integer pageNo) {

        toPdfImage(pdfFilePath, true, 300, pageNo);
    }*/

    /**
     * 上传文件
     *
     * @param file
     * @param saveFolder
     * @param type       =doc时会转换成pdf =pic时会生成缩略图
     * @param sImgWidth
     * @param sImgHeight
     * @return
     */
    default String upload(MultipartFile file, String saveFolder,
                          String type,
                          int sImgWidth,
                          int sImgHeight){

        // 系统允许上传文件白名单
        if(file!=null && !ContentTypeUtils.isAnyFormat(file, CmTag.getStringProperty("upload_file_whitelist"))){

            logger.warn(accessLog("不允许上传的文件格式:" + file.getOriginalFilename()));
            throw new OpException("不允许上传的文件格式");
        }

        if (file == null || file.isEmpty()) return null;

        SpringProps springProps = CmTag.getBean(SpringProps.class);

        // #tomcat版本>=8.0.39 下 win10下url路径中带正斜杠的文件路径读取不了
        String FILE_SEPARATOR = File.separator;

        String realPath = FILE_SEPARATOR + saveFolder +
                FILE_SEPARATOR + DateUtils.formatDate(new Date(), "yyyyMMdd") +
                FILE_SEPARATOR + UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String savePath = realPath + FileUtils.getExtention(originalFilename);
        FileUtils.copyFile(file, new File(springProps.uploadPath + savePath));

        if (StringUtils.equalsIgnoreCase(type, "doc")) {

            String pdfPath = realPath + ".pdf";
            FileUtils.word2pdf(springProps.uploadPath + savePath, springProps.uploadPath + pdfPath);
            //String swfPath = realPath + ".swf";
            //pdf2Swf(pdfPath, swfPath);

        }/* else if (StringUtils.equalsIgnoreCase(type, "pdf")) {

            //String swfPath = realPath + ".swf";
            //pdf2Swf(savePath, swfPath);

        } */else if (StringUtils.equalsIgnoreCase(type, "pic")) {
            // 需要缩略图的情况
            String shortImgPath = realPath + "_s"
                    + StringUtils.defaultIfBlank(FileUtils.getExtention(originalFilename), ".jpg");
            String filePath = springProps.uploadPath + savePath;
            try {
                GmTool gmTool = GmTool.getInstance(PropertiesUtils.getString("gm.command"));
                gmTool.scaleResize(filePath, shortImgPath, sImgWidth, sImgHeight);
            }catch (Exception ex){
                throw new OpException("文件上传失败：" + ex.getMessage());
            }
        }

        return savePath;
    }

    default String upload(MultipartFile file, String saveFolder) {

        return upload(file, saveFolder, null, 0, 0);
    }

    default String uploadDocOrPdf(MultipartFile file, String saveFolder){

        if (StringUtils.contains(file.getContentType(), "pdf")) {

            return uploadPdf(file, saveFolder);
        } else {
            return uploadDoc(file, saveFolder);
        }
    }

    default String uploadDoc(MultipartFile file, String saveFolder) {

        return upload(file, saveFolder, "doc", 0, 0);
    }

    default String uploadPdf(MultipartFile file, String saveFolder) {

        return upload(file, saveFolder, "pdf", 0, 0);
    }

    /**
     * 上传缩略图（保存原图）
     *
     * @param file
     * @param saveFolder
     * @param sImgWidth
     * @param sImgHeight
     * @return
     */
    default String uploadPic(MultipartFile file, String saveFolder, int sImgWidth, int sImgHeight) {

        return upload(file, saveFolder, "pic", sImgWidth, sImgHeight);
    }

    /**
     * 上传缩略图（不保存原图）
     * @param file
     * @param saveFolder
     * @param sImgWidth
     * @param sImgHeight
     * @return
     */
    default String uploadThumbPic(MultipartFile file, String saveFolder, int sImgWidth, int sImgHeight){

         // 系统允许上传文件白名单
        if(file!=null && !ContentTypeUtils.isAnyFormat(file, CmTag.getStringProperty("upload_file_whitelist"))){

            logger.warn(accessLog("不允许上传的文件格式:" + file.getOriginalFilename()));
            throw new OpException("不允许上传的文件格式");
        }

        if (file == null || file.isEmpty()) return null;

        SpringProps springProps = CmTag.getBean(SpringProps.class);

        // #tomcat版本>=8.0.39 下 win10下url路径中带正斜杠的文件路径读取不了
        String FILE_SEPARATOR = File.separator;

        String realPath = FILE_SEPARATOR + saveFolder +
                FILE_SEPARATOR + DateUtils.formatDate(new Date(), "yyyyMMdd") +
                FILE_SEPARATOR + UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String savePath = realPath + FileUtils.getExtention(originalFilename);

        try {
            String filePath = springProps.uploadPath + savePath;
            FileUtils.saveFile(file, new File(filePath));
            GmTool gmTool = GmTool.getInstance(PropertiesUtils.getString("gm.command"));
            gmTool.scaleResize(filePath, filePath, sImgWidth, sImgHeight);
        }catch (Exception ex){
            throw new OpException("文件上传失败：" + ex.getMessage());
        }

        return savePath;
    }

    default String uploadPdfOrImage(MultipartFile file, String saveFolder) {

        if (StringUtils.indexOfAny(file.getContentType(), "pdf", "image") == -1) {
            throw new OpException("文件格式错误，请上传pdf或图片文件");
        }

        if (StringUtils.contains(file.getContentType(), "pdf")) {

            return uploadPdf(file, saveFolder);
        } else {

            return uploadPic(file, saveFolder, 400, 300);
        }
    }

    // 未登录操作日志
    default String addNoLoginLog(Integer userId, String username, Integer logType, String content, Object... params) {

        if (params != null && params.length > 0)
            content = MessageFormat.format(content, params);

        LogService logService = CmTag.getBean(LogService.class);

        return logService.log(userId, username, logType, content);
    }

    // 登录后操作日志
    default String log(Integer logType, String content, Object... params) {

        if (params != null && params.length > 0)
            content = MessageFormat.format(content, params);

        LogService logService = CmTag.getBean(LogService.class);

        return logService.log(logType, content);
    }

    // 登录后操作日志
    //@Deprecated
    default String addLog(Integer logType, String content, Object... params) {

        if (params != null && params.length > 0)
            content = String.format(content, params);

        LogService logService = CmTag.getBean(LogService.class);

        return logService.log(logType, content);
    }

    public static String accessLog(String msg) {

        HttpServletRequest request = ContextHelper.getRequest();

        return accessLog(request, msg);
    }

    public static String accessLog(HttpServletRequest request, String msg) {

        String username = ShiroHelper.getCurrentUsername();
        return MessageFormat.format("{0}, {1}, {2}, {3}, {4}, {5}, {6}",
                username, msg, request.getRequestURI(),
                request.getMethod(),
                JSONUtils.toString(request.getParameterMap(), false),
                RequestUtils.getUserAgent(request), IpUtils.getRealIp(request));
    }
}
