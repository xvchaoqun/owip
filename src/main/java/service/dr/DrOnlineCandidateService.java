package service.dr;

import domain.dr.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrOnlineCandidateService extends DrBaseMapper {

    @Autowired
    private DrOnlinePostService drOnlinePostService;

    //todo 更新推荐职务中的候选人需要测试
    @Transactional
    public List<Integer> insertCandidate(Integer postId, Integer[] candidates){

        List<Integer> candidateIds = new ArrayList<>();
        if (postId != null){
            for (Integer userId: candidates){
                DrOnlineCandidate candidate = new DrOnlineCandidate();
                candidate.setUserId(userId);
                candidate.setPostId(postId);
                candidate.setSortOrder(getNextSortOrder("dr_online_candidate", null));
                drOnlineCandidateMapper.insert(candidate);

                candidateIds.add(candidate.getId());
            }
        }
        //更新推荐职务中的候选人
        DrOnlinePost record = drOnlinePostService.findAll().get(postId);
        String _candidateIds = record.getCandidates();
        if (StringUtils.isBlank(_candidateIds)){
            _candidateIds = StringUtils.join(candidateIds, ",");
        }else {
            _candidateIds += ("," + StringUtils.join(candidateIds, ","));
        }
        record.setCandidates(_candidateIds);
        drOnlinePostMapper.updateByPrimaryKey(record);

        return candidateIds;
    }

    @Transactional
    public void updateCandidate(DrOnlinePost record, Integer[] candidates){

        deleteCandidate(record.getId());

        if (candidates != null)
            insertCandidate(record.getId(), candidates);
    }

    @Transactional
    public void deleteCandidate(Integer postId){
        List<Integer> ids = getCandidateIds(postId);

        for (Integer id : ids){
            drOfflineCandidateMapper.deleteByPrimaryKey(id);
        }
    }

    public List<Integer> getCandidateIds(Integer postId){

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        example.createCriteria().andPostIdEqualTo(postId);
        List<DrOnlineCandidate> candidates = drOnlineCandidateMapper.selectByExample(example);

        List<Integer> candidateIds = new ArrayList<>();

        if (candidates.size() > 0) {
            for (DrOnlineCandidate drOnlineCandidate : candidates) {
                candidateIds.add(drOnlineCandidate.getId());
            }
        }
        return candidateIds;
    }

    public Map<Integer, List<DrOnlineCandidate>> findAll(Integer onlineId){

        Map<Integer, List<DrOnlineCandidate>> candidateMap = new HashMap<>();

        DrOnlinePostViewExample example = new DrOnlinePostViewExample();
        example.createCriteria().andOnlineIdEqualTo(onlineId);
        example.setOrderByClause("id desc");
        List<DrOnlinePostView> postViews = drOnlinePostViewMapper.selectByExample(example);

        for (DrOnlinePostView postView : postViews){

            DrOnlineCandidateExample candidateExample = new DrOnlineCandidateExample();
            candidateExample.setOrderByClause("id desc");
            candidateExample.createCriteria().andPostIdEqualTo(postView.getId());
            List<DrOnlineCandidate> candidates = drOnlineCandidateMapper.selectByExample(candidateExample);

            candidateMap.put(postView.getId(), candidates);
        }

        return candidateMap;
    }

    public Integer getId(Integer userId, Integer postId){

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        example.createCriteria().andUserIdEqualTo(userId).andPostIdEqualTo(postId);
        List<DrOnlineCandidate> candidates = drOnlineCandidateMapper.selectByExample(example);

        return candidates.size() > 0 ? candidates.get(0).getId() : null;
    }

    //返回candidateIds
    public List<Integer> insertOther(Integer postId, String userCodes){

        int count = 0;
        String[] users = userCodes.split(",");
        Integer[] userIds = new Integer[users.length];
        for (String user : users){
            String _code = (user.split("\\("))[1];
            String code = _code.substring(0, _code.length()-1);
            Integer userId = CmTag.getUserByCode(code).getUserId();
            if (getId(userId, postId) == null){
                userIds[count++] = userId;
            }
        }

        return insertCandidate(postId, userIds);
    }
}
