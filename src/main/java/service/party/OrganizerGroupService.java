package service.party;

import domain.party.OrganizerGroup;
import domain.party.OrganizerGroupExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class OrganizerGroupService extends BaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        OrganizerGroupExample example = new OrganizerGroupExample();
        OrganizerGroupExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return organizerGroupMapper.countByExample(example) > 0;
    }*/

    @Transactional
    public void insertSelective(OrganizerGroup record){

        record.setSortOrder(getNextSortOrder("ow_organizer_group", null));
        organizerGroupMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        OrganizerGroupExample example = new OrganizerGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        organizerGroupMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(OrganizerGroup record){
        return organizerGroupMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        OrganizerGroup entity = organizerGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        OrganizerGroupExample example = new OrganizerGroupExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<OrganizerGroup> overEntities = organizerGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            OrganizerGroup targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("ow_organizer_group", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_organizer_group", null, baseSortOrder, targetEntity.getSortOrder());

            OrganizerGroup record = new OrganizerGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            organizerGroupMapper.updateByPrimaryKeySelective(record);
        }
    }
}
