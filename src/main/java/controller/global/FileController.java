package controller.global;

import controller.BaseController;
import domain.sys.AttachFile;
import domain.sys.SysUserView;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.qrcode.QRCodeUtil;
import sys.utils.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.MessageFormat;

/**
 * Created by fafa on 2015/12/8.
 */
@Controller
public class FileController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/attach")
    public void attach(String code,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        AttachFile attachFile = attachFileService.get(code);
        if (attachFile == null) throw new FileNotFoundException("文件不存在");

        DownloadUtils.download(request, response, springProps.uploadPath + attachFile.getPath(),
                attachFile.getFilename() + attachFile.getExt());
    }

    @RequestMapping(value = "/attach_download")
    public void attach_download(HttpServletRequest request, String path, String filename, HttpServletResponse response) throws IOException {

        path = HtmlUtils.htmlUnescape(path);
        filename = HtmlUtils.htmlUnescape(filename);

        if(!FileUtils.exists(springProps.uploadPath, path)){
            throw new OpException("文件不存在："+ path);
        }
        //DownloadUtils.addFileDownloadCookieHeader(response);
        DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
    }

    @RequestMapping("/pdf_preview")
    public String pdf_preview(String type, String path, String filename,
                              // pdf附件标识
                              String code,
                              Boolean nd, // 不需要下载按钮
                              Boolean np,  // 不需要打印按钮
                              ModelMap modelMap) {

        modelMap.put("nd", BooleanUtils.isTrue(nd));
        modelMap.put("np", BooleanUtils.isTrue(np));

        if(StringUtils.isNotBlank(code)) {
            AttachFile attachFile = attachFileService.get(code);
            if (attachFile != null) {

                if (attachFile.getType() != SystemConstants.ATTACH_FILE_TYPE_PDF)
                    throw new OpException("文件不存在");
                path = attachFile.getPath();
                filename = attachFile.getFilename();
            }
        }
        modelMap.put("path", path);
        modelMap.put("filename", filename);

        if(StringUtils.equals(type, "url")) // 查看swf 页面打开
             return "common/pdf_preview_url";

        if(StringUtils.equals(type, "html")) // 嵌入页面
            return "common/pdf_preview_html";

        return "common/pdf_preview"; // 查看swf modal
    }

    // pdf内容
    @RequestMapping("/pdf")
    public void pdf(String path, HttpServletResponse response) throws IOException {

        // 强制读取pdf文件（word转pdf的情况）
        path = PdfUtils.forcePdfPath(path);
        String filePath = springProps.uploadPath + path;

        byte[] bytes = FileUtils.getBytes(filePath);
        if (bytes == null) return;

        response.reset();
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/pdf;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    // 显示pdf内容
    @RequestMapping("/{fileName}.pdf")
    public void pdfShow(String path, HttpServletResponse response) throws IOException {

        // path的后缀可能为大写的, .PDF
        path = PdfUtils.forcePdfPath(path);
        String filePath = springProps.uploadPath + path;

        byte[] bytes = FileUtils.getBytes(filePath);
        if (bytes == null) return;

        response.reset();
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/pdf;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    // pdf转图片显示
    @RequestMapping("/pdf_image")
    public void pdf_image(String path, Boolean flush, Integer r,
                          Integer pageNo, // 如果传入了页码，则返回该页图片，如果没有，则返回所有页合并后的图片
                          HttpServletResponse response) throws IOException, InterruptedException {

       displayPdfImage(path, BooleanUtils.isTrue(flush), r, pageNo, response);
    }

    // swf内容
    @RequestMapping("/swf")
    public void swf(String path, HttpServletResponse response) throws IOException {

        String filePath = springProps.uploadPath + FileUtils.getFileName(path) + ".swf";

        byte[] bytes = FileUtils.getBytes(filePath);
        if (bytes == null) return;

        response.reset();
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    // 图片
    @RequestMapping("/pic")
    public void pic(String path, Integer w, Integer h,
                    HttpServletResponse response, HttpServletRequest request) throws IOException {

        if(w==null && h==null){
            w = 800;
            h = 800;
        }else if(w==null){
            w = Integer.MAX_VALUE;
        }else{
            h = Integer.MAX_VALUE;
        }

        String imagepath = springProps.uploadPath + path;
        if(FileUtils.exists(imagepath)) {
            BufferedImage bi = ImageIO.read(new File(imagepath));
            if(bi!=null) { // 不是图片则返回空
                int srcWidth = bi.getWidth();      // 源图宽度
                int srcHeight = bi.getHeight();    // 源图高度

                if (srcWidth > w || srcHeight > h) {
                    Thumbnails.of(imagepath)
                            .size(w, h)
                            .keepAspectRatio(true)
                            .toOutputStream(response.getOutputStream());
                } else {
                    ImageUtils.displayImage(FileUtils.getBytes(imagepath), response);
                }
            }
        }else{

            logger.warn(MessageFormat.format("{0}, {1}, {2}, {3}, {4}, {5}, {6}",
                    ShiroHelper.getCurrentUsername(), "图片" + imagepath + "不存在", request.getRequestURI(),
                    request.getMethod(),
                    JSONUtils.toString(request.getParameterMap(), false),
                    RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)));
        }
    }

    // 手写签名
    @RequestMapping("/sign")
    public void sign(Integer userId, HttpServletResponse response) throws IOException {

        if(userId==null || ShiroHelper.lackRole(RoleConstants.ROLE_ADMIN)){
            userId = ShiroHelper.getCurrentUserId();
        }
        SysUserView uv = CmTag.getUserById(userId);

        ImageUtils.displayImage(FileUtils.getBytes(springProps.uploadPath + uv.getSign()), response);
    }

    //二维码
    @RequestMapping("/qrcode")
    public void qrcode(String content, HttpServletResponse response) throws Exception {

        BufferedImage image = QRCodeUtil.createImage(content, CmTag.getImgFolder() + "qr.png", false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        ImageUtils.displayImage(baos.toByteArray(), response);
        baos.close();
    }
}
