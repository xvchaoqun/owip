package service.dispatch;

import domain.DispatchCadreRelate;
import domain.DispatchCadreRelateExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DispatchCadreRelateService extends BaseMapper {

    @Autowired
    private DispatchService dispatchService;

    // 删除已关联的干部发文
    public int delDispatchCadreRelates(List<Integer> relateIds, byte relateType){

        DispatchCadreRelateExample example = new DispatchCadreRelateExample();
        example.createCriteria().andRelateIdIn(relateIds).andRelateTypeEqualTo(relateType);
        return dispatchCadreRelateMapper.deleteByExample(example);
    }

    // 查找已关联的干部发文
    public List<DispatchCadreRelate> findDispatchCadreRelates(int relateId, byte relateType){

        DispatchCadreRelateExample example = new DispatchCadreRelateExample();
        example.createCriteria().andRelateIdEqualTo(relateId).andRelateTypeEqualTo(relateType);
        return dispatchCadreRelateMapper.selectByExample(example);
    }

    // 查找模块内其他记录已关联的干部发文 Set<dispatchCadreId>
    public Set<Integer> findOtherDispatchCadreRelateSet(int relateId, byte relateType){

        DispatchCadreRelateExample example = new DispatchCadreRelateExample();
        example.createCriteria().andRelateIdNotEqualTo(relateId).andRelateTypeEqualTo(relateType);
        List<DispatchCadreRelate> dispatchCadreRelates = dispatchCadreRelateMapper.selectByExample(example);
        Set<Integer> cadreDispatchIdSet = new HashSet<>();
        for (DispatchCadreRelate dispatchCadreRelate : dispatchCadreRelates) {
            cadreDispatchIdSet.add(dispatchCadreRelate.getDispatchCadreId());
        }
        return cadreDispatchIdSet;
    }

    // 更新关联的干部发文
    @Transactional
    public void updateDispatchCadreRelates(int relateId, byte relateType, Integer[] ids){

        // 先删除
        DispatchCadreRelateExample example = new DispatchCadreRelateExample();
        example.createCriteria().andRelateIdEqualTo(relateId).andRelateTypeEqualTo(relateType);
        dispatchCadreRelateMapper.deleteByExample(example);

        if(ids!=null && ids.length>0) {
            for (Integer id : ids) {
                DispatchCadreRelate record = new DispatchCadreRelate();
                record.setDispatchCadreId(id);
                record.setRelateId(relateId);
                record.setRelateType(relateType);
                dispatchCadreRelateMapper.insertSelective(record);
            }
        }
    }
}
