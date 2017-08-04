package service.crs;

import domain.crs.CrsPostExpert;
import domain.crs.CrsPostExpertExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lm on 2017/7/31.
 */
@Service
public class CrsPostExpertService extends BaseMapper {

    public boolean idDuplicate(Integer id, int postId, int userId) {

        CrsPostExpertExample example = new CrsPostExpertExample();
        CrsPostExpertExample.Criteria criteria = example.createCriteria().andPostIdEqualTo(postId).andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return crsPostExpertMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CrsPostExpert record) {
        Assert.isTrue(!idDuplicate(null, record.getPostId(), record.getUserId()), "重复");
        record.setSortOrder(getNextSortOrder("crs_post_expert", "post_id=" + record.getPostId()));
        crsPostExpertMapper.insertSelective(record);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrsPostExpert record) {

        record.setPostId(null);
        record.setSortOrder(null);
        return crsPostExpertMapper.updateByPrimaryKeySelective(record);
    }


    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsPostExpertExample example = new CrsPostExpertExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        crsPostExpertMapper.deleteByExample(example);
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CrsPostExpert entity = crsPostExpertMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer postId = entity.getPostId();

        CrsPostExpertExample example = new CrsPostExpertExample();
        if (addNum > 0) {

            example.createCriteria().andPostIdEqualTo(postId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andPostIdEqualTo(postId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CrsPostExpert> overEntities = crsPostExpertMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CrsPostExpert targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("crs_post_expert", "post_id=" + postId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("crs_post_expert", "post_id=" + postId, baseSortOrder, targetEntity.getSortOrder());

            CrsPostExpert record = new CrsPostExpert();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            crsPostExpertMapper.updateByPrimaryKeySelective(record);
        }
    }
}
