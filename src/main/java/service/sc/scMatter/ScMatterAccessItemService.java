package service.sc.scMatter;

import domain.sc.scMatter.ScMatterAccessItem;
import domain.sc.scMatter.ScMatterAccessItemExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;

import java.util.Arrays;

@Service
public class ScMatterAccessItemService extends ScBaseMapper {

    @Transactional
    public void insertSelective(ScMatterAccessItem record){

        scMatterAccessItemMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scMatterAccessItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterAccessItemExample example = new ScMatterAccessItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMatterAccessItemMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScMatterAccessItem record){
        return scMatterAccessItemMapper.updateByPrimaryKeySelective(record);
    }

    /*public Map<Integer, ScMatterAccessItem> findAll() {

        ScMatterAccessItemExample example = new ScMatterAccessItemExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<ScMatterAccessItem> scMatterAccessItemes = scMatterAccessItemMapper.selectByExample(example);
        Map<Integer, ScMatterAccessItem> map = new LinkedHashMap<>();
        for (ScMatterAccessItem scMatterAccessItem : scMatterAccessItemes) {
            map.put(scMatterAccessItem.getId(), scMatterAccessItem);
        }

        return map;
    }*/
}
