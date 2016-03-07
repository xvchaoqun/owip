package service.abroad;

import domain.ApplicatPost;
import domain.ApplicatPostExample;
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
public class ApplicatPostService extends BaseMapper {

    public boolean idDuplicate(Integer id, int postId){

        ApplicatPostExample example = new ApplicatPostExample();
        ApplicatPostExample.Criteria criteria = example.createCriteria().andPostIdEqualTo(postId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return applicatPostMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ApplicatPost:ALL", allEntries = true)
    public int insertSelective(ApplicatPost record){

        Assert.isTrue(!idDuplicate(null, record.getPostId()));
        applicatPostMapper.insertSelective(record);

        Integer id = record.getId();
        ApplicatPost _record = new ApplicatPost();
        _record.setId(id);
        _record.setSortOrder(id);
        return applicatPostMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="ApplicatPost:ALL", allEntries = true)
    public void del(Integer id){

        applicatPostMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="ApplicatPost:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApplicatPostExample example = new ApplicatPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        applicatPostMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="ApplicatPost:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(ApplicatPost record){
        if(record.getPostId()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getPostId()));
        return applicatPostMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="ApplicatPost:ALL")
    public Map<Integer, ApplicatPost> findAll() {

        ApplicatPostExample example = new ApplicatPostExample();
        example.setOrderByClause("sort_order desc");
        List<ApplicatPost> applicatPostes = applicatPostMapper.selectByExample(example);
        Map<Integer, ApplicatPost> map = new LinkedHashMap<>();
        for (ApplicatPost applicatPost : applicatPostes) {
            map.put(applicatPost.getId(), applicatPost);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "ApplicatPost:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ApplicatPost entity = applicatPostMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ApplicatPostExample example = new ApplicatPostExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ApplicatPost> overEntities = applicatPostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ApplicatPost targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("abroad_applicat_post", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("abroad_applicat_post", baseSortOrder, targetEntity.getSortOrder());

            ApplicatPost record = new ApplicatPost();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            applicatPostMapper.updateByPrimaryKeySelective(record);
        }
    }
}
