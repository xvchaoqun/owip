package service.dr;

import domain.dr.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.utils.NumberUtils;

import java.util.*;

@Service
public class DrOnlinePostService extends DrBaseMapper {

    @Transactional
    public void insertSelective(DrOnlinePost record){

        record.setSortOrder(getNextSortOrder("dr_online_post", null));
        drOnlinePostMapper.insertSelective(record);

    }

    @Transactional
    public void del(Integer id){

        drOnlinePostMapper.deleteByPrimaryKey(id);
    }

    /*
        删除候选人
        删除结果
        删除岗位
    */
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        int count = 0;

        //候选人、结果、岗位
        DrOnlineCandidateExample candidateExample = new DrOnlineCandidateExample();
        candidateExample.createCriteria().andPostIdIn(Arrays.asList(ids));
        drOnlineCandidateMapper.deleteByExample(candidateExample);

        DrOnlineResultExample resultExample = new DrOnlineResultExample();
        resultExample.createCriteria().andPostIdIn(Arrays.asList(ids));
        drOnlineResultMapper.deleteByExample(resultExample);

        DrOnlinePostExample example = new DrOnlinePostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlinePostMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DrOnlinePost record){

        drOnlinePostMapper.updateByPrimaryKeySelective(record);

    }

    public Map<Integer, DrOnlinePost> findAll() {

        DrOnlinePostExample example = new DrOnlinePostExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DrOnlinePost> records = drOnlinePostMapper.selectByExample(example);
        Map<Integer, DrOnlinePost> map = new LinkedHashMap<>();
        for (DrOnlinePost record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        DrOnlinePost drOnlinePost = drOnlinePostMapper.selectByPrimaryKey(id);
        changeOrder("dr_online_post", "online_id=" + drOnlinePost.getOnlineId(), ORDER_BY_DESC, id, addNum);
    }

    public DrOnlinePost getPost(Integer id){

        DrOnlinePostExample example = new DrOnlinePostExample();
        example.createCriteria().andIdEqualTo(id);
        List<DrOnlinePost> drOnlinePosts = drOnlinePostMapper.selectByExample(example);

        return drOnlinePosts.size() > 0 ? drOnlinePosts.get(0) : null;

    }

    public List<Integer> getByUnitPostId(Integer onlineId, Integer unitPostId){

        DrOnlinePostExample example = new DrOnlinePostExample();
        example.createCriteria().andOnlineIdEqualTo(onlineId).andUnitPostIdEqualTo(unitPostId);
        List<DrOnlinePost> drOnlinePosts = drOnlinePostMapper.selectByExample(example);
        List<Integer> ids = new ArrayList<>();
        if (drOnlinePosts.size() > 0){
            for (DrOnlinePost record : drOnlinePosts) {
                ids.add(record.getId());
            }
        }
        return ids;
    }

    public List<DrOnlinePost> getAllByOnlineId(Integer onlineId){

        DrOnlinePostExample example = new DrOnlinePostExample();
        example.createCriteria().andOnlineIdEqualTo(onlineId);
        example.setOrderByClause("sort_order desc");
        List<DrOnlinePost> postViews = drOnlinePostMapper.selectByExample(example);

        return postViews;
    }

    public List<DrOnlinePost> getNeedRecommend(DrOnlineInspector inspector){

        DrOnlineInspectorLog inspectorLog = drOnlineInspectorLogMapper.selectByPrimaryKey(inspector.getLogId());
        String[] postIds = StringUtils.split(inspectorLog.getPostIds(), ",");

        DrOnlinePostExample example = new DrOnlinePostExample();
        DrOnlinePostExample.Criteria criteria = example.createCriteria().andOnlineIdEqualTo(inspector.getOnlineId());
        if (postIds != null && postIds.length > 0){
            List<Integer> _postIds = new ArrayList<>();
            for (String postId : postIds) {
                _postIds.add(Integer.parseInt(postId));
            }
            criteria.andIdIn(_postIds);
        }
        example.setOrderByClause("sort_order desc");//排序和管理员页面显示一致
        List<DrOnlinePost> postViews = drOnlinePostMapper.selectByExample(example);

        return postViews;
    }

    //判断推荐职务是否超额推荐
    public Boolean checkCandidateNum(Integer postId){

        DrOnlinePostExample example = new DrOnlinePostExample();
        example.createCriteria().andIdEqualTo(postId);

        List<DrOnlinePost> posts = drOnlinePostMapper.selectByExample(example);
        if (null != posts && posts.size() > 0){
            DrOnlinePost post = posts.get(0);

            Set<Integer> candidateIds = NumberUtils.toIntSet(post.getCandidates(), ",");

            return candidateIds.size() < post.getHeadCount();
        }

        return false;
    }

    //更新岗位的candidates
    @Transactional
    public void updateCandidates(Integer postId){

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        example.createCriteria().andPostIdEqualTo(postId);
        example.setOrderByClause(" sort_order desc");
        List<DrOnlineCandidate> records = drOnlineCandidateMapper.selectByExample(example);

        String candidates = null;
        List<Integer> ids = new ArrayList<>();
        if (null != records && records.size() > 0){
            for (DrOnlineCandidate record : records){
                ids.add(record.getId());
            }
            candidates = StringUtils.join(ids, ",");
        }

        if (StringUtils.isBlank(candidates)){
            commonMapper.excuteSql("update dr_online_post set candidates=null where id=" + postId);
        }else {
            DrOnlinePost post = new DrOnlinePost();
            post.setId(postId);
            post.setCandidates(candidates);
            updateByPrimaryKeySelective(post);
        }
    }
}
