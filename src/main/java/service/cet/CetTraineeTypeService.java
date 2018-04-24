package service.cet;

import domain.cet.CetTraineeType;
import domain.cet.CetTraineeTypeExample;
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
public class CetTraineeTypeService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        CetTraineeTypeExample example = new CetTraineeTypeExample();
        CetTraineeTypeExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetTraineeTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="CetTraineeType:ALL", allEntries = true)
    public void insertSelective(CetTraineeType record){

        record.setSortOrder(getNextSortOrder("cet_trainee_type", null));
        cetTraineeTypeMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetTraineeType:ALL", allEntries = true)
    public void del(Integer id){

        cetTraineeTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="CetTraineeType:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTraineeTypeExample example = new CetTraineeTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTraineeTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="CetTraineeType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(CetTraineeType record){
        return cetTraineeTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="CetTraineeType:ALL")
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
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value="CetTraineeType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_ASC;

        CetTraineeType entity = cetTraineeTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetTraineeTypeExample example = new CetTraineeTypeExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetTraineeType> overEntities = cetTraineeTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetTraineeType targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_trainee_type", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_trainee_type", null, baseSortOrder, targetEntity.getSortOrder());

            CetTraineeType record = new CetTraineeType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetTraineeTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
