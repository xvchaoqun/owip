package controller.global;

import bean.AvatarImportResult;
import controller.BaseController;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.RoleConstants;
import sys.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by fafa on 2016/5/23.
 */
@Controller
public class AvatarController extends BaseController {

    // 头像
    @GetMapping(value="/avatar", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] show_avatar(String path,
                              // m=1 移动端
                              @RequestParam(defaultValue = "0", required = false)boolean m ) throws IOException {

        String _path = springProps.avatarFolder + path;
        if(StringUtils.isBlank(path) || !new File(_path).exists()){
            _path = springProps.avatarFolder + FILE_SEPARATOR + springProps.defaultAvatar;
        }

        if(!m){
            return FileUtils.getBytes(_path);
        }else{
            BufferedImage bi = ImageIO.read(new File(_path));
            int srcWidth = bi.getWidth();      // 源图宽度
            int srcHeight = bi.getHeight();    // 源图高度

            int region = srcWidth<srcHeight?srcWidth:srcHeight;
            int tmp = srcWidth<srcHeight?(128*srcWidth/srcHeight):(128*srcHeight/srcWidth);
            int size = tmp<128?tmp:128;

            BufferedImage bufferedImage = Thumbnails.of(_path)
                    .sourceRegion(Positions.CENTER, region, region)
                    .size(size, size)
                    .keepAspectRatio(true).asBufferedImage();
            //.toOutputStream(response.getOutputStream());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpeg", baos);
            return baos.toByteArray();
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
                FILE_SEPARATOR + StringUtils.defaultIfBlank(ext, springProps.avatarFolderExt)), result);
        long endTime=System.currentTimeMillis();
        logger.info("total:" + result.total + " save:" + result.save + " error:" + result.error + "处理头像运行时间： " + (endTime - startTime) + "ms");
    }
}
