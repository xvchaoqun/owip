package servcie;

import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.SpringProps;
import service.sys.SysUserService;
import sys.utils.FileUtils;
import sys.utils.PatternUtils;

import java.io.File;

/**
 * Created by fafa on 2016/5/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AvatarTest {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SpringProps springProps;

    @Test
    public void process(){

        listFolder(new File("D:\\upload\\test"));
    }

    public void listFolder(File folder){
        File[] files = folder.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                listFolder(file);
            }
            if(file.isFile()){
                String filename = file.getName();

                if( PatternUtils.match("^.*\\.(jpg|JPG)$", filename)) {
                    String code = filename.split("\\.")[0];
                    SysUserView sysUser = sysUserService.findByCode(code);
                    if(sysUser!=null){
                        String avatar =  springProps.avatarFolder + File.separator + sysUser.getId()%100 + File.separator;
                        File path = new File(avatar);
                        if(!path.exists()) path.mkdirs();

                        System.out.println((avatar + code + ".jpg"));
                        FileUtils.copyFile(file, new File(avatar + code +".jpg"));
                    }
                }
            }
        }
    }

}
