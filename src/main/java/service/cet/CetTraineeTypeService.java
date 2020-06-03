package service.cet;

import domain.cet.CetTraineeType;
import domain.cet.CetTraineeTypeExample;
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
public class CetTraineeTypeService extends CetBaseMapper {

    public CetTraineeType getByName(String name) {

        if(StringUtils.isBlank(name)) return null;

        CetTraineeTypeExample example = new CetTraineeTypeExample();
        example.createCriteria().andNameEqualTo(name);
        List<CetTraineeType> upperTrainTypes = cetTraineeTypeMapper.selectByExample(example);
        if (upperTrainTypes.size() == 1) {
            return upperTrainTypes.get(0);
        }
        return null;
    }

    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        CetTraineeTypeExample example = new CetTraineeTypeExample();
        CetTraineeTypeExample.Criteria criteria = example.createCriteria();
        if (id != null) criteria.andIdNotEqualTo(id);

        return cetTraineeTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value = "CetTraineeType:ALL", allEntries = true)
    public void insertSelective(CetTraineeType record) {

        record.setSortOrder(getNextSortOrder("cet_trainee_type", null));
        cetTraineeTypeMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "CetTraineeType:ALL", allEntries = true)
    public void del(Integer id) {

        cetTraineeTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value = "CetTraineeType:ALL", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CetTraineeTypeExample example = new CetTraineeTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTraineeTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value = "CetTraineeType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(CetTraineeType record) {
        return cetTraineeTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "CetTraineeType:ALL")
    public Map<Integer, CetTraineeType> findAll() {

        CetTraineeTypeExample example = new CetTraineeTypeExample();
        example.createCriteria();
        example.setOrderByClause("sort_order asc");
        List<CetTraineeType> cetCourseTypees = cetTraineeTypeMapper.selectByExample(example);
        Map<Integer, CetTraineeType> map = new LinkedHashMap<>();
        for (CetTraineeType cetCourseType : cetCourseTypees) {
            map.put(cetCourseType.getId(), cetCourseType);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "CetTraineeType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("cet_trainee_type", null, ORDER_BY_ASC, id, addNum);
    }
}
