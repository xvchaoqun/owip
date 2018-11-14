package service.unit;

import domain.unit.UnitPost;
import domain.unit.UnitPostExample;
import domain.unit.UnitPostView;
import domain.unit.UnitPostViewExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnitPostService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code) {

        UnitPostExample example = new UnitPostExample();
        UnitPostExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return unitPostMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="unitPosts", allEntries = true)
    public void insertSelective(UnitPost record) {

        Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate");
        record.setSortOrder(getNextSortOrder("unit_post",
                String.format("unit_id=%s and status=%s", record.getUnitId(), record.getStatus())));
        unitPostMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="unitPosts", allEntries = true)
    public void del(Integer id) {

        unitPostMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="unitPosts", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        UnitPostExample example = new UnitPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitPostMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="unitPosts", allEntries = true)
    public int updateByPrimaryKeySelective(UnitPost record) {
        if (record.getCode() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");
        return unitPostMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="unitPosts", key = "#unitId")
    public Map<Integer, UnitPostView> findAll(int unitId) {

        UnitPostViewExample example = new UnitPostViewExample();
        example.createCriteria().andUnitIdEqualTo(unitId);
        example.setOrderByClause("sort_order desc");
        List<UnitPostView> records = unitPostViewMapper.selectByExample(example);
        Map<Integer, UnitPostView> map = new LinkedHashMap<>();
        for (UnitPostView unitPost : records) {
            map.put(unitPost.getId(), unitPost);
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
    @CacheEvict(value="unitPosts", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        byte orderBy = ORDER_BY_DESC;

        UnitPost entity = unitPostMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitPostExample example = new UnitPostExample();
        if (addNum * orderBy > 0) {

            example.createCriteria().andUnitIdEqualTo(entity.getUnitId()).andStatusEqualTo(entity.getStatus())
                    .andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andUnitIdEqualTo(entity.getUnitId()).andStatusEqualTo(entity.getStatus())
                    .andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<UnitPost> overEntities = unitPostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            UnitPost targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum * orderBy > 0)
                commonMapper.downOrder("unit_post", String.format("unit_id=%s and status=%s", entity.getUnitId(), entity.getStatus()),
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("unit_post", String.format("unit_id=%s and status=%s", entity.getUnitId(), entity.getStatus()),
                        baseSortOrder, targetEntity.getSortOrder());

            UnitPost record = new UnitPost();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitPostMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    @CacheEvict(value="unitPosts", allEntries = true)
    public void abolish(int id, Date abolishDate) {

        UnitPost record = new UnitPost();
        record.setId(id);
        record.setAbolishDate(abolishDate);
        record.setStatus(SystemConstants.UNIT_POST_STATUS_ABOLISH);

        UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
        record.setSortOrder(getNextSortOrder("unit_post",
                String.format("unit_id=%s and status=%s", unitPost.getUnitId(), SystemConstants.UNIT_POST_STATUS_ABOLISH)));

        unitPostMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @CacheEvict(value="unitPosts", allEntries = true)
    public void unabolish(int id) {

        UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
        int sortOrder = getNextSortOrder("unit_post",
                String.format("unit_id=%s and status=%s", unitPost.getUnitId(), SystemConstants.UNIT_POST_STATUS_NORMAL));

        commonMapper.excuteSql(String.format("update unit_post set abolish_date=null, status=%s, sort_order=%s where id=%s",
                SystemConstants.UNIT_POST_STATUS_NORMAL, sortOrder, id));
    }
}
