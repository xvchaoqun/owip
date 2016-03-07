package controller;

import domain.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SpringProps;
import shiro.CurrentUser;
import sys.utils.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by fafa on 2015/12/8.
 */
@Controller
public class FileController extends BaseController{

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
        String filepath =  springProps.avatarFolder + File.separator + sysUser.getId()%100 + File.separator
                + username +".jpg";
        File imgFile = new File(filepath);
        if(!imgFile.exists()) {
            filepath = springProps.avatarFolder + springProps.defaultAvatar;
        }

        showPic(filepath, response);
    }

    public void showPic(String filepath, HttpServletResponse response) throws IOException {

        byte[] bytes = FileUtils.getBytes(filepath);
        if(bytes==null) return ;

        response.reset();
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("image/jpeg");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
