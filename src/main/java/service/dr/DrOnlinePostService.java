package service.dr;

import domain.dr.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrOnlinePostService extends DrBaseMapper {

    @Autowired
    private DrOnlineCandidateService drOnlineCandidateService;

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DrOnlinePostExample example = new DrOnlinePostExample();
        DrOnlinePostExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return drOnlinePostMapper.countByExample(example) > 0;
    }

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

        for (Integer id : ids){
            drOnlineCandidateService.deleteCandidate(id);
        }

        if(ids==null || ids.length==0) return;

        DrOnlinePostExample example = new DrOnlinePostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlinePostMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DrOnlinePost record, Integer[] candidates){

        drOnlineCandidateService.updateCandidate(record, candidates);
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
        List<DrOnlinePostView> postViews = drOnlinePostViewMapper.selectByExample(example);

        return postViews;
    }
}
