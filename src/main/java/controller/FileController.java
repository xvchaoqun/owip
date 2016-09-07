package controller;

import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawFile;
import domain.sys.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.qrcode.QRCodeUtil;
import sys.utils.ConfigUtil;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;

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

        DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
    }

    @RequestMapping(value = "/attach/passportDrawFile")
    public void passportDrawFile(@CurrentUser SysUser loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PassportDrawFile passportDrawFile = passportDrawFileMapper.selectByPrimaryKey(id);
        if (passportDrawFile != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(passportDrawFile.getDrawId());
            if(passportDraw.getCadre().getUserId().intValue() != loginUser.getId()) {
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

    // 查看swf
    @RequestMapping("/swf/preview")
    public String swf_preview() {

        return "common/swf_preview";
    }

    // swf内容
    @RequestMapping("/swf")
    public void dispatch_swf(String path, HttpServletResponse response) throws IOException{

        String filePath = springProps.uploadPath + FileUtils.getFileName(path) + ".swf";

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

    // 图片
    @RequestMapping("/img")
    public void sign(String path, HttpServletResponse response) throws IOException {

        showPic(FileUtils.getBytes(springProps.uploadPath + path), response);
    }

    // 手写签名
    @RequestMapping("/sign")
    public void sign(@CurrentUser SysUser loginUser, HttpServletResponse response) throws IOException {

        showPic(FileUtils.getBytes(springProps.uploadPath + loginUser.getSign()), response);
    }

    @RequestMapping("/avatar/{username}")
    public void avatar(@PathVariable String username, HttpServletResponse response) throws IOException {

        SysUser sysUser = sysUserService.findByUsername(username);
        String filepath = springProps.avatarFolder + File.separator + sysUser.getId() % 100 + File.separator
                + sysUser.getCode() + ".jpg";
        File imgFile = new File(filepath);
        if (!imgFile.exists()) {
            filepath = springProps.avatarFolder + springProps.defaultAvatar;
        }

        showPic(FileUtils.getBytes(filepath), response);
    }

    public void showPic(byte[] bytes, HttpServletResponse response) throws IOException {

        if (bytes == null) return;

        response.reset();
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("image/jpeg");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    //二维码
    @RequestMapping("/qrcode")
    public void qrcode(String content, HttpServletResponse response) throws Exception {

        String homePath = ConfigUtil.defaultHomePath();
        BufferedImage image = QRCodeUtil.createImage(content, homePath + "/extend/img/bnu60.jpg", false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "JPG", baos);
        showPic(baos.toByteArray(), response);
        baos.close();
    }

}
