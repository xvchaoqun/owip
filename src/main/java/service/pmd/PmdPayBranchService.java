package service.pmd;

import domain.pmd.PmdPayBranch;
import domain.pmd.PmdPayBranchExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmdPayBranchService extends BaseMapper {

    // <BranchId, PmdPayBranch>
    public Map<Integer, PmdPayBranch> getAllPayBranchIdSet(Integer partyId){

        Map<Integer, PmdPayBranch> resultMap = new HashMap<>();
        PmdPayBranchExample example = new PmdPayBranchExample();
        if(partyId!=null){
            example.createCriteria().andPartyIdEqualTo(partyId);
        }
        List<PmdPayBranch> pmdPayParties = pmdPayBranchMapper.selectByExample(example);

        for (PmdPayBranch pmdPayBranch : pmdPayParties) {

            resultMap.put(pmdPayBranch.getBranchId(), pmdPayBranch);
        }

        return resultMap;
    }

    @Transactional
    public void insertSelective(PmdPayBranch record) {

        pmdPayBranchMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer partyId) {

        pmdPayBranchMapper.deleteByPrimaryKey(partyId);
    }

    @Transactional
    public void batchDel(Integer[] partyIds) {

        if (partyIds == null || partyIds.length == 0) return;

        PmdPayBranchExample example = new PmdPayBranchExample();
        example.createCriteria().andPartyIdIn(Arrays.asList(partyIds));
        pmdPayBranchMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdPayBranch record) {

        return pmdPayBranchMapper.updateByPrimaryKeySelective(record);
    }
}
