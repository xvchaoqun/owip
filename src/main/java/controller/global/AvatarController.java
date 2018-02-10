package controller.global;

import bean.AvatarImportResult;
import controller.BaseController;
import domain.sys.SysUserView;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.RoleConstants;
import sys.utils.FileUtils;
import sys.utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by fafa on 2016/5/23.
 */
@Controller
public class AvatarController extends BaseController {

    // 头像
    @RequestMapping("/avatar")
    public void show_avatar(String path, HttpServletResponse response) throws IOException {

        path = springProps.avatarFolder + path;
        if(!new File(path).exists()){
            path = springProps.avatarFolder + FILE_SEPARATOR + springProps.defaultAvatar;
        }
        ImageUtils.displayImage(FileUtils.getBytes(path), response);
    }

    @RequestMapping("/avatar/{username}")
    public void avatar(@PathVariable String username,
                       @RequestParam(defaultValue = "0", required = false)boolean m,// m=1 移动端
                       HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuffer requestURL = request.getRequestURL();

        String filepath = null;
        SysUserView sysUser = sysUserService.findByUsername(username);
        if(StringUtils.isNotBlank(sysUser.getAvatar())) {
            if (new File(springProps.avatarFolder + sysUser.getAvatar()).exists()) {
                filepath = springProps.avatarFolder + sysUser.getAvatar();
            }
        }

        if(filepath==null)
            filepath = springProps.avatarFolder + FILE_SEPARATOR + springProps.defaultAvatar;

        byte[] bytes = FileUtils.getBytes(filepath);
        if(!m){
            ImageUtils.displayImage(bytes, response);
            return;
        }else{
            BufferedImage bi = ImageIO.read(new File(filepath));
            int srcWidth = bi.getWidth();      // 源图宽度
            int srcHeight = bi.getHeight();    // 源图高度

            int region = srcWidth<srcHeight?srcWidth:srcHeight;
            int tmp = srcWidth<srcHeight?(128*srcWidth/srcHeight):(128*srcHeight/srcWidth);
            int size = tmp<128?tmp:128;

            Thumbnails.of(filepath)
                    .sourceRegion(Positions.CENTER, region, region)
                    .size(size, size)
                    .keepAspectRatio(true)
                    .toOutputStream(response.getOutputStream());
        }
    }


    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(RoleConstants.ROLE_ADMIN)
    @RequestMapping("/avatar/process")
    public void avatar(String ext){  // ext: 图片存放路径，在头像基础目录之下

        logger.info("批量处理头像，文件夹路径：" + ext);
        long startTime=System.currentTimeMillis();
        AvatarImportResult result = new AvatarImportResult();
        avatarService.importAvatar(new File(springProps.avatarFolder +
                FILE_SEPARATOR + StringUtils.defaultString(ext, springProps.avatarFolderExt)), result);
        long endTime=System.currentTimeMillis();
        logger.info("total:" + result.total + " save:" + result.save + " error:" + result.error + "处理头像运行时间： " + (endTime - startTime) + "ms");
    }
}
