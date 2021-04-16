package service.parttime;

import domain.cadre.*;
import domain.parttime.ParttimeApplicatCadreExample;
import domain.parttime.ParttimeApplicatType;
import domain.parttime.ParttimeApplicatTypeExample;
import domain.parttime.ParttimeApplicatCadre;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.parttime.ParttimeApplicatCadreMapper;
import persistence.parttime.ParttimeApplicatTypeMapper;
import service.BaseMapper;
import sys.constants.ParttimeConstants;
import sys.tags.CmTag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ParttimeApplicatTypeService extends BaseMapper {
    @Autowired
    private ParttimeApplicatTypeMapper parttimeApplicatTypeMapper;
    @Autowired
    private ParttimeApplicatCadreMapper parttimeApplicatCadreMapper;

    public boolean idDuplicate(Integer id, String name) {

        Assert.isTrue(StringUtils.isNotBlank(name), "name is blank");

        ParttimeApplicatTypeExample example = new ParttimeApplicatTypeExample();
        ParttimeApplicatTypeExample.Criteria criteria = example.createCriteria().andNameEqualTo(name);
        if (id != null) criteria.andIdNotEqualTo(id);

        return parttimeApplicatTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value = "ParttimeApplicatType:ALL", allEntries = true)
    public int insertSelective(ParttimeApplicatType record) {

        Assert.isTrue(!idDuplicate(null, record.getName()), "duplicate name");
        parttimeApplicatTypeMapper.insertSelective(record);

        Integer id = record.getId();
        ParttimeApplicatType _record = new ParttimeApplicatType();
        _record.setId(id);
        _record.setSortOrder(id);
        return parttimeApplicatTypeMapper.updateByPrimaryKeySelective(_record);
    }

    @Transactional
    @CacheEvict(value = "ParttimeApplicatType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(ParttimeApplicatType record) {
        if (StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "duplicate name");
        return parttimeApplicatTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @CacheEvict(value = "ParttimeApplicatType:ALL", allEntries = true)
    public void del(Integer id) {

        parttimeApplicatTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value = "ParttimeApplicatType:ALL", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ParttimeApplicatTypeExample example = new ParttimeApplicatTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        parttimeApplicatTypeMapper.deleteByExample(example);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "ParttimeApplicatType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        ParttimeApplicatType entity = parttimeApplicatTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ParttimeApplicatTypeExample example = new ParttimeApplicatTypeExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ParttimeApplicatType> overEntities = parttimeApplicatTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            ParttimeApplicatType targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("parttime_applicat_type", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("parttime_applicat_type", null, baseSortOrder, targetEntity.getSortOrder());

            ParttimeApplicatType record = new ParttimeApplicatType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            parttimeApplicatTypeMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 已分配干部身份的干部
    public Set<Integer> getCadreIds(Integer typeId) {

        Set<Integer> cadreIdSet = new HashSet<Integer>();
        ParttimeApplicatCadreExample example = new ParttimeApplicatCadreExample();
        if (typeId != null) example.createCriteria().andTypeIdEqualTo(typeId);
        List<ParttimeApplicatCadre> applicatCadres = parttimeApplicatCadreMapper.selectByExample(example);
        for (ParttimeApplicatCadre applicatCadre : applicatCadres) {

            int cadreId = applicatCadre.getCadreId();
            CadreView cadreView = CmTag.getCadreById(cadreId);
            if (cadreView != null && ParttimeConstants.PARTTIME_APPLICAT_CADRE_STATUS_SET
                    .contains(cadreView.getStatus()))
                cadreIdSet.add(cadreId);
        }

        return cadreIdSet;
    }

    @Transactional
    public void updateCadreIds(int typeId, Integer[] cadreIds) {

        ParttimeApplicatCadreExample example = new ParttimeApplicatCadreExample();
        example.createCriteria().andTypeIdEqualTo(typeId);
        parttimeApplicatCadreMapper.deleteByExample(example);

        if (cadreIds == null || cadreIds.length == 0) return;

        for (Integer cadreId : cadreIds) {

            ParttimeApplicatCadre record = new ParttimeApplicatCadre();
            record.setTypeId(typeId);
            record.setCadreId(cadreId);
            record.setSortOrder(getNextSortOrder("parttime_applicat_type", null));
            parttimeApplicatCadreMapper.insert(record);
        }
    }
}
