package service.sys;

import bean.AvatarImportResult;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import service.BaseMapper;
import service.SpringProps;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.PatternUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by fafa on 2016/12/2.
 */
@Service
public class AvatarService extends BaseMapper{

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SpringProps springProps;

    /*public String uploadAvatar(MultipartFile _avatar, String code) throws IOException {

        String avatar = null;
        if(_avatar!=null && !_avatar.isEmpty()){
            //String originalFilename = _avatar.getOriginalFilename();
            avatar =  FILE_SEPARATOR + DateUtils.getCurrentDateTime(DateUtils.YYYYMMDD) + FILE_SEPARATOR;
            File path = new File(springProps.avatarFolder + avatar);
            if(!path.exists()) path.mkdirs();
            avatar += code +".jpg";

            Thumbnails.of(_avatar.getInputStream())
                    .size(143, 198)
                    .outputFormat("jpg")
                    .outputQuality(1.0f)
                    .toFile(springProps.avatarFolder + avatar);
            //FileUtils.copyFile(_avatar, new File(springProps.uploadPath + avatar));
        }

        return avatar;
    }*/
    // 保存头像文件（不入库），上传头像 或 修改申请时调用
    public String uploadAvatar(MultipartFile _avatar) throws IOException {

        String avatar = null;
        if(_avatar!=null && !_avatar.isEmpty()){
            //String originalFilename = _avatar.getOriginalFilename();
            avatar =  FILE_SEPARATOR + DateUtils.getCurrentDateTime(DateUtils.YYYYMMDD)
                    + FILE_SEPARATOR + "upload" + FILE_SEPARATOR;
            File path = new File(springProps.avatarFolder + avatar);
            if(!path.exists()) path.mkdirs();
            avatar += System.currentTimeMillis() +".jpg";

            Thumbnails.of(_avatar.getInputStream())
                    .size(143, 198)
                    .outputFormat("jpg")
                    .outputQuality(1.0f)
                    .toFile(springProps.avatarFolder + avatar);
        }

        return avatar;
    }

    // 备份头像
    public String backupAvatar(int userId){

        SysUserView uv = sysUserService.findById(userId);
        String avatar =  uv.getAvatar();
        String backup = FILE_SEPARATOR + DateUtils.getCurrentDateTime(DateUtils.YYYYMMDD) +
                FILE_SEPARATOR + "backup" + FILE_SEPARATOR;
        File path = new File(springProps.avatarFolder + backup);
        if(!path.exists()) path.mkdirs();
        backup += System.currentTimeMillis() +".jpg";

        if(FileUtils.exists(springProps.avatarFolder + avatar)){
            try {
                Thumbnails.of(springProps.avatarFolder + avatar).scale(1f).toFile(springProps.avatarFolder + backup);
            }catch (Exception ex){
                throw new RuntimeException("图片保存失败：" + ex.getMessage());
            }
        }

        return backup;
    }

    // 把某个文件夹下的头像文件（学工号命名的），存储到系统的指定头像地址
    public void importAvatar(File folder, AvatarImportResult result){

        // 转换为标准的路径
        String basePath = new File(springProps.avatarFolder).getPath();

        File[] files = folder.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                importAvatar(file, result);
            }
            if(file.isFile()){
                result.total++;
                String filename = file.getName();
                try {
                    if (PatternUtils.match("^.*\\.(jpg|JPG)$", filename)) {
                        String code = filename.split("\\.")[0];
                        SysUserView sysUser = sysUserService.findByCode(code);
                        if (sysUser != null) {

                            String path = file.getPath();
                            if(path.startsWith(basePath)){
                                path = path.substring(basePath.length());
                            }
                            if(StringUtils.isNotBlank(path)) {
                                SysUserInfo record = new SysUserInfo();
                                record.setUserId(sysUser.getId());
                                record.setAvatar(path);
                                sysUserService.insertOrUpdateUserInfoSelective(record);
                            }

                            result.save++;
                        }
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    result.error++;
                }
            }
        }
    }
}
