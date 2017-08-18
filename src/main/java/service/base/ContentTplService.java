package service.base;

import domain.base.ContentTpl;
import domain.base.ContentTplExample;
import domain.base.ShortMsgReceiver;
import domain.base.ShortMsgReceiverExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentTplService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    // 获取短信接收人手机号
    public List<SysUserView> getShorMsgReceivers(int tplId){

        ShortMsgReceiverExample example = new ShortMsgReceiverExample();
        example.createCriteria().andTplIdEqualTo(tplId)
                .andStatusEqualTo(SystemConstants.SHORT_MSG_RECEIVER_STATUS_NORMAL);

        List<ShortMsgReceiver> shortMsgReceivers = shortMsgReceiverMapper.selectByExample(example);
        List<SysUserView> receivers = new ArrayList<>();
        for (ShortMsgReceiver shortMsgReceiver : shortMsgReceivers) {
            Integer userId = shortMsgReceiver.getUserId();
            SysUserView uv = sysUserService.findById(userId);
            receivers.add(uv);
        }

        return  receivers;
    }

    public String genCode() {

        String prefix = "ct";
        String code = "";
        int count = 0;
        do {
            code = prefix + "_" + RandomStringUtils.randomAlphanumeric(8).toLowerCase();
            ContentTplExample example = new ContentTplExample();
            example.createCriteria().andCodeEqualTo(code);
            count = contentTplMapper.countByExample(example);
        } while (count > 0);
        return code;
    }
    
    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        ContentTplExample example = new ContentTplExample();
        ContentTplExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return contentTplMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ContentTpl:Code:ALL", allEntries = true)
    public void insertSelective(ContentTpl record){
        if(StringUtils.isBlank(record.getCode())){
            record.setCode(genCode());
        }
        Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate code");
        contentTplMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="ContentTpl:Code:ALL", allEntries = true)
    public void del(Integer id){

        contentTplMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="ContentTpl:Code:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ContentTplExample example = new ContentTplExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        contentTplMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="ContentTpl:Code:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(ContentTpl record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate code");
        return contentTplMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "ContentTpl:Code:ALL")
    public Map<String, ContentTpl> codeKeyMap() {

        ContentTplExample example = new ContentTplExample();
        List<ContentTpl> contentTpls = contentTplMapper.selectByExample(example);
        Map<String, ContentTpl> map = new HashMap<>();
        for (ContentTpl contentTpl : contentTpls) {
            map.put(contentTpl.getCode(), contentTpl);
        }
        return map;
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value = "ContentTpl:Code:ALL", allEntries = true)
    })
    public void updateRoles(int id, int roleId){

        ContentTpl record = new ContentTpl();
        record.setId(id);
        record.setRoleId(roleId);

        contentTplMapper.updateByPrimaryKeySelective(record);
    }
}
