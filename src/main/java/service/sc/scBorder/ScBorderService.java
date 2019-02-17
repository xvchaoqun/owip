package service.sc.scBorder;

import domain.cadre.CadreView;
import domain.sc.scBorder.ScBorder;
import domain.sc.scBorder.ScBorderExample;
import domain.sc.scBorder.ScBorderItem;
import domain.sc.scBorder.ScBorderItemExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sc.ScBaseMapper;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScBorderService extends ScBaseMapper {

    @Transactional
    public void insertSelective(ScBorder record){

        scBorderMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scBorderMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScBorderExample example = new ScBorderExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scBorderMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScBorder record){
        return scBorderMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void updateCadreIds(Integer borderId, byte type, Integer[] cadreIds) {

        {
            ScBorderItemExample example = new ScBorderItemExample();
            ScBorderItemExample.Criteria criteria = example.createCriteria().andBorderIdEqualTo(borderId).andTypeEqualTo(type);
            if(cadreIds!=null && cadreIds.length>0){ // 保留重复添加的干部，删除此次不包含的干部
                criteria.andCadreIdNotIn(Arrays.asList(cadreIds));
            }
            scBorderItemMapper.deleteByExample(example);
        }
        // 已添加的干部
        Set<Integer> addedCadreIdSet = new HashSet<>();
        {
            ScBorderItemExample example = new ScBorderItemExample();
            example.createCriteria().andBorderIdEqualTo(borderId).andTypeEqualTo(type).andCadreIdIn(Arrays.asList(cadreIds));
            List<ScBorderItem> scBorderItems = scBorderItemMapper.selectByExample(example);
            for (ScBorderItem scBorderItem : scBorderItems) {
                addedCadreIdSet.add(scBorderItem.getCadreId());
            }
        }

        for (Integer cadreId : cadreIds) {
            if(!addedCadreIdSet.contains(cadreId)){

                ScBorderItem record = new ScBorderItem();
                record.setBorderId(borderId);
                record.setType(type);
                record.setCadreId(cadreId);
                CadreView cv = CmTag.getCadreById(cadreId);
                record.setTitle(cv.getTitle());
                record.setAdminLevel(cv.getAdminLevel());

                scBorderItemMapper.insertSelective(record);
            }
        }

    }
}
