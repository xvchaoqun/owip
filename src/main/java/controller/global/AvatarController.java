package controller.global;

import bean.AvatarImportResult;
import controller.BaseController;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.utils.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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
        if(StringUtils.isBlank(path) || !FileUtils.exists(_path)){
            _path = ConfigUtil.defaultHomePath() + FILE_SEPARATOR
                            + "img"+ FILE_SEPARATOR +"default.png";
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

    @RequiresPermissions("avatar:import")
    @RequestMapping("/avatar/process")
    @ResponseBody
    public Map avatar(String ext){  // ext: 图片存放路径，在头像基础目录之下

        String extPath = "";
        if(StringUtils.isNotBlank(ext)){
            extPath = springProps.avatarFolder + FILE_SEPARATOR + ext;
        }else{
            extPath = springProps.avatarFolderExt;
        }
        if(!FileUtils.exists(extPath)){
            return failed("文件路径{0}不存在", extPath);
        }

        logger.info("批量处理头像，文件夹路径：" + ext);
        long startTime=System.currentTimeMillis();
        AvatarImportResult result = new AvatarImportResult();
        avatarService.importAvatar(new File(extPath), result);
        long endTime=System.currentTimeMillis();
        logger.info("total:" + result.total + " save:" + result.save + " error:" + result.error + "处理头像运行时间： " + (endTime - startTime) + "ms");

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("save", result.save);
        resultMap.put("error", result.error);
        resultMap.put("total", result.total);

        return resultMap;
    }

    @RequiresPermissions("avatar:import")
    @RequestMapping("/avatar_import")
    public String avatar_import(ModelMap modelMap) {

        return "sys/sysUser/avatar_import";
    }

    @RequiresPermissions("avatar:import")
    @RequestMapping(value = "/avatar_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_avatar_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile zip = multipartRequest.getFile("zip");

        String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "avatars";
        FileUtils.mkdirs(tmpdir, false);

        String filepath = tmpdir + FILE_SEPARATOR + zip.getOriginalFilename();
        FileUtils.copyFile(zip, new File(filepath));

        String destDir = springProps.avatarFolder + FILE_SEPARATOR
                + DateUtils.getCurrentDateTime(DateUtils.YYYYMMDD)
                    + FILE_SEPARATOR + "import";
        ZipUtils.unzipFile(filepath, destDir);

        AvatarImportResult result = new AvatarImportResult();

        long startTime=System.currentTimeMillis();
        avatarService.importAvatar(new File(destDir), result);
        long endTime=System.currentTimeMillis();

        FileUtils.deleteDir(new File(tmpdir));

        logger.info("total:" + result.total + " save:" + result.save + " error:" + result.error
                + "导入头像运行时间： " + (endTime - startTime) + "ms");

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("save", result.save);
        resultMap.put("error", result.error);
        resultMap.put("total", result.total);

        return resultMap;
    }

    @RequiresPermissions("avatar:import")
    @RequestMapping(value = "/avatar_download")
    public void avatar_download(HttpServletRequest request, String path, String filename, HttpServletResponse response) throws IOException {

        if(!FileUtils.exists(springProps.avatarFolder, path)){
            throw new OpException("文件不存在："+ path);
        }

        DownloadUtils.download(request, response, springProps.avatarFolder + path, filename);
    }
}
