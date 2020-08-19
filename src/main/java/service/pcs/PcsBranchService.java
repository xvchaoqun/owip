package service.pcs;

import domain.pcs.PcsBranch;
import domain.pcs.PcsBranchExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class PcsBranchService extends PcsBaseMapper {

    public PcsBranch get(int configId, int partyId, Integer branchId) {

        PcsBranchExample example = new PcsBranchExample();
        PcsBranchExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId);
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        List<PcsBranch> pcsBranchs = pcsBranchMapper.selectByExample(example);

        return pcsBranchs.size() == 0 ? null : pcsBranchs.get(0);
    }

    @Transactional
    public void exclude(Integer[] ids ,Boolean isDeleted){

        if(ids==null || ids.length==0) return;

        PcsBranch record =new PcsBranch();
        record.setIsDeleted(isDeleted);

        PcsBranchExample example = new PcsBranchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsBranchMapper.updateByExampleSelective(record,example);

    }
}
