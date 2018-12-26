package service.pmd;

import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdConfigMemberTypeExample;
import domain.pmd.PmdNorm;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.PmdConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmdConfigMemberTypeService extends PmdBaseMapper {

    private final static String TABLE_NAME = "pmd_config_member_type";
    @Autowired
    private PmdNormService pmdNormService;

    public boolean idDuplicate(Integer id, int normId) {

        PmdConfigMemberTypeExample example = new PmdConfigMemberTypeExample();
        PmdConfigMemberTypeExample.Criteria criteria = example.createCriteria();

        // 类别为公式的计算方法，对应的党员分类必须唯一
        PmdNorm pmdNorm = pmdNormMapper.selectByPrimaryKey(normId);
        if(pmdNorm.getType() == PmdConstants.PMD_NORM_SET_TYPE_FORMULA){
            criteria.andNormIdEqualTo(pmdNorm.getId());
        }else {
            return false;
        }

        if (id != null) criteria.andIdNotEqualTo(id);

        return pmdConfigMemberTypeMapper.countByExample(example) > 0;
    }

    // 类别为公式的列表 <pmdNorm.formulaType, pmdConfigMemberType>
    @Cacheable(value = "PmdConfigMemberTypeMap:formula")
    public Map<Byte, PmdConfigMemberType> formulaMap(){

        List<PmdNorm> list = pmdNormService.list(PmdConstants.PMD_NORM_TYPE_PAY,
                PmdConstants.PMD_NORM_SET_TYPE_FORMULA);
        Map<Byte, PmdConfigMemberType> map = new HashMap<>();
        for (PmdNorm pmdNorm : list) {

            PmdConfigMemberTypeExample example = new PmdConfigMemberTypeExample();
            example.createCriteria().andIsDeletedEqualTo(false).andNormIdEqualTo(pmdNorm.getId());
            List<PmdConfigMemberType> pmdConfigMemberTypees = pmdConfigMemberTypeMapper.selectByExample(example);
            if(pmdConfigMemberTypees.size()>0){
                PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypees.get(0);
                map.put(pmdNorm.getFormulaType(), pmdConfigMemberType);
            }
        }

        return map;
    }

    @Cacheable(value = "PmdConfigMemberType", key = "#id")
    public PmdConfigMemberType get(int id) {

        return pmdConfigMemberTypeMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "PmdConfigMemberTypeList", allEntries = true),
            @CacheEvict(value = "PmdConfigMemberTypeMap:formula", allEntries = true)
    })
    public void insertSelective(PmdConfigMemberType record) {

        Assert.isTrue(!idDuplicate(record.getId(), record.getNormId()), "公式重复");

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "is_deleted=0"));
        pmdConfigMemberTypeMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "PmdConfigMemberTypeList", allEntries = true),
            @CacheEvict(value = "PmdConfigMemberTypeMap:formula", allEntries = true),
            @CacheEvict(value = "PmdConfigMemberType", key = "#id")
    })
    public void del(Integer id) {

        pmdConfigMemberTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "PmdConfigMemberTypeList", allEntries = true),
            @CacheEvict(value = "PmdConfigMemberTypeMap:formula", allEntries = true),
            @CacheEvict(value = "PmdConfigMemberType", allEntries = true)
    })
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PmdConfigMemberTypeExample example = new PmdConfigMemberTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdConfigMemberTypeMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "PmdConfigMemberTypeList", allEntries = true),
            @CacheEvict(value = "PmdConfigMemberTypeMap:formula", allEntries = true),
            @CacheEvict(value = "PmdConfigMemberType", key = "#record.id")
    })
    public int updateByPrimaryKeySelective(PmdConfigMemberType record) {

        if(record.getNormId()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getNormId()), "公式重复");

        return pmdConfigMemberTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "PmdConfigMemberTypeList", key = "#type")
    public List<PmdConfigMemberType> list(byte type) {

        PmdConfigMemberTypeExample example = new PmdConfigMemberTypeExample();
        example.createCriteria().andIsDeletedEqualTo(false)
                .andTypeEqualTo(type);
        example.setOrderByClause("sort_order asc");

        List<PmdConfigMemberType> pmdConfigMemberTypees = pmdConfigMemberTypeMapper.selectByExample(example);
        List<PmdConfigMemberType> list = new ArrayList<>();
        for (PmdConfigMemberType pmdConfigMemberType : pmdConfigMemberTypees) {
            list.add(pmdConfigMemberType);
        }

        return list;
    }

    /**
     * 排序 ，升序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "PmdConfigMemberTypeList", allEntries = true),
            @CacheEvict(value = "PmdConfigMemberTypeMap:formula", allEntries = true),
            @CacheEvict(value = "PmdConfigMemberType", allEntries = true)
    })
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;
        byte orderBy = ORDER_BY_ASC;
        PmdConfigMemberType entity = pmdConfigMemberTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        PmdConfigMemberTypeExample example = new PmdConfigMemberTypeExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andIsDeletedEqualTo(false).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andIsDeletedEqualTo(false).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PmdConfigMemberType> overEntities = pmdConfigMemberTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PmdConfigMemberType targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder(TABLE_NAME, "is_deleted=0", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(TABLE_NAME, "is_deleted=0", baseSortOrder, targetEntity.getSortOrder());

            PmdConfigMemberType record = new PmdConfigMemberType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            pmdConfigMemberTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
