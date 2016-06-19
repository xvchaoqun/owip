package controller;

import domain.PassportDraw;
import domain.PassportDrawFile;
import domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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

    @RequestMapping(value = "/attach/passportDrawFile")
    public void passportDrawFile(@CurrentUser SysUser loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PassportDrawFile passportDrawFile = passportDrawFileMapper.selectByPrimaryKey(id);
        if (passportDrawFile != null) {
            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(passportDrawFile.getDrawId());
            if(passportDraw.getUserId().intValue() != loginUser.getId()) {
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

    // 图片
    @RequestMapping("/img")
    public void sign(String path, HttpServletResponse response) throws IOException {

        showPic(springProps.uploadPath + path, response);
    }

    // 手写签名
    @RequestMapping("/sign")
    public void sign(@CurrentUser SysUser loginUser, HttpServletResponse response) throws IOException {

        showPic(springProps.uploadPath + loginUser.getSign(), response);
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

        showPic(filepath, response);
    }

    public void showPic(String filepath, HttpServletResponse response) throws IOException {

        byte[] bytes = FileUtils.getBytes(filepath);
        if (bytes == null) return;

        response.reset();
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("image/jpeg");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
