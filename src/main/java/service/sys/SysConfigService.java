package service.sys;

import domain.sys.SysConfig;
import domain.sys.SysConfigExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.List;

@Service
public class SysConfigService extends BaseMapper {

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

    }
}
