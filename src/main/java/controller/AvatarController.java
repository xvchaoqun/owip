package controller;

import bean.AvatarImportResult;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.SystemConstants;
import sys.utils.FileUtils;
import sys.utils.ImageUtils;
import sys.utils.PatternUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by fafa on 2016/5/23.
 */
@Controller
public class AvatarController extends BaseController{

    // 头像
    @RequestMapping("/avatar")
    public void show_avatar(String path, HttpServletResponse response) throws IOException {

        ImageUtils.displayImage(FileUtils.getBytes(springProps.avatarFolder + path), response);
    }

    @RequestMapping("/avatar/{username}")
    public void avatar(@PathVariable String username, HttpServletResponse response) throws IOException {

        String filepath = null;
        SysUserView sysUser = sysUserService.findByUsername(username);
        if(StringUtils.isNotBlank(sysUser.getAvatar())) {
            if (new File(springProps.avatarFolder + sysUser.getAvatar()).exists()) {
                filepath = springProps.avatarFolder + sysUser.getAvatar();
            }
        }

        if(filepath==null)
            filepath = springProps.avatarFolder + File.separator + springProps.defaultAvatar;

        ImageUtils.displayImage(FileUtils.getBytes(filepath), response);
    }


    private Logger logger = LoggerFactory.getLogger(getClass());

    // 把某个文件夹下的头像文件（学工号命名的），存储到系统的指定头像地址
    /*public void listFolder(File folder){

        File[] files = folder.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                listFolder(file);
            }
            if(file.isFile()){
                total++;
                String filename = file.getName();
                try {
                    if (PatternUtils.match("^.*\\.(jpg|JPG)$", filename)) {
                        String code = filename.split("\\.")[0];
                        SysUserView sysUser = sysUserService.findByCode(code);
                        if (sysUser != null) {
                            String avatar = springProps.avatarFolder + File.separator + sysUser.getId() % 100 + File.separator;
                            File path = new File(avatar);
                            if (!path.exists()) path.mkdirs();

                            logger.info(code + " save to " + (avatar + code + ".jpg"));
                            FileUtils.copyFile(file, new File(avatar + code + ".jpg"));
                            save++;
                        }
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    logger.error(filename +" save error.");
                    error++;
                }
            }
        }
    }*/

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping("/avatar/process")
    public void avatar(String ext){  // ext: 图片存放路径，在头像基础目录之下

        System.out.println("处理头像");
        long startTime=System.currentTimeMillis();
        AvatarImportResult result = new AvatarImportResult();
        avatarService.importAvatar(new File(springProps.avatarFolder +
                File.separator + StringUtils.defaultString(ext, springProps.avatarFolderExt)), result);
        long endTime=System.currentTimeMillis();
        System.out.println("total:"+ result.total + " save:"+result.save + " error:" + result.error + "处理头像运行时间： " + (endTime - startTime) + "ms");
    }
}
