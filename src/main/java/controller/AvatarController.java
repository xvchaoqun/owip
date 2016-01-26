package controller;

import domain.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SpringProps;
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
public class AvatarController extends BaseController{

    @RequestMapping("/avatar/{code}")
    public void avatar(@PathVariable String code, HttpServletResponse response) throws IOException {

        String defaultAvatar = springProps.avatarFolder + springProps.defaultAvatar;
        //String filepath = springProps.avatarFolder + code + ".jpg";
        SysUser sysUser = sysUserService.findByUsername(code);
        String filepath =  springProps.avatarFolder + File.separator + sysUser.getId()%100 + File.separator
                + code +".jpg";
        File imgFile = new File(filepath);
        if(!imgFile.exists()) {
            filepath = defaultAvatar;
        }

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
