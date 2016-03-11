package service.abroad;

import domain.SafeBox;
import domain.SafeBoxExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SafeBoxService extends BaseMapper {


    public SafeBox getByCode(String code) {

        SafeBoxExample example = new SafeBoxExample();
        example.createCriteria().andCodeEqualTo(code.trim());
        List<SafeBox> safeBoxes = safeBoxMapper.selectByExample(example);
        if(safeBoxes.size()>0) return safeBoxes.get(0);
        return null;
    }

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        SafeBoxExample example = new SafeBoxExample();
        SafeBoxExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return safeBoxMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="SafeBox:ALL", allEntries = true)
    public int insertSelective(SafeBox record){

        Assert.isTrue(!idDuplicate(null, record.getCode()));
        safeBoxMapper.insertSelective(record);

        Integer id = record.getId();
        SafeBox _record = new SafeBox();
        _record.setId(id);
        _record.setSortOrder(id);
        return safeBoxMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="SafeBox:ALL", allEntries = true)
    public void del(Integer id){

        safeBoxMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="SafeBox:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SafeBoxExample example = new SafeBoxExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        safeBoxMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="SafeBox:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(SafeBox record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
        return safeBoxMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="SafeBox:ALL")
    public Map<Integer, SafeBox> findAll() {

        SafeBoxExample example = new SafeBoxExample();
        example.setOrderByClause("sort_order desc");
        List<SafeBox> safeBoxes = safeBoxMapper.selectByExample(example);
        Map<Integer, SafeBox> map = new LinkedHashMap<>();
        for (SafeBox safeBox : safeBoxes) {
            map.put(safeBox.getId(), safeBox);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "SafeBox:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        SafeBox entity = safeBoxMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        SafeBoxExample example = new SafeBoxExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<SafeBox> overEntities = safeBoxMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            SafeBox targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("abroad_safe_box", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("abroad_safe_box", baseSortOrder, targetEntity.getSortOrder());

            SafeBox record = new SafeBox();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            safeBoxMapper.updateByPrimaryKeySelective(record);
        }
    }
}
