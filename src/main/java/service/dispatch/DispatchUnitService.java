package service.dispatch;

import domain.base.MetaType;
import domain.dispatch.DispatchUnit;
import domain.dispatch.DispatchUnitExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.tags.CmTag;

import java.util.Arrays;

@Service
public class DispatchUnitService extends BaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DispatchUnitExample example = new DispatchUnitExample();
        DispatchUnitExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dispatchUnitMapper.countByExample(example) > 0;
    }*/

    @Transactional
    public int insertSelective(DispatchUnit record){

        return dispatchUnitMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        dispatchUnitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DispatchUnitExample example = new DispatchUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dispatchUnitMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(DispatchUnit record){
    
        Integer type = record.getType();
        MetaType metaType = CmTag.getMetaType(type);
        String extraAttr = metaType.getExtraAttr();
        if(StringUtils.equals(extraAttr, "add")){
            commonMapper.excuteSql("update dispatch_unit set old_unit_id=null where id=" + record.getId());
        }else if(StringUtils.equals(extraAttr, "delete")){
            commonMapper.excuteSql("update dispatch_unit set unit_id=null where id=" + record.getId());
        }
    
        return dispatchUnitMapper.updateByPrimaryKeySelective(record);
    }
}
