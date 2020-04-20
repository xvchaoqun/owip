package service.dr;

import domain.dr.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DrOnlinePostService extends DrBaseMapper {

    @Autowired
    private DrOnlineCandidateService drOnlineCandidateService;

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

        changeOrder("dr_online_post", null, ORDER_BY_DESC, id, addNum);
    }

    public DrOnlinePostView getPost(Integer id){

        DrOnlinePostViewExample example = new DrOnlinePostViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<DrOnlinePostView> drOnlinePostViews = drOnlinePostViewMapper.selectByExample(example);

        return drOnlinePostViews.size() > 0 ? drOnlinePostViews.get(0) : null;

    }

    public List<DrOnlinePostView> getAllByOnlineId(Integer onlineId){

        DrOnlinePostViewExample example = new DrOnlinePostViewExample();
        example.createCriteria().andOnlineIdEqualTo(onlineId);
        example.setOrderByClause("id desc");
        List<DrOnlinePostView> postViews = drOnlinePostViewMapper.selectByExample(example);

        return postViews;
    }

    //判断推荐职务是否超额推荐
    public Boolean checkCandidateNum(Integer postId){

        DrOnlinePostViewExample example = new DrOnlinePostViewExample();
        example.createCriteria().andIdEqualTo(postId);

        List<DrOnlinePostView> posts = drOnlinePostViewMapper.selectByExample(example);
        if (null != posts && posts.size() > 0){
            DrOnlinePostView post = posts.get(0);
            return post.getExistNum() < post.getCompetitiveNum();
        }

        return false;
    }

    //更新岗位的candidates
    @Transactional
    public void updateCandidates(Integer postId){

        DrOnlineCandidateExample example = new DrOnlineCandidateExample();
        DrOnlineCandidateExample.Criteria criteria = example.createCriteria().andPostIdEqualTo(postId);
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

        DrOnlinePost post = new DrOnlinePost();
        post.setId(postId);
        post.setHasCandidate(null != candidates);
        post.setCandidates(candidates);
        updateByPrimaryKeySelective(post);
    }
}
