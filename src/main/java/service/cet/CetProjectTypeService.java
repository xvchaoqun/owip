package service.cet;

import domain.cet.CetProjectType;
import domain.cet.CetProjectTypeExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetProjectTypeService extends CetBaseMapper {

    public boolean idDuplicate(Integer id, String name){

        Assert.isTrue(StringUtils.isNotBlank(name), "类型为空");

        CetProjectTypeExample example = new CetProjectTypeExample();
        CetProjectTypeExample.Criteria criteria = example.createCriteria().andNameEqualTo(name.trim());
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetProjectTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="CetProjectType:ALL", allEntries = true)
    public void insertSelective(CetProjectType record){

        Assert.isTrue(!idDuplicate(null, record.getName()), "类型重复");
        record.setSortOrder(getNextSortOrder("cet_project_type", null));
        cetProjectTypeMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetProjectType:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetProjectTypeExample example = new CetProjectTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetProjectTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="CetProjectType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(CetProjectType record){

        if(StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "类型重复");

        return cetProjectTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CetProjectType:ALL")
    public Map<Integer, CetProjectType> findAll() {

        CetProjectTypeExample example = new CetProjectTypeExample();
        example.createCriteria();
        example.setOrderByClause("sort_order asc");
        List<CetProjectType> cetProjectTypees = cetProjectTypeMapper.selectByExample(example);
        Map<Integer, CetProjectType> map = new LinkedHashMap<>();
        for (CetProjectType cetProjectType : cetProjectTypees) {
            map.put(cetProjectType.getId(), cetProjectType);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetProjectType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("cet_project_type", null, ORDER_BY_ASC, id, addNum);
    }
}
