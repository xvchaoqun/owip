package service.party;

import domain.party.Organizer;
import domain.party.OrganizerExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class OrganizerService extends BaseMapper {

    public boolean idDuplicate(Integer id, byte type, int userId){

        OrganizerExample example = new OrganizerExample();
        OrganizerExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type)
                .andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return organizerMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(Organizer record){

        Assert.isTrue(!idDuplicate(null, record.getType(), record.getUserId()), "duplicate");
        record.setSortOrder(getNextSortOrder("ow_organizer", null));
        organizerMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        OrganizerExample example = new OrganizerExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        organizerMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(Organizer record){

        Assert.isTrue(!idDuplicate(record.getId(), record.getType(), record.getUserId()), "duplicate");
        return organizerMapper.updateByPrimaryKeySelective(record);
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

        Organizer entity = organizerMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        byte type = entity.getType();
        byte status = entity.getStatus();

        OrganizerExample example = new OrganizerExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andTypeEqualTo(type).andStatusEqualTo(status)
                    .andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andTypeEqualTo(type).andStatusEqualTo(status)
                    .andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Organizer> overEntities = organizerMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            Organizer targetEntity = overEntities.get(overEntities.size()-1);

            String whereSql = String.format("type=%s and status=%s", type, status);
            if (addNum*orderBy > 0)
                commonMapper.downOrder("ow_organizer", whereSql, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_organizer", whereSql, baseSortOrder, targetEntity.getSortOrder());

            Organizer record = new Organizer();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            organizerMapper.updateByPrimaryKeySelective(record);
        }
    }
}
