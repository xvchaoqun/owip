package service.sys;

import domain.SysConfig;
import domain.SysConfigExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysConfigService extends BaseMapper {

    public String genCode() {

        String prefix = "sc";
        String code = "";
        int count = 0;
        do {
            code = prefix + "_" + RandomStringUtils.randomAlphanumeric(6).toLowerCase();
            SysConfigExample example = new SysConfigExample();
            example.createCriteria().andCodeEqualTo(code);
            count = sysConfigMapper.countByExample(example);
        } while (count > 0);
        return code;
    }

    public boolean codeAvailable(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code));

        SysConfigExample example = new SysConfigExample();
        SysConfigExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return sysConfigMapper.countByExample(example) == 0;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "SysConfig:Code:ALL", allEntries = true)
    })
    public void insertSelective(SysConfig record) {

        Assert.isTrue(codeAvailable(null, record.getCode()));
        sysConfigMapper.insertSelective(record);
    }

    @Cacheable(value = "SysConfig:Code:ALL")
    public Map<String, SysConfig> codeKeyMap() {

        SysConfigExample example = new SysConfigExample();
        List<SysConfig> sysConfiges = sysConfigMapper.selectByExample(example);
        Map<String, SysConfig> map = new HashMap<>();
        for (SysConfig sysConfig : sysConfiges) {
            map.put(sysConfig.getCode(), sysConfig);
        }
        return map;
    }

    @CacheEvict(value = "SysConfig:Code:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(SysConfig record) {

        sysConfigMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "SysConfig:Code:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SysConfigExample example = new SysConfigExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        sysConfigMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value = "SysConfig:Code:ALL", allEntries = true)
    })
    public void updateRoles(int id, int roleId){

        SysConfig sysConfig = new SysConfig();
        sysConfig.setId(id);
        sysConfig.setRoleId(roleId);

        sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
    }
}
