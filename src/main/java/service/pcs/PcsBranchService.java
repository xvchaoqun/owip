package service.pcs;

import domain.pcs.PcsBranch;
import domain.pcs.PcsBranchExample;
import org.springframework.stereotype.Service;

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
}
