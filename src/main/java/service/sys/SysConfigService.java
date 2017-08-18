package service.sys;

import domain.sys.SysConfig;
import domain.sys.SysConfigExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import sys.tags.CmTag;
import sys.utils.FileUtils;

import java.util.List;

@Service
public class SysConfigService extends BaseMapper {

    @Autowired
    private SpringProps springProps;

    @Cacheable(value = "SysConfig")
    public SysConfig get() {

        SysConfig sysConfig = null;
        List<SysConfig> sysConfigs = sysConfigMapper.selectByExample(new SysConfigExample());
        if (sysConfigs.size() > 0) sysConfig = sysConfigs.get(0);

        return sysConfig;
    }

    @Transactional
    @CacheEvict(value = "SysConfig", allEntries = true)
    public void insertOrUpdate(SysConfig record) {

        SysConfig sysConfig = null;
        List<SysConfig> sysConfigs = sysConfigMapper.selectByExample(new SysConfigExample());
        if (sysConfigs.size() > 0) sysConfig = sysConfigs.get(0);

        if (sysConfig == null) {
            sysConfigMapper.insertSelective(record);
        } else {
            record.setId(sysConfig.getId());
            sysConfigMapper.updateByPrimaryKeySelective(record);
        }

        // 拷贝图片
        sysConfigs = sysConfigMapper.selectByExample(new SysConfigExample());
        sysConfig = sysConfigs.get(0);
        if(StringUtils.isNotBlank(sysConfig.getLogo())) {
            FileUtils.copyFile(springProps.uploadPath + sysConfig.getLogo(), CmTag.getImgFolder() + "logo.png");
        }
        if(StringUtils.isNotBlank(sysConfig.getLogoWhite())) {
            FileUtils.copyFile(springProps.uploadPath + sysConfig.getLogoWhite(), CmTag.getImgFolder() + "logo_white.png");
        }
        if(StringUtils.isNotBlank(sysConfig.getLoginBg())) {
            FileUtils.copyFile(springProps.uploadPath + sysConfig.getLoginBg(), CmTag.getImgFolder() + "login_bg.png");
        }
    }
}
