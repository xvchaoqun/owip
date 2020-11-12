package service.cet;

import domain.cet.CetTraineeType;
import domain.cet.CetTraineeTypeExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<CetTraineeType> records = cetTraineeTypeMapper.selectByExample(example);
        Map<Integer, CetTraineeType> map = new LinkedHashMap<>();
        for (CetTraineeType record : records) {
            map.put(record.getId(), record);
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

    @Transactional
    public String genCode(){

        String prefix = "t";
        String code = "";
        int count = 0;
        do {
            code = prefix + "_" + RandomStringUtils.randomAlphanumeric(6).toLowerCase();
            CetTraineeTypeExample example = new CetTraineeTypeExample();
            example.createCriteria().andCodeEqualTo(code);
            count = (int) cetTraineeTypeMapper.countByExample(example);
        } while(count>0);
        return code;
    }
}
