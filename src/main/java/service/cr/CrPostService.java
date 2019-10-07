package service.cr;

import domain.cr.CrInfo;
import domain.cr.CrPost;
import domain.cr.CrPostExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrPostService extends CrBaseMapper {

    public CrPost get(int id) {

        return crPostMapper.selectByPrimaryKey(id);
    }

    public List<CrPost> get(List<Integer> ids) {

        CrPostExample example = new CrPostExample();
        example.createCriteria().andIdIn(ids);
        return crPostMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(CrPost record) {

        record.setCreateTime(new Date());
        record.setSortOrder(getNextSortOrder("cr_post", "info_id=" + record.getInfoId()));

        crPostMapper.insertSelective(record);

        refreshInfoNum(record.getInfoId());
    }

    @Transactional
    public void updateByPrimaryKeySelective(CrPost record) {

        crPostMapper.updateByPrimaryKeySelective(record);

        refreshInfoNum(record.getInfoId());
    }

    public List<CrPost> getPosts(int infoId){

        CrPostExample example = new CrPostExample();
        example.createCriteria().andInfoIdEqualTo(infoId);
        example.setOrderByClause("sort_order asc");

        return crPostMapper.selectByExample(example);
    }

    public Map<Integer, CrPost> getPostMap(int infoId){

        List<CrPost> posts = getPosts(infoId);
        Map<Integer, CrPost> postMap = new HashMap<>();
        for (CrPost post : posts) {
            postMap.put(post.getId(), post);
        }

        return postMap;
    }

    public void refreshInfoNum(int infoId){

        List<CrPost> crPosts = getPosts(infoId);
        int postNum = 0;
        int requireNum = 0;
        for (CrPost crPost : crPosts) {
            postNum ++;
            requireNum += crPost.getNum();
        }

        CrInfo record = new CrInfo();
        record.setId(infoId);
        record.setPostNum(postNum);
        record.setRequireNum(requireNum);

        crInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            CrPost crPost = crPostMapper.selectByPrimaryKey(id);

            crPostMapper.deleteByPrimaryKey(id);
            refreshInfoNum(crPost.getInfoId());
        }
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        CrPost crPost = crPostMapper.selectByPrimaryKey(id);
        changeOrder("cr_post", "info_id=" + crPost.getInfoId(), ORDER_BY_ASC, id, addNum);
    }

    public CrPost getByName(int infoId, String name){

        CrPostExample example = new CrPostExample();
        example.createCriteria().andInfoIdEqualTo(infoId).andNameEqualTo(name.trim());
        List<CrPost> crPosts = crPostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return crPosts.size()==1?crPosts.get(0):null;
    }

    @Transactional
    public int bacthImport(List<CrPost> records) {

        int addCount = 0;
        for (CrPost record : records) {

            CrPost crPost = getByName(record.getInfoId(), record.getName());
            if (crPost == null) {
                insertSelective(record);
                addCount++;
            } else {
                record.setId(crPost.getId());
                updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
    }
}
