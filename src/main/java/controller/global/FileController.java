package controller.global;

import controller.BaseController;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawFile;
import domain.sys.AttachFile;
import domain.sys.SysUserView;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.tool.qrcode.QRCodeUtil;
import sys.utils.ConfigUtil;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;
import sys.utils.ImageUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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

    @RequestMapping(value = "/attach/download")
    public void download(HttpServletRequest request, String path, String filename, HttpServletResponse response) throws IOException {

        if(!FileUtils.exists(springProps.uploadPath, path)){
            throw new OpException("文件不存在："+ path);
        }
        DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
    }

    @RequestMapping(value = "/attach/passportDrawFile")
    public void passportDrawFile(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PassportDrawFile passportDrawFile = passportDrawFileMapper.selectByPrimaryKey(id);
        if (passportDrawFile != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(passportDrawFile.getDrawId());
            if (passportDraw.getCadre().getUserId().intValue() != loginUser.getId()) {
                // 本人、干部管理员或管理员才可以下载
                if (!ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN)) {
                    throw new UnauthorizedException();
                }
            }

            String path = springProps.uploadPath + passportDrawFile.getFilePath();
            DownloadUtils.download(request, response, path, passportDrawFile.getFileName());
        }
    }


    @RequestMapping("/swf/preview")
    public String swf_preview(String type, String path, String filename,
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
             return "common/swf_preview_url";

        if(StringUtils.equals(type, "html")) // 嵌入页面
            return "common/swf_preview_html";

        return "common/swf_preview"; // 查看swf modal
    }

    // pdf内容
    @RequestMapping("/pdf")
    public void pdf(String path, HttpServletResponse response) throws IOException {

        // 强制读取pdf文件（word转pdf的情况）
        path = FileUtils.getFileName(path) + ".pdf";
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
    public void pic(String path, HttpServletResponse response, HttpServletRequest request) throws IOException {

        String imagepath = springProps.uploadPath + path;

        if(FileUtils.exists(springProps.uploadPath, path)) {
            BufferedImage bi = ImageIO.read(new File(imagepath));
            if(bi!=null) { // 不是图片则返回空
                int srcWidth = bi.getWidth();      // 源图宽度
                int srcHeight = bi.getHeight();    // 源图高度

                if (srcWidth > 800 || srcHeight > 800) {
                    Thumbnails.of(imagepath)
                            .size(800, 800)
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
    /*@RequestMapping("/public/logo")
    public void logo(HttpServletResponse response, HttpServletRequest request){

        SysConfig sysConfig = CmTag.getSysConfig();
        String imagepath = springProps.uploadPath + sysConfig.getLogo();
        try {
            ImageUtils.displayImage(FileUtils.getBytes(imagepath), response);
        }catch (Exception ex){
        }
    }
    @RequestMapping("/public/logoWhite")
    public void logoWhite(HttpServletResponse response, HttpServletRequest request) {

        SysConfig sysConfig = CmTag.getSysConfig();
        String imagepath = springProps.uploadPath + sysConfig.getLogoWhite();
        try {
            ImageUtils.displayImage(FileUtils.getBytes(imagepath), response);
        }catch (Exception ex){
        }
    }
    @RequestMapping("/public/loginBg")
    public void loginBg(HttpServletResponse response, HttpServletRequest request) {

        SysConfig sysConfig = CmTag.getSysConfig();
        String imagepath = springProps.uploadPath + sysConfig.getLoginBg();
        try {
            ImageUtils.displayImage(FileUtils.getBytes(imagepath), response);
        }catch (Exception ex){
        }
    }*/

    // 手写签名
    @RequestMapping("/sign")
    public void sign(@CurrentUser SysUserView loginUser, HttpServletResponse response) throws IOException {

        ImageUtils.displayImage(FileUtils.getBytes(springProps.uploadPath + loginUser.getSign()), response);
    }

    //二维码
    @RequestMapping("/qrcode")
    public void qrcode(String content, HttpServletResponse response) throws Exception {

        String homePath = ConfigUtil.defaultHomePath();
        BufferedImage image = QRCodeUtil.createImage(content, homePath + "/extend/img/bnu90.png", false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        ImageUtils.displayImage(baos.toByteArray(), response);
        baos.close();
    }

}
