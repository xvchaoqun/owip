package service.base;

import domain.base.ApiKey;
import domain.base.ApiKeyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiKeyService extends BaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    public boolean idDuplicate(Integer id, String app){

        ApiKeyExample example = new ApiKeyExample();
        ApiKeyExample.Criteria criteria = example.createCriteria();
        criteria.andAppEqualTo(app);
        if(id!=null) criteria.andIdNotEqualTo(id);
        return apiKeyMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ApiKey:ALL", allEntries = true)
    public void insertSelective(ApiKey record){

        apiKeyMapper.insertSelective(record);
        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_BASE_APIKEY,"添加Api接口",SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS,null);
    }

    @Transactional
    @CacheEvict(value="ApiKey:ALL", allEntries = true)
    public void del(Integer id){
        apiKeyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="ApiKey:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        ApiKeyExample example = new ApiKeyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        apiKeyMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="ApiKey:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(ApiKey record){

        ApiKey apiKey = apiKeyMapper.selectByPrimaryKey(record.getId());
        apiKeyMapper.updateByPrimaryKeySelective(record);
        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_BASE_APIKEY,"修改Api接口",
                SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS,apiKey.getSecret());
    }

    @Cacheable(value="ApiKey:ALL")
    public Map<String, ApiKey> findAll() {

        ApiKeyExample example = new ApiKeyExample();
        example.createCriteria();
        List<ApiKey> records = apiKeyMapper.selectByExample(example);
        Map<String, ApiKey> map = new LinkedHashMap<>();
        for (ApiKey record : records) {
            map.put(record.getApp(), record);
        }
        return map;
    }
}
