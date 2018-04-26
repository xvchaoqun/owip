package service.sc.scGroup;

import domain.sc.scGroup.ScGroupMember;
import domain.sc.scGroup.ScGroupMemberExample;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ScGroupMemberService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScGroupMemberExample example = new ScGroupMemberExample();
        ScGroupMemberExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scGroupMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScGroupMember record){

        record.setSortOrder(getNextSortOrder("sc_group_member", "is_current=" + BooleanUtils.isTrue(record.getIsCurrent())));
        scGroupMemberMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scGroupMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void transfer(Integer[] ids, boolean isCurrent){

        if(ids==null || ids.length==0) return;

        ScGroupMemberExample example = new ScGroupMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScGroupMember record = new ScGroupMember();
        record.setIsCurrent(isCurrent);

        scGroupMemberMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScGroupMemberExample example = new ScGroupMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scGroupMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScGroupMember record){
        return scGroupMemberMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ScGroupMember entity = scGroupMemberMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Boolean isCurrent = BooleanUtils.isTrue(entity.getIsCurrent());

        ScGroupMemberExample example = new ScGroupMemberExample();
        if (addNum > 0) {

            example.createCriteria().andIsCurrentEqualTo(isCurrent).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andIsCurrentEqualTo(isCurrent).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ScGroupMember> overEntities = scGroupMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ScGroupMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("sc_group_member", "is_current=" + isCurrent, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("sc_group_member", "is_current=" + isCurrent, baseSortOrder, targetEntity.getSortOrder());

            ScGroupMember record = new ScGroupMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            scGroupMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
