package service.dr;

import domain.dr.*;
import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.tags.CmTag;

import java.io.IOException;
import java.util.*;

@Service
public class DrOnlineCandidateService extends DrBaseMapper {

    @Autowired
    private DrOnlinePostService drOnlinePostService;

    //todo 将候选人表没有的候选人更新,其他表不受影响
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
        return candidateIds;
    }

    //根据postId来删除候选人
    @Transactional
    public void deleteCandidate(Integer postId){

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        example.createCriteria().andPostIdEqualTo(postId);
        drOnlineCandidateMapper.deleteByExample(example);
    }

    //<postId,List<DrOnlineCandidate>> --参评人
    public Map<Integer, List<DrOnlineCandidate>> findAll(Integer onlineId){

        Map<Integer, List<DrOnlineCandidate>> candidateMap = new HashMap<>();

        DrOnlinePostViewExample example = new DrOnlinePostViewExample();
        example.createCriteria().andOnlineIdEqualTo(onlineId);
        example.setOrderByClause("id desc");
        List<DrOnlinePostView> postViews = drOnlinePostViewMapper.selectByExample(example);

        for (DrOnlinePostView postView : postViews){
            List<DrOnlineCandidate> candidateList = new ArrayList<>();
            DrOnlinePost post = drOnlinePostMapper.selectByPrimaryKey(postView.getId());
            String candidates = post.getCandidates();
            if (StringUtils.isNotEmpty(candidates)) {
                String[] canIds = candidates.split(",");

                for (String canId : canIds) {
                    DrOnlineCandidate candidate = drOnlineCandidateMapper.selectByPrimaryKey(Integer.valueOf(canId));
                    candidateList.add(candidate);
                }
            }

            candidateMap.put(postView.getId(), candidateList);
        }

        return candidateMap;
    }

    public Integer getId(Integer userId, Integer postId){

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        example.createCriteria().andUserIdEqualTo(userId).andPostIdEqualTo(postId);
        List<DrOnlineCandidate> candidates = drOnlineCandidateMapper.selectByExample(example);

        return candidates.size() > 0 ? candidates.get(0).getId() : null;
    }

    //添加推荐人， 返回的candidateIds     --参评人
    public List<Integer> insertOther(Integer postId, String userCodes){

        int count = 0;
        List<Integer> candidateIds = new ArrayList<>();
        Integer candidateId = null;
        String[] users = userCodes.split("-");
        Integer[] userIds = new Integer[users.length];
        for (String user : users){
            String _code = (user.split("\\("))[1];
            String code = _code.substring(0, _code.length()-1);
            Integer userId = CmTag.getUserByCode(code).getUserId();
            candidateId = getId(userId, postId);
            if (null == candidateId){
                userIds[count++] = userId;
            }else {
                candidateIds.add(candidateId);
            }
        }
        //如果有新增，则进行从插入，求并集操作
        if (count > 0)
            candidateIds.addAll(insertCandidate(postId, userIds));

        return candidateIds;
    }

    //只能根据post表取candidate
    public Map<Integer, DrOnlineCandidate> getByPostId(Integer postId){

        DrOnlinePost post = drOnlinePostMapper.selectByPrimaryKey(postId);
        String candidates = post.getCandidates();
        Map<Integer, DrOnlineCandidate> candidateMap = new HashMap<>();
        if (StringUtils.isNotEmpty(candidates)) {
            String[] canIds = candidates.split(",");

            for (String canId : canIds) {
                DrOnlineCandidate candidate = drOnlineCandidateMapper.selectByPrimaryKey(Integer.valueOf(canId));
                candidateMap.put(candidate.getUserId(), candidate);
            }
        }
        return candidateMap;
    }

    //无候选人时，备选名单  --管理员、参评人
    public List<String> sysUser_selects(Byte type) throws IOException {

        SysUserViewExample example = new SysUserViewExample();
        SysUserViewExample.Criteria criteria = example.createCriteria();

        if (type != null){
            criteria.andTypeEqualTo(type);
        }

        List<SysUserView> uvs = sysUserViewMapper.selectByExample(example);
        List<String> options = new ArrayList<String>();
        String nameCode = null;
        for (SysUserView uv : uvs){
            nameCode = uv.getRealname() + "(" + uv.getCode() + ")";
            options.add(nameCode);
        }

        return options;
    }

    //插入候选人
    @Transactional
    public DrOnlineCandidate insert(Integer postId, Integer userId){

        DrOnlineCandidate record = new DrOnlineCandidate();
        if (postId != null){
            record.setPostId(postId);
        }
        record.setSign((byte) 1);
        record.setUserId(userId);
        record.setSortOrder(getNextSortOrder("dr_online_candidate", null));
        drOnlineCandidateMapper.insert(record);

        return drOnlineCandidateMapper.selectByPrimaryKey(record.getId());
    }

    @Transactional
    public void addPostId(Integer postId, Map<Integer, DrOnlineCandidate> drOnlineCandidateMap){

        for (Map.Entry<Integer, DrOnlineCandidate> entry : drOnlineCandidateMap.entrySet()){
            DrOnlineCandidate record = drOnlineCandidateMapper.selectByPrimaryKey(entry.getValue().getId());
            record.setId(entry.getValue().getId());
            record.setPostId(postId);
            drOnlineCandidateMapper.updateByPrimaryKeySelective(record);
        }
    }

    //生成回显的候选人sysUser
    public List<SysUserView> getCandidates(Integer postId){

        DrOnlinePost post = drOnlinePostMapper.selectByPrimaryKey(postId);
        String candidateStr = post.getCandidates();
        if (StringUtils.isNotEmpty(candidateStr)) {
            String[] canIds = candidateStr.split(",");

            List<SysUserView> candidates = new ArrayList<>();
            for (String canId : canIds) {
                DrOnlineCandidate candidate = drOnlineCandidateMapper.selectByPrimaryKey(Integer.valueOf(canId));
                candidates.add(candidate.getUser());
            }

            return candidates;
        }else {
            return null;
        }
    }
}
