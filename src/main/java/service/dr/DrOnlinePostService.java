package service.dr;

import domain.dr.*;
import domain.sys.SysUserView;
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

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids){
            drOnlineCandidateService.deleteCandidate(id);
        }

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

    @Transactional
    public void insertPost(DrOnlinePost record, List<SysUserView> uvs){

        insertSelective(record);

        if (record.getHasCandidate() && uvs.size() > 0){

            Integer postId = record.getId();
            Integer userId = null;
            List<Integer> canIds = new ArrayList<>();

            for (SysUserView uv : uvs){
                userId = uv.getUserId();
                DrOnlineCandidate candidate = drOnlineCandidateService.insert(postId, userId);
                canIds.add(candidate.getId());
            }

            DrOnlinePost post = new DrOnlinePost();
            post.setId(postId);
            post.setCandidates(StringUtils.join(canIds,","));
            drOnlinePostMapper.updateByPrimaryKeySelective(post);
        }
    }

    @Transactional
    public void updatePost(DrOnlinePost record, List<SysUserView> uvs){

        Integer postId = record.getId();

        if (record.getHasCandidate() && uvs.size() > 0){
            Map<Integer, DrOnlineCandidate> hadExist = drOnlineCandidateService.getByPostId(postId);
            for (SysUserView uv : uvs){
                if (hadExist.size() == 0){
                    drOnlineCandidateService.insert(postId, uv.getUserId());
                }else {
                    if (hadExist.containsKey(uv.getUserId())) {
                        hadExist.remove(uv.getUserId());
                    } else {
                        drOnlineCandidateService.insert(postId, uv.getUserId());
                    }
                }
            }

            //删除不需要的候选人
            if (hadExist.size() > 0) {
                for (Map.Entry<Integer, DrOnlineCandidate> entry : hadExist.entrySet()) {
                    drOnlineCandidateMapper.deleteByPrimaryKey(entry.getValue().getId());
                }
            }
            List<Integer> candidateIds = new ArrayList<>();
            Integer candidateId = null;
            for (SysUserView uv : uvs){
                candidateId = drOnlineCandidateService.getId(uv.getUserId(), postId);
                candidateIds.add(candidateId);
            }
            record.setCandidates(StringUtils.join(candidateIds, ","));
            drOnlinePostMapper.updateByPrimaryKeySelective(record);
        }else {
            record.setCandidates("");
            drOnlinePostMapper.updateByPrimaryKeySelective(record);
            drOnlineCandidateService.deleteCandidate(postId);
        }
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
        List<DrOnlinePostView> postViews = drOnlinePostViewMapper.selectByExample(example);

        return postViews;
    }
}
