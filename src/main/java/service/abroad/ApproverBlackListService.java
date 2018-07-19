package service.abroad;

import domain.abroad.ApproverBlackList;
import domain.abroad.ApproverBlackListExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/6/24.
 */
@Service
public class ApproverBlackListService extends BaseMapper {

    @Transactional
    @CacheEvict(value="ApproverBlackList", key = "#approverTypeId")
    public void updateCadreIds(int approverTypeId, String[] cadreIdUnitIds){

        ApproverBlackListExample example = new ApproverBlackListExample();
        example.createCriteria().andApproverTypeIdEqualTo(approverTypeId);
        approverBlackListMapper.deleteByExample(example);

        if(cadreIdUnitIds==null || cadreIdUnitIds.length==0) return ;

        for (String cadreIdUnitId : cadreIdUnitIds) {

            String[] _array = cadreIdUnitId.split("_");
            ApproverBlackList record = new ApproverBlackList();
            record.setApproverTypeId(approverTypeId);
            record.setCadreId(Integer.valueOf(_array[0]));
            record.setUnitId(Integer.valueOf(_array[1]));
            approverBlackListMapper.insert(record);
        }
    }

    // key: cadreId_unitId
    @Cacheable(value="ApproverBlackList", key = "#approverTypeId")
    public Map<String, ApproverBlackList> findAll(int approverTypeId) {

        ApproverBlackListExample example = new ApproverBlackListExample();
        example.createCriteria().andApproverTypeIdEqualTo(approverTypeId);
        List<ApproverBlackList> records = approverBlackListMapper.selectByExample(example);
        Map<String, ApproverBlackList> map = new LinkedHashMap<>();
        for (ApproverBlackList record : records) {
            map.put(record.getCadreId()+"_"+record.getUnitId(), record);
        }

        return map;
    }
}
