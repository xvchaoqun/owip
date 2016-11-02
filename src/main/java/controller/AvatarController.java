package controller;

import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/avatar/{username}")
    public void avatar(@PathVariable String username, HttpServletResponse response) throws IOException {

        SysUserView sysUser = sysUserService.findByUsername(username);
        String filepath = springProps.avatarFolder + File.separator + sysUser.getId() % 100 + File.separator
                + sysUser.getCode() + ".jpg";
        File imgFile = new File(filepath);
        if (!imgFile.exists()) {
            filepath = springProps.avatarFolder + springProps.defaultAvatar;
        }

        ImageUtils.displayImage(FileUtils.getBytes(filepath), response);
    }


    int total = 0;
    int save = 0;
    int error = 0;
    private Logger logger = LoggerFactory.getLogger(getClass());

    // 把某个文件夹下的头像文件（学工号命名的），存储到系统的指定头像地址
    public void listFolder(File folder){

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
    }

    //@RequestMapping("/avatar/process")
    public void avatar(){
        System.out.println("处理头像");
        long startTime=System.currentTimeMillis();
        listFolder(new File("/data/avatar"));
        long endTime=System.currentTimeMillis();
        System.out.println("total:"+ total + " save:"+save + " error:" + error + "处理头像运行时间： " + (endTime - startTime) + "ms");
    }
}
