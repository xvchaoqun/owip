package controller;

import domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.utils.FileUtils;
import sys.utils.PatternUtils;

import java.io.File;

/**
 * Created by fafa on 2016/5/23.
 */
@Controller
public class AvatarController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());
    public void listFolder(File folder){
        File[] files = folder.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                listFolder(file);
            }
            if(file.isFile()){
                String filename = file.getName();
                try {
                    if (PatternUtils.match("^.*\\.(jpg|JPG)$", filename)) {
                        String code = filename.split("\\.")[0];
                        SysUser sysUser = sysUserService.findByCode(code);
                        if (sysUser != null) {
                            String avatar = springProps.avatarFolder + File.separator + sysUser.getId() % 100 + File.separator;
                            File path = new File(avatar);
                            if (!path.exists()) path.mkdirs();

                            logger.info(code + " save to " + (avatar + code + ".jpg"));
                            FileUtils.copyFile(file, new File(avatar + code + ".jpg"));
                        }
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    logger.error(filename +" save error.");
                }
            }
        }
    }

    @RequestMapping("/avatar/process")
    public void avatar(){
        System.out.println("处理头像");
        long startTime=System.currentTimeMillis();
        listFolder(new File("/data/avatar"));
        long endTime=System.currentTimeMillis();
        System.out.println("处理头像运行时间： " + (endTime - startTime) + "ms");
    }
}
