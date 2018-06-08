package service.cla;

import domain.cla.ClaApproverBlackList;
import domain.cla.ClaApproverBlackListExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClaApproverBlackListService extends BaseMapper {

    @Transactional
    @CacheEvict(value="ClaApproverBlackList", key = "#approverTypeId")
    public void updateCadreIds(int approverTypeId, Integer[] cadreIds){

        ClaApproverBlackListExample example = new ClaApproverBlackListExample();
        example.createCriteria().andApproverTypeIdEqualTo(approverTypeId);
        claApproverBlackListMapper.deleteByExample(example);

        if(cadreIds==null || cadreIds.length==0) return ;

        for (Integer cadreId : cadreIds) {

            ClaApproverBlackList record = new ClaApproverBlackList();
            record.setApproverTypeId(approverTypeId);
            record.setCadreId(cadreId);
            claApproverBlackListMapper.insert(record);
        }
    }

    // key: cadreId
    @Cacheable(value="ClaApproverBlackList", key = "#approverTypeId")
    public Map<Integer, ClaApproverBlackList> findAll(int approverTypeId) {

        ClaApproverBlackListExample example = new ClaApproverBlackListExample();
        example.createCriteria().andApproverTypeIdEqualTo(approverTypeId);
        List<ClaApproverBlackList> records = claApproverBlackListMapper.selectByExample(example);
        Map<Integer, ClaApproverBlackList> map = new LinkedHashMap<>();
        for (ClaApproverBlackList record : records) {
            map.put(record.getCadreId(), record);
        }
        return map;
    }
}
