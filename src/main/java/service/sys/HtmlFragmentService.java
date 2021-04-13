package service.sys;

import domain.sys.HtmlFragment;
import domain.sys.HtmlFragmentExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HtmlFragmentService extends BaseMapper {

    public String genCode() {

        String prefix = "hf";
        String code = "";
        int count = 0;
        do {
            code = prefix + "_" + RandomStringUtils.randomAlphanumeric(6).toLowerCase();
            HtmlFragmentExample example = new HtmlFragmentExample();
            example.createCriteria().andCodeEqualTo(code);
            count = htmlFragmentMapper.countByExample(example);
        } while (count > 0);
        return code;
    }

    public boolean codeAvailable(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        HtmlFragmentExample example = new HtmlFragmentExample();
        HtmlFragmentExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return htmlFragmentMapper.countByExample(example) == 0;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "HtmlFragment:Code:ALL", allEntries = true),
            @CacheEvict(value = "HtmlFragment:Code:Tree", allEntries = true)
    })
    public void insertSelective(HtmlFragment record) {

        Assert.isTrue(codeAvailable(null, record.getCode()), "wrong code");
        record.setSortOrder(getNextSortOrder("sys_html_fragment", null));
        htmlFragmentMapper.insertSelective(record);
    }

    @Cacheable(value = "HtmlFragment:Code:ALL")
    public Map<String, HtmlFragment> codeKeyMap() {

        HtmlFragmentExample example = new HtmlFragmentExample();
        List<HtmlFragment> htmlFragmentes = htmlFragmentMapper.selectByExample(example);
        Map<String, HtmlFragment> map = new HashMap<>();
        for (HtmlFragment htmlFragment : htmlFragmentes) {
            map.put(htmlFragment.getCode(), htmlFragment);
        }
        return map;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "HtmlFragment:Code:ALL", allEntries = true),
            @CacheEvict(value = "HtmlFragment:Code:Tree", allEntries = true)
    })
    public void updateByPrimaryKeySelective(HtmlFragment record) {

        htmlFragmentMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "HtmlFragment:Code:ALL", allEntries = true),
            @CacheEvict(value = "HtmlFragment:Code:Tree", allEntries = true)
    })
    //彻底删除系统说明
    public void doBatchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        HtmlFragmentExample example = new HtmlFragmentExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsDeletedEqualTo(true);
        htmlFragmentMapper.deleteByExample(example);
    }

    //删除系统说明
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "HtmlFragment:Code:ALL", allEntries = true),
            @CacheEvict(value = "HtmlFragment:Code:Tree", allEntries = true)
    })
    public void batchDel(Integer[] ids) {
        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            HtmlFragment htmlFragment = new HtmlFragment();
            htmlFragment.setId(id);
            htmlFragment.setIsDeleted(true);
            htmlFragment.setSortOrder(getNextSortOrder("sys_html_fragment","is_deleted="+true));
            htmlFragmentMapper.updateByPrimaryKeySelective(htmlFragment);
        }
    }

    //返回系统说明
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "HtmlFragment:Code:ALL", allEntries = true),
            @CacheEvict(value = "HtmlFragment:Code:Tree", allEntries = true)
    })
    public void batchUnDel(Integer[] ids) {
        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            HtmlFragment htmlFragment = new HtmlFragment();
            htmlFragment.setId(id);
            htmlFragment.setIsDeleted(false);
            htmlFragment.setSortOrder(getNextSortOrder("sys_html_fragment","is_deleted="+false));
            htmlFragmentMapper.updateByPrimaryKeySelective(htmlFragment);
        }
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "HtmlFragment:Code:ALL", allEntries = true),
            @CacheEvict(value = "HtmlFragment:Code:Tree", allEntries = true)
    })
    public void updateRoles(int id, int roleId) {

        HtmlFragment htmlFragment = new HtmlFragment();
        htmlFragment.setId(id);
        htmlFragment.setRoleId(roleId);

        htmlFragmentMapper.updateByPrimaryKeySelective(htmlFragment);
    }

    // 读取二级目录（只返回第二级列表）
    @Cacheable(value = "HtmlFragment:Code:Tree", key = "#code")
    public List<HtmlFragment> twoLevelTree(String code) {

        List<HtmlFragment> menuList = new ArrayList<>();
        HtmlFragment top = codeKeyMap().get(code);
        if (top != null){

            {
                HtmlFragmentExample example = new HtmlFragmentExample();
                HtmlFragmentExample.Criteria criteria = example.createCriteria();
                criteria.andFidEqualTo(top.getId());
                example.setOrderByClause("sort_order desc");
                List<HtmlFragment> topChilds = htmlFragmentMapper.selectByExample(example);
                top.setChilds(topChilds);
            }
            // 第一级
            //menuList.add(top);


            for (HtmlFragment levelTwoChild : top.getChilds()) {

                {
                    HtmlFragmentExample example = new HtmlFragmentExample();
                    HtmlFragmentExample.Criteria criteria = example.createCriteria();
                    criteria.andFidEqualTo(levelTwoChild.getId());
                    example.setOrderByClause("sort_order desc");
                    List<HtmlFragment> levelTwoChilds = htmlFragmentMapper.selectByExample(example);
                    levelTwoChild.setChilds(levelTwoChilds);
                }

                // 第二级
                menuList.add(levelTwoChild);
            }
        }

        return menuList;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "HtmlFragment:Code:ALL", allEntries = true),
            @CacheEvict(value = "HtmlFragment:Code:Tree", allEntries = true)
    })
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        HtmlFragment entity = htmlFragmentMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        String tableName = "sys_html_fragment";
        String whereSql = null;
        adjustSortOrder(tableName, whereSql);
        if(baseSortOrder==null) return;

        HtmlFragmentExample example = new HtmlFragmentExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<HtmlFragment> overEntities = htmlFragmentMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            HtmlFragment targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder(tableName, whereSql, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(tableName, whereSql, baseSortOrder, targetEntity.getSortOrder());

            HtmlFragment record = new HtmlFragment();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            htmlFragmentMapper.updateByPrimaryKeySelective(record);
        }
    }
}
