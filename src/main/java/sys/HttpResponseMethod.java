package sys;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.web.multipart.MultipartFile;
import service.SpringProps;
import service.sys.LogService;
import sys.tags.CmTag;
import sys.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface HttpResponseMethod {

    default Map<String, Object> success() {

        return success(null);
    }

    default  Map<String, Object> success(String msg) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        resultMap.put("msg", StringUtils.defaultIfBlank(msg, "success"));
        return resultMap;
    }

    default  Map<String, Object> formValidError(String fieldName, String msg) {

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
    default void displayPdfImage(String path, boolean flush, Integer resolution, HttpServletResponse response) throws IOException, InterruptedException {

        String ext = FileUtils.getExtention(path); // linux区分文件名大小写
		path = FileUtils.getFileName(path) + (StringUtils.equalsIgnoreCase(ext, ".pdf")?ext:".pdf");

		SpringProps springProps = CmTag.getBean(SpringProps.class);
		String pdfFilePath = springProps.uploadPath + path;

		if(!FileUtils.exists(pdfFilePath)) return;

		String imgPath = pdfFilePath+".jpg";
		if(flush || !FileUtils.exists(imgPath)){

		    resolution = resolution==null?CmTag.getIntProperty("pdfResolution", 300):resolution;
			PdfUtils.pdf2jpg(pdfFilePath, resolution, PropertiesUtils.getString("gs.command"));
		}

		ImageUtils.displayImage(FileUtils.getBytes(imgPath), response);
    }

    /*default void pdf2Swf(String filePath, String swfPath) throws IOException, InterruptedException {

        SpringProps springProps = CmTag.getBean(SpringProps.class);

        FileUtils.pdf2Swf(springProps.swfToolsCommand, springProps.uploadPath + filePath,
                springProps.uploadPath + swfPath, springProps.swfToolsLanguagedir);
    }*/

    /**
     * 上传文件
     *
     * @param file
     * @param saveFolder
     * @param type       = pdf pic
     * @param sImgWidth
     * @param sImgHeight
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    default String upload(MultipartFile file, String saveFolder,
                         String type,
                         int sImgWidth,
                         int sImgHeight) throws IOException, InterruptedException {

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

        }else if (StringUtils.equalsIgnoreCase(type, "pdf")) {

            //String swfPath = realPath + ".swf";
            //pdf2Swf(savePath, swfPath);

        } else if (StringUtils.equalsIgnoreCase(type, "pic")) {
            // 需要缩略图的情况
            String shortImgPath = realPath + "_s"
                    + StringUtils.defaultIfBlank(FileUtils.getExtention(originalFilename), ".jpg");

            Thumbnails.of(file.getInputStream())
                    .size(sImgWidth, sImgHeight)
                    //.outputFormat("jpg")
                    .outputQuality(1.0f)
                    .toFile(springProps.uploadPath + shortImgPath);
        }

        return savePath;
    }

    default String upload(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        return upload(file, saveFolder, null, 0, 0);
    }

    default String uploadDocOrPdf(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        if (StringUtils.contains(file.getContentType(), "pdf")) {

            return uploadPdf(file, saveFolder);
        } else {
            return uploadDoc(file, saveFolder);
        }
    }

    default String uploadDoc(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        return upload(file, saveFolder, "doc", 0, 0);
    }

    default String uploadPdf(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        return upload(file, saveFolder, "pdf", 0, 0);
    }

    default String uploadPic(MultipartFile file, String saveFolder, int sImgWidth, int sImgHeight) throws IOException, InterruptedException {

        return upload(file, saveFolder, "pic", sImgWidth, sImgHeight);
    }

    default String uploadPdfOrImage(MultipartFile file, String saveFolder) throws IOException, InterruptedException {

        if (StringUtils.indexOfAny(file.getContentType(), "pdf", "image")==-1) {
            throw new FileFormatException("文件格式错误，请上传pdf或图片文件");
        }

        if (StringUtils.contains(file.getContentType(), "pdf")) {

            return uploadPdf(file, saveFolder);
        }else{

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
}
