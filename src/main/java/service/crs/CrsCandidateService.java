package service.crs;

import domain.crs.CrsCandidate;
import domain.crs.CrsCandidateExample;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.List;

@Service
public class CrsCandidateService extends BaseMapper {


    public Integer getUserId(int postId, boolean isFirst){

        CrsCandidateExample example = new CrsCandidateExample();
        example.createCriteria().andPostIdEqualTo(postId)
                .andIsFirstEqualTo(isFirst);
        List<CrsCandidate> crsCandidates = crsCandidateMapper.selectByExample(example);

        if(crsCandidates.size()>0) return crsCandidates.get(0).getUserId();
        return null;
    }
}
