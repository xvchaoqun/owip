package servcie;

import bean.AvatarImportResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.SpringProps;
import service.sys.AvatarService;

import java.io.File;

/**
 * Created by fafa on 2016/5/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AvatarTest {

    @Autowired
    private AvatarService avatarService;
    @Autowired
    private SpringProps springProps;

    @Test
    public void process(){

        System.out.println("处理头像");
        long startTime=System.currentTimeMillis();
        AvatarImportResult result = new AvatarImportResult();
        avatarService.importAvatar(new File(StringUtils.defaultString(null,
                springProps.avatarFolder + File.separator + springProps.avatarFolderExt)), result);
        long endTime=System.currentTimeMillis();
        System.out.println("total:" + result.total + " save:" + result.save + " error:" + result.error + "处理头像运行时间： " + (endTime - startTime) + "ms");
    }

}
