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
    public void updateCadreIds(int approverTypeId, Integer[] cadreIds){

        ApproverBlackListExample example = new ApproverBlackListExample();
        example.createCriteria().andApproverTypeIdEqualTo(approverTypeId);
        approverBlackListMapper.deleteByExample(example);

        if(cadreIds==null || cadreIds.length==0) return ;

        for (Integer cadreId : cadreIds) {

            ApproverBlackList record = new ApproverBlackList();
            record.setApproverTypeId(approverTypeId);
            record.setCadreId(cadreId);
            approverBlackListMapper.insert(record);
        }
    }

    // key: cadreId
    @Cacheable(value="ApproverBlackList", key = "#approverTypeId")
    public Map<Integer, ApproverBlackList> findAll(int approverTypeId) {

        ApproverBlackListExample example = new ApproverBlackListExample();
        example.createCriteria().andApproverTypeIdEqualTo(approverTypeId);
        List<ApproverBlackList> records = approverBlackListMapper.selectByExample(example);
        Map<Integer, ApproverBlackList> map = new LinkedHashMap<>();
        for (ApproverBlackList record : records) {
            map.put(record.getCadreId(), record);
        }
        return map;
    }
}
