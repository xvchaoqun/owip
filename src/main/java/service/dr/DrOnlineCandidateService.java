package service.dr;

import domain.dr.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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

    /*
        删除候选人
        删除结果
    */
    @Transactional
    public void del(Integer id){

        DrOnlineCandidate record = drOnlineCandidateMapper.selectByPrimaryKey(id);
        Integer postId = record.getPostId();

        drOnlineCandidateMapper.deleteByPrimaryKey(id);
        drOnlinePostService.updateCandidates(postId);

        DrOnlineResultExample example =new DrOnlineResultExample();
        example.createCriteria().andPostIdEqualTo(postId).andUserIdEqualTo(record.getUserId());
        drOnlineResultMapper.deleteByExample(example);
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

        List<DrOnlinePostView> postViews = drOnlinePostService.getAllByOnlineId(onlineId);

        for (DrOnlinePostView postView : postViews){

            Integer postId = postView.getId();

            List<DrOnlineCandidate> candidateList = getByPostId(postId);
            candidateMap.put(postId, candidateList);
        }

        return candidateMap;
    }

    public DrOnlineCandidate getId(Integer userId, Integer postId){

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        example.createCriteria().andUserIdEqualTo(userId).andPostIdEqualTo(postId);
        List<DrOnlineCandidate> candidates = drOnlineCandidateMapper.selectByExample(example);

        return candidates.size() > 0 ? candidates.get(0) : null;
    }

    public List<DrOnlineCandidate> getByPostId(Integer postId){

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        example.createCriteria().andPostIdEqualTo(postId);
        example.setOrderByClause("sort_order desc");

        return drOnlineCandidateMapper.selectByExample(example);
    }

    //插入候选人
    @Transactional
    public DrOnlineCandidate insert(Integer postId, Integer userId){

        DrOnlineCandidate record = new DrOnlineCandidate();
        record.setPostId(postId);
        record.setUserId(userId);
        record.setCandidate(CmTag.getUserById(userId).getRealname());
        record.setSortOrder(getNextSortOrder("dr_online_candidate", null));
        drOnlineCandidateMapper.insert(record);

        drOnlinePostService.updateCandidates(postId);

        return drOnlineCandidateMapper.selectByPrimaryKey(record.getId());
    }

    @Transactional
    public void update(DrOnlineCandidate record) {

        drOnlineCandidateMapper.updateByPrimaryKeySelective(record);

        DrOnlineCandidate candidate = drOnlineCandidateMapper.selectByPrimaryKey(record.getId());
        DrOnlineResultExample example = new DrOnlineResultExample();
        example.createCriteria().andPostIdEqualTo(candidate.getPostId()).andUserIdEqualTo(candidate.getUserId());

        DrOnlineResult result = new DrOnlineResult();
        result.setCandidate(candidate.getCandidate());
        drOnlineResultMapper.updateByExampleSelective(result, example);

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

    //判断候选人是否重复添加
    public Boolean checkDuplicate(Integer userId, Integer postId){

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        example.createCriteria().andUserIdEqualTo(userId).andPostIdEqualTo(postId);
        List<DrOnlineCandidate> candidates = drOnlineCandidateMapper.selectByExample(example);

        if (null != candidates && candidates.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        DrOnlineCandidate record = drOnlineCandidateMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = record.getSortOrder();
        Integer postId = record.getPostId();

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        if (addNum > 0) {

            example.createCriteria().andPostIdEqualTo(postId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andPostIdEqualTo(postId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DrOnlineCandidate> overRecords = drOnlineCandidateMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overRecords.size() > 0) {

            DrOnlineCandidate targetEntity = overRecords.get(overRecords.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("dr_online_candidate", "post_id=" + record.getPostId(), baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("dr_online_candidate", "post_id=" + record.getPostId(), baseSortOrder, targetEntity.getSortOrder());

            DrOnlineCandidate _record = new DrOnlineCandidate();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            drOnlineCandidateMapper.updateByPrimaryKeySelective(record);
        }

        drOnlinePostService.updateCandidates(postId);
    }
}
