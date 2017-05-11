package service.abroad;

import domain.abroad.ApplicatCadre;
import domain.abroad.ApplicatCadreExample;
import domain.abroad.ApplicatType;
import domain.abroad.ApplicatTypeExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.*;

@Service
public class ApplicatTypeService extends BaseMapper {

    public Set<Integer> getCadreIds (Integer typeId){

        Set<Integer> cadreIdSet = new HashSet<Integer>();
        ApplicatCadreExample example = new ApplicatCadreExample();
        if(typeId!=null) example.createCriteria().andTypeIdEqualTo(typeId);
        List<ApplicatCadre> applicatCadres = applicatCadreMapper.selectByExample(example);
        for (ApplicatCadre applicatCadre : applicatCadres) {
            cadreIdSet.add(applicatCadre.getCadreId());
        }

        return cadreIdSet;
    }

    @Transactional
    public void updateCadreIds(int typeId, Integer[] cadreIds){

        ApplicatCadreExample example = new ApplicatCadreExample();
        example.createCriteria().andTypeIdEqualTo(typeId);
        applicatCadreMapper.deleteByExample(example);

        if(cadreIds==null || cadreIds.length==0) return ;

        for (Integer cadreId : cadreIds) {

            ApplicatCadre record = new ApplicatCadre();
            record.setTypeId(typeId);
            record.setCadreId(cadreId);
            record.setSortOrder(getNextSortOrder("abroad_applicat_type", "1=1"));
            applicatCadreMapper.insert(record);
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

    public boolean idDuplicate(Integer id, String name){

        Assert.isTrue(StringUtils.isNotBlank(name), "name is blank");

        ApplicatTypeExample example = new ApplicatTypeExample();
        ApplicatTypeExample.Criteria criteria = example.createCriteria().andNameEqualTo(name);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return applicatTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ApplicatType:ALL", allEntries = true)
    public int insertSelective(ApplicatType record){

        Assert.isTrue(!idDuplicate(null, record.getName()), "duplicate name");
        applicatTypeMapper.insertSelective(record);

        Integer id = record.getId();
        ApplicatType _record = new ApplicatType();
        _record.setId(id);
        _record.setSortOrder(id);
        return applicatTypeMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="ApplicatType:ALL", allEntries = true)
    public void del(Integer id){

        applicatTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="ApplicatType:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApplicatTypeExample example = new ApplicatTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        applicatTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="ApplicatType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(ApplicatType record){
        if(StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "duplicate name");
        return applicatTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="ApplicatType:ALL")
    public Map<Integer, ApplicatType> findAll() {

        ApplicatTypeExample example = new ApplicatTypeExample();
        example.setOrderByClause("sort_order desc");
        List<ApplicatType> applicatTypees = applicatTypeMapper.selectByExample(example);
        Map<Integer, ApplicatType> map = new LinkedHashMap<>();
        for (ApplicatType applicatType : applicatTypees) {
            map.put(applicatType.getId(), applicatType);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "ApplicatType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ApplicatType entity = applicatTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ApplicatTypeExample example = new ApplicatTypeExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ApplicatType> overEntities = applicatTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ApplicatType targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("abroad_applicat_type", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("abroad_applicat_type", null, baseSortOrder, targetEntity.getSortOrder());

            ApplicatType record = new ApplicatType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            applicatTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
