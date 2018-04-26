package service.crs;

import controller.global.OpException;
import domain.crs.CrsApplyUser;
import domain.crs.CrsApplyUserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.CrsConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CrsApplyUserService extends BaseMapper {

    public boolean idDuplicate(Integer id, int userId){

        CrsApplyUserExample example = new CrsApplyUserExample();
        CrsApplyUserExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return crsApplyUserMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CrsApplyUser record, Integer[] userIds){

        if(userIds==null || userIds.length==0) return;

        record.setCreateTime(new Date());

        for (Integer userId : userIds) {

            record.setId(null);
            record.setUserId(userId);
            if(idDuplicate(null, record.getUserId())){
                throw new OpException("补报人员重复：{0}", record.getUser().getRealname());
            }
            record.setSortOrder(getNextSortOrder("crs_apply_user", "post_id=" + record.getPostId()));
            crsApplyUserMapper.insertSelective(record);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CrsApplyUserExample example = new CrsApplyUserExample();
        example.createCriteria().andIdIn(Arrays.asList(ids))
                .andStatusNotEqualTo(CrsConstants.CRS_APPLY_USER_STATUS_FINISH);
        crsApplyUserMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrsApplyUser record){
        if(record.getUserId()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getUserId()), "duplicate");
        return crsApplyUserMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_ASC;

        CrsApplyUser entity = crsApplyUserMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CrsApplyUserExample example = new CrsApplyUserExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CrsApplyUser> overEntities = crsApplyUserMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CrsApplyUser targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("crs_apply_user", "post_id=" + entity.getPostId(), baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("crs_apply_user", "post_id=" + entity.getPostId(), baseSortOrder, targetEntity.getSortOrder());

            CrsApplyUser record = new CrsApplyUser();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            crsApplyUserMapper.updateByPrimaryKeySelective(record);
        }
    }
}
