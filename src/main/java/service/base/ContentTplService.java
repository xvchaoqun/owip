package service.base;

import domain.base.ContentTpl;
import domain.base.ContentTplExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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

        List<Integer> shorMsgReceiverUserIds = iBaseMapper.getShorMsgReceiverUserIds(tplId,
                SystemConstants.SHORT_MSG_RECEIVER_STATUS_NORMAL);

        List<SysUserView> receivers = new ArrayList<>();
        for (Integer userId : shorMsgReceiverUserIds) {
            SysUserView uv = sysUserService.findById(userId);
            receivers.add(uv);
        }
        return  receivers;
    }

    public String genCode() {

        String prefix = "ct";
        String code = "";
        long count = 0;
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

        record.setSortOrder(getNextSortOrder("base_content_tpl", null));
        contentTplMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="ContentTpl:Code:ALL", allEntries = true)
    public void del(Integer id){

        contentTplMapper.deleteByPrimaryKey(id);
    }

    //删除消息模版
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            ContentTpl contentTpl = new ContentTpl();
            contentTpl.setId(id);
            contentTpl.setIsDeleted(true);
            contentTpl.setSortOrder(getNextSortOrder("base_content_tpl","is_deleted="+true));
            contentTplMapper.updateByPrimaryKeySelective(contentTpl);
        }
    }

    //彻底删除消息模板
    @Transactional
    public void doBatchUnDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        ContentTplExample example = new ContentTplExample();
        example.createCriteria().andIsDeletedEqualTo(true).andIdIn(Arrays.asList(ids));
        contentTplMapper.deleteByExample(example);
    }

    //返回列表
    @Transactional
    public void batchUnDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            ContentTpl contentTpl = new ContentTpl();
            contentTpl.setId(id);
            contentTpl.setIsDeleted(false);
            contentTpl.setSortOrder(getNextSortOrder("base_content_tpl","is_deleted="+false));
            contentTplMapper.updateByPrimaryKeySelective(contentTpl);
        }
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

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "ContentTpl:Code:ALL", allEntries = true)
    })
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ContentTpl entity = contentTplMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        String tableName = "base_content_tpl";
        String whereSql = null;
        adjustSortOrder(tableName, whereSql);
        if(baseSortOrder==null) return;

        ContentTplExample example = new ContentTplExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ContentTpl> overEntities = contentTplMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ContentTpl targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder(tableName, whereSql, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(tableName, whereSql, baseSortOrder, targetEntity.getSortOrder());

            ContentTpl record = new ContentTpl();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            contentTplMapper.updateByPrimaryKeySelective(record);
        }
    }
}
