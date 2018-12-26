package service.crs;

import domain.crs.CrsPostExpert;
import domain.crs.CrsPostExpertExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.constants.CrsConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lm on 2017/7/31.
 */
@Service
public class CrsPostExpertService extends CrsBaseMapper {

    public List<Integer> getExpertUserIds(int postId, Byte role) {

        CrsPostExpertExample example = new CrsPostExpertExample();
        CrsPostExpertExample.Criteria criteria = example.createCriteria().andPostIdEqualTo(postId);
        if(role!=null) criteria.andRoleEqualTo(role);

        List<CrsPostExpert> crsPostExperts = crsPostExpertMapper.selectByExample(example);
        List<Integer> expertIds = new ArrayList<>();
        for (CrsPostExpert crsPostExpert : crsPostExperts) {
            expertIds.add(crsPostExpert.getUserId());
        }

        return expertIds;
    }

    @Transactional
    public void updateExpertUserIds(int postId, Integer[] headUserIds, Integer[] leaderUserIds, Integer[] memberUserIds){

        CrsPostExpertExample example = new CrsPostExpertExample();
        example.createCriteria().andPostIdEqualTo(postId);
        crsPostExpertMapper.deleteByExample(example);

        if(headUserIds!=null) {
            for (Integer userId : headUserIds) {

                CrsPostExpert record = new CrsPostExpert();
                record.setPostId(postId);
                record.setUserId(userId);
                record.setRole(CrsConstants.CRS_POST_EXPERT_ROLE_HEAD);
                record.setSortOrder(getNextSortOrder("crs_post_expert", "post_id=" + record.getPostId()));
                crsPostExpertMapper.insertSelective(record);
            }
        }

        if(leaderUserIds!=null) {
            for (Integer userId : leaderUserIds) {

                CrsPostExpert record = new CrsPostExpert();
                record.setPostId(postId);
                record.setUserId(userId);
                record.setRole(CrsConstants.CRS_POST_EXPERT_ROLE_LEADER);
                record.setSortOrder(getNextSortOrder("crs_post_expert", "post_id=" + record.getPostId()));
                crsPostExpertMapper.insertSelective(record);
            }
        }

        if(memberUserIds!=null) {
            for (Integer userId : memberUserIds) {

                CrsPostExpert record = new CrsPostExpert();
                record.setPostId(postId);
                record.setUserId(userId);
                record.setRole(CrsConstants.CRS_POST_EXPERT_ROLE_MEMBER);
                record.setSortOrder(getNextSortOrder("crs_post_expert", "post_id=" + record.getPostId()));
                crsPostExpertMapper.insertSelective(record);
            }
        }
    }


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
