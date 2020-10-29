package service.sys;

import bean.AvatarImportResult;
import controller.global.OpException;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import service.BaseMapper;
import service.SpringProps;
import sys.tags.CmTag;
import sys.tool.graphicsmagick.GmTool;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.PatternUtils;
import sys.utils.PropertiesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fafa on 2016/12/2.
 */
@Service
public class AvatarService extends BaseMapper{

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SpringProps springProps;

    // 保存头像文件（不入库），上传头像 或 修改申请时调用
    public String uploadAvatar(MultipartFile _avatar) throws IOException {

        String avatar = null;
        if(_avatar!=null && !_avatar.isEmpty()){
           avatar = saveAvatar(_avatar.getInputStream(), _avatar.getOriginalFilename());
        }

        return avatar;
    }

    // 从系统文件拷贝至头像文件（不入库），导入中组部任免审批表申请时调用
    public String copyToAvatar(File file) throws IOException {

        String avatar = null;
        if(file!=null && file.exists()){
            avatar = saveAvatar(new FileInputStream(file), file.getName());
        }

        return avatar;
    }

    public String saveAvatar(InputStream is, String filename) throws IOException {

        String avatar = null;
        if(is!=null){
            //String originalFilename = _avatar.getOriginalFilename();
            avatar =  FILE_SEPARATOR + DateUtils.getCurrentDateTime(DateUtils.YYYYMMDD)
                    + FILE_SEPARATOR + "upload" + FILE_SEPARATOR + System.currentTimeMillis()
                    + StringUtils.defaultIfBlank(FileUtils.getExtention(filename), ".jpg");

            String filePath = springProps.avatarFolder + avatar;
            FileUtils.saveFile(is, filePath);

            try {
                GmTool gmTool = GmTool.getInstance(PropertiesUtils.getString("gm.command"));
                gmTool.scaleResize(filePath, filePath, CmTag.getIntProperty("avatarWidth", 400),
                        CmTag.getIntProperty("avatarHeight", 500));
            }catch (Exception ex){
                throw new OpException("上传失败：" + ex.getMessage());
            }
        }

        return avatar;
    }

    // 备份头像
    public String backupAvatar(int userId){

        SysUserView uv = sysUserService.findById(userId);
        String avatar =  uv.getAvatar();
        String backup = FILE_SEPARATOR + DateUtils.getCurrentDateTime(DateUtils.YYYYMMDD) +
                FILE_SEPARATOR + "backup" + FILE_SEPARATOR + System.currentTimeMillis()
                + StringUtils.defaultIfBlank(FileUtils.getExtention(avatar), ".jpg");

        if(FileUtils.exists(springProps.avatarFolder + avatar)){
            try {
                FileUtils.mkdirs(springProps.avatarFolder + backup);
                FileUtils.copyFile(springProps.avatarFolder + avatar,
                        springProps.avatarFolder + backup);
            }catch (Exception ex){
                throw new OpException("图片保存失败：" + ex.getMessage());
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
                    if (PatternUtils.match("^.*\\.(jpg|png|gif)$", StringUtils.lowerCase(filename))) {
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
                    logger.error("异常", ex);
                    result.error++;
                }
            }
        }
    }
}
