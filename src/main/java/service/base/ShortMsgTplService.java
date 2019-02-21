package service.base;

import domain.base.ShortMsgTpl;
import domain.base.ShortMsgTplExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class ShortMsgTplService extends BaseMapper {


    @Transactional
    public void insertSelective(ShortMsgTpl record){

        record.setSortOrder(getNextSortOrder("base_short_msg_tpl", null));
        shortMsgTplMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        shortMsgTplMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ShortMsgTplExample example = new ShortMsgTplExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        shortMsgTplMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ShortMsgTpl record){

        return shortMsgTplMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
	public void changeOrder(int id, int addNum) {

		if(addNum == 0) return ;
		byte orderBy = ORDER_BY_DESC;
		ShortMsgTpl entity = shortMsgTplMapper.selectByPrimaryKey(id);
		Integer baseSortOrder = entity.getSortOrder();

		ShortMsgTplExample example = new ShortMsgTplExample();
		if (addNum*orderBy > 0) {

			example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
			example.setOrderByClause("sort_order asc");
		}else {

			example.createCriteria().andSortOrderLessThan(baseSortOrder);
			example.setOrderByClause("sort_order desc");
		}

		List<ShortMsgTpl> overEntities = shortMsgTplMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
		if(overEntities.size()>0) {

			ShortMsgTpl targetEntity = overEntities.get(overEntities.size()-1);

			if (addNum*orderBy > 0)
				commonMapper.downOrder("base_short_msg_tpl", null, baseSortOrder, targetEntity.getSortOrder());
			else
				commonMapper.upOrder("base_short_msg_tpl", null, baseSortOrder, targetEntity.getSortOrder());

			ShortMsgTpl record = new ShortMsgTpl();
			record.setId(id);
			record.setSortOrder(targetEntity.getSortOrder());
			shortMsgTplMapper.updateByPrimaryKeySelective(record);
		}
	}
}
