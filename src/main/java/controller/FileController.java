package controller;

import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawFile;
import domain.sys.AttachFile;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.qrcode.QRCodeUtil;
import sys.utils.ConfigUtil;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;
import sys.utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

/**
 * Created by fafa on 2015/12/8.
 */
@Controller
public class FileController extends BaseController {

    @RequestMapping(value = "/attach")
    public void attach(String code,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        AttachFile attachFile = attachFileService.get(code);
        if (attachFile == null) throw new FileNotFoundException("文件不存在");

        DownloadUtils.download(request, response, springProps.uploadPath + attachFile.getPath(),
                attachFile.getFilename() + attachFile.getExt());
    }

    @RequestMapping(value = "/attach/cadre")
    public void cadreXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = FileController.class.getResource("/").getPath() + "cadre.xlsx";
        DownloadUtils.download(request, response, path, "干部录入样表.xlsx");
    }

    @RequestMapping(value = "/attach/passport")
    public void passportXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = FileController.class.getResource("/").getPath() + "passport.xlsx";
        DownloadUtils.download(request, response, path, "证件录入样表.xlsx");
    }

    @RequestMapping(value = "/attach/download")
    public void download(HttpServletRequest request, String path, String filename, HttpServletResponse response) throws IOException {

        if(!FileUtils.exists(springProps.uploadPath + path)){
            throw new RuntimeException("文件不存在："+ path);
        }

        DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
    }

    @RequestMapping(value = "/attach/passportDrawFile")
    public void passportDrawFile(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PassportDrawFile passportDrawFile = passportDrawFileMapper.selectByPrimaryKey(id);
        if (passportDrawFile != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(passportDrawFile.getDrawId());
            if (passportDraw.getCadre().getUserId().intValue() != loginUser.getId()) {
                boolean[] hasRoles = SecurityUtils.getSubject().hasRoles(Arrays.asList(SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_CADREADMIN));
                // 本人、干部管理员或管理员才可以下载
                if (!hasRoles[0] && !hasRoles[1]) {
                    throw new UnauthorizedException();
                }
            }

            String path = springProps.uploadPath + passportDrawFile.getFilePath();
            DownloadUtils.download(request, response, path, passportDrawFile.getFileName());
        }
    }


    @RequestMapping("/swf/preview")
    public String swf_preview(String type) {

        if(StringUtils.equals(type, "url")) // 查看swf 页面打开
             return "common/swf_preview_url";

        if(StringUtils.equals(type, "html")) // 嵌入页面
            return "common/swf_preview_html";

        return "common/swf_preview"; // 查看swf modal
    }

    // swf内容
    @RequestMapping("/swf")
    public void dispatch_swf(String path, HttpServletResponse response) throws IOException {

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
    public void pic(String path, HttpServletResponse response) throws IOException {

        ImageUtils.displayImage(FileUtils.getBytes(springProps.uploadPath + path), response);
    }

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
