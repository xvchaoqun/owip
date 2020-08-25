package service.cla;

import domain.cadre.CadreView;
import domain.cla.ClaApplicatCadre;
import domain.cla.ClaApplicatCadreExample;
import domain.cla.ClaApplicatType;
import domain.cla.ClaApplicatTypeExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.cadre.CadreService;
import sys.constants.ClaConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class ClaApplicatTypeService extends ClaBaseMapper {

    @Autowired
    private CadreService cadreService;

    // 已分配干部身份的干部
    public Set<Integer> getCadreIds(Integer typeId) {

        Set<Integer> cadreIdSet = new HashSet<Integer>();
        ClaApplicatCadreExample example = new ClaApplicatCadreExample();
        if (typeId != null) example.createCriteria().andTypeIdEqualTo(typeId);
        List<ClaApplicatCadre> applicatCadres = claApplicatCadreMapper.selectByExample(example);
        for (ClaApplicatCadre applicatCadre : applicatCadres) {

            int cadreId = applicatCadre.getCadreId();
            CadreView cadreView = CmTag.getCadreById(cadreId);
            if (cadreView != null && ClaConstants.CLA_APPLICAT_CADRE_STATUS_SET
                    .contains(cadreView.getStatus()))
                cadreIdSet.add(cadreId);
        }

        return cadreIdSet;
    }

    @Transactional
    public void updateCadreIds(int typeId, Integer[] cadreIds) {

        ClaApplicatCadreExample example = new ClaApplicatCadreExample();
        example.createCriteria().andTypeIdEqualTo(typeId);
        claApplicatCadreMapper.deleteByExample(example);

        if (cadreIds == null || cadreIds.length == 0) return;

        for (Integer cadreId : cadreIds) {

            ClaApplicatCadre record = new ClaApplicatCadre();
            record.setTypeId(typeId);
            record.setCadreId(cadreId);
            record.setSortOrder(getNextSortOrder("cla_applicat_type", null));
            claApplicatCadreMapper.insert(record);
        }
    }

    /*public Set<Integer> getPostIds (Integer typeId){

        Set<Integer> postIdSet = new HashSet<Integer>();
        ApplicatPostExample example = new ApplicatPostExample();
        if(typeId!=null) example.createCriteria().andTypeIdEqualTo(typeId);
        List<ApplicatPost> applicatPosts = applicatPostMapper.selectByExample(example);
        for (ApplicatPost applicatPost : applicatPosts) {
            postIdSet.add(applicatPost.getPostId());
        }

        return postIdSet;
    }

    @Transactional
    public void updatePostIds(int typeId, Integer[] postIds){

        ApplicatPostExample example = new ApplicatPostExample();
        example.createCriteria().andTypeIdEqualTo(typeId);
        applicatPostMapper.deleteByExample(example);

        if(postIds==null || postIds.length==0) return ;

        for (Integer postId : postIds) {

            ApplicatPost record = new ApplicatPost();
            record.setTypeId(typeId);
            record.setPostId(postId);
            applicatPostMapper.insert(record);

            ApplicatPost _record = new ApplicatPost();
            _record.setId(record.getId());
            _record.setSortOrder(record.getId());
            applicatPostMapper.updateByPrimaryKeySelective(_record);
        }
    }*/

    public boolean idDuplicate(Integer id, String name) {

        Assert.isTrue(StringUtils.isNotBlank(name), "name is blank");

        ClaApplicatTypeExample example = new ClaApplicatTypeExample();
        ClaApplicatTypeExample.Criteria criteria = example.createCriteria().andNameEqualTo(name);
        if (id != null) criteria.andIdNotEqualTo(id);

        return claApplicatTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value = "ClaApplicatType:ALL", allEntries = true)
    public int insertSelective(ClaApplicatType record) {

        Assert.isTrue(!idDuplicate(null, record.getName()), "duplicate name");
        claApplicatTypeMapper.insertSelective(record);

        Integer id = record.getId();
        ClaApplicatType _record = new ClaApplicatType();
        _record.setId(id);
        _record.setSortOrder(id);
        return claApplicatTypeMapper.updateByPrimaryKeySelective(_record);
    }

    @Transactional
    @CacheEvict(value = "ClaApplicatType:ALL", allEntries = true)
    public void del(Integer id) {

        claApplicatTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value = "ClaApplicatType:ALL", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ClaApplicatTypeExample example = new ClaApplicatTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        claApplicatTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value = "ClaApplicatType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(ClaApplicatType record) {
        if (StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "duplicate name");
        return claApplicatTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "ClaApplicatType:ALL")
    public Map<Integer, ClaApplicatType> findAll() {

        ClaApplicatTypeExample example = new ClaApplicatTypeExample();
        example.setOrderByClause("sort_order desc");
        List<ClaApplicatType> applicatTypees = claApplicatTypeMapper.selectByExample(example);
        Map<Integer, ClaApplicatType> map = new LinkedHashMap<>();
        for (ClaApplicatType applicatType : applicatTypees) {
            map.put(applicatType.getId(), applicatType);
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
    @CacheEvict(value = "ClaApplicatType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        ClaApplicatType entity = claApplicatTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ClaApplicatTypeExample example = new ClaApplicatTypeExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ClaApplicatType> overEntities = claApplicatTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            ClaApplicatType targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("cla_applicat_type", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cla_applicat_type", null, baseSortOrder, targetEntity.getSortOrder());

            ClaApplicatType record = new ClaApplicatType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            claApplicatTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
